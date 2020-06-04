<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<%@ include file="../header.jsp" %>
<head>	
	<script src="resources/se2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
	
	<style>
	.auto_tx_area {
	    clear: both;
	    position: absolute;
	    z-index: 10002;
	    width: 410px;
	    background: #fff;
	}
	ul{
	   list-style:none;
	   padding-left:0px;
	}

	 a:link {color: black;text-decoration: none;}
	 a:visited { color: black; text-decoration: none;}
	 a:hover { color: black;text-decoration: none;}
	</style>
	
	<script>
	$(document).on('click', '#btnSave', function(e){
		oEditors.getById["bookReviewContents"].exec("UPDATE_CONTENTS_FIELD", []);
		if($("#bookReviewTitle").val()==""){
			alert("글 제목을 입력해 주세요.");
			$("#bookReviewTitle").focus();
			return false;
		}
		if($("#bookReviewTitle").val().length >= 20){
			alert("글 제목은 20자 내로 작성해 주세요.");
			$("#bookReviewTitle").focus();
			return false;
		}
		if($("#bookTitle").val()==""){
			alert("책 제목을 입력해 주세요.");
			$("#bookTitle").focus();
			return false;
		}
		if($("#bookTitle").val().length >= 30){
			alert("책 제목은 30자 내로 작성해 주세요.");
			$("#bookTitle").focus();
			return false;
		}
		var text = $("#bookReviewContents").val().replace(/[<][^>]*[>]/gi, "");
		if(text==""){
			alert("리뷰 내용을 입력해 주세요.");
			oEditors.getById["bookReviewContents"].exec("FOCUS");
			return false;
		} 
		if(text.length >= 5000){
			alert("글 내용은 5000자 내로 작성해 주세요.");
			oEditors.getById["bookReviewContents"].exec("FOCUS");
			return false;
		} 
		var result = text.substring(0, 40);
		$("#bookReviewPreview").val(result);
		$("#form").submit();
	});

	$(document).on('click', '#btnList', function(e){
		location.href="${pageContext.request.contextPath}/bookBoard";
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
			  $("#bookScore").val("0.5"); 
		  }
		  if (starClass2 == "starR2 on") {
			  $("#bookScore").val("1.0"); 
		  }
		  if (starClass3 == "starR1 on") {
			  $("#bookScore").val("1.5"); 
		  }
		  if (starClass4 == "starR2 on") {
			  $("#bookScore").val("2.0"); 
		  }
		  if (starClass5 == "starR1 on") {
			  $("#bookScore").val("2.5"); 
		  }
		  if (starClass6 == "starR2 on") {
			  $("#bookScore").val("3.0"); 
		  }
		  if (starClass7 == "starR1 on") {
			  $("#bookScore").val("3.5"); 
		  }
		  if (starClass8 == "starR2 on") {
			  $("#bookScore").val("4.0"); 
		  }
		  if (starClass9 == "starR1 on") {
			  $("#bookScore").val("4.5"); 
		  }
		  if (starClass10 == "starR2 on") {
			  $("#bookScore").val("5.0"); 
		  }
		  return false;
	});

 	$(document).on('change', '#bookSecretReview', function(e){
		e.preventDefault();
		if($("#bookSecretReview").is(":checked")){
			$("#bookSecretReview").val("1");
		}else{
			$("#bookSecretReview").val("0");
		}
	}); 

 	function fillValue(keyword, image) {
		var keyword = keyword.replace(/[<][^>]*[>]/gi, "");
		$("#bookTitle").val(keyword);
		$("#bookImage").val(image);
		$("#bookResult").css('display', 'none');
	}

	$(document).on('keyup', '#bookTitle', function(e){
		$("#bookResult").css('display', 'block');
		var keyword = $("#bookTitle").val();
 	    $.ajax({
 	        url : '/bookAPI?keyword='+ keyword,
 	        type : 'post',
 	        success : function(data){
 	        	var html = '';
 	        	html += '<div class="autoComplete">';
 	        	html += '<ul>';
 	 	        for (var i = 0 in data) {
 	 	 	        html += '<li>';
 	 	 	     	html += '<a href="javascript:void(0);" onclick="fillValue(\'' + data[i].title + '\'' + ',\''+ data[i].image+'\');">';
 	 	 	        html += '<div>';
 	 	 	        html += '<p>';
 	 	 	        if (data[i].image == "") {
 	 	 	 	        html += '<img src="resources/img/noImage.PNG" width="80" height="100">';
 	 	 	 	    }
 	 	 	        else {
 	 					html += '<img src="' + data[i].image + '" width="70" height="100">';
 	 	 	        }
 	 	 	        html += '</p>';
 	 	 	        if (data[i].title.length >= 25) {
 	 	 	        	html += '<p> <'+ data[i].title.substring(0, 30) + '...> ' + data[i].author +'</p>';
 	 	 	 	    }
 	 	 	        else{
 	 	 	        	html += '<p> <'+ data[i].title + '> ' + data[i].author +'</p>';
 	 	 	 	    } 
 	 	 	        html += '<p>출판사: ' + data[i].publisher + '</p>';
 	 	 	        html += '</div>';
 	 	 	     	html += '</a>';
 	 	 	        html += '</li>';
 	 	 	    }
 	 	 	    html += '</ul>';
 	 	 	    html += '</div>';
 	 	 	
 	 	      	$('#bookResult').html(html);
 	        }
 	    });
	}); 

	$("body").click(function(e) { 
	     if($("#bookResult").css("display") == "block") {
	         if(!$('#bookResult, #bookTitle').has(e.target).length) { 
	               $("#bookResult").hide();
	          } 
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
  <header class="masthead" style="background-image: url('resources/img/form-bg.jpg')">
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="site-heading">
            <h1>bookBoardForm</h1>
            <span class="subheading">A Blog Theme by Start Bootstrap</span>
          </div>
        </div>
      </div>
    </div>
  </header>
	<article>
		<div class="container" role="main">
			<form name="form" id="form" role="form" method="post" action="${pageContext.request.contextPath}/bookBoard/save">
				<div class="mb-3">
					<label for="bookReviewTitle">글 제목</label>
					<input type="text" class="form-control" name="bookReviewTitle" id="bookReviewTitle" placeholder="글 제목을 입력해 주세요">
				</div>
				<div class="mb-3">
					<label for="bookTitle">책 제목</label>
					<input type="text" class="form-control" name="bookTitle" id="bookTitle" placeholder="책 제목을 입력해 주세요">
					<div id = "bookResult" class="auto_tx_area"></div>
				</div>
				<div class="mb-3">
					<label for="bookScore">책 평점</label>
					<input type="hidden" name="bookScore" id="bookScore" value="0.5">
						<div id="starRating">
					        <span class="starR1 on" id="star1">0.5</span>
					        <span class="starR2" id="star2">1.0</span>
					        <span class="starR1" id="star3">1.5</span>
					        <span class="starR2" id="star4">2.0</span>
					        <span class="starR1" id="star5">2.5</span>
					        <span class="starR2" id="star6">3.0</span>
					        <span class="starR1" id="star7">3.5</span>
					        <span class="starR2" id="star8">4.0</span>
					        <span class="starR1" id="star9">4.5</span>
					        <span class="starR2" id="star10">5.0</span>       
						</div>
				</div>
				<br>
				<br>
				<div class="mb-3">
					<label for="bookReviewContents">글 내용</label>
					<textarea name="bookReviewContents" id="bookReviewContents" rows="5" cols="151"></textarea>
					<input type="hidden" id="bookReviewPreview" name="bookReviewPreview">
				</div>
				<div class="mb-3">
					<label for="favoriteWord">명대사</label>
					<input type="text" class="form-control" name="favoriteWord" id="favoriteWord" placeholder="글귀를 입력해 주세요">
				</div>
				<input type="hidden" name="bookImage" id = "bookImage">    
			<label for="bookSecretReview">
				<input type="checkbox" id="bookSecretReview" name="bookSecretReview"> 비밀글
			</label>
			</form>
			<div style="float:right;">
				<button type="button" class="btn btn-sm btn-primary" id="btnSave">저장</button>
				<button type="button" class="btn btn-sm btn-primary" id="btnList">목록</button>
			</div>
		</div>	
	</article>
</body>
<script type="text/javascript">
	var oEditors = [];
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "bookReviewContents",
		sSkinURI: "resources/se2/SmartEditor2Skin.html",
		fCreator: "createSEditor2"
	});
</script>
</html>