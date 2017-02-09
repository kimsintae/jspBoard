package service;

import java.sql.Connection;

import dao.ArticleContentDao;
import dao.ArticleDao;
import exceptions.ArticleContentNotFoundException;
import jdbc.ConnectionProvider;
import vo.Article;
import vo.ArticleContent;

public class ReadArticleService {

	private ArticleDao articleDao = new ArticleDao();
	private ArticleContentDao articleContentDao = new ArticleContentDao();
	
	public ArticleData getArticle(int articleNum, boolean increaseReadCount){
		System.out.println("ReadArticleService --> getArticle() »£√‚");
		try(Connection conn = ConnectionProvider.getConnection()){
			Article article = articleDao.selectById(conn, articleNum);
			if(article==null){
				throw new ArticleContentNotFoundException();
			}
			
			ArticleContent articleContent = articleContentDao.selectById(conn, articleNum);
			if(articleContent==null){
				throw new ArticleContentNotFoundException();
			}
			if(increaseReadCount){
				articleDao.increaseReadCount(conn, articleNum);
			}
			return new ArticleData(article, articleContent);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
