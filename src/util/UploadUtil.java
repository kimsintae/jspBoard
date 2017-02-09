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
		System.out.println("post 호출");
		System.out.println("UploadUtil --> "+realPath);
		req.setCharacterEncoding("utf-8");
		
		resp.setContentType("text/html; charset=utf-8");	
		String contentType = req.getContentType();
		System.out.println("contentType : "+ contentType);
		if(contentType != null &&
				contentType.toLowerCase().startsWith("multipart/")){
			//컨텐트 타입이 존재하고, multipart 일경우 
			System.out.println("여기까지 옴");
			
			Collection<Part> parts = req.getParts();
			System.out.println("여기까지 옴"+parts);
				for(Part part : parts){
					if(part.getHeader("Content-Disposition").contains("filename=")){
						String filename = part.getSubmittedFileName();
						if(part.getSize()>0){
							//업로드 임시파일을 서버에 저장
							part.write(realPath+ filename);
							System.out.println("업로드 완료");
						}
					}else{String value = req.getParameter(part.getName());}
					
					//업로드 후 페이지 리다이렉트
					resp.sendRedirect("fileUpload.jsp");

			}//for
		}
	}//getUpload()
}
