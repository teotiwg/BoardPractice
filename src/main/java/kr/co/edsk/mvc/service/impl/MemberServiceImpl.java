/**
 * 
 */
package kr.co.edsk.mvc.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.edsk.mvc.service.MemberService;
import kr.co.edsk.mvc.service.MemberVO;

/**
* <pre>
* 회원관련 서비스 구현
* 
* </pre>
* 
* 
* @Company : (주)한국이디에스
* @Author  : SeYon Kim
* @Date    : 2021. 9. 23 오후 2:32:55
* @Version : 4.0
*/
@Service
public class MemberServiceImpl extends EgovAbstractServiceImpl implements MemberService {

	@Resource(name="memberMapper")
	private MemberMapper memberDAO;
	
	@Override
	public MemberVO login(MemberVO vo) {
		return memberDAO.login(vo);
	}


}
