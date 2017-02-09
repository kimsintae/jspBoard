package service;

import java.sql.Connection;

import dao.MemberDAO;
import exceptions.LoginFailException;
import jdbc.ConnectionProvider;
import vo.MemberVO;

public class LoginService {

	private MemberDAO memberDAO = new MemberDAO();
	
	public AuthUser login(String email, String password){
		System.out.println("LoginService --> login() ȣ��");
		try(Connection conn = ConnectionProvider.getConnection()){
			
			//ȸ�����Ե� �̸����� �ִ��� �˻�
			//ȸ�� ������
			MemberVO memberVO = memberDAO.selectByid(conn, email);
			//���̵� �������� ������
			System.out.println(memberVO);
			if(memberVO==null){
				throw new LoginFailException();
			}
			//��й�ȣ�� �ٸ���
			if(!memberVO.matchPassword(password)){
				throw new LoginFailException();
			}
			//�̸���, ����� ������ ���� ������ �Ѱ� authUser��ü ����
			return new AuthUser(memberVO.getMemberEmail(), memberVO.getName(),memberVO.getPassword(),memberVO.getProfile());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}//login()
	
	
}
