package service;

import java.sql.Connection;
import java.sql.SQLException;

import dao.ArticleDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;

public class DeleteArticleService {

	private ArticleDao articleDao = new ArticleDao();
	
	public int deleteService(int no){
		try(Connection conn = ConnectionProvider.getConnection()){
			int result = articleDao.deleteArticle(conn, no);
			return result;
		}catch (SQLException e) {
			return 0;
		}

	}
	
}
