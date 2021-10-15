/**
 * 
 */
package kr.co.edsk.mvc.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.rte.fdl.property.EgovPropertyService;
import kr.co.edsk.mvc.service.BoardVO;
import kr.co.edsk.mvc.service.LikesVO;
import kr.co.edsk.mvc.service.MemberVO;
import kr.co.edsk.mvc.service.MyPageService;

/**
* <pre>
* 	마이페이지 컨트롤러
* 
* </pre>
* 
* 
* @Company : (주)한국이디에스
* @Author  : Eunseo Gee
* @Date    : 2021. 10. 14. 오후 3:11:19
* @Version : 4.0
*/

@Controller
public class MyPageController {

		// 트랜잭션
		@Resource(name = "txManager") 
		protected DataSourceTransactionManager txManager;
		
		/** MyPageService */
		@Resource(name = "mypageService")
		private MyPageService mypageService;

		/** EgovPropertyService */
		@Resource(name = "propertiesService")
		protected EgovPropertyService propertiesService;

		/** Validator */
		@Resource(name = "beanValidator")
		protected DefaultBeanValidator beanValidator;
		
		/**
		  * <pre>
		  *		마이페이지로 이동
		  * </pre>
		  * @param model
		  * @param req
		  * @return
		  * @throws Exception
		*/
		@RequestMapping(value="/mypage.do", method=RequestMethod.GET)
		public String myPage(Model model, HttpServletRequest req) throws Exception{
			
			HttpSession session = req.getSession();
			String userid = String.valueOf(session.getAttribute("sessionID"));
			System.out.println(userid + "의 마이페이지");
				
			List<BoardVO> boardVO = mypageService.recentPosts(userid);
			
			BoardVO vo = new BoardVO();
			ArrayList<BoardVO> likes = new ArrayList<BoardVO>();
			ArrayList<Integer> likebidx = (ArrayList<Integer>)mypageService.recentLikes(userid);
			for(Integer bidx : likebidx) {
				System.out.println("bidx : " + bidx);
				vo = mypageService.getBoard(bidx);
				likes.add(vo);
			}
			for(BoardVO test : likes) {
				System.out.println(test.getB_idx());
				System.out.println(test.getTitle());
			}
			
			model.addAttribute("boardVO", boardVO);
			model.addAttribute("likes", likes);
			
			//return "mypage/mypage";
			return "mypage";
		}
	
		/**
		  * <pre>
		  *		회원정보 조회
		  * </pre>
		  * @param model
		  * @param req
		  * @return
		  * @throws Exception
		*/
		@RequestMapping(value="/myinfo.do", method=RequestMethod.GET)
		public String myInfo(Model model, HttpServletRequest req) throws Exception{
			
			HttpSession session = req.getSession();
			String userid= String.valueOf(session.getAttribute("sessionID"));
			
			MemberVO myInfo = mypageService.memberInfo(userid);
			
			model.addAttribute("myInfo", myInfo);
			
			return "myinfo";
		}
		
		@ResponseBody
		@RequestMapping(value="/check.do", method=RequestMethod.POST)
		public String checkPW(Model model, HttpServletRequest req, HttpServletResponse resp) throws Exception{
			
			String result = "";
			
			resp.setContentType("text/html; charset=UTF-8");
			
			String userid = req.getParameter("userid");
			System.out.println(userid);
			
			String userpw = mypageService.checkPass(userid);
			System.out.println(userpw);
			
			String pw = req.getParameter("userpw");
			System.out.println("pw : " + pw);
			
			if(pw.equals(userpw))
				result="correct";
			
			//MemberVO memberVO = mypageService.memberInfo(userid);
			
			model.addAttribute("result", result);
			
			return result;
		}
		
		/**
		  * <pre>
		  *		비밀번호 변경 처리
		  * </pre>
		  * @param model
		  * @param req
		  * @throws Exception
		*/
		@RequestMapping(value="/updatepw.do", method=RequestMethod.POST)
		public String updatePW(Model model, HttpServletRequest req) throws Exception{
			
			String userid=req.getParameter("userid");
			String userpw = req.getParameter("userpw");
			
			mypageService.updatePass(userid, userpw);
			
			return "redirect:myinfo.do";
		}
		
		/**
		  * <pre>
		  *		회원정보 수정 처리
		  * </pre>
		  * @param model
		  * @param req
		  * @return
		  * @throws Exception
		*/
		@RequestMapping(value="/updateinfo.do", method=RequestMethod.POST)
		public String updateInfo(Model model, HttpServletRequest req) throws Exception{
			
			MemberVO memberVO = new MemberVO();
			
			memberVO.setUserid(req.getParameter("userid"));
			System.out.println(memberVO.getUserid());
			memberVO.setName(req.getParameter("name"));
			memberVO.setPhone(req.getParameter("phone"));
			memberVO.setEmail(req.getParameter("email"));
			
			mypageService.updateMember(memberVO);
			
			return "redirect:myinfo.do";
		}
		
		/**
		  * <pre>
		  *		전체 게시글 조회 페이지로 이동
		  * </pre>
		  * @param model
		  * @param req
		  * @return
		  * @throws Exception
		*/
		@RequestMapping(value="/myposts.do", method=RequestMethod.GET)
		public String myPosts(Model model, HttpServletRequest req) throws Exception{
			
			HttpSession session = req.getSession();
			String userid = String.valueOf(session.getAttribute("sessionID"));
			System.out.println(userid + "의 작성게시물 목록");
			
			List<BoardVO> myPosts = mypageService.getAllPosts(userid);
			
			model.addAttribute("myPosts", myPosts);
			
			return "myposts";
		}
		
		/**
		  * <pre>
		  *		전체 좋아요 조회 페이지로 이동
		  * </pre>
		  * @param model
		  * @param req
		  * @return
		  * @throws Exception
		*/
		@RequestMapping(value="/mylikes.do", method=RequestMethod.GET)
		public String myLikes(Model model, HttpServletRequest req) throws Exception{
			
			HttpSession session = req.getSession();
			String userid = String.valueOf(session.getAttribute("sessionID"));
			System.out.println(userid + "의 좋아요 리스트");
			
			BoardVO vo = new BoardVO();
			ArrayList<BoardVO> likes = new ArrayList<BoardVO>();
			ArrayList<Integer> likeBidx = (ArrayList<Integer>)mypageService.getAllLikes(userid);
			for(int bidx : likeBidx) {
				vo = mypageService.getBoard(bidx);
				likes.add(vo);
			}
			
			model.addAttribute("likes", likes);
			
			return "mylikes";
		}
		
}
