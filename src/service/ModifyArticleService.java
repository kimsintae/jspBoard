package service;

import java.sql.Connection;

import dao.ArticleContentDao;
import dao.ArticleDao;
import exceptions.ArticleNotFoundException;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import vo.Article;

public class ModifyArticleService {

	private ArticleDao articleDao = new ArticleDao();
	private ArticleContentDao articleContentDao = new ArticleContentDao();
	
	public void modify(ModifyRequest modifyRequest){
		Connection conn = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			Article article = articleDao.selectById(conn, modifyRequest.getArticleNumber());
			if(article==null){
				throw new ArticleNotFoundException();
			}
			
			int rs = articleDao.update(conn, modifyRequest.getArticleNumber(), modifyRequest.getTitle());
			System.out.println("제목 수정 결과 "+rs);
			int rs2 = articleContentDao.update(conn, modifyRequest.getArticleNumber(), modifyRequest.getContent());
			System.out.println("내용 수정 결과 "+rs2);
			conn.commit();
		} catch (Exception e) {
			JdbcUtil.rollback(conn);
			JdbcUtil.close(conn);
			throw new RuntimeException(e);
		}finally {
			JdbcUtil.close(conn);
		}
	}//modify

}
