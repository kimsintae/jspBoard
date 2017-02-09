package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import dao.MemberDAO;
import exceptions.DuplicateIdException;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import vo.MemberVO;

public class JoinService {

	private MemberDAO memberDAO = new MemberDAO();
	
	public void join(JoinRequest joinReq){
		
		System.out.println("join()�޼��� ����");
		Connection conn = null;
		try {
			
			//dao�� �Ѱ��� ���ؼ� ����
			conn = ConnectionProvider.getConnection();
			
			//Ʈ����� ����
			conn.setAutoCommit(false);
			
			//�ߺ� id üũ
			MemberVO member = memberDAO.selectByid(conn, joinReq.getEmail());
			if(member != null){
				JdbcUtil.rollback(conn);
				throw new DuplicateIdException();
			}
			
			memberDAO.insert(conn, new MemberVO(joinReq.getEmail(), joinReq.getName(), joinReq.getPassword(), new Date(),joinReq.getProfile()));
			conn.commit();
			
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		}finally {
			JdbcUtil.close(conn);
		}
		
	}
}
