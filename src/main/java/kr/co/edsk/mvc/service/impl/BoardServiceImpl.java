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
package kr.co.edsk.mvc.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import kr.co.edsk.mvc.service.BoardVO;
import kr.co.edsk.mvc.service.BoardService;
import kr.co.edsk.mvc.service.ImagesVO;
import kr.co.edsk.mvc.service.LikesVO;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



/**
* <pre>
* 	게시판과 게시물 관련 서비스들 구현
* 
* </pre>
* 
* 
* @Company : (주)한국이디에스
* @Author  : Eunseo Gee
* @Date    : 2021. 10. 14. 오후 2:22:39
* @Version : 4.0
*/

@Transactional
@Service("boardService")
public class BoardServiceImpl extends EgovAbstractServiceImpl implements BoardService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoardServiceImpl.class);
	
	// TODO mybatis 사용
	@Resource(name="boardMapper")
	private BoardMapper boardDAO;

	/** ID Generation */
	@Resource(name = "egovIdGnrService")
	private EgovIdGnrService egovIdGnrService;

	// 게시판(게시물 리스트) 불러오기
	@Override
	public List<BoardVO> boardList(@Param("p_flag") String p_flag, 
									@Param("order") String order,
									@Param("start") int start,
									@Param("end") int end) throws Exception{
		return boardDAO.boardList(p_flag, order, start, end);	
	}
	
	// 게시물 총 갯수 계산
	@Override
	public int boardCnt(@Param("p_flag") String p_flag) throws Exception{
		int count = boardDAO.boardCnt(p_flag);
		return count;
	}
		
	// 게시물 분류 기준들 불러오기
	@Override
	public HashSet<String> selectFlags() throws Exception{
		HashSet<String> flags = boardDAO.selectFlags();
		return flags;
	}
	
	// 게시물 상세보기
	@Override
	public BoardVO viewDetail(int b_idx) throws Exception{
		BoardVO boardVO = boardDAO.viewDetail(b_idx);	
		return boardVO;
	}
	// 게시물 상세보기에서 이미지 보기
	@Override
	public ImagesVO viewImg(int b_idx) throws Exception{
		ImagesVO imageVO = boardDAO.viewImg(b_idx);
		return imageVO;
	}
	
	// 게시판에 나열된 게시물들에 좋아요 표시
	@Override
	public ArrayList<Integer> listLikes(String id) throws Exception{
		return boardDAO.listLikes(id);
	}
	
	// 상세페이지에서 해당 게시물의 좋아요 갯수 표시
	@Override
	public int countLike(int b_idx) throws Exception{
		int cntLike = boardDAO.countLike(b_idx);
		return cntLike;
	}
	
	// 해당 계정의 각 게시물들의 좋아요 여부를 게시판에서 조회
	@Override
	public List<LikesVO> viewLikes(String userid) throws Exception{
		return boardDAO.viewLikes(userid);
	}
	
	// 게시물 상세페이지에서 좋아요 여부 표시
	@Override
	public LikesVO viewLike(int b_idx, String userid) throws Exception{
		LikesVO likeVO = boardDAO.viewLike(b_idx, userid);
		return likeVO;
	}
	
	// 좋아요 Ajax기능 위한 좋아요 인덱스 불러오기
	@Override
	public int viewLidx(@Param("b_idx")int b_idx, @Param("userid") String userid) throws Exception{
		int l_idx = boardDAO.viewLidx(b_idx, userid);
		return l_idx;
	}
	// 좋아요 Ajax기능 위한 좋아요 테이블 불러오기
	@Override
	public ArrayList<LikesVO> likeList(@Param("userid") String userid) throws Exception{
		ArrayList<LikesVO> likeVO = boardDAO.likeList(userid);
		return likeVO;
	}
	
	// 좋아요 추가
	@Override
	public void addLike(LikesVO likeVO) throws Exception{
		boardDAO.addLike(likeVO);
	}
	// 좋아요 취소
	@Override
	public void removeLike(@Param("l_idx")int l_idx) throws Exception{
		boardDAO.removeLike(l_idx);
	}
	
	// 게시판 테이블의 게시물 좋아요 컬럼 수 증감
	@Override
	public void plusLike(@Param("b_idx")int b_idx) throws Exception{
		boardDAO.plusLike(b_idx);
	}
	@Override
	public void minusLike(@Param("b_idx")int b_idx) throws Exception{
		boardDAO.minusLike(b_idx);
	}
	
	// 조회수 증가
	@Override
	public int updateViews(int b_idx) throws Exception{
		int viewCount = boardDAO.updateViews(b_idx);
		return viewCount;
	}
	
	// 게시물 작성
	@Override
	public void insertDetail(BoardVO boardVO) throws Exception{
		boardDAO.insertDetail(boardVO);
	}
	// 이미지 불러오기 위한 Bidx 가져오기
	@Override
	public int getBidx(String userid, String img) throws Exception{
		int b_idx =  boardDAO.getBidx(userid, img);
		return b_idx;
	}
	// 이미지 추가
	@Override
	public void insertImg(ImagesVO imageVO) throws Exception {
		boardDAO.insertImg(imageVO);
	}
	
	// 게시물  수정
	@Override
	public void updateDetail(BoardVO boardVO) throws Exception{
		boardDAO.updateDetail(boardVO);
	}
	// 이미지 테이블에서 이미지 수정 위해 이미지 인덱스 가져오기
	public int getIidx(@Param("b_idx")int b_idx) throws Exception{
		int iidx = boardDAO.getIidx(b_idx);
		return iidx;
	}
	// 이미지 수정
	@Override
	public void updateImg(ImagesVO imageVO) throws Exception{
		boardDAO.updateImg(imageVO);
	}
	
	// 게시물 삭제
	@Override
	public void delete(int b_idx) throws Exception{
		boardDAO.delete(b_idx);
	}
	// 이미지 삭제
	@Override
	public void deleteImg(int b_idx) throws Exception{
		boardDAO.deleteImg(b_idx);
	}
	
	// 비밀번호 확인-----------------------
	@Override
	public int pwGet(@Param("b_idx")int b_idx) throws Exception{
		int pw = boardDAO.pwGet(b_idx);
		return pw;
	}

	//김세연 추가 : 검색결과 가져오기
	@Override
	public List<BoardVO> srchList(String key) {
		return boardDAO.srchList(key);
	}
	
	

}
