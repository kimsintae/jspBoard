package handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.AuthUser;
import service.WriteArticleService;
import service.WriteRequest;
import vo.Writer;

public class WriteArticleHandler implements CommandHandler{

	private static final String FORM_VIEW = "/WEB-INF/view/writeForm.jsp";
	private WriteArticleService writeService = new WriteArticleService();
	
	
	@Override
	public String process(HttpServletResponse response, HttpServletRequest request) throws Exception {
		System.out.println("WriteArticleHandler --> process() 호출");
		
		if(request.getMethod().equalsIgnoreCase("get")){
			System.out.println("WriteArticleHandler --> get방식");
			return processForm(request, response);
			
		}else if(request.getMethod().equalsIgnoreCase("post")){
			System.out.println("WriteArticleHandler --> post방식");
			return processSubmit(request, response);
			
		}else{
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	
	}//process
	
	private String processForm(HttpServletRequest request, HttpServletResponse response){
		return FORM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest request, HttpServletResponse response){
		System.out.println("WriteArticleHandler --> processSubmit() 호출");
		
		Map<String , Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);
		
		//로그인 유저 객체
		AuthUser user = (AuthUser)request.getSession().getAttribute("loginUser");
		
		WriteRequest writeReq = createWriteRequest(user, request);
		writeReq.validate(errors);
		if(!errors.isEmpty()){
			return FORM_VIEW;
		}
		
		int newArticleNo = writeService.write(writeReq);
		request.setAttribute("newArticleNo", newArticleNo);
		try {
			response.sendRedirect("/list.do");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private WriteRequest createWriteRequest(AuthUser user, HttpServletRequest request){
		return new WriteRequest(new Writer(user.getEmail(), user.getName()), request.getParameter("title"), request.getParameter("content"));
	}
}
