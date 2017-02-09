package util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterEncodingFilter implements Filter {

	private String encoding;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		req.setCharacterEncoding(encoding);
		chain.doFilter(req, res);
		//System.out.println(encoding+"으로 캐릭터 인코딩 설정");
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		//웹어플리케이션 구동시
		//필터초기화로 encoding 설정
		encoding = filterConfig.getInitParameter("encoding");
		if(encoding==null){
			encoding="UTF-8";
		}
		//System.out.println("필터 초기화 설정");
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}
