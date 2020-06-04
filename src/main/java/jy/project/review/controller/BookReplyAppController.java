package jy.project.review.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import jy.project.review.constants.Constants;
import jy.project.review.dto.BookReplyDto;
import jy.project.review.dto.BookReplyLikeDto;
import jy.project.review.dto.UserDto;
import jy.project.review.page.ReviewSearch;
import jy.project.review.service.BookReplyService;
import jy.project.review.service.BookReviewService;

@RestController
public class BookReplyAppController {
	private Logger logger = LoggerFactory.getLogger(BookReplyAppController.class);

	@Autowired
	private BookReplyService replyService;
	
	@Autowired
	private BookReviewService reviewService;

	@RequestMapping(value = "/getBookReplyPaging", method = { RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ReviewSearch getBookReplyPaging(ModelAndView mv,
	        @RequestParam("bookReviewID") int bookReviewID,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
	        @RequestParam(required = false, defaultValue = "10") int contentNum) {

		ReviewSearch page = new ReviewSearch();
	    page.setBookReviewID(bookReviewID);
	      
	    int totalCount = this.replyService.bookReplyCount(bookReviewID);
	    page.pageInfo(pageNum, contentNum, totalCount);
	      
	    return page;
	}   
	
//	게시글 상세보기를 클릭했을 때 댓글 리스트 가져오기
	@RequestMapping(value = "/getBookReplyList", method = { RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<BookReplyDto> getBookReplyList(ModelAndView mv,
			@RequestParam("bookReviewID") int bookReviewID,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int contentNum) {
		
		ReviewSearch page = new ReviewSearch();
		page.setBookReviewID(bookReviewID);
		
		int totalCount = this.replyService.bookReplyCount(bookReviewID);
		page.pageInfo(pageNum, contentNum, totalCount);
		List<BookReplyDto> bookReplyList = this.replyService.getBookReplyList(page);
		
		return bookReplyList;
	}	
	
//	댓글 입력하기
	@RequestMapping(value = "/insertBookReply", method = RequestMethod.POST)
	@ResponseBody
	public int insertBookReply(ModelAndView mv, HttpServletRequest request,
			@ModelAttribute("bookReply") BookReplyDto bookReply) {
		
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();
		
		bookReply.setUserID(userID);
		logger.info(bookReply.toString());
//		reviweWriter는 후에 비밀댓글 읽기 권한을 위해 지정
		String bookReviewWriter = reviewService.getBookReviewWriter(bookReply.getBookReviewID());
		bookReply.setBookReviewWriter(bookReviewWriter);
//		대댓글을 위해 parents 지정
		bookReply.setBookParentsWriter(userID);
		replyService.insertBookReply(bookReply);
//		게시글 목록에서 댓글 수 확인이 가능하도록 하기 위해 지정
		reviewService.plusReplyCnt(bookReply.getBookReviewID());
		
		int replyCnt = replyService.bookReplyCount(bookReply.getBookReviewID());
		return replyCnt;
	}
	
//	댓글 수정
	@RequestMapping(value= "/updateBookReply", method = RequestMethod.POST) 
    @ResponseBody
    public void updateBookReply(ModelAndView mv,
    		@RequestParam("bookReplyID") int bookReplyID,
			@RequestParam("bookReplyContents") String bookReplyContents,
			@RequestParam("bookSecretReply") boolean bookSecretReply) throws Exception{
        
		BookReplyDto bookReply = new BookReplyDto();
		bookReply.setBookReplyID(bookReplyID);
		bookReply.setBookReplyContents(bookReplyContents);
		bookReply.setBookSecretReply(bookSecretReply);
        
        replyService.updateBookReply(bookReply);
    }
	
	@RequestMapping(value = "/bookReplyComment", method = RequestMethod.POST)
	@ResponseBody
	public int bookReplyComment(ModelAndView mv, HttpServletRequest request,
			@RequestParam("bookReplyID") int bookReplyID,
			@RequestParam("bookReplyContents") String bookReplyContents,
			@RequestParam("bookSecretReply") boolean bookSecretReply) {

//		이때  bookReply는 답글의 reply객체, bookReplyID는 원본 댓글의 ID, bookSecretReply는 답글의 비밀댓글 여부
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();

//		답글을 달 원본 댓글
		BookReplyDto originalReply = this.replyService.getBookReply(bookReplyID); 
		
//		답글의 객체
		BookReplyDto bookReply = new BookReplyDto();
		
		bookReply.setBookReplyID(replyService.findLastBookReplyID() + 1);
		bookReply.setBookReplyGID(bookReplyID);

//		댓글과 대댓글의 정렬을 위해 지정
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("bookReplyGID", bookReply.getBookReplyGID());
		map.put("bookReplyOrder", originalReply.getBookReplyOrder());
		this.replyService.setBookReplyOrder(map);
		
//		비밀댓글의 읽기 권한 지정
		String bookReviewWriter = reviewService.getBookReviewWriter(originalReply.getBookReviewID());
		String bookParentsWriter = replyService.findBookReplyUser(bookReplyID); // 원본 댓글 user

		bookReply.setBookReplyContents(bookReplyContents);
		bookReply.setBookReviewWriter(bookReviewWriter);
		bookReply.setBookParentsWriter(bookParentsWriter);
		bookReply.setBookReplyOrder(originalReply.getBookReplyOrder() + 1);
		bookReply.setBookReplyParents(bookReplyID);
		bookReply.setUserID(userID);
		bookReply.setBookReviewID(originalReply.getBookReviewID());
		bookReply.setBookSecretReply(bookSecretReply);
		
		int bookReviewID = bookReply.getBookReviewID();
		
		replyService.insertBookReply(bookReply);
		reviewService.plusReplyCnt(bookReviewID);
		replyService.updateSecretComment(bookReply);
		logger.info(bookReply.toString());
		
		int replyCnt = replyService.bookReplyCount(bookReply.getBookReviewID());
		return replyCnt;
	}
	
//	댓글 삭제
	@RequestMapping(value = "/deleteBookReply", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public int processDeleteBookBoard(ModelAndView mv, HttpServletRequest request,
			@RequestParam("bookReplyID") int bookReplyID,
			@RequestParam("bookReviewID") int bookReviewID) {
		
		BookReplyDto bookReply = replyService.getBookReply(bookReplyID);
		
//		게시글 목록에서 댓글 수를 확인할 수 있도록 함: 부모 댓글을 삭제하면 자식댓글도 자동으로 삭제되도록 하여 댓글 수를 맞춤
		if (bookReply.getBookReplyParents() == 0) {
			int cnt = replyService.countSameGID(bookReplyID) + 1;
			for (int i = 0; i < cnt; i++) {
				reviewService.minusReplyCnt(bookReviewID);
			}
		}
		else {
			reviewService.minusReplyCnt(bookReviewID);
		}
		replyService.deleteBookReply_delete(bookReplyID); //추천 수부터 없애기
		replyService.deleteBookReply(bookReplyID); // 실제 댓글 없애기 -> 만약 답글이 달린 글이면 답글까지 전부 삭제
		
		int replyCnt = replyService.bookReplyCount(bookReviewID);
		return replyCnt;
	}
	
//	댓글 추천 기능
	@RequestMapping(value="/bookReplyLike", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Integer> bookReplyLike(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) int bookReplyID) {
	
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();
		
		BookReplyLikeDto bookReplyLikeDto = new BookReplyLikeDto(bookReplyID, userID);
		int bookReplyLike = replyService.getBookReply(bookReplyID).getBookReplyLike();
		int count = replyService.findBookReplyLike(bookReplyLikeDto);
		
		if (count == 0) {
			bookReplyLike += 1;
			replyService.insertBookReplyLike(bookReplyLikeDto);
			replyService.plusBookReplyLike(bookReplyID);
		}
		else {
			bookReplyLike -= 1;
			replyService.deleteBookReplyLike(bookReplyLikeDto);
			replyService.minusBookReplyLike(bookReplyID);
		}
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("count", count);
		map.put("bookReplyLike", bookReplyLike);
		
		return map;
	}
}