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
 * @Class Name : EgovSampleController.java
 * @Description : EgovSample Controller Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
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

	// 메인 게시판
	@RequestMapping(value = "/home.do", method = RequestMethod.GET)
	public String home(Model model, Locale locale, HttpServletRequest req)throws Exception{
		
		System.out.println("홈컨 들어옴");
		
		String p_flag = req.getParameter("flag");
		if(p_flag == null || p_flag=="undefined")
			p_flag="all";

		String order = req.getParameter("order");
		if(order == null || order=="undefined")
			order="";
		System.out.println("order : "+order);
		
		List<BoardVO> boardVO = boardService.boardList(p_flag, order);
		
		HashSet<String> flagList = boardService.selectFlags();
		ArrayList<String> flags = new ArrayList<>(flagList);

		String id = "";
		HttpSession session = req.getSession();
		if(session.getAttribute("sessionID")!=null)
			id = String.valueOf(session.getAttribute("sessionID"));
		
		List<LikesVO> likeVO = boardService.viewLikes(id);
		
		ArrayList<Integer> likes = null;
		if(id!="" && id!=null) {
			likes = boardService.listLikes(id);
			Collections.sort(likes);
		}
		
		//model.addAttribute("flag", flag);
		model.addAttribute("flags", flags);
		model.addAttribute("flag", p_flag);
		model.addAttribute("order", order);
		
		model.addAttribute("likes", likes);
		model.addAttribute("likeVO", likeVO);
		
		model.addAttribute("boardVO", boardVO);
		return "home";
	}
	
	// 게시물 입력 페이지로 이동
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
		System.out.println("생성된UUID-1: " + uuid);
		// 중간에 포함된 하이픈 제거
		uuid = uuid.replaceAll("-", "");
		System.out.println("생성된UUID-2: " + uuid);
		
		return uuid;
	}
	
	// 게시물 입력 동작 수행
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
				//System.out.println("mfile = " + mfile);
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
		
	// 게시물 상세보기
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
	
	@ResponseBody
	@RequestMapping(value="/checkpw.do", method=RequestMethod.POST)
	public String checkPw (Model model, HttpServletRequest req, HttpServletResponse resp) throws Exception{
		
		System.out.println("ajax로 check 호출");
		
		String params = req.getParameter("params");
		System.out.println(params);
		
		String result="";
		
		System.out.println("1");
		resp.setContentType("text/html; charset=UTF-8");
		
		System.out.println("2");
		int b_idx = Integer.parseInt(req.getParameter("b_idx"));
		System.out.println("b_idx : " + b_idx);
		
		String pw  = Integer.toString(boardService.pwGet(b_idx));
		System.out.println("pw : " + pw);
		
		String postpw = req.getParameter("postpw");	
		System.out.println("postpw : " + postpw);
		
		int check = boardService.pwCheck(b_idx, postpw);
		System.out.println("check : " + check);
		
		if(pw.equals(postpw))
			result="correct";
		System.out.println("result :  " + result);
		
		BoardVO boardVO = boardService.viewDetail(b_idx);
		ImagesVO imageVO = boardService.viewImg(b_idx);

		model.addAttribute("result", result);
		model.addAttribute("b_idx", b_idx);
		
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("imageVO", imageVO);
		
		return result;
		
	}
	// 게시글에 비밀번호가 설정된 경우, 수정 창에 들어가기 전에 비밀번호 확인
	@RequestMapping(value="/check.do", method=RequestMethod.POST)
	public String check (Model model, HttpServletRequest req, HttpServletResponse resp, RedirectAttributes rttr) throws Exception{
		
		String result="";
		String msg="";
		String referer = req.getHeader("Referer");
		
		resp.setContentType("text/html; charset=UTF-8");

		int b_idx = Integer.parseInt(req.getParameter("b_idx"));
		System.out.println("b_idx : " + b_idx);
		
		String pw  = Integer.toString(boardService.pwGet(b_idx));
		System.out.println("pw : " + pw);
		
		String postpw = req.getParameter("postpw");	
		System.out.println("postpw : " + postpw);
		
		int check = boardService.pwCheck(b_idx, postpw);
		System.out.println("check : " + check);
		
		if(check==1)
			result="update";
		else {
			rttr.addFlashAttribute("result", "wrong");
			result="forward:" + referer;
			//msg="비밀번호 오류. 비밀번호를 다시 입력해주세요.";
		}
		/*
		if(pw == postpw)
			result = "correct";
		*/

		System.out.println("result :  " + result);
		
		BoardVO boardVO = boardService.viewDetail(b_idx);
		ImagesVO imageVO = boardService.viewImg(b_idx);
		
		//model.addAttribute("msg", msg);
		model.addAttribute("b_idx", b_idx);
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("imageVO", imageVO);
		
		return result;
		//return "update";
	}
	
	// 게시물 업데이트 페이지로 이동
	@RequestMapping(value="/update.do", method=RequestMethod.POST)
	public String update(Model model, HttpServletRequest req, BoardVO boardVO, ImagesVO imageVO)throws Exception{
		
		System.out.println("업뎃 들어옴");
		
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
	// 업데이트 수행
	@RequestMapping(value="/updateAction.do", method=RequestMethod.POST)
	public String updateAction(Model model, MultipartHttpServletRequest req, HttpServletResponse resp)throws Exception {
		
		System.out.println("업뎃액션 컨트롤러 시작");
		
		BoardVO boardVO = new BoardVO();
		ImagesVO imageVO = new ImagesVO();
		//imageVO = new ImagesVO();
		
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
				System.out.println("mfile = " + mfile);
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
		System.out.println("img2 : " + img2);
		
		String olduserid = req.getParameter("olduserid");
		System.out.println("olduserid : " + olduserid);
		
		//String userid = req.getParameter("userid");
		HttpSession session = req.getSession();
		String userid = String.valueOf(session.getAttribute("sessionID"));
		System.out.println("new : " + userid);
		boardVO.setTitle(req.getParameter("title"));
		boardVO.setSummary(req.getParameter("summary"));
		boardVO.setImg(img);
		boardVO.setContents(req.getParameter("contents"));
		String postpw = req.getParameter("postpw");
		boardVO.setPostpw(postpw);
		boardVO.setP_flag(req.getParameter("p_flag"));

		//int b_idx = boardService.getBidx(olduserid, img2);
		int b_idx = Integer.parseInt(req.getParameter("b_idx"));
		System.out.println("b_idx1: " + b_idx);
		boardVO.setB_idx(b_idx);
		boardVO.setUpdateuser(userid);
		
		System.out.println("b_idx2: " + b_idx);
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
	// 게시물 삭제
	@RequestMapping(value="/delete.do")
	public String delete(Model model, HttpServletRequest req) throws Exception {
		
		int b_idx = Integer.parseInt(req.getParameter("b_idx"));
		boardService.deleteImg(b_idx);
		boardService.delete(b_idx);
		
		return "redirect:home.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "/likeList.do", method = RequestMethod.POST)
	public ArrayList<LikesVO> likeList(Model model, HttpServletRequest req, LikesVO likeVO) throws Exception {
		
		System.out.println("likeList호출");
		
		String userid = "";
		
		HttpSession session = req.getSession();
		if(session.getAttribute("sessionID")!=null)
			userid = String.valueOf(session.getAttribute("sessionID"));
		
		ArrayList<LikesVO> likeList = boardService.likeList(userid);
		
		model.addAttribute("likeList", likeList);

		return likeList;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addlike.do", method = RequestMethod.POST)
	public void addLike(Model model, HttpServletRequest req) throws Exception {
		
		System.out.println("addLike호출");
		
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
	
	@ResponseBody
	@RequestMapping(value = "/removelike.do", method = RequestMethod.POST)
	public void removeLike(Model model, HttpServletRequest req) throws Exception {
		
		System.out.println("likeRemove호출");
		
		String userid = "";
		
		HttpSession session = req.getSession();
		if(session.getAttribute("sessionID")!=null)
			userid = String.valueOf(session.getAttribute("sessionID"));
		
		int b_idx = Integer.parseInt(req.getParameter("b_idx"));
		
		int l_idx = boardService.viewLidx(b_idx, userid);
		
		boardService.removeLike(l_idx);
		boardService.minusLike(b_idx);
		
		
	}
	
	
	// 김세연 추가 : 검색기능
	@RequestMapping("/srchAll.do")
	public String srchAll(Model m, String key) {
		List<BoardVO> blist = boardService.srchList(key);
		m.addAttribute("blist", blist);
		m.addAttribute("keyword", key);
		m.addAttribute("total", String.valueOf(blist.size()));
		return "search/srchList";
	}
	

	
	/**
	 * 글 목록을 조회한다. (pageing)
	 * @param searchVO - 조회할 정보가 담긴 SampleDefaultVO
	 * @param model
	 * @return "egovSampleList"
	 * @exception Exception
	@RequestMapping(value = "/egovSampleList.do")
	public String selectSampleList(@ModelAttribute("searchVO") SampleDefaultVO searchVO, ModelMap model) throws Exception {

		// EgovPropertyService.sample
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		// pageing setting 
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<?> sampleList = sampleService.selectSampleList(searchVO);
		model.addAttribute("resultList", sampleList);

		int totCnt = sampleService.selectSampleListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "sample/egovSampleList";
	}
*/

	/**
	 * 글 등록 화면을 조회한다.
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param model
	 * @return "egovSampleRegister"
	 * @exception Exception
	@RequestMapping(value = "/addSample.do", method = RequestMethod.GET)
	public String addSampleView(@ModelAttribute("searchVO") SampleDefaultVO searchVO, Model model) throws Exception {
		model.addAttribute("sampleVO", new SampleVO());
		return "sample/egovSampleRegister";
	}
	 */

	/**
	 * 글을 등록한다.
	 * @param sampleVO - 등록할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return "forward:/egovSampleList.do"
	 * @exception Exception
	@RequestMapping(value = "/addSample.do", method = RequestMethod.POST)
	public String addSample(@ModelAttribute("searchVO") SampleDefaultVO searchVO, SampleVO sampleVO, BindingResult bindingResult, Model model, SessionStatus status)
			throws Exception {

		// Server-Side Validation
		beanValidator.validate(sampleVO, bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("sampleVO", sampleVO);
			return "sample/egovSampleRegister";
		}

		sampleService.insertSample(sampleVO);
		status.setComplete();
		return "forward:/egovSampleList.do";
	}
	 */

	/**
	 * 글 수정화면을 조회한다.
	 * @param id - 수정할 글 id
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param model
	 * @return "egovSampleRegister"
	 * @exception Exception
	@RequestMapping("/updateSampleView.do")
	public String updateSampleView(@RequestParam("selectedId") String id, @ModelAttribute("searchVO") SampleDefaultVO searchVO, Model model) throws Exception {
		SampleVO sampleVO = new SampleVO();
		sampleVO.setId(id);
		// 변수명은 CoC 에 따라 sampleVO
		model.addAttribute(selectSample(sampleVO, searchVO));
		return "sample/egovSampleRegister";
	}
	 */

	/**
	 * 글을 조회한다.
	 * @param sampleVO - 조회할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return @ModelAttribute("sampleVO") - 조회한 정보
	 * @exception Exception
	public SampleVO selectSample(SampleVO sampleVO, @ModelAttribute("searchVO") SampleDefaultVO searchVO) throws Exception {
		return sampleService.selectSample(sampleVO);
	}
	 */

	/**
	 * 글을 수정한다.
	 * @param sampleVO - 수정할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return "forward:/egovSampleList.do"
	 * @exception Exception
	@RequestMapping("/updateSample.do")
	public String updateSample(@ModelAttribute("searchVO") SampleDefaultVO searchVO, SampleVO sampleVO, BindingResult bindingResult, Model model, SessionStatus status)
			throws Exception {

		beanValidator.validate(sampleVO, bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("sampleVO", sampleVO);
			return "sample/egovSampleRegister";
		}

		sampleService.updateSample(sampleVO);
		status.setComplete();
		return "forward:/egovSampleList.do";
	}
	 */

	/**
	 * 글을 삭제한다.
	 * @param sampleVO - 삭제할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return "forward:/egovSampleList.do"
	 * @exception Exception
	@RequestMapping("/deleteSample.do")
	public String deleteSample(SampleVO sampleVO, @ModelAttribute("searchVO") SampleDefaultVO searchVO, SessionStatus status) throws Exception {
		sampleService.deleteSample(sampleVO);
		status.setComplete();
		return "forward:/egovSampleList.do";
	}
	 */

}
