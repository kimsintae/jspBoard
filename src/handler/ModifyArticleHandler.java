package handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.ArticleNotFoundException;
import service.ArticleData;
import service.AuthUser;
import service.ModifyArticleService;
import service.ModifyRequest;
import service.ReadArticleService;

public class ModifyArticleHandler implements CommandHandler {

	private static final String form_view = "/WEB-INF/view/updateForm.jsp";
	
	private ModifyArticleService modifyArticleService = new ModifyArticleService();
	private ReadArticleService readService = new ReadArticleService();
	@Override
	public String process(HttpServletResponse response, HttpServletRequest request) throws Exception {

		if(request.getMethod().equalsIgnoreCase("GET")){
			System.out.println("ModifyArticleHandler -->  process()호출 하고 get방식 받음");
			return processForm(request, response);
		}else if(request.getMethod().equalsIgnoreCase("POST")){
			System.out.println("ModifyArticleHandler -->  process()호출 하고 post방식 받음");
			return processSubmit(request, response);
		}else{
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
		
	}//process
	
	private String processForm(HttpServletRequest request, HttpServletResponse response)throws IOException{
		try {
			String noVal = request.getParameter("no");
			int no = Integer.parseInt(noVal);
			
			ArticleData articleData = readService.getArticle(no, false);
			AuthUser user = (AuthUser)request.getSession().getAttribute("loginUser");
			ModifyRequest modifyRequest = new ModifyRequest(user.getEmail(),no, articleData.getArticle().getTitle(), articleData.getArticleContent().getContent());
			
			request.setAttribute("modReq", modifyRequest);
			return form_view;
		} catch (ArticleNotFoundException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
	
	private String processSubmit(HttpServletRequest request, HttpServletResponse response)throws IOException{
		AuthUser user = (AuthUser)request.getSession().getAttribute("loginUser");
		String pageNo = request.getParameter("pageNo");
		String noVal = request.getParameter("no");
		int no = Integer.parseInt(noVal);
		
		ModifyRequest modifyRequest= new ModifyRequest(user.getEmail(), no, request.getParameter("title"), request.getParameter("content"));
		request.setAttribute("modReq", modifyRequest);
		System.out.println(modifyRequest);
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);
		modifyRequest.validate(errors);
		if(!errors.isEmpty()){
			return form_view;
		}
		
		try {
			System.out.println("여기까지성공?");
			modifyArticleService.modify(modifyRequest);
			response.sendRedirect("/read.do?no="+no+"&pageNo="+pageNo);
			return null;
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

	}
	
}
