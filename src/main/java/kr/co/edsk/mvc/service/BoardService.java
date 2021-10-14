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
package kr.co.edsk.mvc.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;


/**
* <pre>
* 	게시판과 게시물 관련 서비스 인터페이스
* 
* </pre>
* 
* 
* @Company : (주)한국이디에스
* @Author  : Eunseo Gee
* @Date    : 2021. 10. 14. 오후 2:24:15
* @Version : 4.0
*/
public interface BoardService {
	
	/**
	  * <pre>
	  *		게시물 전체 목록 불러오기
	  * </pre>
	  * @param p_flag
	  * @param order
	  * @param start
	  * @param end
	  * @return 게시판
	  * @throws Exception
	*/
	List<BoardVO> boardList(@Param("p_flag") String p_flag, 
							@Param("order") String order,
							@Param("start") int start,
							@Param("end") int end) throws Exception;
	/**
	  * <pre>
	  *		게시물 총 갯수 계산
	  * </pre>
	  * @param p_flag
	  * @return 게시물 총 갯수
	  * @throws Exception
	*/
	int boardCnt(@Param("p_flag") String p_flag) throws Exception; 
	
	/**
	  * <pre>
	  *		게시물 분류 기준들 불러오기
	  * </pre>
	  * @return 게시물 카테고리
	  * @throws Exception
	*/
	HashSet<String> selectFlags() throws Exception;
	
	/**
	  * <pre>
	  *		게시물 상세보기
	  * </pre>
	  * @param b_idx
	  * @return	
	  * @throws Exception
	*/
	BoardVO	viewDetail(int b_idx) throws Exception;
	
	/**
	  * <pre>
	  *		게시물의 이미지 정보 가져오기
	  * </pre>
	  * @param b_idx
	  * @return
	  * @throws Exception
	*/
	ImagesVO viewImg(int b_idx) throws Exception;
	
	/**
	  * <pre>
	  *		게시판에 나열된 게시물들에 좋아요 표시
	  * </pre>
	  * @param id
	  * @return
	  * @throws Exception
	*/
	ArrayList<Integer> listLikes(@Param("id") String id) throws Exception;
	
	/**
	  * <pre>
	  *		특정 게시물의 총 좋아요 갯수 표시
	  * </pre>
	  * @param b_idx
	  * @return
	  * @throws Exception
	*/
	int countLike(int b_idx) throws Exception;
	
	/**
	  * <pre>
	  *		게시판에서 게시물 좋아요 여부 표시
	  * </pre>
	  * @param userid
	  * @return
	  * @throws Exception
	*/
	List<LikesVO> viewLikes ( @Param("userid") String userid) throws Exception;
	
	/**
	 * <pre>
	 *		좋아요 Ajax기능 위한 좋아요 인덱스 불러오기
	 * </pre>
	 * @param b_idx
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	int viewLidx(@Param("b_idx")int b_idx, @Param("userid") String userid) throws Exception;
	
	/**
	  * <pre>
	  *		게시물 상세페이지에서 좋아요 여부 표시
	  * </pre>
	  * @param b_idx
	  * @param userid
	  * @return
	  * @throws Exception
	*/
	LikesVO viewLike(int b_idx, String userid) throws Exception;

	
	/**
	  * <pre>
	  *		좋아요 Ajax기능 위한 좋아요 테이블 불러오기
	  * </pre>
	  * @param userid
	  * @return
	  * @throws Exception
	*/
	ArrayList<LikesVO> likeList(@Param("userid") String userid) throws Exception;
	
	/**
	  * <pre>
	  *	좋아요 추가
	  * </pre>
	  * @param likeVO
	  * @throws Exception
	*/
	void addLike(LikesVO likeVO) throws Exception;
	
	/**
	  * <pre>
	  *		좋아요 취소
	  * </pre>
	  * @param l_idx
	  * @throws Exception
	*/
	void removeLike(@Param("l_idx")int l_idx) throws Exception;
	
	/**
	  * <pre>
	  *		게시판 테이블의 게시물 좋아요 컬럼 수 증감
	  * </pre>
	  * @param b_idx
	  * @throws Exception
	*/
	void plusLike(@Param("b_idx")int b_idx) throws Exception;
	void minusLike(@Param("b_idx")int b_idx) throws Exception;
	
	/**
	  * <pre>
	  *		조회수 증가
	  * </pre>
	  * @param b_idx
	  * @return
	  * @throws Exception
	*/
	int updateViews(int b_idx) throws Exception;
	
	/**
	  * <pre>
	  *		게시물 등록
	  * </pre>
	  * @param boardVO
	  * @throws Exception
	*/
	void insertDetail(BoardVO boardVO) throws Exception;
	
	/**
	  * <pre>
	  *		이미지 입력 위한 외래키인 게시글 번호 가져오기
	  * </pre>
	  * @param userid
	  * @param img
	  * @return 게시글 번호
	  * @throws Exception
	*/
	int getBidx(String userid, String img) throws Exception;
	
	/**
	  * <pre>
	  *		이미지 등록
	  * </pre>
	  * @param imageVO
	  * @throws Exception
	*/
	void insertImg(ImagesVO imageVO) throws Exception;

	/**
	  * <pre>
	  *		게시물 비밀번호 확인
	  * </pre>
	  * @param b_idx
	  * @param postpw
	  * @return 게시물 비밀번호
	  * @throws Exception
	*/
	int pwGet(@Param("b_idx")int b_idx) throws Exception;
	
	/**
	  * <pre>
	  *		게시물 수정
	  * </pre>
	  * @param boardVO
	  * @throws Exception
	*/
	void updateDetail(BoardVO boardVO) throws Exception;
	
	/**
	  * <pre>
	  *		이미지 수정 위해 사진 번호 가져오기
	  * </pre>
	  * @param b_idx
	  * @return 사진 번호
	  * @throws Exception
	*/
	int getIidx(@Param("b_idx")int b_idx) throws Exception;
	
	/**
	  * <pre>
	  *		이미지 수정
	  * </pre>
	  * @param imageVO
	  * @throws Exception
	*/
	void updateImg(ImagesVO imageVO) throws Exception;
	
	/**
	  * <pre>
	  *		게시물 삭제
	  * </pre>
	  * @param b_idx
	  * @throws Exception
	*/
	void delete(int b_idx) throws Exception;
	
	/**
	  * <pre>
	  *		이미지 삭제
	  * </pre>
	  * @param b_idx
	  * @throws Exception
	*/
	void deleteImg(int b_idx) throws Exception;
	
	
	/**
	  * <pre>
	  *		김세연 추가 : 검색결과 가져오기
	  * </pre>
	  * @param key
	  * @return
	*/
	List<BoardVO> srchList(String key);
	

	
	


}
