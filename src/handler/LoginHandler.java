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
		System.out.println("LoginHandler --> process()�޼��� ȣ��");
		
		if(request.getMethod().equalsIgnoreCase("get")){
			//��û�� get����� ���
			return processForm(request, response);
		}
		if(request.getMethod().equalsIgnoreCase("post")){
			//��û�� post����� ���
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
	
		//�α��� ��ư Ŭ���� �Է��� �̸��ϰ� ��й�ȣ�� �޾ƿ�
		String email = trim(request.getParameter("email"));
		String password = trim(request.getParameter("password"));
		String prePage = request.getParameter("prePage");

		/*
		 *  /chap21_Model2_board(ver.2)/WEB-INF/view/main.jsp
		 *  
		 *  ���ؽ�Ʈ �н� �����η� ���� �α��� ����
		 */
		
		String realPrePage=prePage.substring(13,prePage.indexOf("."))+".do";
		
		System.out.println("������ �α���â�� �Է��� �̸���, ��й�ȣ, ���������� -->"+email+" / "+password+" / "+prePage);
		System.out.println("realPrePage : "+realPrePage);
		
		//�α��ν� ������ ǥ���ϱ����� setAttribute�� ���� ����
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);
		
		if(email == null || email.isEmpty()){errors.put("email", Boolean.TRUE);}
		if(password==null || password.isEmpty()){errors.put("password", Boolean.TRUE);}
		
		//�߸� �Էµ� ������ �ϳ��� �ִٸ� ���� �������� ����
		if(!errors.isEmpty()){return prePage;}
		
		try {
			//�α��� ����
			AuthUser user = logoinService.login(email, password);
			System.out.println("�α��� ���� : "+user);
			
			//�α��ε� ������ ������ ����
			request.getSession().setAttribute("loginUser", user);

			//���� ��������
			System.out.println("���������� :"+ realPrePage);
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
