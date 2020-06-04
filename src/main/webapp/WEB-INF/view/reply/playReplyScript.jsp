<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
var playReviewID = '${detailPlayReview.playReviewID}'; //게시글 번호
var userID = '${user.userID}';
$(document).on('click', '#btnReply', function(e){
	if($("#playReplyContents").val()==""){
		alert("댓글 내용을 입력하세요");
		$("#playReplyContents").focus();
		return false;
	}
	 var insertData = $('#ReplyInsertForm').serialize();
	 replyInsert(insertData); 
});

function replyListPaging(){
	var pageNum = getParam('pageNum');
	var contentNum = getParam('contentNum');
	var url = '/getPlayReplyPaging?pageNum='+pageNum +'&contentNum='+contentNum;
    $.ajax({
        url : url,
        type : 'get',
        data : {"playReviewID":playReviewID},
        dataType: 'json',
        success : function(data){
        	var html =''; 
            html += '<ul class="pagination justify-content-center">';
            if (data.prev) {
               var startPage = data.startPage - 1;
               html += '<li class="page-item">';
	           html += '<a class="page-link" onClick="fn_pagination(' + startPage + ', ' + data.contentNum + ', ' + data.playReviewID + ');">';      
	           html += '<i class="fa fa-long-arrow-left"></i> Previous</a>';   
	           html += '</li>';
            }

          	if (data.totalCount != 0) {
             for (var idx = data.startPage; idx <= data.endPage; idx++) {
                if (data.pageNum == idx - 1) {
                     html += '<li class="page-item active">';
                }
                else {
                     html += '<li class="page-item">';
                }
                html += '<a class="page-link" id="page" onClick="fn_pagination(' + idx + ', ' + data.contentNum + ', ' + data.playReviewID + ');">' + idx + '</a>';
                html += '</li>';
             }
          	}

          	if (data.next) {
             var endPage = data.endPage + 1;
             html += '<li class="page-item">';
             html += '<a class="page-link" onClick="fn_pagination(' + endPage + ', ' + data.contentNum + ', ' + data.playReviewID + ');">';
             html += 'Next <i class="fa fa-long-arrow-right"></i></a>';
             html += '</li>';   
          }
          html += '</ul>';

          $("#replyListPaging").html(html);
         }
    });
}
	   
