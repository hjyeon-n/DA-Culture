<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<style>
div#wrapper { 
    padding: 5px 20px;
    position: absolute;
    top: 50%;
    left: 50%;
    width: 450px; height: 250px;
    margin-left: -220px;
    margin-top : -170px;
    
    display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
} 
</style>
<script>
/* 회원정보 수정 전 비밀번호 확인  */
$(document).ready(function(){
	$("#btnConfirm").on("click", function(){
		var pass1 = $("#pwd").val();
		var pass2 = $("#confirm_pwd").val();
		if(pass1 != pass2) {
			alert("비밀번호가 일치하지 않습니다.");
			$("#confirm_pwd").focus();
			return false;
		}
		var confirmPW = $("#pwd").val();
		location.href="${pageContext.request.contextPath}/myPage-modify?confirmPW=" + confirmPW;
	});
	
	$("#btnBoard").on("click", function(){
		location.href="${pageContext.request.contextPath}/myMovieBoard";
	});

	$("#btnLike").on("click", function(){
		location.href="${pageContext.request.contextPath}/userLikesMovie";
	});
});
</script>
</head>
<body>
<div id="wrapper">
	<div class="signup-form">	
		<div class="form-group">
			<button type="button" id="btnBoard" class="btn btn-primary btn-block btn-lg">내가 쓴 글</button>
			<button type="button" id="btnLike" class="btn btn-primary btn-block btn-lg">내가 추천한 글</button>
			<button type="button" id="btnModify" class="btn btn-primary btn-block btn-lg" data-toggle="modal" data-target="#myModal">회원정보 수정</button>
		</div>
			<c:if test="${passError}">
	        	<div><font color="#EC7063">잘못된 비밀번호입니다.</font></div>
	        	<br>
        	</c:if>
		</div>
</div>

<!-- Modal HTML -->
<div id="myModal" class="modal fade">
	<div class="modal-dialog modal-login">
		<div class="modal-content">
			<div class="modal-header">				
				<h4 class="modal-title">비밀번호 확인</h4>
			</div>
			<div class="modal-body">
				<form action="/myPage_modify" method="post">
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-lock"></i></span>
							<input type="password" class="form-control" id="pwd" name="pwd" placeholder="Password" required="required">
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon">
								<i class="fa fa-lock"></i>
								<i class="fa fa-check"></i>
							</span>
							<input type="password" class="form-control" id="confirm_pwd" name="confirm_pwd" placeholder="Password Confirmation" required="required">
						</div>
					</div>
					<div class="form-group">
						<button type="button" id="btnConfirm" class="btn btn-primary btn-block btn-lg">확인</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>     
</body>
</html>