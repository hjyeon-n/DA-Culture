<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<%@ include file="../header.jsp" %>
<head>
<meta charset="UTF-8">
<title>playBoardForm</title>
	<script src="resources/se2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
	
	<script>
	$(document).on('click', '#btnModify', function(e){
		oEditors.getById["playReviewContents"].exec("UPDATE_CONTENTS_FIELD", []);
		if($("#playReviewTitle").val()==""){
			alert("글 제목을 입력해 주세요.");
			$("#playReviewTitle").focus();
			return false;
		}
		if($("#playReviewTitle").val().length >= 20){
			alert("글 제목은 20자 내로 작성해 주세요.");
			$("#playReviewTitle").focus();
			return false;
		}
		if($("#playTitle").val()==""){
			alert("전시회 제목을 입력해 주세요.");
			$("#playTitle").focus();
			return false;
		}
		if($("#playTitle").val().length >= 30){
			alert("전시회 제목은 30자 내로 작성해 주세요.");
			$("#playTitle").focus();
			return false;
		}
		var text = $("#playReviewContents").val().replace(/[<][^>]*[>]/gi, "");
		if(text==""){
			alert("리뷰 내용을 입력해 주세요.");
			oEditors.getById["playReviewContents"].exec("FOCUS");
			return false;
		} 
		if(text.length >= 5000){
			alert("글 내용은 5000자 내로 작성해 주세요.");
			oEditors.getById["playReviewContents"].exec("FOCUS");
			return false;
		} 
		var result = text.substring(0, 40);
		$("#playReviewPreview").val(result);
		$("#form").submit();
	});

	$(document).on('click', '#btnList', function(e){
		e.preventDefault();
		location.href="${pageContext.request.contextPath}/playBoard";
	});
	$(document).on('click', '#starRating span', function(e){
		  $(this).parent().children('span').removeClass('on');
		  $(this).addClass('on').prevAll('span').addClass('on');

		  var starClass1 = $('#star1').attr('class');
		  var starClass2 = $('#star2').attr('class');
		  var starClass3 = $('#star3').attr('class');
		  var starClass4 = $('#star4').attr('class');
		  var starClass5 = $('#star5').attr('class');
		  var starClass6 = $('#star6').attr('class');
		  var starClass7 = $('#star7').attr('class');
		  var starClass8 = $('#star8').attr('class');
		  var starClass9 = $('#star9').attr('class');
		  var starClass10 = $('#star10').attr('class');
		  
		  if (starClass1 == "starR1 on") {
			  $("#playScore").val("0.5"); 
		  }
		  if (starClass2 == "starR2 on") {
			  $("#playScore").val("1.0"); 
		  }
		  if (starClass3 == "starR1 on") {
			  $("#playScore").val("1.5"); 
		  }
		  if (starClass4 == "starR2 on") {
			  $("#playScore").val("2.0"); 
		  }
		  if (starClass5 == "starR1 on") {
			  $("#playScore").val("2.5"); 
		  }
		  if (starClass6 == "starR2 on") {
			  $("#playScore").val("3.0"); 
		  }
		  if (starClass7 == "starR1 on") {
			  $("#playScore").val("3.5"); 
		  }
		  if (starClass8 == "starR2 on") {
			  $("#playScore").val("4.0"); 
		  }
		  if (starClass9 == "starR1 on") {
			  $("#playScore").val("4.5"); 
		  }
		  if (starClass10 == "starR2 on") {
			  $("#playScore").val("5.0"); 
		  }
		  return false;
	});
	$(document).on('change', '#playSecretReview', function(e){
		e.preventDefault();
		if($("#playSecretReview").is(":checked")){
			$("#playSecretReview").val("1");
		}else{
			$("#playSecretReview").val("0");
		}
	}); 
	</script>
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
	</style>