//댓글 목록 
function replyList(){
	var pageNum = getParam('pageNum');
	var contentNum = getParam('contentNum');
	var url = '/getPlayReplyList?pageNum='+pageNum +'&contentNum='+contentNum;
    $.ajax({
        url : url,
        type : 'get',
        data : {"playReviewID":playReviewID},
        dataType: 'json',
        success : function(data){
            var html =''; 
            if(data.length < 1){
				html+=("등록된 댓글이 없습니다.");
			}
            else {
	            $.each(data, function(key, value){
	            	var userID = "<%=(String)session.getAttribute("USERID")%>"
	            	if (value.playReplyParents == 0) {
		                html += '<div class="media text-muted pt-3"  id="playReplyID' + this.playReplyID + '">';
	                    html += '<p class="media-body pb-3 mb-0 small lh-125 border-bottom horder-gray">';
	                    html += '<span class="d-block">';
	                    html += '<strong class="text-gray-dark">' + value.userID + '</strong>';     
	                    html += '<span style="padding-left: 7px; font-size: 9pt">'; 
	                    html += value.playReplyDate;
		                html += '</span>';
	                    html += '</span>';
	                    html += '<br>';
	                    if (value.playSecretReply == 0) {
			                html += value.playReplyContents;
		            	}
		            	else if (userID == value.userID || userID == value.playReviewWriter){
		            		html += '<img src="resources/img/lock.png">' + ' ' + value.playReplyContents;
			            }
		            	else {
		                    html += '<img src="resources/img/lock.png"><span style="color:#FF6666"> 비밀 댓글 입니다.</span>';
			            }
			            html += '<br>';
			            html += '<br>';
	                    html += '<span style="font-size: 9pt">';
	                    if (userID != 'null') {
	                   		html += '<a style="padding-right:5px" onclick="insertReplyComment('+value.playReplyID+',\''+ value.playReviewID+'\');">답글</a>';
		                   	if (userID == value.userID) {
		                       	html += '<a style="padding-right:5px" onclick="replyUpdate(' + this.playReplyID + ', \'' + this.playReplyContents + '\', \'' + this.userID + '\', \'' + this.playSecretReply + '\' );"> 수정 </a>';
		                        html += '<a onclick="replyDelete('+value.playReplyID+',\''+ value.playReviewID+'\');"> 삭제 </a>';
		                    }
		                   	html += '<a href="javascript:void(0);" onclick="checkLike_reply('+value.playReplyID+',\''+ value.userID+'\');"><img src="resources/img/reply_like.png"></a>';  
	                    }
	                    else {
	                    	html += '<a href="javascript:void(0);" onclick="checkUser_reply();"><img src="resources/img/reply_like.png"></a>';    
		                }
	                    html += '<span id ="playReplyLike_'+ value.playReplyID +'">&nbsp;' + value.playReplyLike + '</span>';
		                html += '</span>';
	                    html += '</p>';
	                    html += '</div>';
	  	              	html += '<div id="insertReplyComment' + this.playReplyID +'"></div>';
	            	}
	            	else {
	            		 	html += '<div class="media text-muted pt-3"  id="playReplyID' + this.playReplyID + '">';
	            		 	html += '<img src="resources/img/reply.png"></img>&nbsp;&nbsp;'
		                	html += '<p class="media-body pb-3 mb-0 small lh-125 border-bottom horder-gray">';
		                    html += '<span class="d-block">';
		                    html += '<strong class="text-gray-dark">' + value.userID + '</strong>';     
		                    html += '<span style="padding-left: 7px; font-size: 9pt">'; 
		                    html += value.playReplyDate;
			                html += '</span>';
		                    html += '</span>';
		                    html += '<br>';
		                    if (value.playSecretReply == 0) {
				                html += value.playReplyContents;
			            	}
			            	else if (userID == value.userID || userID == value.playReviewWriter || userID == value.playParentsWriter) {
			            		html += '<img src="resources/img/lock.png">' + ' ' + value.playReplyContents;
				            }
			            	else {
			                    html += '<img src="resources/img/lock.png"><span style="color:#FF6666">비밀 댓글 입니다.</span>';
				            }
				            html += '<br>';
				            html += '<br>';
		                    html += '<span style="font-size: 9pt">';
		                    if (userID != 'null') {
			                   	if (userID == value.userID) {
			                       	html += '<a style="padding-right:5px" onclick="replyUpdate(' + this.playReplyID + ', \'' + this.playReplyContents + '\', \'' + this.userID + '\', \'' + this.playSecretReply + '\' );"> 수정 </a>';
			                        html += '<a onclick="replyDelete('+value.playReplyID+',\''+ value.playReviewID+'\');"> 삭제 </a>';
			                    }
			                   	html += '<a href="javascript:void(0);" onclick="checkLike_reply('+value.playReplyID+',\''+ value.userID+'\');"><img src="resources/img/reply_like.png"></a>';  
		                    }
		                    else {
		                    	html += '<a href="javascript:void(0);" onclick="checkUser_reply();"><img src="resources/img/reply_like.png"></a>';    
			                }
		                    html += '<span id ="playReplyLike_'+ value.playReplyID +'">&nbsp;' + value.playReplyLike + '</span>';
			                html += '</span>';
		                    html += '</p>';
		                    html += '</div>';
		  	              	html += '<div id="insertReplyComment' + this.playReplyID +'"></div>';
		            }
	            }); 
            }
            $("#replyList").html(html);
        	$("#playSecretReply").attr('checked', false);		
        	$("#playSecretReply").val("0");
        	replyListPaging();
        }
    });
}
 
