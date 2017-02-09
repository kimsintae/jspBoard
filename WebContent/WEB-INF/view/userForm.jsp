<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
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

<div class="container-fluid text-center">    
  <div class="row content">
    <div class="col-sm-2 sidenav"></div>
    <u:Login>
		    <div class="col-sm-8 text-left"> 
		      <h1 class="text-center">정보 수정</h1>
		        <hr />
		        <form class="form-horizontal" action="changPwd.do" method="post" enctype="multipart/form-data">
		            <div class="form-group">
		              <label class="col-sm-2 control-label">이메일</label>
		              <div class="col-sm-10">
		                <input class="form-control " id="email" name="email" type="email" value="${loginUser.email}" disabled="disabled">
		              </div>
		            </div>
		            <div class="form-group">
		              <label class="col-sm-2 control-label">이름</label>
		              <div class="col-sm-10">
		                <input class="form-control" id="name" name="name" type="text" value="${loginUser.name}" disabled="disabled">
		              </div>
		            </div>
		            <div class="form-group">
		              <label class="col-sm-2 control-label">현재 비밀번호</label>
		              <div class="col-sm-10">
		                <input class="form-control" id="password" name="curPwd" type="password" value="">
		                <span class="alert-danger"><c:if test="${errors.badCurPwd}">비밀번호가 틀렸습니다.</c:if></span>
		              </div>
		            </div>
		                        <div class="form-group">
		              <label class="col-sm-2 control-label">새 비밀번호</label>
		              <div class="col-sm-10">
		                <input class="form-control" id="password" name="newPwd" type="password" value="">
		              </div>
		            </div>
		            
		            <div class="form-group">
		              <label class="col-sm-2 control-label">프로필 사진</label>
		              <div class="col-sm-3">
		                <input class="form-control" id="profile" name="profile" type="file" accept="image/jpeg,jpg,png[, MIME_TYPES]">
		              </div>
		            </div>
		             <button type="submit" class="btn btn-default col-sm-2">수정</button>
		        </form>
	        </div>
     </u:Login> 

    <u:NotLogin>
		   <div class="col-sm-8 text-left"> 
		     <h1 class="text-center">회원가입</h1>
		       <hr />
		       <form class="form-horizontal" action="join.do" method="post" enctype="multipart/form-data">
		           <div class="form-group">
		             <label class="col-sm-2 control-label">이메일</label>
		             <div class="col-sm-10">
		               <input class="form-control " id="email" name="email" type="email" value="${param.email}">
		               <c:if test="${errors.email}">이메일을 입력해주세요</c:if>
		               <c:if test="${errors.duplicateEmail}">이미 사용중인 이메일입니다.</c:if>
		             </div>
		           </div>
		           <div class="form-group">
		             <label class="col-sm-2 control-label">이름</label>
		             <div class="col-sm-10">
		               <input class="form-control" id="name" name="name" type="text" value="${param.name}">
		               <c:if test="${errors.name}">이름을 입력해주세요</c:if>
		             </div>
		           </div>
		           <div class="form-group">
		             <label class="col-sm-2 control-label">비밀번호</label>
		             <div class="col-sm-10">
		               <input class="form-control" id="password" name="password" type="password" value="${param.password}">
		               <c:if test="${errors.password}">비밀번호를 입력해주세요</c:if>
		             </div>
		           </div>
		           <div class="form-group">
		             <label class="col-sm-2 control-label">비밀번호 확인</label>
		             <div class="col-sm-10">
		               <input class="form-control" id="confirmPw" name="confirmPassword" type="password" value="">
		               <c:if test="${errors.confirmPassword}">비밀번호를 확인 해주세요</c:if>
		               <c:if test="${errors.notMatch}">비밀번호가 다릅니다.</c:if>
		             </div>
		           </div>
		           
		          	<div class="form-group">
		             <label class="col-sm-2 control-label">프로필 사진</label>
		             <div class="col-sm-3">
		               <input class="form-control" id="profile" name="profile" type="file" accept="image/jpeg,jpg,png[, MIME_TYPES]">
		             </div>
		           </div>
		            <button type="submit" class="btn btn-default col-sm-2 text-right">JOIN</button>
		       </form>
			</div>
     </u:NotLogin> 
    <div class="col-sm-2 sidenav"></div>
  </div>
</div>

</body>
</html>
