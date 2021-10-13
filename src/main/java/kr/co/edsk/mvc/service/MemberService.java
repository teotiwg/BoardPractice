/**
 * 
 */
package kr.co.edsk.mvc.service;

/**
* <pre>
* 회원 관련 서비스 인터페이스
* 
* </pre>
* 
* 
* @Company : (주)한국이디에스
* @Author  : SeYon Kim
* @Date    : 2021. 9. 23 오후 2:30:52
* @Version : 4.0
*/
public interface MemberService {
	
	/**
	  * <pre>
	  * 로그인 시 회원정보 조회
	  * </pre>
	  * @return 회원정보
	*/
	MemberVO login(MemberVO vo);
	

}
