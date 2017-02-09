package service;

import java.sql.Connection;

import dao.MemberDAO;
import exceptions.LoginFailException;
import jdbc.ConnectionProvider;
import vo.MemberVO;

public class LoginService {

	private MemberDAO memberDAO = new MemberDAO();
	
	public AuthUser login(String email, String password){
		System.out.println("LoginService --> login() 호출");
		try(Connection conn = ConnectionProvider.getConnection()){
			
			//회원가입된 이메일이 있는지 검사
			//회원 가져옴
			MemberVO memberVO = memberDAO.selectByid(conn, email);
			//아이디가 존재하지 않으면
			System.out.println(memberVO);
			if(memberVO==null){
				throw new LoginFailException();
			}
			//비밀번호가 다르면
			if(!memberVO.matchPassword(password)){
				throw new LoginFailException();
			}
			//이메일, 비번이 맞으면 유저 정보를 넘겨 authUser객체 생성
			return new AuthUser(memberVO.getMemberEmail(), memberVO.getName(),memberVO.getPassword(),memberVO.getProfile());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}//login()
	
	
}
