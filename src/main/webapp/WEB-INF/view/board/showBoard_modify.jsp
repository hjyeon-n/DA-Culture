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
<title>showBoardForm</title>
	<script src="resources/se2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
	
	<script>
	$(document).on('click', '#btnModify', function(e){
		oEditors.getById["showReviewContents"].exec("UPDATE_CONTENTS_FIELD", []);
		if($("#showReviewTitle").val()==""){
			alert("글 제목을 입력해 주세요.");
			$("#showReviewTitle").focus();
			return false;
		}
		if($("#showReviewTitle").val().length >= 20){
			alert("글 제목은 20자 내로 작성해 주세요.");
			$("#showReviewTitle").focus();
			return false;
		}
		if($("#showTitle").val()==""){
			alert("전시회 제목을 입력해 주세요.");
			$("#showTitle").focus();
			return false;
		}
		if($("#showTitle").val().length >= 30){
			alert("전시회 제목은 30자 내로 작성해 주세요.");
			$("#showTitle").focus();
			return false;
		}
		var text = $("#showReviewContents").val().replace(/[<][^>]*[>]/gi, "");
		if(text==""){
			alert("리뷰 내용을 입력해 주세요.");
			oEditors.getById["showReviewContents"].exec("FOCUS");
			return false;
		} 
		if(text.length >= 5000){
			alert("글 내용은 5000자 내로 작성해 주세요.");
			oEditors.getById["showReviewContents"].exec("FOCUS");
			return false;
		} 
		var result = text.substring(0, 40);
		$("#showReviewPreview").val(result);
		$("#form").submit();
	});

	$(document).on('click', '#btnList', function(e){
		e.preventDefault();
		location.href="${pageContext.request.contextPath}/showBoard";
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
			  $("#showScore").val("0.5"); 
		  }
		  if (starClass2 == "starR2 on") {
			  $("#showScore").val("1.0"); 
		  }
		  if (starClass3 == "starR1 on") {
			  $("#showScore").val("1.5"); 
		  }
		  if (starClass4 == "starR2 on") {
			  $("#showScore").val("2.0"); 
		  }
		  if (starClass5 == "starR1 on") {
			  $("#showScore").val("2.5"); 
		  }
		  if (starClass6 == "starR2 on") {
			  $("#showScore").val("3.0"); 
		  }
		  if (starClass7 == "starR1 on") {
			  $("#showScore").val("3.5"); 
		  }
		  if (starClass8 == "starR2 on") {
			  $("#showScore").val("4.0"); 
		  }
		  if (starClass9 == "starR1 on") {
			  $("#showScore").val("4.5"); 
		  }
		  if (starClass10 == "starR2 on") {
			  $("#showScore").val("5.0"); 
		  }
		  return false;
	});
	$(document).on('change', '#showSecretReview', function(e){
		e.preventDefault();
		if($("#showSecretReview").is(":checked")){
			$("#showSecretReview").val("1");
		}else{
			$("#showSecretReview").val("0");
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
            <h1>showBoardForm</h1>
            <span class="subheading">A Blog Theme by Start Bootstrap</span>
          </div>
        </div>
      </div>
    </div>
  </header>
	<article>
		<div class="container" role="main">
			<form name="form" id="form" role="form" method="post" action="${pageContext.request.contextPath}/updateShowReview-process">
				<div class="mb-3">
					<label for="showReviewTitle">글 제목</label>
					<input type="text" class="form-control" name="showReviewTitle" id="showReviewTitle" value="${fn:escapeXml(detailShowReview.showReviewTitle)}" placeholder="글 제목을 입력해 주세요">
				</div>
				<div class="mb-3">
					<label for="showTitle">전시회 제목</label>
					<input type="text" class="form-control" name="showTitle" id="showTitle" value="${detailShowReview.showTitle}" placeholder="전시회 제목을 입력해 주세요">
				</div>
				<div class="mb-3">
					<label for="showScore">전시회 평점</label>
					<input type="hidden" name="showScore" id="showScore" value="${detailShowReview.showScore}">
						<div id="starRating">
						<c:set var="showScore" value="${detailShowReview.showScore}" />
							<c:choose>
								<c:when test="${showScore == 5.0}">
									<% for(int i = 1; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%>
								</c:when>
								<c:when test="${showScore == 4.5}">
									<% for(int i = 1; i < 10; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%> 
									<% for(int i = 10; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
								</c:when>
								<c:when test="${showScore == 4.0}">
									<% for(int i = 1; i < 9; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%> 
									<% for(int i = 9; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
								</c:when>
								<c:when test="${showScore == 3.5}">
									<% for(int i = 1; i < 8; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%> 
									<% for(int i = 8; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%>   
								</c:when>
								<c:when test="${showScore == 3.0}">
									<% for(int i = 1; i < 7; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%>
									<% for(int i = 7; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%>   
								</c:when>
								<c:when test="${showScore == 2.5}">
									<% for(int i = 1; i < 6; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%>
									<% for(int i = 6; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
								</c:when>
								<c:when test="${showScore == 2.0}">
									<% for(int i = 1; i < 5; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%> 
									<% for(int i = 5; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
								</c:when>
								<c:when test="${showScore == 1.5}">
									<% for(int i = 1; i < 4; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 on" <%}%><% else {%> "starR1 on" <% }%>id= star<%=i%> ></span><% }%>  
									<% for(int i = 4; i < 11; i++) {%><span class=<% if (i % 2 == 0) {%> "starR2 " <%}%><% else {%> "starR1 " <% }%>id= star<%=i%> ></span><% }%> 
								</c:when>
								<c:when test="${showScore == 1.0}">
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
					<label for="showReviewContents">글 내용</label>
					<input type="hidden" id="showReviewPreview" name="showReviewPreview">
					<textarea name="showReviewContents" id="showReviewContents" rows="5" cols="151">
						${detailShowReview.showReviewContents}
					</textarea>
				</div>
				<div class="mb-3">
					<label for="favoriteWord">명대사</label>
					<input type="text" class="form-control" name="favoriteWord" id="favoriteWord" value="${fn:escapeXml(detailShowReview.favoriteWord)}" placeholder="글귀를 입력해 주세요">
				</div>
				<input type="hidden" name="showReviewID" id="showReviewID" value="${detailShowReview.showReviewID}">
			<!-- 	<div class="mb-3">
					<label for="favoriteWord">이미지 업로드</label>
					<input type="file" name="showImage" id="showImage"> 
				</div> -->
				<label for="showSecretReview">
				<input type="checkbox" id="showSecretReview" name="showSecretReview"> 비밀글
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
		elPlaceHolder: "showReviewContents",
		sSkinURI: "resources/se2/SmartEditor2Skin.html",
		fCreator: "createSEditor2"
	});
</script>
</html>