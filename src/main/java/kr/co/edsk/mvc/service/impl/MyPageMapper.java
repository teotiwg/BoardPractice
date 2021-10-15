/**
 * 
 */
package kr.co.edsk.mvc.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.edsk.mvc.service.BoardVO;
import kr.co.edsk.mvc.service.LikesVO;
import kr.co.edsk.mvc.service.MemberVO;

/**
* <pre>
* 	마이페이지 관련 데이터 처리 매퍼 클래스
* 
* </pre>
* 
* 
* @Company : (주)한국이디에스
* @Author  : Eunseo Gee
* @Date    : 2021. 10. 14. 오후 3:16:42
* @Version : 4.0
*/

@Mapper("mypageMapper")
public interface MyPageMapper {

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
	  *		비밀번호 변경 전에 비밀번호 확인용
	  * </pre>
	  * @param userid
	  * @throws Exception
	*/
	String checkPass(@Param("userid") String userid) throws Exception;
	
	/**
	  * <pre>
	  *		비밀번호 변경
	  * </pre>
	  * @param userid
	  * @throws Exception
	*/
	void updatePass(@Param("userid") String userid, @Param("userpw") String userpw) throws Exception;
	
	
	/**
	  * <pre>
	  *		회원정보 변경
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
	
	/**
	  * <pre>
	  *		좋아요한 게시물들 모두 불러오기
	  * </pre>
	  * @param userid
	  * @return
	  * @throws Exception
	*/
	List<Integer> getAllLikes (@Param("userid") String userid) throws Exception;
	
	/**
	  * <pre>
	  *		최근에 좋아요한 게시물들을 게시판 테이블에서 불러오기 위해 
	  *		좋아요 테이블에서 b_idx(게시물 번호) 추출
	  * </pre>
	  * @param userid
	  * @return
	  * @throws Exception
	*/
	List<Integer> recentLikes (@Param("userid") String userid) throws Exception;
	
	/**
	  * <pre>
	  *		좋아요 테이블에서 가져온 게시물 번호로
	  *		게시판 테이블에서 해당 게시물의 정보 가져옴
	  * </pre>
	  * @param b_idx
	  * @return
	  * @throws Exception
	*/
	BoardVO getBoard(@Param("b_idx") int b_idx) throws Exception;
	
	
}