//댓글 등록
function replyInsert(insertData){
    $.ajax({
        url : '/insertPlayReply',
        type : 'post',
        data : insertData,
        success : function(data){
        	replyList();
            $('#playReplyContents').val('');
            
            var pageNum = getParam('pageNum');
        	var contentNum = getParam('contentNum');
        	var playReviewID = getParam('playReviewID');
        	if (isEmpty(pageNum) || isEmpty(contentNum)) {
            	pageNum = 1;
            	contentNum = 10;
           	}
           	if (parseInt(data / contentNum)  + 1 > pageNum && data % contentNum != 0) {
               	pageNum = parseInt(pageNum) + 1;
               	fn_pagination(pageNum, contentNum, playReviewID);               	
            }
        }
    });
}
// 댓글을 수정할 때 비밀댓글 여부에 따른 값 설정
$(document).on('change', '#playSecretReply_modify', function(e){
	e.preventDefault();
	if($("#playSecretReply_modify").is(":checked")){
		$("#playSecretReply_modify").val("1");
	}else{
		$("#playSecretReply_modify").val("0");
	}
}); 
//댓글 수정 - 댓글 내용 출력을 input 폼으로 변경 
function replyUpdate(playReplyID, playReplyContents, userID, playSecretReply){
	var htmls = "";
	htmls += '<div class="media text-muted pt-3" id="playReplyID' + playReplyID + '">';
	htmls += '<p class="media-body pb-3 mb-0 small lh-125 border-bottom horder-gray">';
	htmls += '<span class="d-block">';
	htmls += '<strong class="text-gray-dark">' + userID + '</strong>';
	htmls += '</span>';		
	htmls += '<textarea name="editContent" id="editContent" class="form-control" rows="3">';
	htmls += playReplyContents;
	htmls += '</textarea>';
	if (playSecretReply == "false") {
		htmls += '<label><input type="checkbox" id="playSecretReply_modify" name="playSecretReply_modify" value="false"> 비밀댓글</label>';
	}
	else {
		htmls += '<label><input type="checkbox" id="playSecretReply_modify" name="playSecretReply_modify" value="true" checked> 비밀댓글</label>';
	}
	htmls += '<span style="padding-left: 7px; font-size: 9pt">';
	htmls += '<a onclick="replyUpdateProc('+playReplyID+')" style="padding-right:5px">저장</a>';
	htmls += '<a onClick="replyList()">취소<a>';
	htmls += '</span>';
	htmls += '</p>';
	htmls += '</div>';
	$('#playReplyID' + playReplyID).replaceWith(htmls);
	$('#playReplyID' + playReplyID + ' #editContent').focus();
	$("#playSecretReply_modify").attr('checked', false);		
	$("#playSecretReply_modify").val("0");
}
 
