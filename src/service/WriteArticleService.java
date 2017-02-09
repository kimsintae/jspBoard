package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import dao.ArticleContentDao;
import dao.ArticleDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import vo.Article;
import vo.ArticleContent;

public class WriteArticleService {

	private ArticleDao articleDao = new ArticleDao();
	private ArticleContentDao articleContentDao = new ArticleContentDao();
	
	public Integer write(WriteRequest req){
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			Article article = toArticle(req);
			Article savedArticle = articleDao.insert(conn, article);
			if(savedArticle == null){
				throw new RuntimeException("fail to insert article");
			}
			
			ArticleContent content = new ArticleContent(savedArticle.getNumber(), req.getContent());
			ArticleContent savedContent = articleContentDao.insert(conn, content);
			if(savedContent == null){
				throw new RuntimeException("fail to insert article_content");
			}
			
			conn.commit();
			return savedArticle.getNumber();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		}catch (RuntimeException e) {
			JdbcUtil.rollback(conn);
			throw e;
		}finally {
			JdbcUtil.close(conn);
		}
	}//write
	
	private Article toArticle(WriteRequest req){
		Date now = new Date();
		return new Article(null, req.getWriter(), req.getTitle(), now, now, 0);
	}
}
