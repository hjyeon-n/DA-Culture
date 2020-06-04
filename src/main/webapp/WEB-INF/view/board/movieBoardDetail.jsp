<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<%@ include file="../header.jsp" %>
<head>
<meta charset="UTF-8">
  <link href="resources/css/board.css" rel="stylesheet">
  
  <script>
/*   상세보기에 들어오자마자 이전에 추천했는지의 여부에 따라 그림을 다르게 설정해 놓음 */
  	$(document).ready(function(){
  		var userID = "<%=(String)session.getAttribute("USERID")%>"
  	  	if (userID != 'null') {
	  		var movieReviewID = '${detailMovieReview.movieReviewID}';
	 		$.ajax({
	 	        url : '/movieCheckHeart',
	 	        type : 'post',
	 	        data : {'movieReviewID' : movieReviewID},
	 	        dataType: 'json',
	 	        success : function(data){
	 	 	        if (data == 0){
	 	 	 	        $('#img').attr('src', 'resources/img/empty_heart.png');
	 	 	 	    }
	 	 	        else {
	 	 	 	     	$('#img').attr('src', 'resources/img/full_heart.png');
	 	 	 	    }
	 	        }
	 	    });
  	  	}
	});
	/* 게시글 목룍으로 돌아가기  */
	$(document).on('click', '#btnList', function(e){
		e.preventDefault();
		location.href="${pageContext.request.contextPath}/movieBoard";
	});
	/* 게시글 수정하기 */
	$(document).on('click', '#btnModify', function(e){
		e.preventDefault();
		location.href="${pageContext.request.contextPath}/movieReview-modify?movieReviewID=" + ${detailMovieReview.movieReviewID};
	});
	/* 게시글 삭제하기 */
	$(document).on('click', '#btnDelete', function(e){
		if (confirm('글을 삭제하시겠습니까?')) {
			e.preventDefault();
			location.href="${pageContext.request.contextPath}/deleteMovieReview-process?movieReviewID=" + ${detailMovieReview.movieReviewID};
		}
	});
	/* 비밀댓글 설정 -> 체크박스에 체크가 되면 1 / 그렇지 않으면 0 */
 	$(document).on('change', '#movieSecretReply', function(e){
		e.preventDefault();
		if($("#movieSecretReply").is(":checked")){
			$("#movieSecretReply").val("1");
		}else{
			$("#movieSecretReply").val("0");
		}
	}); 
	/* 댓글 페이징 */
	function fn_pagination(pageNum, contentNum, movieReviewID) {
		var url = "${pageContext.request.contextPath}/movieBoardDetail";
		url = url + "?movieReviewID=" + movieReviewID;
		url = url + "&pageNum=" + pageNum;
		url = url + "&contentNum=" + contentNum;
		location.href = url;
	}
/* 	하트 모양 이모티콘을 누를 때마다 모양이 바뀌며 추천 수가 증가함 -> 실시간 반영을 위해 ajax 사용 */
 	function checkLike() {
 		var movieReviewID = '${detailMovieReview.movieReviewID}';
 		$.ajax({
 	        url : '/movieReviewLike',
 	        type : 'post',
 	        data : {'movieReviewID' : movieReviewID},
 	        dataType: 'json',
 	        success : function(data){
 	 	        var html = ''; 
 	 	        if (data.count == 0){
 	 	 	        alert('추천되었습니다.');
 	 	 	        $('#img').attr('src', 'resources/img/full_heart.png');
 	 	 	    }
 	 	        else {
 	 	 	        alert('추천이 취소되었습니다.');
 	 	 	     	$('#img').attr('src', 'resources/img/empty_heart.png');
 	 	 	    }
 	 	        html += '<h4>추천 수: ' + data.movieReviewLike + '</h4>';
 	            $("#movieLike").html(html);
 	        }
 	    });
	}  
	</script>
	<%@ include file="../reply/movieReplyScript.jsp" %>
	<style>
	.starR1{
	    background: url('resources/img/rating.png') no-repeat -52px 0;
	    background-size: auto 100%;
	    width: 15px;
	    height: 30px;
	    float:left;
	    text-indent: -9999px;
	    cursor: pointer;
	}
	.starR2{
	    background: url('resources/img/rating.png') no-repeat right 0;
	    background-size: auto 100%;
	    width: 15px;
	    height: 30px;
	    float:left;
	    text-indent: -9999px;
	    cursor: pointer;
	}
	.starR1.on{background-position:0 0;}
	.starR2.on{background-position:-15px 0;}
	.ratio_area .poster {
	    float: right;
	    position: relative;
	  	right: 450px;
	    top: 10px;
	    width: 120px;
	    height: 150px;
	}
	</style>
