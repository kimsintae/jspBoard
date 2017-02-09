package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ArticlePage;
import service.ListArticleService;

public class ListArticleHandler implements CommandHandler {

	private ListArticleService listService = new ListArticleService();
	
	@Override
	public String process(HttpServletResponse response, HttpServletRequest request) throws Exception {
		System.out.println("ListArticleHandler --> process() 호출");
		String pageNoVal = request.getParameter("pageNo");
		int pageNo = 1;
		if(pageNoVal!=null){
			pageNo = Integer.parseInt(pageNoVal);
		}
		ArticlePage articlePage = listService.getArticlePage(pageNo);
		
		//이렇게 하면 el에서 사용 가능
		request.setAttribute("articlePage", articlePage);
		System.out.println("넘겨줄 최종 content 사이즈 : "+articlePage.getContent().size());
		return "/WEB-INF/view/list.jsp";
	}
}
