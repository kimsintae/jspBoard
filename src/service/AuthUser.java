package service;

public class AuthUser {

	//로그인 되면 만들어질 로그인유저의 정보가 담긴 객체
	
	private String email;
	private String name;
	private String password;
	private String profile;
	
	
	public AuthUser(String email, String name, String password, String profile) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
		this.profile = profile;
	}
	public String getProfile() {
		return profile;
	}
	public String getEmail() {
		return email;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	
	
	
}
