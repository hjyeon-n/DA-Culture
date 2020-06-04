<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>main</title>

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
  <link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@300&display=swap" rel="stylesheet">
<style>
  p.test {
        font-family: 'Noto Serif KR', serif;
      }
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

  <!-- Page Header -->
  <header class="masthead" style="background-image: url('resources/img/blog-bg.PNG');">
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="site-heading">
            <h1>Main Page</h1>
            <span class="subheading">Main</span>
          </div>
        </div>
      </div>
    </div>
  </header>

  <!-- Main Content -->
  <!-- 기간별 베스트 게시글  -->
  <div class="container">
    <div class="row">
      <div class="col-lg-8 col-md-10 mx-auto">
        <div class="post-preview">
            <h2 class="post-title">
              This Week's Best Movie Review
            </h2>
            <br>
            <c:forEach var="bestMovieReview" items="${bestMovieReviewList}">
            	<a href="${pageContext.request.contextPath}/movieBoardDetail?movieReviewID=${bestMovieReview.movieReviewID}">
		            <h3 class="post-subtitle">
		              	${bestMovieReview.movieReviewTitle}
		            </h3>   
	            </a>       
	          <p class="post-meta">Posted by
	            <a href="${pageContext.request.contextPath}/userMovieBoard?writerID=${bestMovieReview.userID}">${bestMovieReview.userID}</a>
	            on ${bestMovieReview.movieBoardDate}
	                      영화 : ${bestMovieReview.movieTitle} 
	                      추천수 : ${bestMovieReview.movieReviewLike}</p>
            </c:forEach>
        </div>
        <hr>
       <div class="post-preview">
            <h2 class="post-title">
              This Week's Best Show Review
            </h2>
            <br>
            <c:forEach var="bestShowReview" items="${bestShowReviewList}">
            	<a href="${pageContext.request.contextPath}/showBoardDetail?showReviewID=${bestShowReview.showReviewID}">
		            <h3 class="post-subtitle">
		              	${bestShowReview.showReviewTitle}
		            </h3>   
	            </a>       
	          <p class="post-meta">Posted by
	            <a href="${pageContext.request.contextPath}/userShowBoard?writerID=${bestShowReview.userID}">${bestShowReview.userID}</a>
	            on ${bestShowReview.showBoardDate}
	                      전시회 : ${bestShowReview.showTitle} 
	                      추천수 : ${bestShowReview.showReviewLike}</p>
            </c:forEach>
        </div>
        <hr>
        <div class="post-preview">
            <h2 class="post-title">
              This Week's Best Play Review
            </h2>
            <br>
            <c:forEach var="bestPlayReview" items="${bestPlayReviewList}">
            	<a href="${pageContext.request.contextPath}/playBoardDetail?playReviewID=${bestPlayReview.playReviewID}">
		            <h3 class="post-subtitle">
		              	${bestPlayReview.playReviewTitle}
		            </h3>   
	            </a>       
	          <p class="post-meta">Posted by
	            <a href="${pageContext.request.contextPath}/userPlayBoard?writerID=${bestPlayReview.userID}">${bestPlayReview.userID}</a>
	            on ${bestPlayReview.playBoardDate}
	                      연극 : ${bestPlayReview.playTitle} 
	                      추천수 : ${bestPlayReview.playReviewLike}</p>
            </c:forEach>
        </div>
        <hr>
        <div class="post-preview">
            <h2 class="post-title">
              This Week's Best Book Review
            </h2>
            <br>
            <c:forEach var="bestBookReview" items="${bestBookReviewList}">
            	<a href="${pageContext.request.contextPath}/bookBoardDetail?bookReviewID=${bestBookReview.bookReviewID}">
		            <h3 class="post-subtitle">
		              	${bestBookReview.bookReviewTitle}
		            </h3>   
	            </a>       
	          <p class="post-meta">Posted by
	            <a href="${pageContext.request.contextPath}/userBookBoard?writerID=${bestBookReview.userID}">${bestBookReview.userID}</a>
	            on ${bestBookReview.bookBoardDate}
	                      책 : ${bestBookReview.bookTitle} 
	                      추천수 : ${bestBookReview.bookReviewLike}</p>
            </c:forEach>
        </div>
        <hr>
      </div>
    </div>
  </div>

  <hr>

  <!-- Footer -->
  <footer>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <ul class="list-inline text-center">
            <li class="list-inline-item">
              <a href="#">
                <span class="fa-stack fa-lg">
                  <i class="fas fa-circle fa-stack-2x"></i>
                  <i class="fab fa-twitter fa-stack-1x fa-inverse"></i>
                </span>
              </a>
            </li>
            <li class="list-inline-item">
              <a href="#">
                <span class="fa-stack fa-lg">
                  <i class="fas fa-circle fa-stack-2x"></i>
                  <i class="fab fa-facebook-f fa-stack-1x fa-inverse"></i>
                </span>
              </a>
            </li>
            <li class="list-inline-item">
              <a href="#">
                <span class="fa-stack fa-lg">
                  <i class="fas fa-circle fa-stack-2x"></i>
                  <i class="fab fa-github fa-stack-1x fa-inverse"></i>
                </span>
              </a>
            </li>
          </ul>
          <p class="copyright text-muted">Copyright &copy; Your Website 2019</p>
        </div>
      </div>
    </div>
  </footer>
</body>

</html>
