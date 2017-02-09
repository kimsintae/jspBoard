package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.ArticleDao;
import jdbc.ConnectionProvider;
import vo.Article;

public class ListArticleService {

	private ArticleDao articleDao = new ArticleDao();
	private int size = 10;//보여질 게시글 수
	
	public ArticlePage getArticlePage(int pageNum){
		System.out.println("ListArticleService --> getArticlePage() 호출");
		try(Connection conn = ConnectionProvider.getConnection()){
			
			//총 게시글 수 받기
			int total = articleDao.selectCount(conn);
			// (pageNum-1)*size은 게시글을 조회할 시작 번호
			List<Article> content = articleDao.select(conn, (pageNum-1)*size, size);
			//System.out.println("getArticlePage --> 받아온 게시글 수 : "+content.size());
			return new ArticlePage(total, pageNum, size, content);
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
