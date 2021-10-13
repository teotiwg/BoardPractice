/*
 * Copyright 2011 MOPAS(Ministry of Public Administration and Security).
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
package kr.co.edsk.mvc.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.edsk.mvc.service.BoardVO;
import kr.co.edsk.mvc.service.ImagesVO;
import kr.co.edsk.mvc.service.LikesVO;


/**
 * sample에 관한 데이터처리 매퍼 클래스
 *
 * @author  표준프레임워크센터
 * @since 2014.01.24
 * @version 1.0
 * @see <pre>
 *  == 개정이력(Modification Information) ==
 *
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *   2014.01.24        표준프레임워크센터          최초 생성
 *
 * </pre>
 */
@Mapper("boardMapper")
public interface BoardMapper {

	// 게시글 목록 불러오기
	List<BoardVO> boardList(@Param("p_flag") String p_flag, 
							@Param("order") String order) throws Exception;
	// 게시글 분류 기준들 불러오기
	HashSet<String> selectFlags() throws Exception;
	
	// 게시물 상세보기
	BoardVO	viewDetail(int b_idx) throws Exception;
	// 이미지 정보 가져오기
	ImagesVO viewImg(int b_idx) throws Exception;
	
	// 게시판에서 어느 게시글에 좋아요 했는지 조회
	ArrayList<Integer> listLikes(@Param("id") String id) throws Exception;
	// 특정 게시물의 총 좋아요 수가 얼마인지
	int countLike(int b_idx) throws Exception;
	// 게시물이 좋아요 됐는지 조회
	List<LikesVO> viewLikes ( @Param("userid") String userid) throws Exception;
	
	int viewLidx(@Param("b_idx")int b_idx, @Param("userid") String userid) throws Exception;
	
	LikesVO viewLike(@Param("b_idx")int b_idx, @Param("userid") String userid) throws Exception;
	
	ArrayList<LikesVO> likeList(@Param("userid") String userid) throws Exception;
	// 좋아요 추가
	void addLike(LikesVO likeVO) throws Exception;
	// 좋아요 취소
	void removeLike(@Param("l_idx")int l_idx) throws Exception;
	
	// 게시판 테이블의 게시물 좋아요 컬럼 수 증감
	void plusLike(@Param("b_idx")int b_idx) throws Exception;
	void minusLike(@Param("b_idx")int b_idx) throws Exception;
	
	// 조회수 증가
	int updateViews(int b_idx) throws Exception;
	
	// 게시물 입력
	void insertDetail(BoardVO boardVO) throws Exception;
	
	// Images등록 위한 외래키 b_idx 가져오기
	int getBidx(@Param("userid") String userid, @Param("img") String img);
	// Images 등록
	void insertImg(ImagesVO imageVO) throws Exception;

	// 게시물 수정
	void updateDetail(BoardVO boardVO) throws Exception;
	// 이미지 수정
	int getIidx(@Param("b_idx")int b_idx) throws Exception;
	//void updateImg( @Param("img") String img, @Param("b_idx") String b_idx) throws Exception;
	void updateImg(ImagesVO imageVO) throws Exception;
	
	// 게시물 삭제
	void delete(int b_idx) throws Exception;
	// 이미지 삭제
	void deleteImg(int b_idx) throws Exception;
	
	// 게시물 비밀번호 확인
	int pwCheck(@Param("b_idx")int b_idx,@Param("postpw") String postpw) throws Exception;
	int pwGet(@Param("b_idx")int b_idx) throws Exception;
	
	//김세연 추가 : 검색결과 가져오기
	List<BoardVO> srchList(String key);
	
	
	
	/**
	 * 글을 등록한다.
	 * @param vo - 등록할 정보가 담긴 SampleVO
	 * @return 등록 결과
	 * @exception Exception
	void insertSample(SampleVO vo) throws Exception;
	 */

	/**
	 * 글을 수정한다.
	 * @param vo - 수정할 정보가 담긴 SampleVO
	 * @return void형
	 * @exception Exception
	void updateSample(SampleVO vo) throws Exception;
	 */

	/**
	 * 글을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 SampleVO
	 * @return void형
	 * @exception Exception
	void deleteSample(SampleVO vo) throws Exception;
	 */

	/**
	 * 글을 조회한다.
	 * @param vo - 조회할 정보가 담긴 SampleVO
	 * @return 조회한 글
	 * @exception Exception
	SampleVO selectSample(SampleVO vo) throws Exception;
	 */
	/**
	 * 글 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	List<?> selectSampleList(SampleDefaultVO searchVO) throws Exception;
	 */

	/**
	 * 글 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	int selectSampleListTotCnt(SampleDefaultVO searchVO);
	 */

}
