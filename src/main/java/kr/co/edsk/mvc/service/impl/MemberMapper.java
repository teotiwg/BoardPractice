/**
 * 
 */
package kr.co.edsk.mvc.service.impl;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.edsk.mvc.service.MemberVO;

/**
* <pre>
* 회원 관련 테이터처리 매퍼 클래스
* 
* </pre>
* 
* 
* @Company : (주)한국이디에스
* @Author  : SeYon Kim
* @Date    : 2021. 9. 23 오후 2:24:26
* @Version : 4.0
*/
@Mapper("memberMapper")
public interface MemberMapper {
	
	/**
	  * <pre>
	  * 로그인 시 회원정보 확인
	  * </pre>
	  * @return 회원 정보
	*/
	MemberVO login(MemberVO vo);

}
