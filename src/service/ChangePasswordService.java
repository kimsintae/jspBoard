package service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.activity.InvalidActivityException;
import javax.management.RuntimeErrorException;

import dao.MemberDAO;
import exceptions.InvalidPasswordException;
import exceptions.MemberNotFoundException;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import vo.MemberVO;

public class ChangePasswordService {

	private MemberDAO dao = new MemberDAO();
	
	public void changePassword(String email, String curPwd,String newPwd,String profile){
		
		System.out.println("ChangePasswordService --> changePassword() 호출");
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			//회원이 존재하는지 여부 확인
			MemberVO member = dao.selectByid(conn, email);
			if(member == null){throw new MemberNotFoundException();}//회원이 존재하지 않으면 예외처리
			System.out.println("여기까지 호출");
			if(!member.matchPassword(curPwd)){
				System.out.println("비밀번호 틀림");
				throw new InvalidPasswordException();
			}//현재 비밀번호가 틀리면 예외처리
			
			//변경할 새 비밀번호 vo의 password로 초기화
			member.changePassword(newPwd);
			
			//새비밀번호로 초기화한 member객체를 이용해
			//업데이트 구문 작동
			dao.update(conn, member);
			conn.commit();
			
		} 
		catch(SQLException e) {JdbcUtil.rollback(conn); throw new RuntimeException(e);}
		finally{JdbcUtil.close(conn);}
		
	}
}
