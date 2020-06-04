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
import jy.project.review.dto.MovieReplyDto;
import jy.project.review.dto.MovieReplyLikeDto;
import jy.project.review.dto.UserDto;
import jy.project.review.page.ReviewSearch;
import jy.project.review.service.MovieReplyService;
import jy.project.review.service.MovieReviewService;

@RestController
public class MovieReplyAppController {
	private Logger logger = LoggerFactory.getLogger(MovieReplyAppController.class);

	@Autowired
	private MovieReplyService replyService;
	
	@Autowired
	private MovieReviewService reviewService;

//	페이지네이션 적용
	@RequestMapping(value = "/getMovieReplyPaging", method = { RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ReviewSearch getMovieReplyPaging(ModelAndView mv,
	        @RequestParam("movieReviewID") int movieReviewID,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
	        @RequestParam(required = false, defaultValue = "10") int contentNum) {

		ReviewSearch page = new ReviewSearch();
	    page.setMovieReviewID(movieReviewID);
	      
//	    페이지네이션을 위해서 댓글 수 가져오기
	    int totalCount = this.replyService.movieReplyCount(movieReviewID);
	    page.pageInfo(pageNum, contentNum, totalCount);
	      
	    return page;
	}   
	
//	게시글 상세보기를 클릭했을 때 댓글 리스트 가져오기
	@RequestMapping(value = "/getMovieReplyList", method = { RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<MovieReplyDto> getMovieReplyList(ModelAndView mv,
			@RequestParam("movieReviewID") int movieReviewID,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int contentNum) {
		
		ReviewSearch page = new ReviewSearch();
		page.setMovieReviewID(movieReviewID);
		
		int totalCount = this.replyService.movieReplyCount(movieReviewID);
		page.pageInfo(pageNum, contentNum, totalCount);
		List<MovieReplyDto> movieReplyList = this.replyService.getMovieReplyList(page);
		
		return movieReplyList;
	}	
	
//	댓글 입력하기
	@RequestMapping(value = "/insertMovieReply", method = RequestMethod.POST)
	@ResponseBody
	public int insertMovieReply(ModelAndView mv, HttpServletRequest request,
			@ModelAttribute("movieReply") MovieReplyDto movieReply) {
		
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();
		
		movieReply.setUserID(userID);
		logger.info(movieReply.toString());
//		reviweWriter는 후에 비밀댓글 읽기 권한을 위해 지정
		String movieReviewWriter = reviewService.getMovieReviewWriter(movieReply.getMovieReviewID());
		movieReply.setMovieReviewWriter(movieReviewWriter);
//		대댓글을 위해 parents 지정 기본은 0
		movieReply.setMovieParentsWriter(userID);
//		댓글 삽입
		replyService.insertMovieReply(movieReply);
//		게시글 목록에서 댓글 수 확인이 가능하도록 하기 위해 지정
		reviewService.plusReplyCnt(movieReply.getMovieReviewID());
		
		int replyCnt = replyService.movieReplyCount(movieReply.getMovieReviewID());
		return replyCnt;
	}
	
//	댓글 수정
	@RequestMapping(value= "/updateMovieReply", method = RequestMethod.POST) 
    @ResponseBody
    public void updateMovieReply(ModelAndView mv,
    		@RequestParam("movieReplyID") int movieReplyID,
			@RequestParam("movieReplyContents") String movieReplyContents,
			@RequestParam("movieSecretReply") boolean movieSecretReply) throws Exception{
        
		MovieReplyDto movieReply = new MovieReplyDto();
		movieReply.setMovieReplyID(movieReplyID);
		movieReply.setMovieReplyContents(movieReplyContents);
		movieReply.setMovieSecretReply(movieSecretReply);
        
        replyService.updateMovieReply(movieReply);
    }
	
	@RequestMapping(value = "/movieReplyComment", method = RequestMethod.POST)
	@ResponseBody
	public int movieReplyComment(ModelAndView mv, HttpServletRequest request,
			@RequestParam("movieReplyID") int movieReplyID,
			@RequestParam("movieReplyContents") String movieReplyContents,
			@RequestParam("movieSecretReply") boolean movieSecretReply) {

//		이때  movieReply는 답글의 reply객체, movieReplyID는 부모 댓글의 ID, movieSecretReply는 답글의 비밀댓글 여부
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();

//		답글을 달 부모  댓글
		MovieReplyDto originalReply = this.replyService.getMovieReply(movieReplyID); 
		
//		답글의 객체
		MovieReplyDto movieReply = new MovieReplyDto();
		
//		답글의 ID는 마지막 movieReplyID의 + 1
		movieReply.setMovieReplyID(replyService.findLastMovieReplyID() + 1);
		
//		답글의 GID는 부모 댓글의 ID
		movieReply.setMovieReplyGID(movieReplyID);

//		댓글과 답글의 정렬을 위해 지정
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("movieReplyGID", movieReply.getMovieReplyGID());
		map.put("movieReplyOrder", originalReply.getMovieReplyOrder());
		this.replyService.setMovieReplyOrder(map);
		
//		비밀댓글의 읽기 권한 지정
		String movieReviewWriter = reviewService.getMovieReviewWriter(originalReply.getMovieReviewID());
		String movieParentsWriter = replyService.findMovieReplyUser(movieReplyID); // 원본 댓글 user

		movieReply.setMovieReplyContents(movieReplyContents);
		movieReply.setMovieReviewWriter(movieReviewWriter);
		movieReply.setMovieParentsWriter(movieParentsWriter);
		movieReply.setMovieReplyOrder(originalReply.getMovieReplyOrder() + 1);
		movieReply.setMovieReplyParents(movieReplyID);
		movieReply.setUserID(userID);
		movieReply.setMovieReviewID(originalReply.getMovieReviewID());
		movieReply.setMovieSecretReply(movieSecretReply);
		
		int movieReviewID = movieReply.getMovieReviewID();
		
		replyService.insertMovieReply(movieReply);
		reviewService.plusReplyCnt(movieReviewID);
		replyService.updateSecretComment(movieReply);
		logger.info(movieReply.toString());
		
		int replyCnt = replyService.movieReplyCount(movieReply.getMovieReviewID());
		return replyCnt;
	}
	
//	댓글 삭제
	@RequestMapping(value = "/deleteMovieReply", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public int processDeleteMovieBoard(ModelAndView mv, HttpServletRequest request,
			@RequestParam("movieReplyID") int movieReplyID,
			@RequestParam("movieReviewID") int movieReviewID) {
		
		MovieReplyDto movieReply = replyService.getMovieReply(movieReplyID);
		
//		게시글 목록에서 댓글 수를 확인할 수 있도록 함: 부모 댓글을 삭제하면 자식댓글도 자동으로 삭제되도록 하여 댓글 수를 맞춤
		if (movieReply.getMovieReplyParents() == 0) {
			int cnt = replyService.countSameGID(movieReplyID) + 1;
			for (int i = 0; i < cnt; i++) {
				reviewService.minusReplyCnt(movieReviewID);
			}
		}
		else {
			reviewService.minusReplyCnt(movieReviewID);
		}
		replyService.deleteMovieReply_delete(movieReplyID); //추천 수부터 없애기
		replyService.deleteMovieReply(movieReplyID); // 실제 댓글 없애기 -> 만약 답글이 달린 글이면 답글까지 전부 삭제
		
		int replyCnt = replyService.movieReplyCount(movieReviewID);
		return replyCnt;
	}
	
//	댓글 추천 기능
	@RequestMapping(value="/movieReplyLike", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Integer> movieReplyLike(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) int movieReplyID) {
	
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();
		
		MovieReplyLikeDto movieReplyLikeDto = new MovieReplyLikeDto(movieReplyID, userID);
		int movieReplyLike = replyService.getMovieReply(movieReplyID).getMovieReplyLike();
		int count = replyService.findMovieReplyLike(movieReplyLikeDto);
		
//		댓글에 좋아요 기능을 눌러본 적이 없을 때 좋아요 개수가 증가하고 like 테이블에 사용자의 ID와 replyID가 삽입됨
		if (count == 0) {
			movieReplyLike += 1;
			replyService.insertMovieReplyLike(movieReplyLikeDto);
			replyService.plusMovieReplyLike(movieReplyID);
		}
//		댓글에 좋아요 기능을 눌려 있는 상태에서 한번 더 누르면 취소가 되고, 좋아요 개수가 감소하고 like 테이블에 사용자의 ID와 replyID가 삭제됨
		else {
			movieReplyLike -= 1;
			replyService.deleteMovieReplyLike(movieReplyLikeDto);
			replyService.minusMovieReplyLike(movieReplyID);
		}
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("count", count);
		map.put("movieReplyLike", movieReplyLike);
		
		return map;
	}
}