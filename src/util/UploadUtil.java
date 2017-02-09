package util;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class UploadUtil{

/*	private String realPath;
	
	
	
	public UploadUtil(String realPath) {
		super();
		this.realPath = realPath;
	}*/
	
	
	public void getUpload(HttpServletRequest req, HttpServletResponse resp,String realPath)throws IOException,ServletException{
		System.out.println("post ȣ��");
		System.out.println("UploadUtil --> "+realPath);
		req.setCharacterEncoding("utf-8");
		
		resp.setContentType("text/html; charset=utf-8");	
		String contentType = req.getContentType();
		System.out.println("contentType : "+ contentType);
		if(contentType != null &&
				contentType.toLowerCase().startsWith("multipart/")){
			//����Ʈ Ÿ���� �����ϰ�, multipart �ϰ�� 
			System.out.println("������� ��");
			
			Collection<Part> parts = req.getParts();
			System.out.println("������� ��"+parts);
				for(Part part : parts){
					if(part.getHeader("Content-Disposition").contains("filename=")){
						String filename = part.getSubmittedFileName();
						if(part.getSize()>0){
							//���ε� �ӽ������� ������ ����
							part.write(realPath+ filename);
							System.out.println("���ε� �Ϸ�");
						}
					}else{String value = req.getParameter(part.getName());}
					
					//���ε� �� ������ �����̷�Ʈ
					resp.sendRedirect("fileUpload.jsp");

			}//for
		}
	}//getUpload()
}
