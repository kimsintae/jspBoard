package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainHandler implements CommandHandler {

	private static String FORM_VIEW = "/WEB-INF/view/main.jsp";
	
	@Override
	public String process(HttpServletResponse response, HttpServletRequest request) throws Exception {
		System.out.println("MainHandler process()�޼��� ȣ��");
		return FORM_VIEW;
	}
}
