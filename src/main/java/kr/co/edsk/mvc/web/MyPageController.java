/**
 * 
 */
package kr.co.edsk.mvc.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.rte.fdl.property.EgovPropertyService;
import kr.co.edsk.mvc.service.BoardVO;
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
			System.out.println("id : " + userid);
				
			List<BoardVO> boardVO = mypageService.recentPosts(userid);
			
			model.addAttribute("boardVO", boardVO);
			
			//return "mypage/mypage";
			return "mypage";
		}
	
		@RequestMapping(value="/myposts.do", method=RequestMethod.GET)
		public String myPosts(Model model, HttpServletRequest req) throws Exception{
			
			return "myposts";
		}
		
}
