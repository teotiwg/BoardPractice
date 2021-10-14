/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kr.co.edsk.mvc.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import kr.co.edsk.mvc.service.BoardVO;
import kr.co.edsk.mvc.service.BoardService;
import kr.co.edsk.mvc.service.ImagesVO;
import kr.co.edsk.mvc.service.LikesVO;
import kr.co.edsk.mvc.service.SampleDefaultVO;
import kr.co.edsk.mvc.service.SampleVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.ibatis.sqlmap.engine.scope.SessionScope;



/**
* <pre>
*  게시판 컨트롤러
* 
* </pre>
* 
* 
* @Company : (주)한국이디에스
* @Author  : Eunseo Gee
* @Date    : 2021. 10. 14. 오전 11:11:27
* @Version : 4.0
*/

@Controller
public class BoardController {

	// 트랜잭션
	@Resource(name = "txManager") 
	protected DataSourceTransactionManager txManager;
	
	/** BoardService */
	@Resource(name = "boardService")
	private BoardService boardService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;

	/**
	  * <pre>
	  *   홈(메인 페이지)로 이동
	  * </pre>
	  * @param model
	  * @param req
	  * @return
	  * @throws Exception
	*/
	@RequestMapping(value = "/home.do", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest req)throws Exception{
		
		// 게시글 서비스 종류 별로 분류(플래그)
		String flag = req.getParameter("flag");
		String p_flag = req.getParameter("flag");
		if(p_flag == null || p_flag=="undefined")
			p_flag="all";

		//정렬기준
		String order = req.getParameter("order");
		if(order == null || order=="undefined")
			order="";
		
		// 선택 페이지, 처음 디폴트는 1 페이지
		int pageC = 1;

		// 게시글 전체 갯수
		int totalCnt = boardService.boardCnt(p_flag);

		// 한 페이지당 보여줄 게시글 갯수 
		int show = 16;

		// 페이지 갯수
		int pageNum = (int)(Math.ceil((double)totalCnt/show));		
		int[] pages =  new int[pageNum];
		for(int i = 0; i<pageNum; i++) {
			pages[i]=i+1;
		}
		
		// 페이징 시작/끝 번호
		int startP = pages[0];
		int endP = pages[pageNum-1];
		
		String pageReq = req.getParameter("page");
		if(pageReq != null)
			pageC = Integer.parseInt(pageReq);
		
		// 한 페이지 당 시작/끝 게시물 번호
		int start = totalCnt - (show * pageC) + 1;
		if(start<=0)
			start = 1;
		int end = totalCnt - (show * (pageC - 1));
		
		// 전체 리스트 불러오기
		List<BoardVO> boardVO = boardService.boardList(p_flag, order, start, end);
		
		// 게시글 서비스 종류 목록
		HashSet<String> flagList = boardService.selectFlags();
		ArrayList<String> flags = new ArrayList<>(flagList);
		
		// 로그인할 경우 세션에서 로그인 정보 받아옴 
		String id = "";
		HttpSession session = req.getSession();
		if(session.getAttribute("sessionID")!=null)
			id = String.valueOf(session.getAttribute("sessionID"));
		
		// 게시판에서 해당 계정의 좋아요들 표시
		List<LikesVO> likeVO = boardService.viewLikes(id);
		
		// 전체 게시판에 각 게시글 당 좋아요들 표시
		ArrayList<Integer> likes = null;
		if(id!="" && id!=null) {
			likes = boardService.listLikes(id);
			Collections.sort(likes);
		}
		
		// 화면 표시 위해 모델에 데이터 전달
		model.addAttribute("flag", flag);
		model.addAttribute("flags", flags);
		model.addAttribute("flag", p_flag);
		model.addAttribute("order", order);
		
		model.addAttribute("pageC", pageC);
		model.addAttribute("pages", pages);
		model.addAttribute("start", start);
		model.addAttribute("endP", endP);
		
		model.addAttribute("likes", likes);
		model.addAttribute("likeVO", likeVO);
		
		model.addAttribute("boardVO", boardVO);
		
		return "home";
	}
	
	/**
	  * <pre>
	  *		게시물 입력 페이지로 이동
	  * </pre>
	  * @param model
	  * @param req
	  * @return
	  * @throws Exception
	*/
	@RequestMapping(value="/insert.do", method=RequestMethod.GET)
	public String insert(Model model, HttpServletRequest req)throws Exception{

		HashSet<String> flagList = boardService.selectFlags();
		ArrayList<String> flags = new ArrayList<>(flagList);
		
		model.addAttribute("flags", flags);
		model.addAttribute("req", req);
		
		return "insert";
	}
	
