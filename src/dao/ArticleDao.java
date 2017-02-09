package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import jdbc.JdbcUtil;
import vo.Article;
import vo.Writer;

public class ArticleDao {

	
	//글 등록
	public Article insert(Connection conn, Article article)throws SQLException{
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("insert into article"
					+ "(writer_id, writer_name, title, regdate, moddate, read_cnt)"
					+ "values(?,?,?,?,?,0)");
			
			pstmt.setString(1, article.getWriter().getId());
			pstmt.setString(2, article.getWriter().getName());
			pstmt.setString(3, article.getTitle());
			pstmt.setTimestamp(4,toTimestamp(article.getRegdate()));
			pstmt.setTimestamp(5,toTimestamp(article.getRegdate()));
			int insertedCount = pstmt.executeUpdate();
			
			//글쓰기가 성공했을 경우
			if(insertedCount > 0){
				stmt = conn.createStatement();
				
				//위에서 새롭게 씌여진 글의 id값을 가져온다.
				rs=stmt.executeQuery("select last_insert_id() from article");
				if(rs.next()){
					Integer newNum = rs.getInt(1);
					return new Article(newNum, 
										article.getWriter(), 
										article.getTitle(), 
										article.getRegdate(), 
										article.getModifiedDate(), 0);
				}
			}//if
			return null;
			
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
		}
	}//insert
	private Timestamp toTimestamp(Date date){
		return new Timestamp(date.getTime());
	}
	
	//총 게시글 수 가져오기
	public int selectCount(Connection conn)throws SQLException{
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from article");
			if(rs.next()){
				return rs.getInt(1);
			}
			return 0;
		}finally{
			JdbcUtil.close(stmt);
			JdbcUtil.close(rs);
		}
	}//selectCount()
	
	public List<Article> select(Connection conn, int startRow, int size)throws SQLException{
		
		System.out.println("ArticleDao --> select() 호출");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			pstmt = conn.prepareStatement("select * from article "
					+ "order by article_no desc limit ?,?");
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, size);
			
			rs=pstmt.executeQuery();
			System.out.println(rs);
			List<Article> result = new ArrayList<>();
			
			while(rs.next()){
				result.add(convertArticle(rs));
			}
			return result;
		} finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}//select

	
	private Date toDate(Timestamp timestamp){
		return new Date(timestamp.getTime());
	}
	
	//게시글 조회
	public Article selectById(Connection conn, int no)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from article where article_no = ?");
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			Article article = null;
			if(rs.next()){
				article = convertArticle(rs);
			}
			return article;
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}//selectById
	
	
	//조회수 증가
	public void increaseReadCount(Connection conn, int no)throws SQLException{
		try(PreparedStatement pstmt = conn.prepareStatement(""
				+ "update article set read_cnt=read_cnt+1 where article_no=?")){
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		}
	}
	
	
	//게시글 삭제
	public int deleteArticle(Connection conn, int no){
		PreparedStatement pstmt = null;
		System.out.println("deleteArticle() 호출");
		try {
			pstmt = conn.prepareStatement("delete from article where article_no = ?");
			pstmt.setInt(1, no);
			return pstmt.executeUpdate();
		}catch(SQLException e){
			System.out.println("deleteArticle()에서 에러 발생");
			return 0;
		}finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	//게시글 수정
	public int update(Connection conn, int no, String title)throws SQLException{
		try(PreparedStatement pstmt = conn.prepareStatement(""
				+ "update article set title=?, moddate=now() where article_no=?")){
			pstmt.setString(1, title);
			pstmt.setInt(2, no);
			return pstmt.executeUpdate();
		}
		
	}
	
	//객체 초기화
	private Article convertArticle(ResultSet rs)throws SQLException{
		return new Article(rs.getInt("article_no"), 
				new Writer(rs.getString("writer_id"), rs.getString("writer_name")), 
				rs.getString("title"), 
				toDate( rs.getTimestamp("regdate")), 
				toDate( rs.getTimestamp("moddate")), 
				rs.getInt("read_cnt"));
	}
}
