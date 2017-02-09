package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.DeleteArticleService;

public class DelteArticleHandler implements CommandHandler{

	private DeleteArticleService deleteService = new DeleteArticleService();
	
	@Override
	public String process(HttpServletResponse response, HttpServletRequest request) throws Exception {
		System.out.println("DelteArticleHandler --> process() »£√‚");
		String noStr = request.getParameter("no");
		int no = Integer.parseInt(noStr);
		
		int result = deleteService.deleteService(no);
		request.setAttribute("deleteMsg", result);
		response.sendRedirect("/list.do");
		return null;
	}
}
