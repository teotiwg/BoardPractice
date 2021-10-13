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
 * @Class Name : EgovSampleServiceImpl.java
 * @Description : Sample Business Implement Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@Transactional
@Service("boardService")
public class BoardServiceImpl extends EgovAbstractServiceImpl implements BoardService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoardServiceImpl.class);

	/** SampleDAO */
	// TODO ibatis 사용
	//@Resource(name = "sampleDAO")
	//private SampleDAO sampleDAO;
	
	// TODO mybatis 사용
	@Resource(name="boardMapper")
	private BoardMapper boardDAO;

	/** ID Generation */
	@Resource(name = "egovIdGnrService")
	private EgovIdGnrService egovIdGnrService;

	@Override
	public List<BoardVO> boardList(@Param("p_flag") String p_flag, 
									@Param("order") String order) throws Exception{
		return boardDAO.boardList(p_flag, order);	
	}
	
	// 게시글 분류 기준들 불러오기
	@Override
	public HashSet<String> selectFlags() throws Exception{
		HashSet<String> flags = boardDAO.selectFlags();
		return flags;
	}
	
	@Override
	public BoardVO viewDetail(int b_idx) throws Exception{
		BoardVO boardVO = boardDAO.viewDetail(b_idx);	
		return boardVO;
	}
	@Override
	public ImagesVO viewImg(int b_idx) throws Exception{
//		public ImagesVO viewImg(String img, int b_idx) throws Exception{
		//ImagesVO imageVO = sampleDAO.viewImg(img, b_idx);
		ImagesVO imageVO = boardDAO.viewImg(b_idx);
		return imageVO;
	}
	@Override
	public ArrayList<Integer> listLikes(String id) throws Exception{
		//int likes = boardDAO.listLikes(userid);
		return boardDAO.listLikes(id);
	}
	
	// 특정 게시물의 총 좋아요 수가 얼마인지
	@Override
	public int countLike(int b_idx) throws Exception{
		int cntLike = boardDAO.countLike(b_idx);
		return cntLike;
	}
	// 게시글의 좋아요 여부 조회
	@Override
	public List<LikesVO> viewLikes(String userid) throws Exception{
//	public List<Integer> viewLikes(String userid) throws Exception{
		//public int[] viewLikes(String userid) throws Exception{
		//int likes = boardDAO.viewLikes(userid);
		return boardDAO.viewLikes(userid);
	}
	@Override
//	public int viewLike(int b_idx, String userid) throws Exception{
//	public LikesVO viewLike(int b_idx) throws Exception{
	public LikesVO viewLike(int b_idx, String userid) throws Exception{
		LikesVO likeVO = boardDAO.viewLike(b_idx, userid);
		//int likeVO = boardDAO.viewLike(b_idx, userid);
		return likeVO;
	}
	@Override
	public int viewLidx(@Param("b_idx")int b_idx, @Param("userid") String userid) throws Exception{
		int l_idx = boardDAO.viewLidx(b_idx, userid);
		return l_idx;
	}
	
	@Override
	public ArrayList<LikesVO> likeList(@Param("userid") String userid) throws Exception{
		ArrayList<LikesVO> likeVO = boardDAO.likeList(userid);
		return likeVO;
	}
	// 좋아요 추가
	@Override
	public void addLike(LikesVO likeVO) throws Exception{
		boardDAO.addLike(likeVO);
		//LikesVO like = boardDAO.addLike(likeVO);
		//return like;
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
	
	@Override
	public void insertDetail(BoardVO boardVO) throws Exception{
		boardDAO.insertDetail(boardVO);
		//return boardVO;
	}
	@Override
	public int getBidx(String userid, String img) throws Exception{
//		public String getBidx(String userid, String img) throws Exception{
		//String b_idx =  Integer.toString(boardDAO.getBidx(userid, img));
		int b_idx =  boardDAO.getBidx(userid, img);
		
		return b_idx;
	}
	
	@Override
	public void insertImg(ImagesVO imageVO) throws Exception {
		boardDAO.insertImg(imageVO);
	}
	
	@Override
	public void updateDetail(BoardVO boardVO) throws Exception{
		boardDAO.updateDetail(boardVO);
		//return boardVO;
	}
	
	public int getIidx(@Param("b_idx")int b_idx) throws Exception{
		int iidx = boardDAO.getIidx(b_idx);
		return iidx;
	}
	
	@Override
	public void updateImg(ImagesVO imageVO) throws Exception{
//		public void updateImg(String img, String b_idx) throws Exception{
		//sampleDAO.updateImg(img, Integer.parseInt(b_idx));
		boardDAO.updateImg(imageVO);
	}
	
	@Override
	public void delete(int b_idx) throws Exception{
		boardDAO.delete(b_idx);
	}
	@Override
	public void deleteImg(int b_idx) throws Exception{
		boardDAO.deleteImg(b_idx);
	}
	
	@Override
	public int pwCheck(int b_idx, String postpw) throws Exception{
		int check = boardDAO.pwCheck(b_idx, postpw);	
		return check;
	}
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
	
	
	/**
	 * 글을 등록한다.
	 * @param vo - 등록할 정보가 담긴 SampleVO
	 * @return 등록 결과
	 * @exception Exception
	@Override
	public String insertSample(SampleVO vo) throws Exception {
		LOGGER.debug(vo.toString());

		//** ID Generation Service
		String id = egovIdGnrService.getNextStringId();
		vo.setId(id);
		LOGGER.debug(vo.toString());

		sampleDAO.insertSample(vo);
		return id;
	}
	 */

	/**
	 * 글을 수정한다.
	 * @param vo - 수정할 정보가 담긴 SampleVO
	 * @return void형
	 * @exception Exception
	@Override
	public void updateSample(SampleVO vo) throws Exception {
		sampleDAO.updateSample(vo);
	}
	 */

	/**
	 * 글을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 SampleVO
	 * @return void형
	 * @exception Exception
	@Override
	public void deleteSample(SampleVO vo) throws Exception {
		sampleDAO.deleteSample(vo);
	}
	 */

	/**
	 * 글을 조회한다.
	 * @param vo - 조회할 정보가 담긴 SampleVO
	 * @return 조회한 글
	 * @exception Exception
	@Override
	public SampleVO selectSample(SampleVO vo) throws Exception {
		SampleVO resultVO = sampleDAO.selectSample(vo);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		return resultVO;
	}
	 */

	
	/**
	 * 글 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	@Override
	public List<?> selectSampleList(SampleDefaultVO searchVO) throws Exception {
		return sampleDAO.selectSampleList(searchVO);
	}
	 */

	/**
	 * 글 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	@Override
	public int selectSampleListTotCnt(SampleDefaultVO searchVO) {
		return sampleDAO.selectSampleListTotCnt(searchVO);
	}
	 */

}
