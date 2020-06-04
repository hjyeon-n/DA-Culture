<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
var movieReviewID = '${detailMovieReview.movieReviewID}'; //게시글 번호
var userID = '${user.userID}'; // 사용자 ID

/* 댓글 내용이 없으면 alert창 띄우기  */
$(document).on('click', '#btnReply', function(e){
	if($("#movieReplyContents").val()==""){
		alert("댓글 내용을 입력하세요");
		$("#movieReplyContents").focus();
		return false;
	}
	 var insertData = $('#ReplyInsertForm').serialize();
	 replyInsert(insertData); 
});

/* 댓글 페이징  -> ajax 이용 */
function replyListPaging(){
	var pageNum = getParam('pageNum');
	var contentNum = getParam('contentNum');
	var url = '/getMovieReplyPaging?pageNum='+pageNum +'&contentNum='+contentNum;
    $.ajax({
        url : url,
        type : 'get',
        data : {"movieReviewID":movieReviewID},
        dataType: 'json',
        success : function(data){
        	var html =''; 
            html += '<ul class="pagination justify-content-center">';
            if (data.prev) {
               var startPage = data.startPage - 1;
               html += '<li class="page-item">';
	           html += '<a class="page-link" onClick="fn_pagination(' + startPage + ', ' + data.contentNum + ', ' + data.movieReviewID + ');">';      
	           html += '<i class="fa fa-long-arrow-left"></i> Previous</a>';   
	           html += '</li>';
            }

            /* 댓글 수가 1개 이상일 때 */
          	if (data.totalCount != 0) {
             for (var idx = data.startPage; idx <= data.endPage; idx++) {
                 /* 현재 인덱스는 다른 인덱스와 구별  */
                if (data.pageNum == idx - 1) {
                     html += '<li class="page-item active">';
                }
                else {
                     html += '<li class="page-item">';
                }
                html += '<a class="page-link" id="page" onClick="fn_pagination(' + idx + ', ' + data.contentNum + ', ' + data.movieReviewID + ');">' + idx + '</a>';
                html += '</li>';
             }
          	}

          	if (data.next) {
             var endPage = data.endPage + 1;
             html += '<li class="page-item">';
             html += '<a class="page-link" onClick="fn_pagination(' + endPage + ', ' + data.contentNum + ', ' + data.movieReviewID + ');">';
             html += 'Next <i class="fa fa-long-arrow-right"></i></a>';
             html += '</li>';   
          }
          html += '</ul>';

          /* 댓글 리스트 밑에 페이지네이션 따로 배치하기  */
          $("#replyListPaging").html(html);
         }
    });
}
	   
