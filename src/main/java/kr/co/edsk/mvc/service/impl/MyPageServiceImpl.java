/**
 * 
 */
package kr.co.edsk.mvc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.edsk.mvc.service.BoardVO;
import kr.co.edsk.mvc.service.MemberVO;
import kr.co.edsk.mvc.service.MyPageService;

/**
* <pre>
* 	마이페이지 관련 서비스들 구현
* 
* </pre>
* 
* 
* @Company : (주)한국이디에스
* @Author  : Eunseo Gee
* @Date    : 2021. 10. 14. 오후 3:21:54
* @Version : 4.0
*/

@Service("mypageService")
public class MyPageServiceImpl extends EgovAbstractServiceImpl implements MyPageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyPageServiceImpl.class);
	
	// TODO mybatis 사용
	@Resource(name="mypageMapper")
	private MyPageMapper mypageDAO;

	/** ID Generation */
	@Resource(name = "egovIdGnrService")
	private EgovIdGnrService egovIdGnrService;
	
	// 회원정보 가져오기
	@Override
	public MemberVO memberInfo(String userid) throws Exception{
		MemberVO memberVO = mypageDAO.memberInfo(userid);
		return memberVO;
	}
	
	// 비밀번호 변경
	@Override
	public void updatePass(String userid) throws Exception{
		mypageDAO.updatePass(userid);
	}
	
	// 회원정보 수정
	@Override
	public void updateMember(MemberVO memberVO) throws Exception{
		mypageDAO.updateMember(memberVO);
	}
	
	// 회원 탈퇴
	@Override
	public void memberDel(String userid) throws Exception{
		mypageDAO.memberDel(userid);
	}
	
	// 모든 작성 게시물들 조회
	@Override
	public List<BoardVO> getAllPosts(String userid) throws Exception{
		List<BoardVO> boardVO = mypageDAO.getAllPosts(userid);
		return boardVO;
	}
	
	// 최근  작성 게시물들 조회
	@Override
	public List<BoardVO> recentPosts(String userid) throws Exception{
		List<BoardVO> boardVO = mypageDAO.recentPosts(userid);
		return boardVO;
	}
	
	
}
