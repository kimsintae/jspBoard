<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true"%>
<%@ attribute name="writer" type="java.lang.String" required="true"%>
<%@ attribute name="loginUserName" type="java.lang.String" required="true"%>
<%
	if(writer.equals(loginUserName)){
		System.out.println("동일한 유저입니다.");
%>
<jsp:doBody/>
<% 	
	}
%>
