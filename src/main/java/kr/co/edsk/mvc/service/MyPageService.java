package kr.co.edsk.mvc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
* <pre>
* 	마이 페이지 관련 서비스 인터페이스
* 
* </pre>
* 
* 
* @Company : (주)한국이디에스
* @Author  : Eunseo Gee
* @Date    : 2021. 10. 14. 오후 3:17:18
* @Version : 4.0
*/
public interface MyPageService {

	/**
	  * <pre>
	  *		아이디로 마이페이지에 회원정보 가져오기
	  * </pre>
	  * @param userid
	  * @return 회원정보
	  * @throws Exception
	*/
	MemberVO memberInfo(@Param("userid") String userid) throws Exception;
	
	/**
	  * <pre>
	  *		비밀번호 변경
	  * </pre>
	  * @param userid
	  * @throws Exception
	*/
	void updatePass(@Param("userid") String userid) throws Exception;
	
	/**
	  * <pre>
	  *		회원정보 수정
	  * </pre>
	  * @param memberVO
	  * @throws Exception
	*/
	void updateMember(MemberVO memberVO) throws Exception;
	
	/**
	  * <pre>
	  *		회원 탈퇴
	  * </pre>
	  * @param userid
	  * @throws Exception
	*/
	void memberDel(@Param("userid") String userid) throws Exception;
	
	/**
	  * <pre>
	  *		작성한  게시물들 모두 조회
	  * </pre>
	  * @param userid
	  * @return
	  * @throws Exception
	*/
	List<BoardVO> getAllPosts(@Param("userid") String userid) throws Exception;
	
	/**
	  * <pre>
	  *    작성한 게시물 중 최신 것만 조회
	  * </pre>
	  * @param userid
	  * @return
	  * @throws Exception
	*/
	List<BoardVO> recentPosts(@Param("userid") String userid) throws Exception;
	
	
}
