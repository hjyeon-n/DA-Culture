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
import jy.project.review.dto.PlayReplyDto;
import jy.project.review.dto.PlayReplyLikeDto;
import jy.project.review.dto.UserDto;
import jy.project.review.page.ReviewSearch;
import jy.project.review.service.PlayReplyService;
import jy.project.review.service.PlayReviewService;

@RestController
public class PlayReplyAppController {
	private Logger logger = LoggerFactory.getLogger(PlayReplyAppController.class);

	@Autowired
	private PlayReplyService replyService;
	
	@Autowired
	private PlayReviewService reviewService;

	@RequestMapping(value = "/getPlayReplyPaging", method = { RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ReviewSearch getPlayReplyPaging(ModelAndView mv,
	        @RequestParam("playReviewID") int playReviewID,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
	        @RequestParam(required = false, defaultValue = "10") int contentNum) {

		ReviewSearch page = new ReviewSearch();
	    page.setPlayReviewID(playReviewID);
	      
	    int totalCount = this.replyService.playReplyCount(playReviewID);
	    page.pageInfo(pageNum, contentNum, totalCount);
	      
	    return page;
	}   
	
//	게시글 상세보기를 클릭했을 때 댓글 리스트 가져오기
	@RequestMapping(value = "/getPlayReplyList", method = { RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<PlayReplyDto> getPlayReplyList(ModelAndView mv,
			@RequestParam("playReviewID") int playReviewID,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int contentNum) {
		
		ReviewSearch page = new ReviewSearch();
		page.setPlayReviewID(playReviewID);
		
		int totalCount = this.replyService.playReplyCount(playReviewID);
		page.pageInfo(pageNum, contentNum, totalCount);
		List<PlayReplyDto> playReplyList = this.replyService.getPlayReplyList(page);
		
		return playReplyList;
	}	
	
//	댓글 입력하기
	@RequestMapping(value = "/insertPlayReply", method = RequestMethod.POST)
	@ResponseBody
	public int insertPlayReply(ModelAndView mv, HttpServletRequest request,
			@ModelAttribute("playReply") PlayReplyDto playReply) {
		
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();
		
		playReply.setUserID(userID);
		logger.info(playReply.toString());
//		reviweWriter는 후에 비밀댓글 읽기 권한을 위해 지정
		String playReviewWriter = reviewService.getPlayReviewWriter(playReply.getPlayReviewID());
		playReply.setPlayReviewWriter(playReviewWriter);
//		대댓글을 위해 parents 지정
		playReply.setPlayParentsWriter(userID);
		replyService.insertPlayReply(playReply);
//		게시글 목록에서 댓글 수 확인이 가능하도록 하기 위해 지정
		reviewService.plusReplyCnt(playReply.getPlayReviewID());
		
		int replyCnt = replyService.playReplyCount(playReply.getPlayReviewID());
		return replyCnt;
	}
	
//	댓글 수정
	@RequestMapping(value= "/updatePlayReply", method = RequestMethod.POST) 
    @ResponseBody
    public void updatePlayReply(ModelAndView mv,
    		@RequestParam("playReplyID") int playReplyID,
			@RequestParam("playReplyContents") String playReplyContents,
			@RequestParam("playSecretReply") boolean playSecretReply) throws Exception{
        
		PlayReplyDto playReply = new PlayReplyDto();
		playReply.setPlayReplyID(playReplyID);
		playReply.setPlayReplyContents(playReplyContents);
		playReply.setPlaySecretReply(playSecretReply);
        
        replyService.updatePlayReply(playReply);
    }
	
	@RequestMapping(value = "/playReplyComment", method = RequestMethod.POST)
	@ResponseBody
	public int playReplyComment(ModelAndView mv, HttpServletRequest request,
			@RequestParam("playReplyID") int playReplyID,
			@RequestParam("playReplyContents") String playReplyContents,
			@RequestParam("playSecretReply") boolean playSecretReply) {

//		이때  playReply는 답글의 reply객체, playReplyID는 원본 댓글의 ID, playSecretReply는 답글의 비밀댓글 여부
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();

//		답글을 달 원본 댓글
		PlayReplyDto originalReply = this.replyService.getPlayReply(playReplyID); 
		
//		답글의 객체
		PlayReplyDto playReply = new PlayReplyDto();
		
		playReply.setPlayReplyID(replyService.findLastPlayReplyID() + 1);
		playReply.setPlayReplyGID(playReplyID);

//		댓글과 대댓글의 정렬을 위해 지정
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("playReplyGID", playReply.getPlayReplyGID());
		map.put("playReplyOrder", originalReply.getPlayReplyOrder());
		this.replyService.setPlayReplyOrder(map);
		
//		비밀댓글의 읽기 권한 지정
		String playReviewWriter = reviewService.getPlayReviewWriter(originalReply.getPlayReviewID());
		String playParentsWriter = replyService.findPlayReplyUser(playReplyID); // 원본 댓글 user

		playReply.setPlayReplyContents(playReplyContents);
		playReply.setPlayReviewWriter(playReviewWriter);
		playReply.setPlayParentsWriter(playParentsWriter);
		playReply.setPlayReplyOrder(originalReply.getPlayReplyOrder() + 1);
		playReply.setPlayReplyParents(playReplyID);
		playReply.setUserID(userID);
		playReply.setPlayReviewID(originalReply.getPlayReviewID());
		playReply.setPlaySecretReply(playSecretReply);
		
		int playReviewID = playReply.getPlayReviewID();
		
		replyService.insertPlayReply(playReply);
		reviewService.plusReplyCnt(playReviewID);
		replyService.updateSecretComment(playReply);
		logger.info(playReply.toString());
		
		int replyCnt = replyService.playReplyCount(playReply.getPlayReviewID());
		return replyCnt;
	}
	
//	댓글 삭제
	@RequestMapping(value = "/deletePlayReply", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public int processDeletePlayBoard(ModelAndView mv, HttpServletRequest request,
			@RequestParam("playReplyID") int playReplyID,
			@RequestParam("playReviewID") int playReviewID) {
		
		PlayReplyDto playReply = replyService.getPlayReply(playReplyID);
		
//		게시글 목록에서 댓글 수를 확인할 수 있도록 함: 부모 댓글을 삭제하면 자식댓글도 자동으로 삭제되도록 하여 댓글 수를 맞춤
		if (playReply.getPlayReplyParents() == 0) {
			int cnt = replyService.countSameGID(playReplyID) + 1;
			for (int i = 0; i < cnt; i++) {
				reviewService.minusReplyCnt(playReviewID);
			}
		}
		else {
			reviewService.minusReplyCnt(playReviewID);
		}
		replyService.deletePlayReply_delete(playReplyID); //추천 수부터 없애기
		replyService.deletePlayReply(playReplyID); // 실제 댓글 없애기 -> 만약 답글이 달린 글이면 답글까지 전부 삭제
		
		int replyCnt = replyService.playReplyCount(playReviewID);
		return replyCnt;
	}
	
//	댓글 추천 기능
	@RequestMapping(value="/playReplyLike", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Integer> playReplyLike(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) int playReplyID) {
	
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();
		
		PlayReplyLikeDto playReplyLikeDto = new PlayReplyLikeDto(playReplyID, userID);
		int playReplyLike = replyService.getPlayReply(playReplyID).getPlayReplyLike();
		int count = replyService.findPlayReplyLike(playReplyLikeDto);
		
		if (count == 0) {
			playReplyLike += 1;
			replyService.insertPlayReplyLike(playReplyLikeDto);
			replyService.plusPlayReplyLike(playReplyID);
		}
		else {
			playReplyLike -= 1;
			replyService.deletePlayReplyLike(playReplyLikeDto);
			replyService.minusPlayReplyLike(playReplyID);
		}
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("count", count);
		map.put("playReplyLike", playReplyLike);
		
		return map;
	}
}