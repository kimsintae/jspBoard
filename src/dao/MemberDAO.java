package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import jdbc.JdbcUtil;
import vo.MemberVO;

public class MemberDAO {

	public MemberVO selectByid(Connection conn,String memberEmail)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			//email�� ���� ���� �����͸� ������
			pstmt = conn.prepareStatement("select * from member where memberEmail= ?");
			//ù��° �Ķ���Ϳ� memberEmail ���� ����
			pstmt.setString(1, memberEmail);
			
			//���� ����
			rs = pstmt.executeQuery();
			
			
			MemberVO member = null;
			
			//�����ͷ� ������ ������ ������ �̾Ƽ�
			//member ��ü�� ���� ����
			//��, DB���� ������ �����͸� VO�� �� �ʵ忡 �ʱ�ȭ ��Ŵ
			
			if(rs.next()){
				member = new MemberVO(
						rs.getString("memberEmail"), 
						rs.getString("name"), 
						rs.getString("password"), 
						rs.getTimestamp("regdate"),
						rs.getString("profile"));
			}
			return member;
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}//selectByid
	
	private Date toDate(Timestamp date){
		return date==null? null: new Date(date.getTime());
	}//toDate
	
	//���� ���
	public void insert(Connection conn, MemberVO member)throws SQLException{
		try(PreparedStatement pstmt=
				conn.prepareStatement("insert into member values(?,?,?,?,?)")){
			pstmt.setString(1, member.getMemberEmail());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getPassword());
			pstmt.setTimestamp(4, new Timestamp(member.getRegdate().getTime()));
			pstmt.setString(5, member.getProfile());
			pstmt.executeUpdate();
		}
	}//insert
	
	//ȸ������ ����
	public void update(Connection conn, MemberVO memberVO)throws SQLException{
		try(PreparedStatement pstmt = conn.prepareStatement(
				"update member set name = ?, password =?,profile=? where memberEmail = ?")){
			pstmt.setString(1, memberVO.getName());
			pstmt.setString(2, memberVO.getPassword());
			pstmt.setString(3, memberVO.getMemberEmail());
			pstmt.setString(4, memberVO.getProfile());
			pstmt.executeUpdate();
		}//try
	}
}
