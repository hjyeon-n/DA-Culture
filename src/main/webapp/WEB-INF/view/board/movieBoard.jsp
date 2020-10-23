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
	/* 검색을 수행하기 위하여 키워드와 타입을 정한 후 검색 버튼을 클릭하면 링크로 이동 -> 컨트롤러에서 이후의 일을 처리하도록 함 */
   	$(document).on('click', '#btnSearch', function(e){
		e.preventDefault();
		var url = "${pageContext.request.contextPath}/movieBoard";
		url = url + "?searchType=" + $('#searchType').val();
		url = url + "&keyword=" + $('#keyword').val();
		
		location.href = url;
	});	
	/* 글 작성 버튼을 누르면 form으로 이동  */
   	$(document).on('click', '#btnWrite', function(e){
		e.preventDefault();
		location.href="${pageContext.request.contextPath}/movieBoardForm";
	}); 
	/* 페이지 인덱스를 누를 때마다 해당 인덱스로 페이지가 전환 */
   	function fn_pagination(pageNum, contentNum, searchType, keyword) {
		var url = "${pageContext.request.contextPath}/movieBoard";
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
  <header class="masthead" style="background-image: url('resources/img/movie-bg.jpg')">
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
    <div class="container">
        <div class="table-wrapper">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>영화 제목</th>
                        <th>글 제목</th>
                        <th>내용</th>
                        <th>평점</th>
                        <th>작성자</th>
                        <th>작성일</th>
                        <th>조회수</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="movieReviewList" items="${movieReviewList}">
                    	<c:if test="${movieReviewList.movieSecretReview == false}">
						<tr>
							<td>${movieReviewList.movieReviewID}</td>
							<td>
							<!-- 글자 길이를 제한  -->
							<c:choose>
								<c:when test="${fn:length(movieReviewList.movieTitle) > 10}">
	    							${fn:substring(movieReviewList.movieTitle, 0, 10)}...
	 							</c:when>
	 							<c:otherwise>
	 								${movieReviewList.movieTitle}
	 							</c:otherwise>
	 						</c:choose>
	 						</td>
							<td><a href="${pageContext.request.contextPath}/movieBoardDetail?movieReviewID=${movieReviewList.movieReviewID}">
							<c:choose>
								<c:when test="${fn:length(movieReviewList.movieReviewTitle) > 18}">
	    							${fn:substring(movieReviewList.movieReviewTitle, 0, 18)}...
	 							</c:when>
	 							<c:otherwise>
	 								${movieReviewList.movieReviewTitle}
	 							</c:otherwise>
	 						</c:choose>
	 						</a>
	 						&nbsp;<img src="resources/img/chat.png"> ${movieReviewList.movieReplyCnt}
	 						</td>
							<td>
							<!-- 정해진 수 만큼만 보이도록 처리 함 -->
							<c:choose>
								<c:when test="${fn:length(movieReviewList.movieReviewPreview) > 30}">
	    							${fn:substring(movieReviewList.movieReviewPreview, 0, 30)}...
	 							</c:when>
	 							<c:otherwise>
	 								${movieReviewList.movieReviewPreview}
	 							</c:otherwise>
	 						</c:choose>
							</td>
							<td>${movieReviewList.movieScore}</td>
							<td>${movieReviewList.userID}</td>
							<td>${movieReviewList.movieBoardDate}</td>
							<td align=center>${movieReviewList.movieReviewViews}</td>
						</tr>
						</c:if>				
					</c:forEach> 
                </tbody>
            </table>
            <div class="text-center">
                <ul class="pagination justify-content-center">
	                <c:url var="movieReviewListURL" value="${pageContext.request.contextPath}/movieBoard"></c:url>
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
							<option value="movieTitle">영화 제목</option>
							<option value="movieReviewTitle">글 제목</option>
							<option value="movieReviewContents">내용</option>
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
				<!-- 로그인한 사용자만 글 작성 버튼이 보임  -->
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