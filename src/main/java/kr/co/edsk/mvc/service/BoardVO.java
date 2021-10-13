package kr.co.edsk.mvc.service;

import java.sql.Date;

/**
 * @Class Name : SampleVO.java
 * @Description : SampleVO Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0

public class BoardVO extends BoardDefaultVO {
 */
public class BoardVO{

	private static final long serialVersionUID = 1L;
	
	private int b_idx; // 게시글 번호
	private String userid; // 사용자 아이디
	private String title; // 게시글 제목
	private String contents; // 게시글 내용
	private Date date_c; // 게시글 작성일
	private String postpw; // 게시글 비밀번호
	private String updateuser; // 게시글 수정인
	private Date date_u; // 게시글 수정일
	private String p_flag; // 게시글 유형
	private String img; // 사진
	private int viewcount; // 조회수
	private int likescount; // 좋아요 수
	private String summary; // 업체/서비스 요약 설명
	
	public int getB_idx() {
		return b_idx;
	}
	public void setB_idx(int b_idx) {
		this.b_idx = b_idx;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Date getDate_c() {
		return date_c;
	}
	public void setDate_c(Date date_c) {
		this.date_c = date_c;
	}
	public String getPostpw() {
		return postpw;
	}
	public void setPostpw(String postpw) {
		this.postpw = postpw;
	}
	public String getUpdateuser() {
		return updateuser;
	}
	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}
	public Date getDate_u() {
		return date_u;
	}
	public void setDate_u(Date date_u) {
		this.date_u = date_u;
	}
	public String getP_flag() {
		return p_flag;
	}
	public void setP_flag(String p_flag) {
		this.p_flag = p_flag;
	}
	public int getViewcount() {
		return viewcount;
	}
	public void setViewcount(int viewcount) {
		this.viewcount = viewcount;
	}
	public int getLikescount() {
		return likescount;
	}
	public void setLikescount(int likescount) {
		this.likescount = likescount;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	

}
