/**
 * 
 */
package kr.co.edsk.mvc.service;

/**
* <pre>
* 사용자 정보 VO
* 
* </pre>
* 
* 
* @Company : (주)한국이디에스
* @Author  : user
* @Date    : 2021. 9. 28 오후 3:30:19
* @Version : 4.0
*/
public class MemberVO {
	
	private String userid; // 사용자 아이디
	private String userpw; // 사용자 비밀번호
	private String name;  // 사용자 이름
	private String phone; // 사용자 휴대폰번호
	private String email; // 사용자 이메일
	private String regidate; // 사용자 회원가입 일시
	private String flag;  // 사용자 구분
	private String businessnum;  // 사용자 사업자등록번호
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserpw() {
		return userpw;
	}
	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRegidate() {
		return regidate;
	}
	public void setRegidate(String regidate) {
		this.regidate = regidate;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getBusinessnum() {
		return businessnum;
	}
	public void setBusinessnum(String businessnum) {
		this.businessnum = businessnum;
	}
	

}
