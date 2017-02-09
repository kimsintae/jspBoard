package handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.InvalidPasswordException;
import exceptions.MemberNotFoundException;
import service.AuthUser;
import service.ChangePasswordService;

public class ChangePasswordHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/userForm.jsp";
	private ChangePasswordService changePwService = new ChangePasswordService();
	
	@Override
	public String process(HttpServletResponse response, HttpServletRequest request) throws Exception {
		if(request.getMethod().equalsIgnoreCase("GET")){
			//GET 방식일 경우
			System.out.println("ChangePasswordHandler --> get방식");
			return processForm(request, response);
		}else if(request.getMethod().equalsIgnoreCase("POST")){
			//POST 방식일 경우
			System.out.println("ChangePasswordHandler --> post방식");
			return processSubmit(request, response);
		}else{
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	private String processForm(HttpServletRequest req, HttpServletResponse res){
		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res)throws Exception{
		System.out.println("ChangePasswordHandler --> processSubmit() 호출");
		AuthUser user = (AuthUser)req.getSession().getAttribute("loginUser");
		System.out.println("수정될 유저 객체 : "+user);
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		String curPwd = req.getParameter("curPwd");
		String newPwd = req.getParameter("newPwd");
		String profile = req.getParameter("profile");
		//현재 비밀번호나 새비밀번호가 입력되지 않았을 경우 errors에 에러 담기
		if(curPwd == null || curPwd.isEmpty()){errors.put("curPwd", Boolean.TRUE);}
		if(newPwd == null || newPwd.isEmpty()){errors.put("newPwd", Boolean.TRUE);}
		if(!errors.isEmpty()){return FORM_VIEW;}

		try {
			changePwService.changePassword(user.getEmail(), curPwd, newPwd,profile);
			res.sendRedirect(req.getContextPath()+"/main.do");
			return null;
		} catch (InvalidPasswordException e) {
			errors.put("badCurPwd",Boolean.TRUE);
			return FORM_VIEW;
		} catch (MemberNotFoundException ex){
			res.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
	}
	
}
