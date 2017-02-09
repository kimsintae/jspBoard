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
			
			//email이 같은 유저 데이터를 가져옴
			pstmt = conn.prepareStatement("select * from member where memberEmail= ?");
			//첫번째 파라미터에 memberEmail 값을 대입
			pstmt.setString(1, memberEmail);
			
			//쿼리 실행
			rs = pstmt.executeQuery();
			
			
			MemberVO member = null;
			
			//데이터로 가져온 유저의 정보를 뽑아서
			//member 객체를 만들어서 리턴
			//즉, DB에서 가져온 데이터를 VO의 각 필드에 초기화 시킴
			
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
	
	//유저 등록
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
	
	//회원정보 수정
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
