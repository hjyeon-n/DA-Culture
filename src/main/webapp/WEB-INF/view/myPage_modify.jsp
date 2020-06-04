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

<title>myPage</title>

<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> 
<link href="resources/css/join.css" rel="stylesheet">

<script type="text/javascript">
	$(document).ready(function(){
		$("#modify").on("click", function(){
			if($("#userID").val()==""){
				alert("사용자 아이디를 입력해 주세요.");
				$("#userID").focus();
				return false;
			}
			if($("#email").val()==""){
				alert("이메일 주소를 입력해주세요.");
				$("#email").focus();
				return false;
			} 
			if($("#pwd").val()== ""){
				alert("비밀번호를 입력해주세요.");
				$("#pwd").focus();
				return false;
			}
			else {
				var pwd = $("#pwd").val();
				var passEx =/^[A-Za-z0-9]{6,20}$/; 
	    		if(!passEx.test(pwd)){
	    			alert("비밀번호는 숫자와 문자 조합으로 6자부터 20자까지 입력할 수 있습니다.");
	    			$("#pwd").focus();
	    			return false;
	    		}
			}
    		var pass1 = $("#pwd").val();
    		var pass2 = $("#confirm_pwd").val();
    		if(pass1 != pass2) {
    			alert("비밀번호가 일치하지 않습니다.");
    			$("#confirm_pwd").focus();
    			return false;
    		}    		
		});		
		$("#delete").on("click", function(){
			location.href="${pageContext.request.contextPath}/delete-process";		
		});		
	});
</script>
</head>
<body>
<div class="signup-form">	
    <form action="${pageContext.request.contextPath}/updateUser-process" method="post">
		<h2>My Page</h2>
        <div class="form-group">
			<div class="input-group">
				<span class="input-group-addon"><i class="fa fa-user"></i></span>
				<input type="text" class="form-control" id="userID" name="userID" value="${user.userID}" required="required">
			</div>
        </div>
                
        <div class="form-group">
			<div class="input-group">
				<span class="input-group-addon"><i class="fa fa-paper-plane"></i></span>
				<input type="email" class="form-control" id="email" name="email" value="${user.email}" required="required">
			</div>
        </div>
		<div class="form-group">
			<div class="input-group">
				<span class="input-group-addon"><i class="fa fa-lock"></i></span>
				<input type="password" class="form-control" id="pwd" name="pwd" placeholder="새 비밀번호" required="required">
			</div>
        </div>
		<div class="form-group">
			<div class="input-group">
				<span class="input-group-addon">
					<i class="fa fa-lock"></i>
					<i class="fa fa-check"></i>
				</span>
				<input type="password" class="form-control" id="confirm_pwd" name="confirm_pwd" placeholder="새 비밀번호 확인" required="required">
			</div>
        </div>        
		<div class="form-group">
            <button type="submit" id="modify" class="btn btn-primary btn-block btn-lg">회원정보 수정</button>
        </div>
    </form>
    <button type="button" id="delete" name="delete" class="btn btn-primary btn-block btn-lg" >탈퇴</button>
</div>
</body>
</html>