package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutHandler implements CommandHandler {

	@Override
	public String process(HttpServletResponse response, HttpServletRequest request) throws Exception {
		System.out.println("LogoutHandler --> process() 호출");
		HttpSession session = request.getSession(false);
		if(session != null){
			//로그인 되어있을 때
			//세션 초기화
			session.invalidate();
		}
		response.sendRedirect("/main.do");
		return null;
	}//process
}