//댓글 목록 
function replyList(){
	var pageNum = getParam('pageNum');
	var contentNum = getParam('contentNum');
	var url = '/getMovieReplyList?pageNum='+pageNum +'&contentNum='+contentNum;
    $.ajax({
        url : url,
        type : 'get',
        data : {"movieReviewID":movieReviewID},
        dataType: 'json',
        success : function(data){
            var html =''; 
            if(data.length < 1){
				html+=("등록된 댓글이 없습니다.");
			}
            else {
	            $.each(data, function(key, value){
	            	var userID = "<%=(String)session.getAttribute("USERID")%>"
	            	/* 부모 댓글일 경우  */
	            	if (value.movieReplyParents == 0) {
		                html += '<div class="media text-muted pt-3"  id="movieReplyID' + this.movieReplyID + '">';
	                    html += '<p class="media-body pb-3 mb-0 small lh-125 border-bottom horder-gray">';
	                    html += '<span class="d-block">';
	                    html += '<strong class="text-gray-dark">' + value.userID + '</strong>';     
	                    html += '<span style="padding-left: 7px; font-size: 9pt">'; 
	                    html += value.movieReplyDate;
		                html += '</span>';
	                    html += '</span>';
	                    html += '<br>';
	                    /* 비밀 댓글이 아닐 때 전체 공개 비밀 댓글인 경우 댓글 작성자와 게시글 작성자만 볼 수 있고 나머지 사용자에겐 비공개 처리함 */
	                    if (value.movieSecretReply == 0) {
			                html += value.movieReplyContents;
		            	}
		            	else if (userID == value.userID || userID == value.movieReviewWriter){
		            		html += '<img src="resources/img/lock.png">' + ' ' + value.movieReplyContents;
			            }
		            	else {
		                    html += '<img src="resources/img/lock.png"><span style="color:#FF6666"> 비밀 댓글 입니다.</span>';
			            }
			            html += '<br>';
			            html += '<br>';
	                    html += '<span style="font-size: 9pt">';
	                    /* 로그인한 사용자에게만 적용 */
	                    if (userID != 'null') {
	                   		html += '<a style="padding-right:5px" onclick="insertReplyComment('+value.movieReplyID+',\''+ value.movieReviewID+'\');">답글</a>';
	                   		/* 현재 사용자가 댓글 작성자일 때  */
		                   	if (userID == value.userID) {
		                       	html += '<a style="padding-right:5px" onclick="replyUpdate(' + this.movieReplyID + ', \'' + this.movieReplyContents + '\', \'' + this.userID + '\', \'' + this.movieSecretReply + '\' );"> 수정 </a>';
		                        html += '<a onclick="replyDelete('+value.movieReplyID+',\''+ value.movieReviewID+'\');"> 삭제 </a>';
		                    }
		                   	html += '<a href="javascript:void(0);" onclick="checkLike_reply('+value.movieReplyID+',\''+ value.userID+'\');"><img src="resources/img/reply_like.png"></a>';  
	                    }
	                    /* 로그인하지 않은 사용자일 때 */
	                    else {
	                    	html += '<a href="javascript:void(0);" onclick="checkUser_reply();"><img src="resources/img/reply_like.png"></a>';    
		                }
		                /* 댓글 좋아요 수 보여주기  */
	                    html += '<span id ="movieReplyLike_'+ value.movieReplyID +'">&nbsp;' + value.movieReplyLike + '</span>';
		                html += '</span>';
	                    html += '</p>';
	                    html += '</div>';
	  	              	html += '<div id="insertReplyComment' + this.movieReplyID +'"></div>';
	            	}
	            	/* 답글일 경우, 부모 댓글과 답글 기능이 없다는 점 빼고는 동일  */
	            	else {
	            		 	html += '<div class="media text-muted pt-3"  id="movieReplyID' + this.movieReplyID + '">';
	            		 	html += '<img src="resources/img/reply.png"></img>&nbsp;&nbsp;'
		                	html += '<p class="media-body pb-3 mb-0 small lh-125 border-bottom horder-gray">';
		                    html += '<span class="d-block">';
		                    html += '<strong class="text-gray-dark">' + value.userID + '</strong>';     
		                    html += '<span style="padding-left: 7px; font-size: 9pt">'; 
		                    html += value.movieReplyDate;
			                html += '</span>';
		                    html += '</span>';
		                    html += '<br>';
		                    /* 비밀 댓글이 아닐 때 전체 공개 비밀 댓글인 경우 댓글 작성자와 게시글 작성자만 볼 수 있고 나머지 사용자에겐 비공개 처리함 */
		                    if (value.movieSecretReply == 0) {
				                html += value.movieReplyContents;
			            	}
			            	else if (userID == value.userID || userID == value.movieReviewWriter || userID == value.movieParentsWriter) {
			            		html += '<img src="resources/img/lock.png">' + ' ' + value.movieReplyContents;
				            }
			            	else {
			                    html += '<img src="resources/img/lock.png"><span style="color:#FF6666">비밀 댓글 입니다.</span>';
				            }
				            html += '<br>';
				            html += '<br>';
		                    html += '<span style="font-size: 9pt">';
		                    if (userID != 'null') {
			                   	if (userID == value.userID) {
			                       	html += '<a style="padding-right:5px" onclick="replyUpdate(' + this.movieReplyID + ', \'' + this.movieReplyContents + '\', \'' + this.userID + '\', \'' + this.movieSecretReply + '\' );"> 수정 </a>';
			                        html += '<a onclick="replyDelete('+value.movieReplyID+',\''+ value.movieReviewID+'\');"> 삭제 </a>';
			                    }
			                   	html += '<a href="javascript:void(0);" onclick="checkLike_reply('+value.movieReplyID+',\''+ value.userID+'\');"><img src="resources/img/reply_like.png"></a>';  
		                    }
		                    else {
		                    	html += '<a href="javascript:void(0);" onclick="checkUser_reply();"><img src="resources/img/reply_like.png"></a>';    
			                }
		                    html += '<span id ="movieReplyLike_'+ value.movieReplyID +'">&nbsp;' + value.movieReplyLike + '</span>';
			                html += '</span>';
		                    html += '</p>';
		                    html += '</div>';
		  	              	html += '<div id="insertReplyComment' + this.movieReplyID +'"></div>';
		            }
	            }); 
            }
            $("#replyList").html(html);
        	$("#movieSecretReply").attr('checked', false);		
        	$("#movieSecretReply").val("0");
        	replyListPaging();
        }
    });
}
 
