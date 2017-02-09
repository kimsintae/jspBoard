<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<style>

.welcomeBox{
	margin-top: 12px;
	margin-right: 50px;
}

.welcome{
	font-size: 20px;
	font-weight: 700;
	color:white;
	line-height: 20px;
}

.menuActive{background: white;}

.profile{
	position: absolute;
	z-index: 1;
	top: 0;
	right: 95px;
}
</style>

<!-- header -->    
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">NOKNOK</a>
    </div>
    <ul class="nav navbar-nav">
      <li><a class="home" href="main.do">Home</a></li>
      <li><a class="board" href="list.do">게시판</a></li>
      <u:Login>
			<li><a class="userForm" href="join.do">내 정보수정</a></li>
      </u:Login>
      <u:NotLogin>
            <li><a class="userForm" href="join.do">회원가입</a></li>
      </u:NotLogin>
    </ul>
    <form class="navbar-form navbar-left">
      <div class="form-group">
        <input type="text" class="form-control" placeholder="Search">
      </div>
      <button type="submit" class="btn btn-default">Submit</button>
    </form>
      <ul class="nav navbar-nav navbar-right">
      		<u:Login>
      	      	<%-- <li class="welcomeBox"><span class="welcome">${loginUser.name}님 환영합니다.</span> --%>
      	      	<li>
	     	      	<a href="logout.do" class="logoutBtn">
	     	      	<span class="glyphicon glyphicon-log-out" aria-hidden="true"></span> 
	     	      	Logout</a>
	     	      	<img src="../img/${loginUser.profile}" class="img-circle profile" alt="Cinque Terre" width="50" height="50"> 
      	      	</li>
			</u:Login>
			
			<u:NotLogin>
		        <li><a href="#" data-toggle="modal" data-target="#loginModal"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
			</u:NotLogin>
        
      </ul>    
  </div>
       
    <!-- Modal -->
  <div class="modal fade" id="loginModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Login</h4>
        </div>

        <div class="modal-body">
            <form class="form-horizontal loginForm" action="login.do" method="post">
            	  <input type="hidden" name="prePage" value="${pageContext.request.requestURI}" />
                  <div class="form-group">
                    <label class="control-label col-sm-2" for="email">Email:</label>
                    <div class="col-sm-10">
                      <input type="email" class="form-control" id="email" name="email" placeholder="Enter email">
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="control-label col-sm-2" for="pwd">Password:</label>
                    <div class="col-sm-10">
                      <input type="password" class="form-control" id="pwd" name="password" placeholder="Enter password">
                    </div>
                  </div>
                  <div class="form-group"> 
                    <div class="col-sm-offset-2 col-sm-10">
                      <div class="checkbox">
                        <label><input type="checkbox"> Remember me</label>
                      </div>
                    </div>
                  </div>
                  <div class="form-group"> 
                    <div class="col-sm-offset-2 col-sm-10">
                      <button type="submit" class="btn btn-default loginBtn">login</button>
                    </div>
                  </div>
            </form>
        </div>
        <div class="modal-footer">
        </div>
      </div>
    </div>
	</div>	
</nav>
<script>
var str = "/WEB-INF/view/";
var menu = '${param.menuName}';
var realVaule = menu.substring(str.length);
//alert(realVaule);
$(".menuActive").removeClass("menuActive");
switch(realVaule){
case "main.jsp":
	$(".home").addClass("menuActive");
	break;
case "board.jsp":
	$(".board").addClass("menuActive");
	break;
case "userForm.jsp":
	$("userForm").addClass("menuActive");
	break;
}
var path = '${pageContext.request.requestURI}';
alert("path : "+path.substring(start, end));


</script>