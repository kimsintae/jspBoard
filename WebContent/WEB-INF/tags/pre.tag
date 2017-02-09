<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="value" type="java.lang.String" required="true"%>
<%
	//value = value.replace("\n", "\n<br/>");//띄워쓰기 치환
	value = value.replace("&", "&amp;");//엔퍼센트 치환
	value = value.replace("<", "&lt;");//꺽새 치환
	value = value.replace(" ", "&nbsp;");//띄워쓰기 치환
%>
<%=value%>