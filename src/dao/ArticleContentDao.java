package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import vo.ArticleContent;

public class ArticleContentDao {

	//게시글 추가
	public ArticleContent insert(Connection conn, ArticleContent content)throws SQLException{
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("insert into article_content"
					+ "(article_no, content) values(?,?)");
			pstmt.setLong(1, content.getNumber());
			pstmt.setString(2, content.getContent());
			int insertedCount = pstmt.executeUpdate();
			
			if(insertedCount > 0){
				return content;
			}else{
				return null;
			}
		} finally {
			JdbcUtil.close(pstmt);
		}
	}//ArticleContent
	
	//게시글 내용 조회
	public ArticleContent selectById(Connection conn, int no)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(""
					+ "select * from article_content where article_no = ?");
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();
			ArticleContent articleContent = null;
			if(rs.next()){
				articleContent = new ArticleContent(rs.getInt("article_no"), rs.getString("content"));
			}
			return articleContent;
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}//selectById
	
	//게시글 내용 수정
	public int update(Connection conn, int no, String content)throws SQLException{
		System.out.println("update 호출");
		try(PreparedStatement pstmt = conn.prepareStatement(""
				+ "update article_content set content=? where article_no=?")){
			pstmt.setString(1, content);
			pstmt.setInt(2, no);
			return pstmt.executeUpdate();
		}
	}//update
}
