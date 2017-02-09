package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckFilter implements Filter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("LoginCheckFilter --> doFilter() 호출");
		HttpServletRequest httpServletRequest = (HttpServletRequest)req;
		HttpSession session = httpServletRequest.getSession();
		if(session==null || session.getAttribute("loginUser")==null){
			HttpServletResponse httpServletResponse = (HttpServletResponse) res;
			httpServletResponse.sendRedirect("/main.do");
			System.out.println("로그인 안되있어서 접근 안됨");
		}else{
			chain.doFilter(req, res);
		}
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
