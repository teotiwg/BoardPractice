/**
 * 
 */
package kr.co.edsk.mvc.web;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.edsk.mvc.service.MemberService;
import kr.co.edsk.mvc.service.MemberVO;

/**
 * <pre>
* 회원 관련 컨트롤러
 * 
 * </pre>
 * 
 * 
 * @Company : (주)한국이디에스
 * @Author : SeYon Kim
 * @Date : 2021. 9. 23 오후 2:36:24
 * @Version : 4.0
 */
@Controller
public class MemberController {

	// 회원관련 Service
	@Resource
	private MemberService memberService;

	/**
	 * <pre>
	 * 로그인 페이지 이동
	 * </pre>
	 * 
	 * @return
	 */
	@RequestMapping("/login.do")
	public String loginPage() {
		return "member/login/login";
	}

	/**
	  * <pre>
	  * 로그인 진행
	  * </pre>
	  * @param vo
	  * @param response
	  * @param session
	*/
	@RequestMapping("/loginprocess.do")
	@ResponseBody
	public void loginProcess(MemberVO param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		MemberVO mvo = memberService.login(param);		
		String msg = "";
		try {
			response.setContentType("text/html; charset=utf-8");
			if(mvo != null) {
				session.setAttribute("sessionID", mvo.getUserid());
				session.setAttribute("sessionName", mvo.getName());
				msg = "true";
				response.getWriter().print(msg);
			}else {
				msg = "false";
				response.getWriter().print(msg);				
			}
		}catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	  * <pre>
	  * 로그아웃
	  * </pre>
	  * @param session
	  * @return
	*/
	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		session.removeAttribute("sessionID");
		session.removeAttribute("sessionName");
		return "redirect:/";
	}
	
	@RequestMapping("/tilesTest.do")
	public String tilesTest() {
		return "member/tilestest";
	}
	
}
