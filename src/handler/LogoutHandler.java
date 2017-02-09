package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutHandler implements CommandHandler {

	@Override
	public String process(HttpServletResponse response, HttpServletRequest request) throws Exception {
		System.out.println("LogoutHandler --> process() ȣ��");
		HttpSession session = request.getSession(false);
		if(session != null){
			//�α��� �Ǿ����� ��
			//���� �ʱ�ȭ
			session.invalidate();
		}
		response.sendRedirect("/main.do");
		return null;
	}//process
}