</head>
<body>
 <!-- Page Header -->
  <header class="masthead" style="background-image: url('resources/img/detail-bg.jpg')">
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="post-heading">
            <h2 class="mainheading">${detailMovieReview.movieReviewTitle}</h2>
            <h3 class="subheading">영화, ${detailMovieReview.movieTitle}</h3>
             <div class="ratio_area">
              <span class="meta">Posted by
              <a href="${pageContext.request.contextPath}/userMovieBoard?writerID=${detailMovieReview.userID}">${detailMovieReview.userID}</a>
              on ${detailMovieReview.movieBoardDate} 
               </span> 
               <div class="poster"><img src = "${detailMovieReview.movieImage}">  </div>   
              <br>
              <!-- 별을 누를 때마다 채워지는 모양이 달라짐  -->
              <div id="starRating">
				<c:set var="movieScore" value="${detailMovieReview.movieScore}" />
					<c:choose>
						<c:when test="${movieScore == 5.0}">
							<% for(int i = 1; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%>
						</c:when>
						<c:when test="${movieScore == 4.5}">
							<% for(int i = 1; i < 10; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%> 
							<% for(int i = 10; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
						</c:when>
						<c:when test="${movieScore == 4.0}">
							<% for(int i = 1; i < 9; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%> 
							<% for(int i = 9; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
						</c:when>
						<c:when test="${movieScore == 3.5}">
							<% for(int i = 1; i < 8; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%> 
							<% for(int i = 8; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%>   
						</c:when>
						<c:when test="${movieScore == 3.0}">
							<% for(int i = 1; i < 7; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%>
							<% for(int i = 7; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%>   
						</c:when>
						<c:when test="${movieScore == 2.5}">
							<% for(int i = 1; i < 6; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%>
							<% for(int i = 6; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
						</c:when>
						<c:when test="${movieScore == 2.0}">
							<% for(int i = 1; i < 5; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%> 
							<% for(int i = 5; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
						</c:when>
						<c:when test="${movieScore == 1.5}">
							<% for(int i = 1; i < 4; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%>  
							<% for(int i = 4; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
						</c:when>
						<c:when test="${movieScore == 1.0}">
							<% for(int i = 1; i < 3; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%>  
							<% for(int i = 3; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
						</c:when>
						<c:otherwise>
							<span class="starR1 on" id="star1">0.5</span>
								<% for(int i = 2; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%>    
						</c:otherwise>
					</c:choose>
				</div>
				<br>
				<br>
				<!-- 로그인한 사용자만 추천 기능이 활성화됨 또한 해당 글 작성자가 아니어야 게시글 추천이 가능함  -->
				<c:if test="${user ne null && sessionScope.USERID ne detailMovieReview.userID}">
					<a href="javascript:void(0);" onclick="checkLike();"><img src="resources/img/empty_heart.png" id ="img"></a>
				</c:if>
				<div id="movieLike">
					<h4>추천 수 : ${detailMovieReview.movieReviewLike}</h4>
				</div>
          	</div>
        </div>
      </div>
    </div>
  </div>
  </header>

  <!-- Post Content -->
  <article>
    <div class="container">
      <div class="row">
        <div class="container">
          <p>${detailMovieReview.movieReviewContents}</p>
          <!-- null이 아닐 때만 명대사 표시  -->
          	<c:if test="${detailMovieReview.favoriteWord ne ''}">
          	  <blockquote>
				${detailMovieReview.favoriteWord}
  			  </blockquote>
          	</c:if>
        </div>
      </div>
      <div class="container" align="right">
	      <c:if test="${user.userID eq detailMovieReview.userID}">
	      	<button type="button" id="btnModify" class="btn btn-primary">수정</button>
	      	<button type="button" id="btnDelete" class="btn btn-primary">삭제</button>
	      </c:if>
      </div>
    </div>
  </article>

   <!-- 댓글  리스트 -->
   <c:if test="${user ne null}">
   <div class="container">
       <label for="reply">Reply</label>
       <form id="ReplyInsertForm">
           <div class="input-group">
               <input type="hidden" name="movieReviewID" id="movieReviewID" value="${detailMovieReview.movieReviewID}"/>
               <textarea class="form-control" rows="3" id="movieReplyContents" name="movieReplyContents" placeholder="내용을 입력하세요."></textarea>
               <div class="input-group" align="right">	
        			<label><input type="checkbox" id="movieSecretReply" name="movieSecretReply" value="0" style="padding-right:5px"> 비밀댓글</label>&nbsp; &nbsp; 
                    <button type="button" name="btnReply" id="btnReply" class="btn btn-primary pull-right">등록</button>
               </div>
            </div>
        </form>
    </div>
    </c:if>
    
    <div class="container" style="padding-top: 10px">
		<h6 class="border-bottom pb-2 mb-0">Reply list</h6>
			<div id="replyList"></div>
	</div> 
	
	 <div class="text-center">
         <div id="replyListPaging"></div>
     </div>
</body>
</html>