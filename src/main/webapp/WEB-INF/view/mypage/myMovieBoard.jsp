<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<%@ include file="../header.jsp" %>
<head>	
	<link href="resources/css/board.css" rel="stylesheet">
	
	<script> 
   	$(document).on('click', '#btnSearch', function(e){
		e.preventDefault();
		var url = "${pageContext.request.contextPath}/myMovieBoard";
		url = url + "?searchType=" + $('#searchType').val();
		url = url + "&keyword=" + $('#keyword').val();
		
		location.href = url;
	});	
	function fn_pagination(pageNum, contentNum, searchType, keyword) {
		var url = "${pageContext.request.contextPath}/myMovieBoard";
		url = url + "?pageNum=" + pageNum;
		url = url + "&contentNum=" + contentNum;
		url = url + "&searchType=" + searchType;
		url = url + "&keyword=" + keyword;  
		location.href = url;
	}
  	</script>
</head>
<body>
<!-- Page Header -->
  <header class="masthead" style="background-image: url('resources/img/myPage-bg.png')">
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="site-heading">
            <h1>Movie</h1>
            <span class="subheading">Movie Review</span>
          </div>
        </div>
      </div>
    </div>
  </header>
  <!-- 사이드 바에서 다른 메뉴로 이동이 가능함  -->
  <div id="mySidenav" class="sidenav">
  <a href="${pageContext.request.contextPath}/myMovieBoard" id="movie">Movie</a>
  <a href="${pageContext.request.contextPath}/myShowBoard" id="show">Show</a>
  <a href="${pageContext.request.contextPath}/myPlayBoard" id="play">play</a>
  <a href="${pageContext.request.contextPath}/myBookBoard" id="book">Book</a>
  </div>
    <div class="container">
        <div class="table-wrapper">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>작품 제목</th>
                        <th>글 제목</th>
                        <th>내용</th>
                        <th>평점</th>
                        <th>작성자</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="myMovieReviewList" items="${myMovieReviewList}">
						<tr>
							<td>${myMovieReviewList.movieReviewID}</td>
							<td>
							<c:choose>
								<c:when test="${fn:length(myMovieReviewList.movieTitle) > 10}">
	    							${fn:substring(myMovieReviewList.movieTitle, 0, 10)}...
	 							</c:when>
	 							<c:otherwise>
	 								${myMovieReviewList.movieTitle}
	 							</c:otherwise>
	 						</c:choose>
	 						</td>
							<td><a href="${pageContext.request.contextPath}/movieBoardDetail?movieReviewID=${myMovieReviewList.movieReviewID}">
							<c:choose>
								<c:when test="${fn:length(myMovieReviewList.movieReviewTitle) > 18}">
	    							${fn:substring(myMovieReviewList.movieReviewTitle, 0, 18)}...
	 							</c:when>
	 							<c:otherwise>
	 								${myMovieReviewList.movieReviewTitle}
	 							</c:otherwise>
	 						</c:choose>
	 						</a></td>
							<td>
							<c:choose>
								<c:when test="${fn:length(myMovieReviewList.movieReviewPreview) > 30}">
	    							${fn:substring(myMovieReviewList.movieReviewPreview, 0, 30)}...
	 							</c:when>
	 							<c:otherwise>
	 								${myMovieReviewList.movieReviewPreview}
	 							</c:otherwise>
	 						</c:choose>
							</td>
							<td>${myMovieReviewList.movieScore}</td>
							<td>${myMovieReviewList.userID}</td>
						</tr>				
					</c:forEach> 
                </tbody>
            </table>
            <div class="text-center">
                <ul class="pagination justify-content-center">
					<c:if test="${reviewSearch.prev}">
						<li class="page-item">
							<a class="page-link" onClick="fn_pagination('${reviewSearch.getStartPage() - 1}', '${reviewSearch.getContentNum()}', '${reviewSearch.getSearchType()}', '${reviewSearch.getKeyword()}');">
							<i class="fa fa-long-arrow-left"></i> Previous</a>
						</li>
					</c:if>
					
					<c:if test="${reviewSearch.totalCount ne 0}">
						<c:forEach begin="${reviewSearch.getStartPage()}" end="${reviewSearch.getEndPage()}" var="idx">
							<li class="page-item ${reviewSearch.pageNum == idx - 1 ? 'active' : ''}">
								<a class="page-link" onClick="fn_pagination('${idx}', '${reviewSearch.getContentNum()}', '${reviewSearch.getSearchType()}', '${reviewSearch.getKeyword()}');">${idx}</a>
							</li>
						</c:forEach>
					</c:if>
					
					<c:if test="${reviewSearch.next}">
						<li class="page-item">
							<a class="page-link" onClick="fn_pagination('${reviewSearch.getEndPage() + 1}', '${reviewSearch.getContentNum()}', '${reviewSearch.getSearchType()}', '${reviewSearch.getKeyword()}');">
							Next <i class="fa fa-long-arrow-right"></i></a>
						</li>	
					</c:if>
                </ul>
                <div class="form-group row justify-content-center">
					<div style="padding-right:10px">
						<select class="form-control form-control-sm" name="searchType" id="searchType">
							<option value="movieTitle">영화 제목</option>
							<option value="movieReviewTitle">글 제목</option>
							<option value="movieReviewContents">내용</option>
						</select>	
					</div>
					<div class="input-group search-box">								
							<input type="text" id="keyword" class="form-control" placeholder="Search Here...">
							<span class="input-group-addon">
							<a type="button" id="btnSearch">
								<i class="material-icons">&#xE8B6;</i>
							</a>
							</span>
					</div>
				</div>
            </div>
        </div>
    </div>     
</body>
</html>    