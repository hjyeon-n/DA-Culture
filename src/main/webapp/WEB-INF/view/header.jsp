<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Review</title>
<link href="resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<!-- Bootstrap core CSS -->
  	<link href="resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  	
	<!-- Custom fonts for this template -->
	<link href="resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
	<link href='https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
	<link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>
	
	<!-- Custom styles for this template -->
	<link href="resources/css/clean-blog.min.css" rel="stylesheet">
	  
	<!-- Bootstrap core JavaScript -->
	<script src="resources/vendor/jquery/jquery.min.js"></script>
	<script src="resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	
	<!-- Custom scripts for this template -->
	<script src="resources/js/clean-blog.min.js"></script>
  
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<style>
		.container .btn { 
	        font-size: 12px;
	        font-weight: bold;
			background: #19aa8d;
	        border-radius: 3px;
			border: none;
			min-width: 80px;
	        outline: none !important;
		} 
		.container .btn:hover, .container .btn:focus {
			background: #179b81;
		}
		.mainheading{font-size:40px}
	</style>
</head>
<body>
<!-- Navigation -->
   <nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
    <div class="container">
      <a class="navbar-brand" href="${pageContext.request.contextPath}/">Review</a>
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        Menu
        <i class="fas fa-bars"></i>
      </button>
      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/movieBoard">Movie</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/showBoard">Show</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/playBoard">Play</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/bookBoard">Book</a>
          </li>
           <c:if test="${user eq null}">
           <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/login">Login</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/join">Join</a>
          </li>
          </c:if>
          <c:if test="${user ne null}">
           <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/logout-process">Logout</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/myPage">MyPage</a>
          </li>
          </c:if>
        </ul>
      </div>
    </div>
  </nav>
</body>
</html>