</head>
<body>
<!-- Page Header -->
  <header class="masthead" style="background-image: url('resources/img/modify-bg.jpg')">
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="site-heading">
            <h1>playBoardForm</h1>
            <span class="subheading">A Blog Theme by Start Bootstrap</span>
          </div>
        </div>
      </div>
    </div>
  </header>
	<article>
		<div class="container" role="main">
			<form name="form" id="form" role="form" method="post" action="${pageContext.request.contextPath}/updatePlayReview-process">
				<div class="mb-3">
					<label for="playReviewTitle">글 제목</label>
					<input type="text" class="form-control" name="playReviewTitle" id="playReviewTitle" value="${fn:escapeXml(detailPlayReview.playReviewTitle)}" placeholder="글 제목을 입력해 주세요">
				</div>
				<div class="mb-3">
					<label for="playTitle">전시회 제목</label>
					<input type="text" class="form-control" name="playTitle" id="playTitle" value="${detailPlayReview.playTitle}" placeholder="전시회 제목을 입력해 주세요">
				</div>
				<div class="mb-3">
					<label for="playScore">전시회 평점</label>
					<input type="hidden" name="playScore" id="playScore" value="${detailPlayReview.playScore}">
						<div id="starRating">
						<c:set var="playScore" value="${detailPlayReview.playScore}" />
							<c:choose>
								<c:when test="${playScore == 5.0}">
									<% for(int i = 1; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%>
								</c:when>
								<c:when test="${playScore == 4.5}">
									<% for(int i = 1; i < 10; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%> 
									<% for(int i = 10; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
								</c:when>
								<c:when test="${playScore == 4.0}">
									<% for(int i = 1; i < 9; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%> 
									<% for(int i = 9; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
								</c:when>
								<c:when test="${playScore == 3.5}">
									<% for(int i = 1; i < 8; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%> 
									<% for(int i = 8; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%>   
								</c:when>
								<c:when test="${playScore == 3.0}">
									<% for(int i = 1; i < 7; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%>
									<% for(int i = 7; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%>   
								</c:when>
								<c:when test="${playScore == 2.5}">
									<% for(int i = 1; i < 6; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%>
									<% for(int i = 6; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
								</c:when>
								<c:when test="${playScore == 2.0}">
									<% for(int i = 1; i < 5; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%> 
									<% for(int i = 5; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
								</c:when>
								<c:when test="${playScore == 1.5}">
									<% for(int i = 1; i < 4; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%>  
									<% for(int i = 4; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
								</c:when>
								<c:when test="${playScore == 1.0}">
									<% for(int i = 1; i < 3; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%>  
									<% for(int i = 3; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
								</c:when>
								<c:otherwise>
									<span class="starR1 on" id="star1">0.5</span>
									<% for(int i = 2; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%>    
								</c:otherwise>
							</c:choose>
						</div>
				</div>
				<br>
				<br>
				<div class="mb-3">
					<label for="playReviewContents">글 내용</label>
					<input type="hidden" id="playReviewPreview" name="playReviewPreview">
					<textarea name="playReviewContents" id="playReviewContents" rows="5" cols="151">
						${detailPlayReview.playReviewContents}
					</textarea>
				</div>
				<div class="mb-3">
					<label for="favoriteWord">명대사</label>
					<input type="text" class="form-control" name="favoriteWord" id="favoriteWord" value="${fn:escapeXml(detailPlayReview.favoriteWord)}" placeholder="글귀를 입력해 주세요">
				</div>
				<input type="hidden" name="playReviewID" id="playReviewID" value="${detailPlayReview.playReviewID}">
			<!-- 	<div class="mb-3">
					<label for="favoriteWord">이미지 업로드</label>
					<input type="file" name="playImage" id="playImage"> 
				</div> -->
				<label for="playSecretReview">
				<input type="checkbox" id="playSecretReview" name="playSecretReview"> 비밀글
			</label>
			</form>
			<div style="float:right;">
				<button type="button" class="btn btn-sm btn-primary" id="btnModify">수정</button>
				<button type="button" class="btn btn-sm btn-primary" id="btnList">목록</button>
			</div>
		</div>
	</article>
</body>
<script type="text/javascript">
	var oEditors = [];
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "playReviewContents",
		sSkinURI: "resources/se2/SmartEditor2Skin.html",
		fCreator: "createSEditor2"
	});
</script>
</html>