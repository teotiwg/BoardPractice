package kr.co.edsk.mvc.service;

public class LikesVO {

	private int l_idx; // 좋아요 인덱스 번호
	private int b_idx; // 좋아요된 게시글 번호
	private String userid; //좋아요 한 사용자ID
	
	public int getL_idx() {
		return l_idx;
	}
	public void setL_idx(int l_idx) {
		this.l_idx = l_idx;
	}
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
	
	
}
