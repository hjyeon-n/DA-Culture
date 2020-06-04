<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
var bookReviewID = '${detailBookReview.bookReviewID}'; //게시글 번호
var userID = '${user.userID}';
$(document).on('click', '#btnReply', function(e){
	if($("#bookReplyContents").val()==""){
		alert("댓글 내용을 입력하세요");
		$("#bookReplyContents").focus();
		return false;
	}
	 var insertData = $('#ReplyInsertForm').serialize();
	 replyInsert(insertData); 
});

function replyListPaging(){
	var pageNum = getParam('pageNum');
	var contentNum = getParam('contentNum');
	var url = '/getBookReplyPaging?pageNum='+pageNum +'&contentNum='+contentNum;
    $.ajax({
        url : url,
        type : 'get',
        data : {"bookReviewID":bookReviewID},
        dataType: 'json',
        success : function(data){
        	var html =''; 
            html += '<ul class="pagination justify-content-center">';
            if (data.prev) {
               var startPage = data.startPage - 1;
               html += '<li class="page-item">';
	           html += '<a class="page-link" onClick="fn_pagination(' + startPage + ', ' + data.contentNum + ', ' + data.bookReviewID + ');">';      
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
                html += '<a class="page-link" id="page" onClick="fn_pagination(' + idx + ', ' + data.contentNum + ', ' + data.bookReviewID + ');">' + idx + '</a>';
                html += '</li>';
             }
          	}

          	if (data.next) {
             var endPage = data.endPage + 1;
             html += '<li class="page-item">';
             html += '<a class="page-link" onClick="fn_pagination(' + endPage + ', ' + data.contentNum + ', ' + data.bookReviewID + ');">';
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
	var url = '/getBookReplyList?pageNum='+pageNum +'&contentNum='+contentNum;
    $.ajax({
        url : url,
        type : 'get',
        data : {"bookReviewID":bookReviewID},
        dataType: 'json',
        success : function(data){
            var html =''; 
            if(data.length < 1){
				html+=("등록된 댓글이 없습니다.");
			}
            else {
	            $.each(data, function(key, value){
	            	var userID = "<%=(String)session.getAttribute("USERID")%>"
	            	if (value.bookReplyParents == 0) {
		                html += '<div class="media text-muted pt-3"  id="bookReplyID' + this.bookReplyID + '">';
	                    html += '<p class="media-body pb-3 mb-0 small lh-125 border-bottom horder-gray">';
	                    html += '<span class="d-block">';
	                    html += '<strong class="text-gray-dark">' + value.userID + '</strong>';     
	                    html += '<span style="padding-left: 7px; font-size: 9pt">'; 
	                    html += value.bookReplyDate;
		                html += '</span>';
	                    html += '</span>';
	                    html += '<br>';
	                    if (value.bookSecretReply == 0) {
			                html += value.bookReplyContents;
		            	}
		            	else if (userID == value.userID || userID == value.bookReviewWriter){
		            		html += '<img src="resources/img/lock.png">' + ' ' + value.bookReplyContents;
			            }
		            	else {
		                    html += '<img src="resources/img/lock.png"><span style="color:#FF6666"> 비밀 댓글 입니다.</span>';
			            }
			            html += '<br>';
			            html += '<br>';
	                    html += '<span style="font-size: 9pt">';
	                    if (userID != 'null') {
	                   		html += '<a style="padding-right:5px" onclick="insertReplyComment('+value.bookReplyID+',\''+ value.bookReviewID+'\');">답글</a>';
		                   	if (userID == value.userID) {
		                       	html += '<a style="padding-right:5px" onclick="replyUpdate(' + this.bookReplyID + ', \'' + this.bookReplyContents + '\', \'' + this.userID + '\', \'' + this.bookSecretReply + '\' );"> 수정 </a>';
		                        html += '<a onclick="replyDelete('+value.bookReplyID+',\''+ value.bookReviewID+'\');"> 삭제 </a>';
		                    }
		                   	html += '<a href="javascript:void(0);" onclick="checkLike_reply('+value.bookReplyID+',\''+ value.userID+'\');"><img src="resources/img/reply_like.png"></a>';  
	                    }
	                    else {
	                    	html += '<a href="javascript:void(0);" onclick="checkUser_reply();"><img src="resources/img/reply_like.png"></a>';    
		                }
	                    html += '<span id ="bookReplyLike_'+ value.bookReplyID +'">&nbsp;' + value.bookReplyLike + '</span>';
		                html += '</span>';
	                    html += '</p>';
	                    html += '</div>';
	  	              	html += '<div id="insertReplyComment' + this.bookReplyID +'"></div>';
	            	}
	            	else {
	            		 	html += '<div class="media text-muted pt-3"  id="bookReplyID' + this.bookReplyID + '">';
	            		 	html += '<img src="resources/img/reply.png"></img>&nbsp;&nbsp;'
		                	html += '<p class="media-body pb-3 mb-0 small lh-125 border-bottom horder-gray">';
		                    html += '<span class="d-block">';
		                    html += '<strong class="text-gray-dark">' + value.userID + '</strong>';     
		                    html += '<span style="padding-left: 7px; font-size: 9pt">'; 
		                    html += value.bookReplyDate;
			                html += '</span>';
		                    html += '</span>';
		                    html += '<br>';
		                    if (value.bookSecretReply == 0) {
				                html += value.bookReplyContents;
			            	}
			            	else if (userID == value.userID || userID == value.bookReviewWriter || userID == value.bookParentsWriter) {
			            		html += '<img src="resources/img/lock.png">' + ' ' + value.bookReplyContents;
				            }
			            	else {
			                    html += '<img src="resources/img/lock.png"><span style="color:#FF6666">비밀 댓글 입니다.</span>';
				            }
				            html += '<br>';
				            html += '<br>';
		                    html += '<span style="font-size: 9pt">';
		                    if (userID != 'null') {
			                   	if (userID == value.userID) {
			                       	html += '<a style="padding-right:5px" onclick="replyUpdate(' + this.bookReplyID + ', \'' + this.bookReplyContents + '\', \'' + this.userID + '\', \'' + this.bookSecretReply + '\' );"> 수정 </a>';
			                        html += '<a onclick="replyDelete('+value.bookReplyID+',\''+ value.bookReviewID+'\');"> 삭제 </a>';
			                    }
			                   	html += '<a href="javascript:void(0);" onclick="checkLike_reply('+value.bookReplyID+',\''+ value.userID+'\');"><img src="resources/img/reply_like.png"></a>';  
		                    }
		                    else {
		                    	html += '<a href="javascript:void(0);" onclick="checkUser_reply();"><img src="resources/img/reply_like.png"></a>';    
			                }
		                    html += '<span id ="bookReplyLike_'+ value.bookReplyID +'">&nbsp;' + value.bookReplyLike + '</span>';
			                html += '</span>';
		                    html += '</p>';
		                    html += '</div>';
		  	              	html += '<div id="insertReplyComment' + this.bookReplyID +'"></div>';
		            }
	            }); 
            }
            $("#replyList").html(html);
        	$("#bookSecretReply").attr('checked', false);		
        	$("#bookSecretReply").val("0");
        	replyListPaging();
        }
    });
}
 
//댓글 등록
function replyInsert(insertData){
    $.ajax({
        url : '/insertBookReply',
        type : 'post',
        data : insertData,
        success : function(data){
        	replyList();
            $('#bookReplyContents').val('');
            
            var pageNum = getParam('pageNum');
        	var contentNum = getParam('contentNum');
        	var bookReviewID = getParam('bookReviewID');
        	if (isEmpty(pageNum) || isEmpty(contentNum)) {
            	pageNum = 1;
            	contentNum = 10;
           	}
           	if (parseInt(data / contentNum)  + 1 > pageNum && data % contentNum != 0) {
               	pageNum = parseInt(pageNum) + 1;
               	fn_pagination(pageNum, contentNum, bookReviewID);               	
            }
        }
    });
}
// 댓글을 수정할 때 비밀댓글 여부에 따른 값 설정
$(document).on('change', '#bookSecretReply_modify', function(e){
	e.preventDefault();
	if($("#bookSecretReply_modify").is(":checked")){
		$("#bookSecretReply_modify").val("1");
	}else{
		$("#bookSecretReply_modify").val("0");
	}
}); 
//댓글 수정 - 댓글 내용 출력을 input 폼으로 변경 
function replyUpdate(bookReplyID, bookReplyContents, userID, bookSecretReply){
	var htmls = "";
	htmls += '<div class="media text-muted pt-3" id="bookReplyID' + bookReplyID + '">';
	htmls += '<p class="media-body pb-3 mb-0 small lh-125 border-bottom horder-gray">';
	htmls += '<span class="d-block">';
	htmls += '<strong class="text-gray-dark">' + userID + '</strong>';
	htmls += '</span>';		
	htmls += '<textarea name="editContent" id="editContent" class="form-control" rows="3">';
	htmls += bookReplyContents;
	htmls += '</textarea>';
	if (bookSecretReply == "false") {
		htmls += '<label><input type="checkbox" id="bookSecretReply_modify" name="bookSecretReply_modify" value="false"> 비밀댓글</label>';
	}
	else {
		htmls += '<label><input type="checkbox" id="bookSecretReply_modify" name="bookSecretReply_modify" value="true" checked> 비밀댓글</label>';
	}
	htmls += '<span style="padding-left: 7px; font-size: 9pt">';
	htmls += '<a onclick="replyUpdateProc('+bookReplyID+')" style="padding-right:5px">저장</a>';
	htmls += '<a onClick="replyList()">취소<a>';
	htmls += '</span>';
	htmls += '</p>';
	htmls += '</div>';
	$('#bookReplyID' + bookReplyID).replaceWith(htmls);
	$('#bookReplyID' + bookReplyID + ' #editContent').focus();
	$("#bookSecretReply_modify").attr('checked', false);		
	$("#bookSecretReply_modify").val("0");
}
 
//댓글 수정
function replyUpdateProc(bookReplyID){
	if($("#editContent").val()==""){
		alert("댓글 내용을 입력하세요");
		$("#editContent").focus();
		return false;
	}
    var updateContent = $('#editContent').val(); 
    var bookSecretReply = $('#bookSecretReply_modify').val(); 
    $.ajax({
        url : '/updateBookReply',
        type : 'post',
        data : {'bookReplyContents' : updateContent, 'bookReplyID' : bookReplyID, 'bookSecretReply' : bookSecretReply},
        success : function(data){
             replyList();
        }
    });
}
$(document).on('change', '#bookSecretReply', function(e){
	e.preventDefault();
	if($("#bookSecretReply").is(":checked")){
		$("#bookSecretReply").val("1");
	}else{
		$("#bookSecretReply").val("0");
	}
}); 
$(document).on('change', '#bookSecretReply_comment', function(e){
	e.preventDefault();
	if($("#bookSecretReply_comment").is(":checked")){
		$("#bookSecretReply_comment").val("1");
	}else{
		$("#bookSecretReply_comment").val("0");
	}
}); 
// 답글 작성
function insertReplyComment(bookReplyID, bookReviewID){
	var userID = "<%=(String)session.getAttribute("USERID")%>"
	var htmls = "";
	htmls += '<form id="replyCommentForm">';
	htmls += '<div class="media text-muted pt-3" id="bookReplyID' + bookReplyID + '">';
	htmls += '<img src="resources/img/reply.png"></img>&nbsp;&nbsp;'
	htmls += '<p class="media-body pb-3 mb-0 small lh-125 border-bottom horder-gray">';
	htmls += '<span class="d-block">';
	htmls += '<strong class="text-gray-dark">' + userID + '</strong>';
	htmls += '</span>';		
	htmls += '<textarea name="bookReplyContents_comment" id="bookReplyContents_comment" class="form-control" rows="3">';
	htmls += '</textarea>';
	htmls += '<label><input type="checkbox" id="bookSecretReply_comment" name="bookSecretReply_comment" value="0"> 비밀댓글</label>';
	htmls += '<span style="padding-left: 7px; font-size: 9pt">';
	htmls += '<a onclick="replyCommentProc('+bookReplyID+')" style="padding-right:5px">저장</a>';
	htmls += '<a onClick="replyList()">취소<a>';
	htmls += '</span>';
	htmls += '</p>';
	htmls += '</div>';
	htmls += '</form>';
	$("#insertReplyComment" + bookReplyID).html(htmls);
	$("#bookSecretReply_comment").attr('checked', false);		
	$("#bookSecretReply_comment").val("0");
}
//답글 저장
function replyCommentProc(bookReplyID){
	if($("#bookReplyContents_comment").val()==""){
		alert("댓글 내용을 입력하세요");
		$("#bookReplyContents_comment").focus();
		return false;
	}
	var bookReplyContents = $('#bookReplyContents_comment').val();
	var bookSecretReply = $('#bookSecretReply_comment').val();
    $.ajax({
        url : '/bookReplyComment',
        type : 'post',
        data : {'bookReplyContents' : bookReplyContents, 'bookReplyID' : bookReplyID, 'bookSecretReply' : bookSecretReply},
        success : function(data){
            replyList();
        }
    });
}
 
//댓글 삭제 
function replyDelete(bookReplyID, bookReviewID){
	if (confirm('댓글을 삭제하시겠습니까?')) {
	    $.ajax({
	        url : '/deleteBookReply',
	        data: {"bookReplyID":bookReplyID, 'bookReviewID':bookReviewID},
	        type : 'post',
	        success : function(data){
	          	replyList(); //댓글 삭제후 목록 출력 
	            var pageNum = getParam('pageNum');
	         	var contentNum = getParam('contentNum');
	         	var bookReviewID = getParam('bookReviewID');
	         	if (isEmpty(pageNum) || isEmpty(contentNum)) {
	             	pageNum = 1;
	             	contentNum = 10;
	            }
	            if (data % contentNum == 0 && pageNum != 1) {
	                pageNum = parseInt(pageNum) - 1;
	                fn_pagination(pageNum, contentNum, bookReviewID);               	
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
function checkLike_reply(bookReplyID, writerID) {
		$.ajax({
	        url : '/bookReplyLike',
	        type : 'post',
	        data : {'bookReplyID' : bookReplyID},
	        dataType: 'json',
	        success : function(data){
	        	var userID = "<%=(String)session.getAttribute("USERID")%>"
		      	if (userID == writerID) {
			        alert("본인 글은 추천할 수 없습니다.");
			    }
		        else {
		 	        var html = ''; 
		 	       	var countID = '#bookReplyLike_' + bookReplyID;
		 	        if (data.count == 0){
		 	 	        alert('추천되었습니다.');
		 	 	    }
		 	        else {
		 	 	        alert('추천이 취소되었습니다.');
		 	 	    }
		 	        html += '&nbsp;' + data.bookReplyLike;
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