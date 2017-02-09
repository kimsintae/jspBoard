package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ArticleData;
import service.ReadArticleService;

public class ReadArticleHandler implements CommandHandler {

	private ReadArticleService readArticleService = new ReadArticleService();
	
	
	@Override
	public String process(HttpServletResponse response, HttpServletRequest request) throws Exception {
		System.out.println("ReadArticleHandler --> process() 호출");
		//조회할 게시글 번호
		String noVal = request.getParameter("no");
		int articleNum = Integer.parseInt(noVal);
		try {
			ArticleData articleData = readArticleService.getArticle(articleNum, true);
			request.setAttribute("articleData", articleData);
			return "/WEB-INF/view/read.jsp";
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			// TODO: handle exception
		}
		return null;
	}
}