	public static String getUuid() {
		String uuid = UUID.randomUUID().toString();
		
		// 중간에 포함된 하이픈 제거
		uuid = uuid.replaceAll("-", "");
		
		return uuid;
	}
	
	/**
	  * <pre>
	  *		게시물 입력 작업 수행
	  * </pre>
	  * @param model
	  * @param req
	  * @param resp
	  * @return
	  * @throws Exception
	*/
	@RequestMapping(value="/insertAction.do", method=RequestMethod.POST)
	public String insertAction(Model model, MultipartHttpServletRequest req, HttpServletResponse resp)throws Exception{
		
		BoardVO boardVO = new BoardVO();
		ImagesVO imageVO = new ImagesVO();
		
		//String path = req.getSession().getServletContext().getRealPath("/resources/upload");
		String path = req.getSession().getServletContext().getRealPath("/images");
		resp.setContentType("text/html; charset=utf-8");
		
		PrintWriter pw = resp.getWriter();
		
		Map returnObj = new HashMap();
		
		try {
			Iterator itr = req.getFileNames();
			
			MultipartFile mfile = null;
			String fileName = "";
			List resultList = new ArrayList();
			
			File directory = new File(path);
			if(!directory.isDirectory()) {
				directory.mkdirs();
			}
			while(itr.hasNext()) {
				fileName = (String)itr.next();
				mfile = req.getFile(fileName);
				String ofile = new String(mfile.getOriginalFilename().getBytes(),"UTF-8");
				
				if("".equals(ofile)) {
					continue;
				}
				
				String ext = ofile.substring(ofile.lastIndexOf('.'));
				String sfile = getUuid() + ext;
				//File serverFullName = new File(path + File.separator + sfile);
				File serverFullName = new File(path + File.separator + ofile);
				mfile.transferTo(serverFullName);
				
				Map file = new HashMap();
				file.put("ofile", ofile);
				System.out.println(ofile);
				file.put("sfile", sfile);
				System.out.println(sfile);
				file.put("serverFullName", serverFullName);
				System.out.println("serverFullName: " + serverFullName);
				//file.put("title", title);
				
				resultList.add(file);	
			}
			returnObj.put("files", resultList);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("returnObj", returnObj);
		
		List<String> temp = (List<String>) returnObj.get("files");
		
		Object temp2 = temp.get(0);
		
		Object tempA = ((Map) temp2).get("ofile");
		String img = tempA.toString();
		
		HttpSession session = req.getSession();
		String userid = String.valueOf(session.getAttribute("sessionID"));
		boardVO.setUserid(userid);
		boardVO.setTitle(req.getParameter("title"));
		boardVO.setSummary(req.getParameter("summary"));
		boardVO.setImg(img);
		boardVO.setContents(req.getParameter("contents"));
		boardVO.setPostpw(req.getParameter("postpw"));
		boardVO.setP_flag(req.getParameter("p_flag"));
		
		boardService.insertDetail(boardVO);
		
		int b_idx = boardService.getBidx(userid, img);
		
		imageVO.setUserid(userid);
		imageVO.setB_idx(b_idx);
		imageVO.setImg(img);
		
		boardService.insertImg(imageVO);
		
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("imageVO", imageVO);
		model.addAttribute("req", req);
		
		return "redirect:home.do";
	}
		
	/**
	  * <pre>
	  *		게시물 상세보기
	  * </pre>
	  * @param model
	  * @param req
	  * @return
	  * @throws Exception
	*/
	@Transactional
	@RequestMapping(value="/detail.do", method=RequestMethod.GET)
	public String detail(Model model, HttpServletRequest req)throws Exception{
		
		HashSet<String> flagList = boardService.selectFlags();
		ArrayList<String> flags = new ArrayList<>(flagList);
		
		int l_idx=0;
		String userid = "";
		
		LikesVO likeVO  = new LikesVO();
		HttpSession session = req.getSession();
		if(session.getAttribute("sessionID")!=null)
			userid = String.valueOf(session.getAttribute("sessionID"));
		
		int b_idx = Integer.parseInt(String.valueOf(req.getParameter("b_idx")));
		
		int viewCount = boardService.updateViews(b_idx);
		
		int likeCount = boardService.countLike(b_idx);
		String like = "";
		
		BoardVO boardVO = boardService.viewDetail(b_idx);
		boardVO.setLikescount(likeCount);
		
		String img = boardVO.getImg();
		ImagesVO imageVO = boardService.viewImg(b_idx);
		
		if(likeCount!=0) {
			if(userid!="" && userid!=null)
				likeVO = boardService.viewLike(b_idx, userid);	
		}
		
		model.addAttribute("flags", flags);
		
		model.addAttribute("req", req);
		model.addAttribute("viewCount", viewCount);
		
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("imageVO", imageVO);
		model.addAttribute("likeVO", likeVO);	
		
		return "detail";
	}
	
	/**
	  * <pre>
	  *		게시불 비밀번호 검사
	  * </pre>
	  * @param model
	  * @param req
	  * @param resp
	  * @return
	  * @throws Exception
	*/
	@ResponseBody
	@RequestMapping(value="/checkpw.do", method=RequestMethod.POST)
	public String checkPw (Model model, HttpServletRequest req, HttpServletResponse resp) throws Exception{
		
		//String params = req.getParameter("params");
		
		String result="";
		
		resp.setContentType("text/html; charset=UTF-8");
		
		int b_idx = Integer.parseInt(req.getParameter("b_idx"));
		
		String pw  = Integer.toString(boardService.pwGet(b_idx));
		
		String postpw = req.getParameter("postpw");	
		
		if(pw.equals(postpw))
			result="correct";
		
		BoardVO boardVO = boardService.viewDetail(b_idx);
		ImagesVO imageVO = boardService.viewImg(b_idx);

		model.addAttribute("result", result);
		model.addAttribute("b_idx", b_idx);
		
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("imageVO", imageVO);
		
		return result;
		
	}
	
	/**
	  * <pre>
	  *		게시물 업데이트 페이지로 이동
	  * </pre>
	  * @param model
	  * @param req
	  * @param boardVO
	  * @param imageVO
	  * @return
	  * @throws Exception
	*/
	@RequestMapping(value="/update.do", method=RequestMethod.POST)
	public String update(Model model, HttpServletRequest req, BoardVO boardVO, ImagesVO imageVO)throws Exception{
		
		HashSet<String> flagList = boardService.selectFlags();
		ArrayList<String> flags = new ArrayList<>(flagList);
		
		int b_idx = Integer.parseInt(req.getParameter("b_idx"));
		String postpw = req.getParameter("postpw");
		
		boardVO = boardService.viewDetail(b_idx);
		String img = boardVO.getImg();

		imageVO = boardService.viewImg(b_idx);
		
		model.addAttribute("req", req);
		model.addAttribute("model", model);
		
		model.addAttribute("flags", flags);
		
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("imageVO", imageVO);

		return "update";
	}

	/**
	  * <pre>
	  *		게시물 업데이트 작업 수행
	  * </pre>
	  * @param model
	  * @param req
	  * @param resp
	  * @return
	  * @throws Exception
	*/
	@RequestMapping(value="/updateAction.do", method=RequestMethod.POST)
	public String updateAction(Model model, MultipartHttpServletRequest req, HttpServletResponse resp)throws Exception {
		
		BoardVO boardVO = new BoardVO();
		ImagesVO imageVO = new ImagesVO();
		
		String path = req.getSession().getServletContext().getRealPath("/images");
		resp.setContentType("text/html; charset=utf-8");
		
		PrintWriter pw = resp.getWriter();
		
		Map returnObj = new HashMap();
		
		try {
			Iterator itr = req.getFileNames();
			
			MultipartFile mfile = null;
			String fileName = "";
			List resultList = new ArrayList();
			
			File directory = new File(path);
			if(!directory.isDirectory()) {
				directory.mkdirs();
			}
			while(itr.hasNext()) {
				fileName = (String)itr.next();
				mfile = req.getFile(fileName);
				String ofile = new String(mfile.getOriginalFilename().getBytes(),"UTF-8");
				
				if("".equals(ofile)) {
					continue;
				}
				
				String ext = ofile.substring(ofile.lastIndexOf('.'));
				String sfile = getUuid() + ext;
				//File serverFullName = new File(path + File.separator + sfile);
				File serverFullName = new File(path + File.separator + ofile);
				mfile.transferTo(serverFullName);
				
				Map file = new HashMap();
				file.put("ofile", ofile);
				System.out.println(ofile);
				file.put("sfile", sfile);
				System.out.println(sfile);
				file.put("serverFullName", serverFullName);
				System.out.println("serverFullName: " + serverFullName);
				//file.put("title", title);
				
				resultList.add(file);
				
			}
			
			returnObj.put("files", resultList);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("returnObj", returnObj);
		
		List<String> temp = (List<String>) returnObj.get("files");
		
		Object temp2 = temp.get(0);
		
		Object tempA = ((Map) temp2).get("ofile");
		String img = tempA.toString();
		String img2 = img;
		
		String olduserid = req.getParameter("olduserid");
		
		HttpSession session = req.getSession();
		String userid = String.valueOf(session.getAttribute("sessionID"));
		boardVO.setTitle(req.getParameter("title"));
		boardVO.setSummary(req.getParameter("summary"));
		boardVO.setImg(img);
		boardVO.setContents(req.getParameter("contents"));
		String postpw = req.getParameter("postpw");
		boardVO.setPostpw(postpw);
		boardVO.setP_flag(req.getParameter("p_flag"));

		int b_idx = Integer.parseInt(req.getParameter("b_idx"));
		boardVO.setB_idx(b_idx);
		boardVO.setUpdateuser(userid);
		
		int i_idx = boardService.getIidx(b_idx);
		imageVO.setI_idx(i_idx);
		imageVO.setUserid(userid);
		imageVO.setB_idx(b_idx);
		imageVO.setImg(img);
		
		boardService.updateImg(imageVO);
		boardService.updateDetail(boardVO);

		imageVO = boardService.viewImg(b_idx);
		
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("imageVO", imageVO);
		model.addAttribute("req", req);
			
		return "redirect:detail.do?b_idx=" + b_idx;
	}
	
	/**
	  * <pre>
	  *		게시물 삭제
	  * </pre>
	  * @param model
	  * @param req
	  * @return
	  * @throws Exception
	*/
	@RequestMapping(value="/delete.do")
	public String delete(Model model, HttpServletRequest req) throws Exception {
		
		int b_idx = Integer.parseInt(req.getParameter("b_idx"));
		boardService.deleteImg(b_idx);
		boardService.delete(b_idx);
		
		return "redirect:home.do";
	}
	
	/**
	  * <pre>
	  *		좋아요 Ajax 작업 위한 좋아요 테이불 불러오기
	  * </pre>
	  * @param model
	  * @param req
	  * @param likeVO
	  * @return
	  * @throws Exception
	*/
	@ResponseBody
	@RequestMapping(value = "/likeList.do", method = RequestMethod.POST)
	public ArrayList<LikesVO> likeList(Model model, HttpServletRequest req, LikesVO likeVO) throws Exception {
		
		String userid = "";
		
		HttpSession session = req.getSession();
		if(session.getAttribute("sessionID")!=null)
			userid = String.valueOf(session.getAttribute("sessionID"));
		
		ArrayList<LikesVO> likeList = boardService.likeList(userid);
		
		model.addAttribute("likeList", likeList);

		return likeList;
	}
	
	/**
	  * <pre>
	  *		좋아요 추가
	  * </pre>
	  * @param model
	  * @param req
	  * @throws Exception
	*/
	@ResponseBody
	@RequestMapping(value = "/addlike.do", method = RequestMethod.POST)
	public void addLike(Model model, HttpServletRequest req) throws Exception {
		
		LikesVO likeVO = new LikesVO();
		String userid = "";
		
		HttpSession session = req.getSession();
		if(session.getAttribute("sessionID")!=null)
			userid = String.valueOf(session.getAttribute("sessionID"));
		
		int b_idx = Integer.parseInt(req.getParameter("b_idx"));
		
		likeVO.setB_idx(b_idx);
		likeVO.setUserid(userid);
		
		boardService.addLike(likeVO);
		boardService.plusLike(b_idx);
		
	}
	
	/**
	  * <pre>
	  *		좋아요 취소
	  * </pre>
	  * @param model
	  * @param req
	  * @throws Exception
	*/
	@ResponseBody
	@RequestMapping(value = "/removelike.do", method = RequestMethod.POST)
	public void removeLike(Model model, HttpServletRequest req) throws Exception {
		
		String userid = "";
		
		HttpSession session = req.getSession();
		if(session.getAttribute("sessionID")!=null)
			userid = String.valueOf(session.getAttribute("sessionID"));
		
		int b_idx = Integer.parseInt(req.getParameter("b_idx"));
		
		int l_idx = boardService.viewLidx(b_idx, userid);
		
		boardService.removeLike(l_idx);
		boardService.minusLike(b_idx);
		
	}
	
	/**
	  * <pre>
	  *		김세연 추가 : 검색기능
	  * </pre>
	  * @param m
	  * @param key
	  * @return
	*/
	@RequestMapping("/srchAll.do")
	public String srchAll(Model m, String key) {
		List<BoardVO> blist = boardService.srchList(key);
		m.addAttribute("blist", blist);
		m.addAttribute("keyword", key);
		m.addAttribute("total", String.valueOf(blist.size()));
		return "search/srchList";
	}
	


}
