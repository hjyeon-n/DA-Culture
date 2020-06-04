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

<title>signUp</title>

<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> 

<link href="resources/css/join.css" rel="stylesheet">

<script type="text/javascript">
	$(document).ready(function(){
		$("#submit").on("click", function(){
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
	    			alert("비밀번호는 숫자와 문자 조합으로 20자까지 입력할 수 있습니다.");
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

		/* 아이디 중복 확인 및 조건 확인 */
		$("#userID").on("blur", function(){
			var userID = $("#userID").val();
			if ($("#userID").val() != "") {
		    $.ajax({
		    	async: true,
		    	type : 'GET',
		    	url : "/checkID?userID="+userID,
		    	success : function(data) {
		             if (data == 1) {
		             	$("#id_check").text("사용 중인 아이디입니다");
		 				$("#id_check").css("color", "#EC7063");
		                $("#userID").focus();
		             } else {
			             if(userID.length < 20) {
			            	$("#id_check").text("사용 가능한 아이디입니다");
				 			$("#id_check").css("color", "#2980B9");
				            $("#userpwd").focus();
					     }
			             else {
			            	$("#id_check").text("아이디는 숫자와 문자 조합으로 20자까지 입력할 수 있습니다.");
				 			$("#id_check").css("color", "#EC7063");
				            $("#userID").focus();
				         }
		             }
		         },
		         error : function(error) {
		             alert("error : " + error);
		         }
		    });
			}
		});		
	});
</script>
</head>
<body>
<div class="signup-form">	
    <form action="/join-process" method="post">
		<h2>Create Account</h2>
		<!-- <p class="lead">It's free and hardly takes more than 30 seconds.</p> -->
        <div class="form-group">
			<div class="input-group">
				<span class="input-group-addon"><i class="fa fa-user"></i></span>
				<input type="text" class="form-control" id="userID" name="userID" placeholder="ID" required="required">
			</div>
        </div>
        <div class="div-id" id= "id_check"></div>
        
        <div class="form-group">
			<div class="input-group">
				<span class="input-group-addon"><i class="fa fa-paper-plane"></i></span>
				<input type="email" class="form-control" id="email" name="email" placeholder="Email Address" required="required">
			</div>
        </div>
		<div class="form-group">
			<div class="input-group">
				<span class="input-group-addon"><i class="fa fa-lock"></i></span>
				<input type="password" class="form-control" id="pwd" name="pwd" placeholder="PW" required="required">
			</div>
        </div>
		<div class="form-group">
			<div class="input-group">
				<span class="input-group-addon">
					<i class="fa fa-lock"></i>
					<i class="fa fa-check"></i>
				</span>
				<input type="password" class="form-control" id="confirm_pwd" name="confirm_pwd" placeholder="Confirm Password" required="required">
			</div>
        </div>        
		<div class="form-group">
            <button type="submit" id="submit" class="btn btn-primary btn-block btn-lg">Sign Up</button>
        </div>
		<p class="small text-center">By clicking the Sign Up button, you agree to our <br><a href="#">Terms &amp; Conditions</a>, and <a href="#">Privacy Policy</a>.</p>
    </form>
	<div class="text-center">이미 회원이신가요? <a href="${pageContext.request.contextPath}/login">Login here</a>.</div>
	<div class="text-center">나중에 가입할래요. <a href="${pageContext.request.contextPath}/">Main</a>.</div>
</div>
</body>
</html>