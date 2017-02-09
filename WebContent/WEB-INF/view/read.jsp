<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
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
	.sidenav{background: white;}
	.read-table tr td:nth-child(1){
		width: 80px;
	}
	.read-btns{margin:0 10px 10px 0}
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
	      <h1 class="text-center" >Read</h1>
	      <hr>
	      	 <div class="btn-group read-btns">
	      	  
	      	 <c:set var="pageNo" value="${empty param.pageNo ? '1' : param.pageNo}"/>
			  <a href="list.do?pageNo=${pageNo}" class="btn btn-default read-btns">목록</a>
			  
			  <u:confirmArticleOwner loginUserName="${loginUser.name}" writer="${articleData.article.writer.name}">
			  
			  <a href="modify.do?no=${articleData.article.number}&pageNo=${param.pageNo}" class="btn btn-default read-btns">수정</a>
			  <a href="delete.do?no=${articleData.article.number}" class="btn btn-default read-btns">삭제</a>
			  </u:confirmArticleOwner>
			</div>

			 <table class="table table-condensed table-bordered read-table">
			   <tbody>
			     <tr>
			       <td>NO</td>
			       <td>${articleData.article.number}</td>
			     </tr>
			     <tr>
			       <td>작성자</td>
			       <td>${articleData.article.writer.name}</td>
			     </tr>
			     <tr>
			       <td>제목</td>
			       <td>${articleData.article.title}</td>
			     </tr>
			     
				<tr>
			       <td colspan="2">
			       <u:pre value="${articleData.articleContent.content}"/>
			       </td>
			     </tr>
			   </tbody>
			 </table>
        </div>

        <div class="col-sm-2 sidenav"></div>
    </div>
   
</div><!--//container-fluid  -->


</body>
</html>
