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
		
		System.out.println("ChangePasswordService --> changePassword() ȣ��");
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			//ȸ���� �����ϴ��� ���� Ȯ��
			MemberVO member = dao.selectByid(conn, email);
			if(member == null){throw new MemberNotFoundException();}//ȸ���� �������� ������ ����ó��
			System.out.println("������� ȣ��");
			if(!member.matchPassword(curPwd)){
				System.out.println("��й�ȣ Ʋ��");
				throw new InvalidPasswordException();
			}//���� ��й�ȣ�� Ʋ���� ����ó��
			
			//������ �� ��й�ȣ vo�� password�� �ʱ�ȭ
			member.changePassword(newPwd);
			
			//����й�ȣ�� �ʱ�ȭ�� member��ü�� �̿���
			//������Ʈ ���� �۵�
			dao.update(conn, member);
			conn.commit();
			
		} 
		catch(SQLException e) {JdbcUtil.rollback(conn); throw new RuntimeException(e);}
		finally{JdbcUtil.close(conn);}
		
	}
}