//댓글 등록
function replyInsert(insertData){
    $.ajax({
        url : '/insertMovieReply',
        type : 'post',
        data : insertData,
        success : function(data){
        	replyList();
            $('#movieReplyContents').val('');
            
            var pageNum = getParam('pageNum');
        	var contentNum = getParam('contentNum');
        	var movieReviewID = getParam('movieReviewID');

        	/* 댓글 페이징 때문에 설정 */
        	/* pageNum, contentNum을 default로 설정 */
        	if (isEmpty(pageNum) || isEmpty(contentNum)) {
            	pageNum = 1;
            	contentNum = 10;
           	}

           	/* 마지막 페이지에서 댓글이 하나 늘어나면 다음 페이징으로 자동으로 넘어갈 수 있게 설정  */
           	if (parseInt(data / contentNum)  + 1 > pageNum && data % contentNum != 0) {
               	pageNum = parseInt(pageNum) + 1;
               	fn_pagination(pageNum, contentNum, movieReviewID);               	
            }
        }
    });
}

// 댓글을 수정할 때 비밀댓글 여부에 따른 값 설정
$(document).on('change', '#movieSecretReply_modify', function(e){
	e.preventDefault();
	if($("#movieSecretReply_modify").is(":checked")){
		$("#movieSecretReply_modify").val("1");
	}else{
		$("#movieSecretReply_modify").val("0");
	}
}); 

//댓글 수정 - 댓글 내용 출력을 input 폼으로 변경 
function replyUpdate(movieReplyID, movieReplyContents, userID, movieSecretReply){
	var htmls = "";
	htmls += '<div class="media text-muted pt-3" id="movieReplyID' + movieReplyID + '">';
	htmls += '<p class="media-body pb-3 mb-0 small lh-125 border-bottom horder-gray">';
	htmls += '<span class="d-block">';
	htmls += '<strong class="text-gray-dark">' + userID + '</strong>';
	htmls += '</span>';		
	htmls += '<textarea name="editContent" id="editContent" class="form-control" rows="3">';
	htmls += movieReplyContents;
	htmls += '</textarea>';
	if (movieSecretReply == "false") {
		htmls += '<label><input type="checkbox" id="movieSecretReply_modify" name="movieSecretReply_modify" value="false"> 비밀댓글</label>';
	}
	else {
		htmls += '<label><input type="checkbox" id="movieSecretReply_modify" name="movieSecretReply_modify" value="true" checked> 비밀댓글</label>';
	}
	htmls += '<span style="padding-left: 7px; font-size: 9pt">';
	htmls += '<a onclick="replyUpdateProc('+movieReplyID+')" style="padding-right:5px">저장</a>';
	htmls += '<a onClick="replyList()">취소<a>';
	htmls += '</span>';
	htmls += '</p>';
	htmls += '</div>';
	$('#movieReplyID' + movieReplyID).replaceWith(htmls);
	$('#movieReplyID' + movieReplyID + ' #editContent').focus();
	$("#movieSecretReply_modify").attr('checked', false);		
	$("#movieSecretReply_modify").val("0");
}
 
//댓글 수정
function replyUpdateProc(movieReplyID){
	if($("#editContent").val()==""){
		alert("댓글 내용을 입력하세요");
		$("#editContent").focus();
		return false;
	}
    var updateContent = $('#editContent').val(); 
    var movieSecretReply = $('#movieSecretReply_modify').val(); 
    $.ajax({
        url : '/updateMovieReply',
        type : 'post',
        data : {'movieReplyContents' : updateContent, 'movieReplyID' : movieReplyID, 'movieSecretReply' : movieSecretReply},
        success : function(data){
             replyList();
        }
    });
}
$(document).on('change', '#movieSecretReply', function(e){
	e.preventDefault();
	if($("#movieSecretReply").is(":checked")){
		$("#movieSecretReply").val("1");
	}else{
		$("#movieSecretReply").val("0");
	}
}); 
$(document).on('change', '#movieSecretReply_comment', function(e){
	e.preventDefault();
	if($("#movieSecretReply_comment").is(":checked")){
		$("#movieSecretReply_comment").val("1");
	}else{
		$("#movieSecretReply_comment").val("0");
	}
}); 
// 답글 작성
function insertReplyComment(movieReplyID, movieReviewID){
	var userID = "<%=(String)session.getAttribute("USERID")%>"
	var htmls = "";
	htmls += '<form id="replyCommentForm">';
	htmls += '<div class="media text-muted pt-3" id="movieReplyID' + movieReplyID + '">';
	htmls += '<img src="resources/img/reply.png"></img>&nbsp;&nbsp;'
	htmls += '<p class="media-body pb-3 mb-0 small lh-125 border-bottom horder-gray">';
	htmls += '<span class="d-block">';
	htmls += '<strong class="text-gray-dark">' + userID + '</strong>';
	htmls += '</span>';		
	htmls += '<textarea name="movieReplyContents_comment" id="movieReplyContents_comment" class="form-control" rows="3">';
	htmls += '</textarea>';
	htmls += '<label><input type="checkbox" id="movieSecretReply_comment" name="movieSecretReply_comment" value="0"> 비밀댓글</label>';
	htmls += '<span style="padding-left: 7px; font-size: 9pt">';
	htmls += '<a onclick="replyCommentProc('+movieReplyID+')" style="padding-right:5px">저장</a>';
	htmls += '<a onClick="replyList()">취소<a>';
	htmls += '</span>';
	htmls += '</p>';
	htmls += '</div>';
	htmls += '</form>';
	$("#insertReplyComment" + movieReplyID).html(htmls);
	$("#movieSecretReply_comment").attr('checked', false);		
	$("#movieSecretReply_comment").val("0");
}
//답글 저장
function replyCommentProc(movieReplyID){
	if($("#movieReplyContents_comment").val()==""){
		alert("댓글 내용을 입력하세요");
		$("#movieReplyContents_comment").focus();
		return false;
	}
	var movieReplyContents = $('#movieReplyContents_comment').val();
	var movieSecretReply = $('#movieSecretReply_comment').val();
    $.ajax({
        url : '/movieReplyComment',
        type : 'post',
        data : {'movieReplyContents' : movieReplyContents, 'movieReplyID' : movieReplyID, 'movieSecretReply' : movieSecretReply},
        success : function(data){
            replyList();
        }
    });
}
 