//댓글 수정
function replyUpdateProc(playReplyID){
	if($("#editContent").val()==""){
		alert("댓글 내용을 입력하세요");
		$("#editContent").focus();
		return false;
	}
    var updateContent = $('#editContent').val(); 
    var playSecretReply = $('#playSecretReply_modify').val(); 
    $.ajax({
        url : '/updatePlayReply',
        type : 'post',
        data : {'playReplyContents' : updateContent, 'playReplyID' : playReplyID, 'playSecretReply' : playSecretReply},
        success : function(data){
             replyList();
        }
    });
}
$(document).on('change', '#playSecretReply', function(e){
	e.preventDefault();
	if($("#playSecretReply").is(":checked")){
		$("#playSecretReply").val("1");
	}else{
		$("#playSecretReply").val("0");
	}
}); 
$(document).on('change', '#playSecretReply_comment', function(e){
	e.preventDefault();
	if($("#playSecretReply_comment").is(":checked")){
		$("#playSecretReply_comment").val("1");
	}else{
		$("#playSecretReply_comment").val("0");
	}
}); 
// 답글 작성
function insertReplyComment(playReplyID, playReviewID){
	var userID = "<%=(String)session.getAttribute("USERID")%>"
	var htmls = "";
	htmls += '<form id="replyCommentForm">';
	htmls += '<div class="media text-muted pt-3" id="playReplyID' + playReplyID + '">';
	htmls += '<img src="resources/img/reply.png"></img>&nbsp;&nbsp;'
	htmls += '<p class="media-body pb-3 mb-0 small lh-125 border-bottom horder-gray">';
	htmls += '<span class="d-block">';
	htmls += '<strong class="text-gray-dark">' + userID + '</strong>';
	htmls += '</span>';		
	htmls += '<textarea name="playReplyContents_comment" id="playReplyContents_comment" class="form-control" rows="3">';
	htmls += '</textarea>';
	htmls += '<label><input type="checkbox" id="playSecretReply_comment" name="playSecretReply_comment" value="0"> 비밀댓글</label>';
	htmls += '<span style="padding-left: 7px; font-size: 9pt">';
	htmls += '<a onclick="replyCommentProc('+playReplyID+')" style="padding-right:5px">저장</a>';
	htmls += '<a onClick="replyList()">취소<a>';
	htmls += '</span>';
	htmls += '</p>';
	htmls += '</div>';
	htmls += '</form>';
	$("#insertReplyComment" + playReplyID).html(htmls);
	$("#playSecretReply_comment").attr('checked', false);		
	$("#playSecretReply_comment").val("0");
}
//답글 저장
function replyCommentProc(playReplyID){
	if($("#playReplyContents_comment").val()==""){
		alert("댓글 내용을 입력하세요");
		$("#playReplyContents_comment").focus();
		return false;
	}
	var playReplyContents = $('#playReplyContents_comment').val();
	var playSecretReply = $('#playSecretReply_comment').val();
    $.ajax({
        url : '/playReplyComment',
        type : 'post',
        data : {'playReplyContents' : playReplyContents, 'playReplyID' : playReplyID, 'playSecretReply' : playSecretReply},
        success : function(data){
            replyList();
        }
    });
}
 
//댓글 삭제 
function replyDelete(playReplyID, playReviewID){
	if (confirm('댓글을 삭제하시겠습니까?')) {
	    $.ajax({
	        url : '/deletePlayReply',
	        data: {"playReplyID":playReplyID, 'playReviewID':playReviewID},
	        type : 'post',
	        success : function(data){
	          	replyList(); //댓글 삭제후 목록 출력 
	            var pageNum = getParam('pageNum');
	         	var contentNum = getParam('contentNum');
	         	var playReviewID = getParam('playReviewID');
	         	if (isEmpty(pageNum) || isEmpty(contentNum)) {
	             	pageNum = 1;
	             	contentNum = 10;
	            }
	            if (data % contentNum == 0 && pageNum != 1) {
	                pageNum = parseInt(pageNum) - 1;
	                fn_pagination(pageNum, contentNum, playReviewID);               	
	             }
	        }
	    });
	}
}
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
function checkLike_reply(playReplyID, writerID) {
		$.ajax({
	        url : '/playReplyLike',
	        type : 'post',
	        data : {'playReplyID' : playReplyID},
	        dataType: 'json',
	        success : function(data){
	        	var userID = "<%=(String)session.getAttribute("USERID")%>"
		      	if (userID == writerID) {
			        alert("본인 글은 추천할 수 없습니다.");
			    }
		        else {
		 	        var html = ''; 
		 	       	var countID = '#playReplyLike_' + playReplyID;
		 	        if (data.count == 0){
		 	 	        alert('추천되었습니다.');
		 	 	    }
		 	        else {
		 	 	        alert('추천이 취소되었습니다.');
		 	 	    }
		 	        html += '&nbsp;' + data.playReplyLike;
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