package service;

import java.util.Map;

public class JoinRequest {

	private String email;
	private String name;
	private String password;
	private String confirmPassword;
	private String profile;
	
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public boolean isPasswordEqualToConfirm(){
		return password != null && password.equals(confirmPassword);
	}
	
	public void validate(Map<String, Boolean> errors){
		checkEmpty(errors, email, "email");
		checkEmpty(errors, name, "name");
		checkEmpty(errors, password, "password");
		checkEmpty(errors, confirmPassword, "confirmPassword");
		
		
		if(!errors.containsKey("confirmPassword")){
			if(!isPasswordEqualToConfirm()){
				//비밀번호 확인란에서 비밀번호가 일치하지않을 경우
				errors.put("notMatch", Boolean.TRUE);
			}
		}
		
	}
	
	private void checkEmpty(Map<String, Boolean> errors,
			String value, String fieldName){
		if(value == null || value.isEmpty()){
			errors.put(fieldName, Boolean.TRUE);
		}
		
	}
	
}
