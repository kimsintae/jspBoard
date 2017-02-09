<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    
<!DOCTYPE html>
<html lang="en">
<head>
  <title>board</title>
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

	.pageBar{

		width:228px;
		margin: auto;
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
      <h1 class="text-center" >BOARD LIS</h1>
     
			 <table class="table table-hover">
			   <thead>
			     <tr>
			       <th>번호</th>
			       <th>제목</th>
			       <th>작성자</th>
			       <th>조회수</th>
			     </tr>
			   </thead>

			   <tbody>
  			   <c:if test="${articlePage.hasNoArticles()}">
			   	<tr>
			   		<td colspan="4">게시글이 없습니다.</td>
			   	</tr>
			   </c:if>
				   <c:forEach var="article" items="${articlePage.content}">
				     <tr>
				       <td>${article.number}</td>
				       <td>
				       	<a href="read.do?no=${article.number}&pageNo=${articlePage.currentPage}">
				       		<c:out value="${article.title}"/>
				       	</a>
				       </td>
				       <td>${article.writer.name}</td>
				       <td>${article.readCount}</td>
				     </tr>
					</c:forEach>
			   </tbody>
			 </table>
	 		<div class="col-sm-12 subBox text-right">
				<a href="/writeForm.do" class="btn btn-default writeBtn">글쓰기</a>  
			</div>
			<div class="pageBar">
		        <ul class="pagination">
	       		<c:if test="${articlePage.hasArticles()}">
							<c:if test="${articlePage.startPage > 5}">
								<li><a href="list.do?pageNo=${articlePage.startPage-5}">이전</a></li>
							</c:if>
							
							<c:forEach var="pageNum" begin="${articlePage.startPage}" end="${articlePage.endPage}" >
								<li><a href="list.do?pageNo=${pageNum}">${pageNum}</a></li>
							</c:forEach>
							
							<c:if test="${articlePage.endPage<articlePage.totalPages}">
				  				<li><a href="list.do?pageNo=${articlePage.startPage+5}">다음</a></li>
		 	 				</c:if>
				</c:if>									
				</ul>	
			</div>
    </div>
    <div class="col-sm-2 sidenav"></div>
  </div>

</div>
<script>
$(".writeBtn").click(function(){
	if(('${loginUser}')==""){
		alert("로그인 해주세요");
		$(this).attr("href","#");
	}else{
		$(this).attr("href","/writeForm.do");
		
	}
})    
</script>
</body>
</html>
