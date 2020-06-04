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
		var url = "${pageContext.request.contextPath}/bookBoard";
		url = url + "?searchType=" + $('#searchType').val();
		url = url + "&keyword=" + $('#keyword').val();
		
		location.href = url;
	});	
   	$(document).on('click', '#btnWrite', function(e){
		e.preventDefault();
		location.href="${pageContext.request.contextPath}/bookBoardForm";
	}); 
   	function fn_pagination(pageNum, contentNum, searchType, keyword) {
		var url = "${pageContext.request.contextPath}/bookBoard";
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
  <header class="masthead" style="background-image: url('resources/img/book-bg.jpg')">
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="site-heading">
            <h1>Book</h1>
            <span class="subheading">Book Review</span>
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
                        <th>책 제목</th>
                        <th>글 제목</th>
                        <th>내용</th>
                        <th>평점</th>
                        <th>작성자</th>
                        <th>작성일</th>
                        <th>조회수</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="bookReviewList" items="${bookReviewList}">
                    	<c:if test="${bookReviewList.bookSecretReview == false}">
						<tr>
							<td>${bookReviewList.bookReviewID}</td>
							<td>
							<c:choose>
								<c:when test="${fn:length(bookReviewList.bookTitle) > 10}">
	    							${fn:substring(bookReviewList.bookTitle, 0, 10)}...
	 							</c:when>
	 							<c:otherwise>
	 								${bookReviewList.bookTitle}
	 							</c:otherwise>
	 						</c:choose>
							</td>
							<td><a href="${pageContext.request.contextPath}/bookBoardDetail?bookReviewID=${bookReviewList.bookReviewID}">
							<c:choose>
								<c:when test="${fn:length(bookReviewList.bookReviewTitle) > 18}">
	    							${fn:substring(bookReviewList.bookReviewTitle, 0, 18)}...
	 							</c:when>
	 							<c:otherwise>
	 								${bookReviewList.bookReviewTitle}
	 							</c:otherwise>
	 						</c:choose>
	 						</a>
	 						&nbsp;<img src="resources/img/chat.png"> ${bookReviewList.bookReplyCnt}
	 						</td>
							<td>
							<c:choose>
								<c:when test="${fn:length(bookReviewList.bookReviewPreview) > 30}">
	    							${fn:substring(bookReviewList.bookReviewPreview, 0, 30)}...
	 							</c:when>
	 							<c:otherwise>
	 								${bookReviewList.bookReviewPreview}
	 							</c:otherwise>
	 						</c:choose>
							</td>
							<td>${bookReviewList.bookScore}</td>
							<td>${bookReviewList.userID}</td>
							<td>${bookReviewList.bookBoardDate}</td>
							<td align=center>${bookReviewList.bookReviewViews}</td>
						</tr>
						</c:if>				
					</c:forEach> 
                </tbody>
            </table>
            <div class="text-center">
                <ul class="pagination justify-content-center">
	                <c:url var="bookReviewListURL" value="${pageContext.request.contextPath}/bookBoard"></c:url>
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
							<option value="bookTitle">책 제목</option>
							<option value="bookReviewTitle">글 제목</option>
							<option value="bookReviewContents">내용</option>
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