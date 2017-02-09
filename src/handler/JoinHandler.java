package handler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import exceptions.DuplicateIdException;
import service.JoinRequest;
import service.JoinService;


public class JoinHandler implements CommandHandler {

	//join.do를 통해서 응답할 페이지
	private static String FORM_VIEW = "/WEB-INF/view/userForm.jsp";
	private JoinService joinService = new JoinService();
	
	
	@Override
	public String process(HttpServletResponse response, HttpServletRequest request) throws Exception {

		if(request.getMethod().equalsIgnoreCase("GET")){
			System.out.println("JoinHandler -->  process()호출 하고 get방식 받음");
			//GET방식으로 요청이 들어오면 
			//processForm 메서드 동작
			// /WEB-INF/view/joinForm.jsp 경로 리턴
			return processForm(request, response);
		}else if(request.getMethod().equalsIgnoreCase("POST")){
			//POST방식으로 요청이 들어오면 
			//processSubmit 메서드 동작(JoinRequest 필드에 값 셋팅)
			System.out.println("JoinHandler -->  process()호출 하고 post방식 받음");
			return processSubmit(request, response);
		}else{
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
		
	}
	
	private String processForm(HttpServletRequest req, HttpServletResponse res){
		return FORM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res){
		System.out.println("JoinHandler --> processSubmit() 호출");
		System.out.println(req.getServletContext().getRealPath("img"));

		
		// error) enctype="multipart/form-data" 하면 request로 파라미터로 접근이 불가
		// 멀티파트로 인코딩 햇을경우 
		// Part를 이용해서 얻는다.
		
		if(req.getMethod().equalsIgnoreCase("post")){
			System.out.println("post로 넘어옴");
		}
			

		//회원가입 폼에서 넘어온 파라미터들 
		//파라미터값을 joinRequest 객체에 값을 초기화
		JoinRequest joinReq = new JoinRequest();
		joinReq.setEmail(req.getParameter("email"));
		joinReq.setName(req.getParameter("name"));
		joinReq.setPassword(req.getParameter("password"));
		joinReq.setConfirmPassword(req.getParameter("confirmPassword"));
		joinReq.setProfile(req.getParameter("profile"));
		
		System.out.println("processSubmit()에서 받은 파라미터들 "+req.getParameter("email")+","+req.getParameter("name")+","+req.getParameter("password")+","+req.getParameter("confirmPassword"));
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		joinReq.validate(errors);
		System.out.println("에러들 : "+ errors.toString());
		if(!errors.isEmpty()){
			System.out.println("칸이 작성되지 않음");
			//작성되지 않은 칸이 있을 경우 form으로 되돌림
			return FORM_VIEW;
		}
		try {
			//회원가입 수행
			joinService.join(joinReq);
			return "/WEB-INF/view/main.jsp";
		} catch (DuplicateIdException e) {
			errors.put("duplicateEmail", Boolean.TRUE);
			return FORM_VIEW;
		}
	}
}
