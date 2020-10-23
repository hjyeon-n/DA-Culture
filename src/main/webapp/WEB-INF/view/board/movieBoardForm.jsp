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
		oEditors.getById["movieReviewContents"].exec("UPDATE_CONTENTS_FIELD", []);	
		if($("#movieReviewTitle").val()==""){
			alert("글 제목을 입력해 주세요.");
			$("#movieReviewTitle").focus();
			return false;
		}
		if($("#movieReviewTitle").val().length >= 20){
			alert("글 제목은 20자 내로 입력해 주세요.");
			$("#movieReviewTitle").focus();
			return false;
		}
		if($("#movieTitle").val()==""){
			alert("영화 제목을 입력해 주세요.");
			$("#movieTitle").focus();
			return false;
		}
		if($("#movieTitle").val().length >= 30){
			alert("영화 제목은 30자 내로 작성해 주세요.");
			$("#movieTitle").focus();
			return false;
		}
		/* 공백 확인을 위해서 */
		var favoriteWord = $("#favoriteWord").val().replace(/[<][^>]*[>]/gi, "");
		var text = $("#movieReviewContents").val().replace(/[<][^>]*[>]/gi, "");
		if(text ==""){
			alert("리뷰 내용을 입력해 주세요.");
			oEditors.getById["movieReviewContents"].exec("FOCUS");
			return false;
		} 
		if(text.length >= 5000){
			alert("글 내용은 5000자 내로 입력해 주세요.");
			oEditors.getById["movieReviewContents"].exec("FOCUS");
			return false;
		} 
		var result = text.substring(0, 40);
		$("#movieReviewPreview").val(result);
		$("#form").submit();
	});

	$(document).on('click', '#btnList', function(e){
		location.href="${pageContext.request.contextPath}/movieBoard";
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
			  $("#movieScore").val("0.5"); 
		  }
		  if (starClass2 == "starR2 on") {
			  $("#movieScore").val("1.0"); 
		  }
		  if (starClass3 == "starR1 on") {
			  $("#movieScore").val("1.5"); 
		  }
		  if (starClass4 == "starR2 on") {
			  $("#movieScore").val("2.0"); 
		  }
		  if (starClass5 == "starR1 on") {
			  $("#movieScore").val("2.5"); 
		  }
		  if (starClass6 == "starR2 on") {
			  $("#movieScore").val("3.0"); 
		  }
		  if (starClass7 == "starR1 on") {
			  $("#movieScore").val("3.5"); 
		  }
		  if (starClass8 == "starR2 on") {
			  $("#movieScore").val("4.0"); 
		  }
		  if (starClass9 == "starR1 on") {
			  $("#movieScore").val("4.5"); 
		  }
		  if (starClass10 == "starR2 on") {
			  $("#movieScore").val("5.0"); 
		  }
		  return false;
	});

 	$(document).on('change', '#movieSecretReview', function(e){
		e.preventDefault();
		if($("#movieSecretReview").is(":checked")){
			$("#movieSecretReview").val("1");
		}else{
			$("#movieSecretReview").val("0");
		}
	}); 

	function fillValue(keyword, image) {
		var keyword = keyword.replace(/[<][^>]*[>]/gi, "");
		$("#movieTitle").val(keyword);
		$("#movieImage").val(image);
		$("#movieResult").css('display', 'none');
	}

	// 키보드를 누를 때마다 수행 -> 자동완성 기능
	$(document).on('keyup', '#movieTitle', function(e){
		$("#movieResult").css('display', 'block');
		var keyword = $("#movieTitle").val();
 	    $.ajax({
 	        url : '/movieAPI?keyword='+ keyword,
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
 	 	 	 	        html += '<img src="resources/img/noImage.PNG" width="80" height="100">'; // 이미지가 없는 경우 기본 이미지로 대체
 	 	 	 	    }
 	 	 	        else {
 	 					html += '<img src="' + data[i].image + '" width="70" height="100">';
 	 	 	        }
 	 	 	        html += '</p>';
 	 	 	        if (data[i].title.length >= 25) {
 	 	 	        	html += '<p>'+ data[i].title.substring(0, 30) + '... <' + data[i].pubDate +' > </p>';
 	 	 	 	    }
 	 	 	        else{
 	 	 	        	html += '<p>'+ data[i].title + ' <' + data[i].pubDate +' > </p>';
 	 	 	 	    } 
 	 	 	        html += '<p>감독: ' + data[i].director + '</p>';
 	 	 	     	html += '<p>배우: ' + data[i].actor + '</p>';
 	 	 	        html += '</div>';
 	 	 	     	html += '</a>';
 	 	 	        html += '</li>';
 	 	 	    }
 	 	 	    html += '</ul>';
 	 	 	    html += '</div>';
 	 	 	
 	 	      	$('#movieResult').html(html);
 	        }
 	    });
	}); 

	$("body").click(function(e) { 
	     if($("#movieResult").css("display") == "block") {
	         if(!$('#movieResult, #movieTitle').has(e.target).length) { 
	               $("#movieResult").hide();
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
            <h1>movieBoardForm</h1>
            <span class="subheading">A Blog Theme by Start Bootstrap</span>
          </div>
        </div>
      </div>
    </div>
  </header>
	<article>
		<div class="container" role="main">
			<form name="form" id="form" role="form" method="post" action="${pageContext.request.contextPath}/movieBoard/save">
				<div class="mb-3">
					<label for="movieReviewTitle">글 제목</label>
					<input type="text" class="form-control" name="movieReviewTitle" id="movieReviewTitle" placeholder="글 제목을 입력해 주세요">
				</div>
				<div class="mb-3">
					<label for="movieTitle">영화 제목</label>
					<input type="text" class="form-control" name="movieTitle" id="movieTitle" placeholder="영화 제목을 입력해 주세요">
					<div id = "movieResult" class="auto_tx_area"></div>
				</div>
				<div class="mb-3">
					<label for="movieScore">영화 평점</label>
					<input type="hidden" name="movieScore" id="movieScore" value="0.5">
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
					<label for="movieReviewContents">글 내용</label>
					<textarea name="movieReviewContents" id="movieReviewContents" rows="5" cols="151"></textarea>
					<input type="hidden" id="movieReviewPreview" name="movieReviewPreview">
				</div>
				<div class="mb-3">
					<label for="favoriteWord">명대사</label>
					<input type="text" class="form-control" name="favoriteWord" id="favoriteWord" placeholder="글귀를 입력해 주세요">
				</div>
		 	 	<input type="hidden" name="movieImage" id = "movieImage">    
			<label for="movieSecretReview">
				<input type="checkbox" id="movieSecretReview" name="movieSecretReview"> 비밀글
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
		elPlaceHolder: "movieReviewContents",
		sSkinURI: "resources/se2/SmartEditor2Skin.html",
		fCreator: "createSEditor2"
	});
</script>
</html>