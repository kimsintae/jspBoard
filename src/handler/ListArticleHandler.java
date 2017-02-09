package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ArticlePage;
import service.ListArticleService;

public class ListArticleHandler implements CommandHandler {

	private ListArticleService listService = new ListArticleService();
	
	@Override
	public String process(HttpServletResponse response, HttpServletRequest request) throws Exception {
		System.out.println("ListArticleHandler --> process() ȣ��");
		String pageNoVal = request.getParameter("pageNo");
		int pageNo = 1;
		if(pageNoVal!=null){
			pageNo = Integer.parseInt(pageNoVal);
		}
		ArticlePage articlePage = listService.getArticlePage(pageNo);
		
		//�̷��� �ϸ� el���� ��� ����
		request.setAttribute("articlePage", articlePage);
		System.out.println("�Ѱ��� ���� content ������ : "+articlePage.getContent().size());
		return "/WEB-INF/view/list.jsp";
	}
}
