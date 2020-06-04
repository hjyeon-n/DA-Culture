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
	  		var bookReviewID = '${detailBookReview.bookReviewID}';
	 		$.ajax({
	 	        url : '/bookCheckHeart',
	 	        type : 'post',
	 	        data : {'bookReviewID' : bookReviewID},
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
	$(document).on('click', '#btnList', function(e){
		e.preventDefault();
		location.href="${pageContext.request.contextPath}/bookBoard";
	});
	$(document).on('click', '#btnModify', function(e){
		e.preventDefault();
		location.href="${pageContext.request.contextPath}/bookReview-modify?bookReviewID=" + ${detailBookReview.bookReviewID};
	});
	$(document).on('click', '#btnDelete', function(e){
		if (confirm('글을 삭제하시겠습니까?')) {
			e.preventDefault();
			location.href="${pageContext.request.contextPath}/deleteBookReview-process?bookReviewID=" + ${detailBookReview.bookReviewID};
		}
	});
 	$(document).on('change', '#bookSecretReply', function(e){
		e.preventDefault();
		if($("#bookSecretReply").is(":checked")){
			$("#bookSecretReply").val("1");
		}else{
			$("#bookSecretReply").val("0");
		}
	}); 
	function fn_pagination(pageNum, contentNum, bookReviewID) {
		var url = "${pageContext.request.contextPath}/bookBoardDetail";
		url = url + "?bookReviewID=" + bookReviewID;
		url = url + "&pageNum=" + pageNum;
		url = url + "&contentNum=" + contentNum;
		location.href = url;
	}
/* 	하트 모양 이모티콘을 누를 때마다 모양이 바뀌며 추천 수가 증가함 -> 실시간 반영을 위해 ajax 사용 */
 	function checkLike() {
 		var bookReviewID = '${detailBookReview.bookReviewID}';
 		$.ajax({
 	        url : '/bookReviewLike',
 	        type : 'post',
 	        data : {'bookReviewID' : bookReviewID},
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
 	 	        html += '<h4>추천 수: ' + data.bookReviewLike + '</h4>';
 	            $("#bookLike").html(html);
 	        }
 	    });
	}  
	</script>
	<%@ include file="../reply/bookReplyScript.jsp" %>
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
            <h2 class="mainheading">${detailBookReview.bookReviewTitle}</h2>
            <h3 class="subheading">책, ${detailBookReview.bookTitle}</h3>
             <div class="ratio_area">
              <span class="meta">Posted by
              <a href="${pageContext.request.contextPath}/userBookBoard?writerID=${detailBookReview.userID}">${detailBookReview.userID}</a>
              on ${detailBookReview.bookBoardDate} 
               </span> 
               <div class="poster"><img src = "${detailBookReview.bookImage}">  </div>   
              <br>
              <div id="starRating">
				<c:set var="bookScore" value="${detailBookReview.bookScore}" />
					<c:choose>
						<c:when test="${bookScore == 5.0}">
							<% for(int i = 1; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%>
						</c:when>
						<c:when test="${bookScore == 4.5}">
							<% for(int i = 1; i < 10; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%> 
							<% for(int i = 10; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
						</c:when>
						<c:when test="${bookScore == 4.0}">
							<% for(int i = 1; i < 9; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%> 
							<% for(int i = 9; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
						</c:when>
						<c:when test="${bookScore == 3.5}">
							<% for(int i = 1; i < 8; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%> 
							<% for(int i = 8; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%>   
						</c:when>
						<c:when test="${bookScore == 3.0}">
							<% for(int i = 1; i < 7; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%>
							<% for(int i = 7; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%>   
						</c:when>
						<c:when test="${bookScore == 2.5}">
							<% for(int i = 1; i < 6; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%>
							<% for(int i = 6; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
						</c:when>
						<c:when test="${bookScore == 2.0}">
							<% for(int i = 1; i < 5; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%> 
							<% for(int i = 5; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
						</c:when>
						<c:when test="${bookScore == 1.5}">
							<% for(int i = 1; i < 4; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%>  
							<% for(int i = 4; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
						</c:when>
						<c:when test="${bookScore == 1.0}">
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
				<c:if test="${user ne null && sessionScope.USERID ne detailBookReview.userID}">
					<a href="javascript:void(0);" onclick="checkLike();"><img src="resources/img/empty_heart.png" id ="img"></a>
				</c:if>
				<div id="bookLike">
					<h4>추천 수 : ${detailBookReview.bookReviewLike}</h4>
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
          <p>${detailBookReview.bookReviewContents}</p>
          	<c:if test="${detailBookReview.favoriteWord ne ''}">
          	  <blockquote>
				${detailBookReview.favoriteWord}
  			  </blockquote>
          	</c:if>
        </div>
      </div>
      <div class="container" align="right">
	      <c:if test="${user.userID eq detailBookReview.userID}">
	      	<button type="button" id="btnModify" class="btn btn-primary">수정</button>
	      	<button type="button" id="btnDelete" class="btn btn-primary">삭제</button>
	      </c:if>
      </div>
    </div>
  </article>

   <!--  댓글  -->
   <c:if test="${user ne null}">
   <div class="container">
       <label for="reply">Reply</label>
       <form id="ReplyInsertForm">
           <div class="input-group">
               <input type="hidden" name="bookReviewID" id="bookReviewID" value="${detailBookReview.bookReviewID}"/>
               <textarea class="form-control" rows="3" id="bookReplyContents" name="bookReplyContents" placeholder="내용을 입력하세요."></textarea>
               <div class="input-group" align="right">	
        			<label><input type="checkbox" id="bookSecretReply" name="bookSecretReply" value="0" style="padding-right:5px"> 비밀댓글</label>&nbsp; &nbsp; 
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