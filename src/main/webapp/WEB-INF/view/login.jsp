<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Sign in</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> 
<link href="resources/css/join.css" rel="stylesheet">
</head>
<body>
<div class="signup-form">	
    <form action="/login-process" method="post">
		<h2>Login</h2>
		<!-- <p class="lead">It's free and hardly takes more than 30 seconds.</p> -->
        <div class="form-group">
			<div class="input-group">
				<span class="input-group-addon"><i class="fa fa-user"></i></span>
				<input type="text" class="form-control" id="userID" name="userID" placeholder="ID" required="required">
			</div>
        </div>
     
		<div class="form-group">
			<div class="input-group">
				<span class="input-group-addon"><i class="fa fa-lock"></i></span>
				<input type="password" class="form-control" id="pwd" name="pwd" placeholder="PW" required="required">
			</div>
        </div>
        
        <!-- 로그인 실패 시 알림  -->
        <c:if test="${loginfail}">
        	<div><font color="#EC7063">가입하지 않은 아이디 혹은 잘못된 비밀번호입니다.</font></div>
        	<br>
        </c:if>
      
		<div class="form-group">
            <button type="submit" id="submit" class="btn btn-primary btn-block btn-lg">Login</button>
        </div>
    </form>
	<div class="text-center">회원이 아니신가요? <a href="${pageContext.request.contextPath}/join">Join here</a>.</div>
	<div class="text-center">메인으로 돌아가기. <a href="${pageContext.request.contextPath}/">Main</a>.</div>
</div>
</body>
</html>                                                  