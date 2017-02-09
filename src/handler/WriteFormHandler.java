package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WriteFormHandler implements CommandHandler {
	
@Override
public String process(HttpServletResponse response, HttpServletRequest request) throws Exception {
	// TODO Auto-generated method stub
	return "/WEB-INF/view/writeForm.jsp";
}
}
