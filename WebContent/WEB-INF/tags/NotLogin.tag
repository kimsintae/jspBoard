<%@ tag body-content="scriptless" language="java" pageEncoding="UTF-8"%>
<%@ tag trimDirectiveWhitespaces="true"%>
<%
	HttpSession httpSession = request.getSession();
	if(httpSession ==null || httpSession.getAttribute("loginUser")==null){
%>

<jsp:doBody/>

<%}%>