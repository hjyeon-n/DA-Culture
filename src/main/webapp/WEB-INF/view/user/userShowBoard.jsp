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
		var writerID = "${writerID}";
		var url = "${pageContext.request.contextPath}/userShowBoard";
		url = url + "?searchType=" + $('#searchType').val();
		url = url + "&keyword=" + $('#keyword').val();
	 	url = url + "&writerID=" + writerID;
		
		location.href = url;
	});	
	function fn_pagination(pageNum, contentNum, searchType, keyword, writerID) {
		var url = "${pageContext.request.contextPath}/userShowBoard";
		url = url + "?pageNum=" + pageNum;
		url = url + "&contentNum=" + contentNum;
		url = url + "&searchType=" + searchType;
		url = url + "&keyword=" + keyword; 
	 	url = url + "&writerID=" + writerID; 
		location.href = url;
	}
  	</script>
</head>
<body>
<!-- Page Header -->
  <header class="masthead" style="background-image: url('resources/img/userPage-bg.jpg')">
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="site-heading">
            <h1>Show</h1>
            <span class="subheading">Show Review</span>
          </div>
        </div>
      </div>
    </div>
  </header>
  <div id="mySidenav" class="sidenav">
	  <a href="${pageContext.request.contextPath}/userMovieBoard?writerID=${writerID}" id="movie">Movie</a>
	  <a href="${pageContext.request.contextPath}/userShowBoard?writerID=${writerID}" id="show">Show</a>
	  <a href="${pageContext.request.contextPath}/userPlayBoard?writerID=${writerID}" id="play">Play</a>
	  <a href="${pageContext.request.contextPath}/userBookBoard?writerID=${writerID}" id="book">Book</a>
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
                    <c:forEach var="userShowReviewList" items="${userShowReviewList}">
						<tr>
							<td>${userShowReviewList.showReviewID}</td>
							<td>
							<c:choose>
								<c:when test="${fn:length(userShowReviewList.showTitle) > 10}">
	    							${fn:substring(userShowReviewList.showTitle, 0, 10)}...
	 							</c:when>
	 							<c:otherwise>
	 								${userShowReviewList.showTitle}
	 							</c:otherwise>
	 						</c:choose>
	 						</td>
							<td><a href="${pageContext.request.contextPath}/showBoardDetail?showReviewID=${userShowReviewList.showReviewID}">
							<c:choose>
								<c:when test="${fn:length(userShowReviewList.showReviewTitle) > 18}">
	    							${fn:substring(userShowReviewList.showReviewTitle, 0, 18)}...
	 							</c:when>
	 							<c:otherwise>
	 								${userShowReviewList.showReviewTitle}
	 							</c:otherwise>
	 						</c:choose>
	 						</a></td>
							<td>
							<c:choose>
								<c:when test="${fn:length(userShowReviewList.showReviewPreview) > 30}">
	    							${fn:substring(userShowReviewList.showReviewPreview, 0, 30)}...
	 							</c:when>
	 							<c:otherwise>
	 								${userShowReviewList.showReviewPreview}
	 							</c:otherwise>
	 						</c:choose>
							</td>
							<td>${userShowReviewList.showScore}</td>
							<td>${userShowReviewList.userID}</td>
						</tr>				
					</c:forEach> 
                </tbody>
            </table>
            <div class="text-center">
                <ul class="pagination justify-content-center">
					<c:if test="${reviewSearch.prev}">
						<li class="page-item">
							<a class="page-link" onClick="fn_pagination('${reviewSearch.getStartPage() - 1}', '${reviewSearch.getContentNum()}', '${reviewSearch.getSearchType()}', '${reviewSearch.getKeyword()}', '${writerID}');">
							<i class="fa fa-long-arrow-left"></i> Previous</a>
						</li>
					</c:if>
					
					<c:if test="${reviewSearch.totalCount ne 0}">
						<c:forEach begin="${reviewSearch.getStartPage()}" end="${reviewSearch.getEndPage()}" var="idx">
							<li class="page-item ${reviewSearch.pageNum == idx - 1 ? 'active' : ''}">
								<a class="page-link" onClick="fn_pagination('${idx}', '${reviewSearch.getContentNum()}', '${reviewSearch.getSearchType()}', '${reviewSearch.getKeyword()}', '${writerID}');">${idx}</a>
							</li>
						</c:forEach>
					</c:if>
					
					<c:if test="${reviewSearch.next}">
						<li class="page-item">
							<a class="page-link" onClick="fn_pagination('${reviewSearch.getEndPage() + 1}', '${reviewSearch.getContentNum()}', '${reviewSearch.getSearchType()}', '${reviewSearch.getKeyword()}', '${writerID}');">
							Next <i class="fa fa-long-arrow-right"></i></a>
						</li>	
					</c:if>
                </ul>
                <div class="form-group row justify-content-center">
					<div style="padding-right:10px">
						<select class="form-control form-control-sm" name="searchType" id="searchType">
							<option value="showTitle">영화 제목</option>
							<option value="showReviewTitle">글 제목</option>
							<option value="showReviewContents">내용</option>
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