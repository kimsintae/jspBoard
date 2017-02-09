package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.ArticleDao;
import jdbc.ConnectionProvider;
import vo.Article;

public class ListArticleService {

	private ArticleDao articleDao = new ArticleDao();
	private int size = 10;//������ �Խñ� ��
	
	public ArticlePage getArticlePage(int pageNum){
		System.out.println("ListArticleService --> getArticlePage() ȣ��");
		try(Connection conn = ConnectionProvider.getConnection()){
			
			//�� �Խñ� �� �ޱ�
			int total = articleDao.selectCount(conn);
			// (pageNum-1)*size�� �Խñ��� ��ȸ�� ���� ��ȣ
			List<Article> content = articleDao.select(conn, (pageNum-1)*size, size);
			//System.out.println("getArticlePage --> �޾ƿ� �Խñ� �� : "+content.size());
			return new ArticlePage(total, pageNum, size, content);
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
