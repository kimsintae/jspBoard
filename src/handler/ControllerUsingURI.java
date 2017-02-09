package handler;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class ControllerUsingURI extends HttpServlet {

	
	// '명령어 = 핸들러클래스이름' 형태로 설정파일에 등록한다.
	// 컨트롤러 서블릿은 설정 파일에서 명령어와 핸들러 클래스의 매핑 정보를 읽어와 명령어에 해당하는
	// 핸들러 클래스 객체를 미리 생성해두었다가 process() 메서드를 통해 사용된다.
	
	
	//<커맨드, 핸들러인스턴스> 매핑 정보 저장
	private Map<String, CommandHandler> commandMap = new HashMap<>();
	
	public void init() throws ServletException{
		
		System.out.println("=============ControllerUsingURI==================");
		String configFile = getInitParameter("configFile");
		System.out.println("web.xml에서 /WEB-INF/commandHandlerURI.properties 초기파라미터값 가져옴");
		Properties prop = new Properties();
		System.out.println("프로퍼티 객체 생성");
		String configFilePath = getServletContext().getRealPath(configFile);
		System.out.println(configFilePath+" 경로 얻음");
		try (FileReader fis = new FileReader(configFilePath)){
			prop.load(fis);
		} catch (IOException e) {
			throw new ServletException(e);
		}
		
		Iterator keyIter = prop.keySet().iterator();
		while(keyIter.hasNext()){
			String viewURI = (String) keyIter.next();
			String handlerClassName = prop.getProperty(viewURI);
			System.out.println("프로퍼티에 존재하는 핸들러 URI와 핸들러 이름  [ "+viewURI+" = "+handlerClassName+" ]");

			
			try {
				//핸들러네임으로 클래스 가져오기(JoinHandler.java)
				Class<?> handlerClass = Class.forName(handlerClassName);
				
				//핸들러클래스 객체 얻기
				CommandHandler handlerInstance = (CommandHandler) handlerClass.newInstance();
				
				//얻은 핸들러클래스 객체와 핸들러의 값(/join.do)을 맵에 담기 
				commandMap.put(viewURI, handlerInstance);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				throw new ServletException(e);
			}
		}//while
		
	}//init()
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("ControllerUsingURI -->  doGet() 호출");
			process(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("ControllerUsingURI -->  doPost() 호출");
		process(req, resp);
	}
	
	private void process(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		System.out.println("ControllerUsingURI --> process() 호출");
		//웹 어플리케이션 내에서의 요청 URI를 얻기
		String command = request.getRequestURI();
		System.out.println("ControllerUsingURI --> process()의 요청 명령어 ="+command);
		if(command.indexOf(request.getContextPath())==0){
			command = command.substring(request.getContextPath().length());
		}
		
		//get이나 post 요청들어오면 commandMap에 담겨있는 요청URI(키값)으로 핸들러 객체 가져옴
		//핸들러 인터페이스 구현
		CommandHandler handler = commandMap.get(command);
		
		if(handler==null){
			handler = new NullHandler();
		}
		
		String viewPage = null;
		try {
			//인터페이스에 의해서
			//각 각의 핸들러객체가 구현하고 있는 process()메서드 호출
			//즉, 각 각의 다른 응답 페이지 URI를 얻는다.
			//다시말해
			//CommandHandler 인터페이스에 의해
			//요구에 맞는 hanler 클래스가 지정되서 로직이 수행된다
			viewPage = handler.process(response, request);
			System.out.println(handler+"가 응답하는 페이지 URI = "+viewPage);
		} catch (Exception e) {
			throw new ServletException();
		}
		if(viewPage!=null){
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}
		
		
	}
	
}
