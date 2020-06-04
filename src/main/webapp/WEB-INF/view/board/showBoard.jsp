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
		var url = "${pageContext.request.contextPath}/showBoard";
		url = url + "?searchType=" + $('#searchType').val();
		url = url + "&keyword=" + $('#keyword').val();
		
		location.href = url;
	});	
   	$(document).on('click', '#btnWrite', function(e){
		e.preventDefault();
		location.href="${pageContext.request.contextPath}/showBoardForm";
	}); 
   	function fn_pagination(pageNum, contentNum, searchType, keyword) {
		var url = "${pageContext.request.contextPath}/showBoard";
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
  <header class="masthead" style="background-image: url('resources/img/show-bg.jpg')">
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
    <div class="container">
        <div class="table-wrapper">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>연극 제목</th>
                        <th>글 제목</th>
                        <th>내용</th>
                        <th>평점</th>
                        <th>작성자</th>
                        <th>작성일</th>
                        <th>조회수</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="showReviewList" items="${showReviewList}">
                    	<c:if test="${showReviewList.showSecretReview == false}">
						<tr>
							<td>${showReviewList.showReviewID}</td>
							<td>
							<c:choose>
								<c:when test="${fn:length(showReviewList.showTitle) > 10}">
	    							${fn:substring(showReviewList.showTitle, 0, 10)}...
	 							</c:when>
	 							<c:otherwise>
	 								${showReviewList.showTitle}
	 							</c:otherwise>
	 						</c:choose>
	 						</td>
							<td><a href="${pageContext.request.contextPath}/showBoardDetail?showReviewID=${showReviewList.showReviewID}">
							<c:choose>
								<c:when test="${fn:length(showReviewList.showReviewTitle) > 18}">
	    							${fn:substring(showReviewList.showReviewTitle, 0, 18)}...
	 							</c:when>
	 							<c:otherwise>
	 								${showReviewList.showReviewTitle}
	 							</c:otherwise>
	 						</c:choose>
	 						</a>
	 						&nbsp;<img src="resources/img/chat.png"> ${showReviewList.showReplyCnt}
	 						</td>
							<td>
							<c:choose>
								<c:when test="${fn:length(showReviewList.showReviewPreview) > 30}">
	    							${fn:substring(showReviewList.showReviewPreview, 0, 30)}...
	 							</c:when>
	 							<c:otherwise>
	 								${showReviewList.showReviewPreview}
	 							</c:otherwise>
	 						</c:choose>
							</td>
							<td>${showReviewList.showScore}</td>
							<td>${showReviewList.userID}</td>
							<td>${showReviewList.showBoardDate}</td>
							<td align=center>${showReviewList.showReviewViews}</td>
						</tr>
						</c:if>				
					</c:forEach> 
                </tbody>
            </table>
            <div class="text-center">
                <ul class="pagination justify-content-center">
	                <c:url var="showReviewListURL" value="${pageContext.request.contextPath}/showBoard"></c:url>
					<c:if test="${reviewSearch.prev}">
						<li class="page-item">
							<a class="page-link" onClick="fn_pagination('${reviewSearch.getStartPage() - 1}', '${reviewSearch.getContentNum()}', '${reviewSearch.getSearchType()}', '${reviewSearch.getKeyword()}');">
							<i class="fa fa-long-arrow-left"></i> Previous</a>
						</li>
					</c:if>
					
					<c:if test="${reviewSearch.totalCount ne 0}">
						<c:forEach begin="${reviewSearch.getStartPage()}" end="${reviewSearch.getEndPage()}" var="idx">
							<li class="page-item ${reviewSearch.pageNum == idx - 1 ? 'active' : ''}">
								<a class="page-link" id="page" onClick="fn_pagination('${idx}', '${reviewSearch.getContentNum()}', '${reviewSearch.getSearchType()}', '${reviewSearch.getKeyword()}');">${idx}</a>
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
							<option value="showTitle">연극 제목</option>
							<option value="showReviewTitle">글 제목</option>
							<option value="showReviewContents">내용</option>
							<option value="userID">작성자</option>
						</select>	
					</div>
					<div class="input-group search-box">								
							<input type="text" name="keyword" id="keyword" class="form-control" placeholder="Search Here...">
							<span class="input-group-addon">
							<a type="button" id="btnSearch">
								<i class="material-icons">&#xE8B6;</i>
							</a>
							</span>
					</div>
				</div>
				<c:if test="${user ne null}">
					<div align="right">
						<button type="button" class="btn btn-sm btn-primary" id="btnWrite">글쓰기</button>
					</div>
				</c:if>
            </div>
        </div>
    </div>     
</body>
</html>    