package handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.LoginFailException;
import javafx.application.Application;
import service.AuthUser;
import service.LoginService;

public class LoginHandler implements CommandHandler {

	
	private static final String FORM_VIEW = "/WEB-INF/view/main.jsp";
	private LoginService logoinService = new LoginService();
	
	@Override
	public String process(HttpServletResponse response, HttpServletRequest request) throws Exception {
		System.out.println("LoginHandler --> process()메서드 호출");
		
		if(request.getMethod().equalsIgnoreCase("get")){
			//요청이 get방식일 경우
			return processForm(request, response);
		}
		if(request.getMethod().equalsIgnoreCase("post")){
			//요청이 post방식일 경우
			return processSubmit(request, response);
		}else{
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

	}
	
	private String processForm(HttpServletRequest request, HttpServletResponse response){
		return FORM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest request, HttpServletResponse response){
	
		//로그인 버튼 클릭시 입력한 이메일과 비밀번호를 받아옴
		String email = trim(request.getParameter("email"));
		String password = trim(request.getParameter("password"));
		String prePage = request.getParameter("prePage");

		/*
		 *  /chap21_Model2_board(ver.2)/WEB-INF/view/main.jsp
		 *  
		 *  컨텍스트 패스 절대경로로 인한 로그인 문제
		 */
		
		String realPrePage=prePage.substring(13,prePage.indexOf("."))+".do";
		
		System.out.println("유저가 로그인창에 입력한 이메일, 비밀번호, 이전페이지 -->"+email+" / "+password+" / "+prePage);
		System.out.println("realPrePage : "+realPrePage);
		
		//로그인시 에러를 표시하기위해 setAttribute로 맵을 저장
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);
		
		if(email == null || email.isEmpty()){errors.put("email", Boolean.TRUE);}
		if(password==null || password.isEmpty()){errors.put("password", Boolean.TRUE);}
		
		//잘못 입력된 정보가 하나라도 있다면 원래 페이지로 보냄
		if(!errors.isEmpty()){return prePage;}
		
		try {
			//로그인 수행
			AuthUser user = logoinService.login(email, password);
			System.out.println("로그인 성공 : "+user);
			
			//로그인된 유저의 정보를 저장
			request.getSession().setAttribute("loginUser", user);

			//이전 페이지로
			System.out.println("이전페이지 :"+ realPrePage);
			response.sendRedirect(realPrePage);

			return null;
		} catch (LoginFailException e) {
			errors.put("notMatch", Boolean.TRUE);
			return prePage;
		} catch(IOException ex){
			return prePage;
		}
		
	}

	private String trim(String str) {
		// TODO Auto-generated method stub
		return str == null ? null: str.trim();
	}
}
