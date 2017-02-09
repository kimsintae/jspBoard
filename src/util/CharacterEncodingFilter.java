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
		//System.out.println(encoding+"���� ĳ���� ���ڵ� ����");
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		//�����ø����̼� ������
		//�����ʱ�ȭ�� encoding ����
		encoding = filterConfig.getInitParameter("encoding");
		if(encoding==null){
			encoding="UTF-8";
		}
		//System.out.println("���� �ʱ�ȭ ����");
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}