//댓글 삭제 
function replyDelete(movieReplyID, movieReviewID){
	if (confirm('댓글을 삭제하시겠습니까?')) {
	    $.ajax({
	        url : '/deleteMovieReply',
	        data: {"movieReplyID":movieReplyID, 'movieReviewID':movieReviewID},
	        type : 'post',
	        success : function(data){
	          	replyList(); //댓글 삭제후 목록 출력 
	          	/* 현재 url 주소에서 추출 */
	            var pageNum = getParam('pageNum');
	         	var contentNum = getParam('contentNum');
	         	var movieReviewID = getParam('movieReviewID');
	         	/* 댓글 페이징 때문에 설정 */
	        	/* pageNum, contentNum을 default로 설정 */
	         	if (isEmpty(pageNum) || isEmpty(contentNum)) {
	             	pageNum = 1;
	             	contentNum = 10;
	            }
	         	/* 마지막 페이지에서 댓글이 하나만 있을 때 삭제를 하면 이전 페이징으로 자동으로 넘어갈 수 있게 설정  */
	            if (data % contentNum == 0 && pageNum != 1) {
	                pageNum = parseInt(pageNum) - 1;
	                fn_pagination(pageNum, contentNum, movieReviewID);               	
	             }
	        }
	    });
	}
}
/* 현재 url 값에서 파라미터 값 추출 */
function getParam(sname) {
    var params = location.search.substr(location.search.indexOf("?") + 1);
    var sval = "";
    params = params.split("&");
    for (var i = 0; i < params.length; i++) {
        temp = params[i].split("=");
        if ([temp[0]] == sname) { sval = temp[1]; }
    }
    return sval;
}
// 댓글 추천 기능 -> 본인 댓글은 추천할 수 없음
function checkLike_reply(movieReplyID, writerID) {
		$.ajax({
	        url : '/movieReplyLike',
	        type : 'post',
	        data : {'movieReplyID' : movieReplyID},
	        dataType: 'json',
	        success : function(data){
	        	var userID = "<%=(String)session.getAttribute("USERID")%>"
		      	if (userID == writerID) {
			        alert("본인 글은 추천할 수 없습니다.");
			    }
		        else {
		 	        var html = ''; 
		 	       	var countID = '#movieReplyLike_' + movieReplyID;
		 	        if (data.count == 0){
		 	 	        alert('추천되었습니다.');
		 	 	    }
		 	        else {
		 	 	        alert('추천이 취소되었습니다.');
		 	 	    }
		 	        html += '&nbsp;' + data.movieReplyLike;
		            $(countID).html(html);
		        }
	        }
	    });
}
function checkUser_reply() {
	if (confirm('로그인 하시겠습니까?')) {
		location.href="${pageContext.request.contextPath}/login";
	}
}
$(document).ready(function(){
    replyList(); //페이지 로딩시 댓글 목록 출력 
});

function isEmpty(str){    
    if(typeof str == "undefined" || str == null || str == "")
        return true;
    else
        return false ;
}
</script>