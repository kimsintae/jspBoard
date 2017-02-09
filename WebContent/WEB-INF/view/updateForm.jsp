<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>NOKNOK</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <style>
    /* Remove the navbar's default margin-bottom and rounded borders */ 
    .navbar {
      margin-bottom: 0;
      border-radius: 0;
    }
    
    /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
    .row.content {height: 450px}
    
    /* Set gray background color and 100% height */
    .sidenav {
      padding-top: 20px;
      background-color: #f1f1f1;
      height: 100%;
    }
    
    /* Set black background color, white text and some padding */
    footer {
      background-color: #555;
      color: white;
      padding: 15px;
    }
    
    /* On small screens, set height to 'auto' for sidenav and grid */
    @media screen and (max-width: 767px) {
      .sidenav {
        height: auto;
        padding: 15px;
      }
      .row.content {height:auto;} 
    }

  </style>
</head>
<body>
<jsp:include page="../template/header.jsp">
	<jsp:param value="<%=request.getRequestURI() %>" name="menuName"/>
</jsp:include>

<!-- content -->    
<div class="container-fluid"> 
  <div class="row content">
    <div class="col-sm-2 sidenav"></div>
	    <div class="col-sm-8"> 
	      <h1 class="text-center" >Write</h1>
	      <hr>
				<form action="<%=request.getContextPath()+"/modify.do"%>?no=${modReq.articleNumber}" method="post">
					<input type="hidden" name="pageNo" value="${param.pageNo}"/>
		        	<div class="form-group">
					  <label for="usr">제목:</label>
					  <input type="text" class="form-control" id="usr" name="title" value="${modReq.title}">
					  <c:if test="${errors.title}">제목을 입력하세요.</c:if>
					</div>
		        
			       	<div class="form-group">
					  <label for="comment">내용:</label>
					  <textarea class="form-control" rows="5" id="comment" name="content">${modReq.content}</textarea>
					 </div>
					 <button type="submit" class="btn btn-default">수정</button>
				</form> 
	     </div>
    <div class="col-sm-2 sidenav"></div>
    </div>
</div>


</body>
</html>
