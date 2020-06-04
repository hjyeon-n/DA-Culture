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
import jy.project.review.dto.ShowReplyDto;
import jy.project.review.dto.ShowReplyLikeDto;
import jy.project.review.dto.UserDto;
import jy.project.review.page.ReviewSearch;
import jy.project.review.service.ShowReplyService;
import jy.project.review.service.ShowReviewService;

@RestController
public class ShowReplyAppController {
	private Logger logger = LoggerFactory.getLogger(ShowReplyAppController.class);

	@Autowired
	private ShowReplyService replyService;
	
	@Autowired
	private ShowReviewService reviewService;

	@RequestMapping(value = "/getShowReplyPaging", method = { RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ReviewSearch getShowReplyPaging(ModelAndView mv,
	        @RequestParam("showReviewID") int showReviewID,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
	        @RequestParam(required = false, defaultValue = "10") int contentNum) {

		ReviewSearch page = new ReviewSearch();
	    page.setShowReviewID(showReviewID);
	      
	    int totalCount = this.replyService.showReplyCount(showReviewID);
	    page.pageInfo(pageNum, contentNum, totalCount);
	      
	    return page;
	}   
	
//	게시글 상세보기를 클릭했을 때 댓글 리스트 가져오기
	@RequestMapping(value = "/getShowReplyList", method = { RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<ShowReplyDto> getShowReplyList(ModelAndView mv,
			@RequestParam("showReviewID") int showReviewID,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int contentNum) {
		
		ReviewSearch page = new ReviewSearch();
		page.setShowReviewID(showReviewID);
		
		int totalCount = this.replyService.showReplyCount(showReviewID);
		page.pageInfo(pageNum, contentNum, totalCount);
		List<ShowReplyDto> showReplyList = this.replyService.getShowReplyList(page);
		
		return showReplyList;
	}	
	
//	댓글 입력하기
	@RequestMapping(value = "/insertShowReply", method = RequestMethod.POST)
	@ResponseBody
	public int insertShowReply(ModelAndView mv, HttpServletRequest request,
			@ModelAttribute("showReply") ShowReplyDto showReply) {
		
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();
		
		showReply.setUserID(userID);
		logger.info(showReply.toString());
//		reviweWriter는 후에 비밀댓글 읽기 권한을 위해 지정
		String showReviewWriter = reviewService.getShowReviewWriter(showReply.getShowReviewID());
		showReply.setShowReviewWriter(showReviewWriter);
//		대댓글을 위해 parents 지정
		showReply.setShowParentsWriter(userID);
		replyService.insertShowReply(showReply);
//		게시글 목록에서 댓글 수 확인이 가능하도록 하기 위해 지정
		reviewService.plusReplyCnt(showReply.getShowReviewID());
		
		int replyCnt = replyService.showReplyCount(showReply.getShowReviewID());
		return replyCnt;
	}
	
//	댓글 수정
	@RequestMapping(value= "/updateShowReply", method = RequestMethod.POST) 
    @ResponseBody
    public void updateShowReply(ModelAndView mv,
    		@RequestParam("showReplyID") int showReplyID,
			@RequestParam("showReplyContents") String showReplyContents,
			@RequestParam("showSecretReply") boolean showSecretReply) throws Exception{
        
		ShowReplyDto showReply = new ShowReplyDto();
		showReply.setShowReplyID(showReplyID);
		showReply.setShowReplyContents(showReplyContents);
		showReply.setShowSecretReply(showSecretReply);
        
        replyService.updateShowReply(showReply);
    }
	
	@RequestMapping(value = "/showReplyComment", method = RequestMethod.POST)
	@ResponseBody
	public int showReplyComment(ModelAndView mv, HttpServletRequest request,
			@RequestParam("showReplyID") int showReplyID,
			@RequestParam("showReplyContents") String showReplyContents,
			@RequestParam("showSecretReply") boolean showSecretReply) {

//		이때  showReply는 답글의 reply객체, showReplyID는 원본 댓글의 ID, showSecretReply는 답글의 비밀댓글 여부
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();

//		답글을 달 원본 댓글
		ShowReplyDto originalReply = this.replyService.getShowReply(showReplyID); 
		
//		답글의 객체
		ShowReplyDto showReply = new ShowReplyDto();
		
		showReply.setShowReplyID(replyService.findLastShowReplyID() + 1);
		showReply.setShowReplyGID(showReplyID);

//		댓글과 대댓글의 정렬을 위해 지정
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("showReplyGID", showReply.getShowReplyGID());
		map.put("showReplyOrder", originalReply.getShowReplyOrder());
		this.replyService.setShowReplyOrder(map);
		
//		비밀댓글의 읽기 권한 지정
		String showReviewWriter = reviewService.getShowReviewWriter(originalReply.getShowReviewID());
		String showParentsWriter = replyService.findShowReplyUser(showReplyID); // 원본 댓글 user

		showReply.setShowReplyContents(showReplyContents);
		showReply.setShowReviewWriter(showReviewWriter);
		showReply.setShowParentsWriter(showParentsWriter);
		showReply.setShowReplyOrder(originalReply.getShowReplyOrder() + 1);
		showReply.setShowReplyParents(showReplyID);
		showReply.setUserID(userID);
		showReply.setShowReviewID(originalReply.getShowReviewID());
		showReply.setShowSecretReply(showSecretReply);
		
		int showReviewID = showReply.getShowReviewID();
		
		replyService.insertShowReply(showReply);
		reviewService.plusReplyCnt(showReviewID);
		replyService.updateSecretComment(showReply);
		logger.info(showReply.toString());
		
		int replyCnt = replyService.showReplyCount(showReply.getShowReviewID());
		return replyCnt;
	}
	
//	댓글 삭제
	@RequestMapping(value = "/deleteShowReply", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public int processDeleteShowBoard(ModelAndView mv, HttpServletRequest request,
			@RequestParam("showReplyID") int showReplyID,
			@RequestParam("showReviewID") int showReviewID) {
		
		ShowReplyDto showReply = replyService.getShowReply(showReplyID);
		
//		게시글 목록에서 댓글 수를 확인할 수 있도록 함: 부모 댓글을 삭제하면 자식댓글도 자동으로 삭제되도록 하여 댓글 수를 맞춤
		if (showReply.getShowReplyParents() == 0) {
			int cnt = replyService.countSameGID(showReplyID) + 1;
			for (int i = 0; i < cnt; i++) {
				reviewService.minusReplyCnt(showReviewID);
			}
		}
		else {
			reviewService.minusReplyCnt(showReviewID);
		}
		replyService.deleteShowReply_delete(showReplyID); //추천 수부터 없애기
		replyService.deleteShowReply(showReplyID); // 실제 댓글 없애기 -> 만약 답글이 달린 글이면 답글까지 전부 삭제
		
		int replyCnt = replyService.showReplyCount(showReviewID);
		return replyCnt;
	}
	
//	댓글 추천 기능
	@RequestMapping(value="/showReplyLike", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Integer> showReplyLike(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) int showReplyID) {
	
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();
		
		ShowReplyLikeDto showReplyLikeDto = new ShowReplyLikeDto(showReplyID, userID);
		int showReplyLike = replyService.getShowReply(showReplyID).getShowReplyLike();
		int count = replyService.findShowReplyLike(showReplyLikeDto);
		
		if (count == 0) {
			showReplyLike += 1;
			replyService.insertShowReplyLike(showReplyLikeDto);
			replyService.plusShowReplyLike(showReplyID);
		}
		else {
			showReplyLike -= 1;
			replyService.deleteShowReplyLike(showReplyLikeDto);
			replyService.minusShowReplyLike(showReplyID);
		}
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("count", count);
		map.put("showReplyLike", showReplyLike);
		
		return map;
	}
}