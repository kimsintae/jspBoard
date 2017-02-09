package vo;

import java.util.Date;

public class MemberVO {

	private String memberEmail;
	private String name;
	private String password;
	private Date regdate;
	private String profile;

	public MemberVO() {
		// TODO Auto-generated constructor stub
	}
	

	public MemberVO(String memberEmail, String name, String password, Date regdate, String profile) {
		super();
		this.memberEmail = memberEmail;
		this.name = name;
		this.password = password;
		this.regdate = regdate;
		this.profile = profile;
	}


	public String getMemberEmail() {
		return memberEmail;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public Date getRegdate() {
		return regdate;
	}
	

	public String getProfile() {
		return profile;
	}

	
	//암호변경 기능 구현
	public boolean matchPassword(String pwd){
		return password.equals(pwd);
	}
	
	//암호변경 메서드
	public void changePassword(String newPwd){
		this.password = newPwd;
	}
	
	
	
}
