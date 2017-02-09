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

	
	// '��ɾ� = �ڵ鷯Ŭ�����̸�' ���·� �������Ͽ� ����Ѵ�.
	// ��Ʈ�ѷ� ������ ���� ���Ͽ��� ��ɾ�� �ڵ鷯 Ŭ������ ���� ������ �о�� ��ɾ �ش��ϴ�
	// �ڵ鷯 Ŭ���� ��ü�� �̸� �����صξ��ٰ� process() �޼��带 ���� ���ȴ�.
	
	
	//<Ŀ�ǵ�, �ڵ鷯�ν��Ͻ�> ���� ���� ����
	private Map<String, CommandHandler> commandMap = new HashMap<>();
	
	public void init() throws ServletException{
		
		System.out.println("=============ControllerUsingURI==================");
		String configFile = getInitParameter("configFile");
		System.out.println("web.xml���� /WEB-INF/commandHandlerURI.properties �ʱ��Ķ���Ͱ� ������");
		Properties prop = new Properties();
		System.out.println("������Ƽ ��ü ����");
		String configFilePath = getServletContext().getRealPath(configFile);
		System.out.println(configFilePath+" ��� ����");
		try (FileReader fis = new FileReader(configFilePath)){
			prop.load(fis);
		} catch (IOException e) {
			throw new ServletException(e);
		}
		
		Iterator keyIter = prop.keySet().iterator();
		while(keyIter.hasNext()){
			String viewURI = (String) keyIter.next();
			String handlerClassName = prop.getProperty(viewURI);
			System.out.println("������Ƽ�� �����ϴ� �ڵ鷯 URI�� �ڵ鷯 �̸�  [ "+viewURI+" = "+handlerClassName+" ]");

			
			try {
				//�ڵ鷯�������� Ŭ���� ��������(JoinHandler.java)
				Class<?> handlerClass = Class.forName(handlerClassName);
				
				//�ڵ鷯Ŭ���� ��ü ���
				CommandHandler handlerInstance = (CommandHandler) handlerClass.newInstance();
				
				//���� �ڵ鷯Ŭ���� ��ü�� �ڵ鷯�� ��(/join.do)�� �ʿ� ��� 
				commandMap.put(viewURI, handlerInstance);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				throw new ServletException(e);
			}
		}//while
		
	}//init()
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("ControllerUsingURI -->  doGet() ȣ��");
			process(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("ControllerUsingURI -->  doPost() ȣ��");
		process(req, resp);
	}
	
	private void process(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		System.out.println("ControllerUsingURI --> process() ȣ��");
		//�� ���ø����̼� �������� ��û URI�� ���
		String command = request.getRequestURI();
		System.out.println("ControllerUsingURI --> process()�� ��û ��ɾ� ="+command);
		if(command.indexOf(request.getContextPath())==0){
			command = command.substring(request.getContextPath().length());
		}
		
		//get�̳� post ��û������ commandMap�� ����ִ� ��ûURI(Ű��)���� �ڵ鷯 ��ü ������
		//�ڵ鷯 �������̽� ����
		CommandHandler handler = commandMap.get(command);
		
		if(handler==null){
			handler = new NullHandler();
		}
		
		String viewPage = null;
		try {
			//�������̽��� ���ؼ�
			//�� ���� �ڵ鷯��ü�� �����ϰ� �ִ� process()�޼��� ȣ��
			//��, �� ���� �ٸ� ���� ������ URI�� ��´�.
			//�ٽø���
			//CommandHandler �������̽��� ����
			//�䱸�� �´� hanler Ŭ������ �����Ǽ� ������ ����ȴ�
			viewPage = handler.process(response, request);
			System.out.println(handler+"�� �����ϴ� ������ URI = "+viewPage);
		} catch (Exception e) {
			throw new ServletException();
		}
		if(viewPage!=null){
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}
		
		
	}
	
}
