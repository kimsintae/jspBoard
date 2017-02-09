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

	//join.do�� ���ؼ� ������ ������
	private static String FORM_VIEW = "/WEB-INF/view/userForm.jsp";
	private JoinService joinService = new JoinService();
	
	
	@Override
	public String process(HttpServletResponse response, HttpServletRequest request) throws Exception {

		if(request.getMethod().equalsIgnoreCase("GET")){
			System.out.println("JoinHandler -->  process()ȣ�� �ϰ� get��� ����");
			//GET������� ��û�� ������ 
			//processForm �޼��� ����
			// /WEB-INF/view/joinForm.jsp ��� ����
			return processForm(request, response);
		}else if(request.getMethod().equalsIgnoreCase("POST")){
			//POST������� ��û�� ������ 
			//processSubmit �޼��� ����(JoinRequest �ʵ忡 �� ����)
			System.out.println("JoinHandler -->  process()ȣ�� �ϰ� post��� ����");
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
		System.out.println("JoinHandler --> processSubmit() ȣ��");
		System.out.println(req.getServletContext().getRealPath("img"));

		
		// error) enctype="multipart/form-data" �ϸ� request�� �Ķ���ͷ� ������ �Ұ�
		// ��Ƽ��Ʈ�� ���ڵ� ������� 
		// Part�� �̿��ؼ� ��´�.
		
		if(req.getMethod().equalsIgnoreCase("post")){
			System.out.println("post�� �Ѿ��");
		}
			

		//ȸ������ ������ �Ѿ�� �Ķ���͵� 
		//�Ķ���Ͱ��� joinRequest ��ü�� ���� �ʱ�ȭ
		JoinRequest joinReq = new JoinRequest();
		joinReq.setEmail(req.getParameter("email"));
		joinReq.setName(req.getParameter("name"));
		joinReq.setPassword(req.getParameter("password"));
		joinReq.setConfirmPassword(req.getParameter("confirmPassword"));
		joinReq.setProfile(req.getParameter("profile"));
		
		System.out.println("processSubmit()���� ���� �Ķ���͵� "+req.getParameter("email")+","+req.getParameter("name")+","+req.getParameter("password")+","+req.getParameter("confirmPassword"));
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		joinReq.validate(errors);
		System.out.println("������ : "+ errors.toString());
		if(!errors.isEmpty()){
			System.out.println("ĭ�� �ۼ����� ����");
			//�ۼ����� ���� ĭ�� ���� ��� form���� �ǵ���
			return FORM_VIEW;
		}
		try {
			//ȸ������ ����
			joinService.join(joinReq);
			return "/WEB-INF/view/main.jsp";
		} catch (DuplicateIdException e) {
			errors.put("duplicateEmail", Boolean.TRUE);
			return FORM_VIEW;
		}
	}
}
