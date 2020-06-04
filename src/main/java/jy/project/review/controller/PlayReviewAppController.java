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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jy.project.review.constants.Constants;
import jy.project.review.dto.PlayReviewLikeDto;
import jy.project.review.dto.PlayReviewDto;
import jy.project.review.dto.UserDto;
import jy.project.review.page.ReviewSearch;
import jy.project.review.service.PlayReviewService;

@RestController
public class PlayReviewAppController {

	private Logger logger = LoggerFactory.getLogger(PlayReviewAppController.class);

	@Autowired
	private PlayReviewService reviewService;
	
//	게시판
	@RequestMapping(value = "/playBoard", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView playBoard(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int contentNum,
			@RequestParam(required = false) String searchType,
			@RequestParam(required = false) String keyword) {

		ReviewSearch reviewSearch = new ReviewSearch();
		reviewSearch.setSearchType(searchType);
		reviewSearch.setKeyword(keyword);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("searchType", searchType);

		int totalCount = this.reviewService.playPageCount(map);

		reviewSearch.pageInfo(pageNum, contentNum, totalCount);
		List<PlayReviewDto> playReviewList = this.reviewService.getAll_play(reviewSearch);

		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);

		mv.addObject("user", userDto);
		mv.addObject("playReviewList", playReviewList);
		mv.addObject("reviewSearch", reviewSearch);
		mv.setViewName("board/playBoard");
		return mv;
	}

//	게시판 게시글 작성
	@RequestMapping(value = "/playBoardForm", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView playBoardForm(ModelAndView mv, HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		mv.addObject("user", userDto);
		mv.setViewName("board/playBoardForm");
		return mv;
	}

//	게시글 저장
	@RequestMapping(value = "/playBoard/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView playBoardForm_save(ModelAndView mv, HttpServletRequest request,
			@ModelAttribute("playDto") PlayReviewDto playDto, RedirectAttributes rttr) {
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();
		playDto.setUserID(userID);
		System.out.println(playDto.toString());
		reviewService.insertPlayReview(playDto);

		mv.setViewName(Constants.REVIEW_REDIRECT_MOVIE);
		return mv;
	}

//  게시글 상세보기
	@RequestMapping(value = "/playBoardDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView detailPlayReview(ModelAndView mv, HttpServletRequest request,
			@RequestParam("playReviewID") int playReviewID) {
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		reviewService.playReviewViews(playReviewID);
     
		mv.addObject("user", userDto);
		mv.addObject("detailPlayReview", reviewService.getDetial_play(playReviewID));
		mv.setViewName("board/playBoardDetail");
		return mv;
	}

//	게시글 수정
	@RequestMapping(value = "/playReview-modify", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView playReviewModify(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) int playReviewID) {
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		mv.addObject("user", userDto);
		mv.addObject("detailPlayReview", reviewService.getDetial_play(playReviewID));
		mv.setViewName("board/playBoard_modify");
		return mv;
	}

//	게시글 수정 수행
	@RequestMapping(value = "/updatePlayReview-process", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView processUpdatePlayBoard(ModelAndView mv, HttpServletRequest request,
			@ModelAttribute("playDto") PlayReviewDto playDto) {
		logger.info(playDto.getPlayReviewPreview());
		reviewService.updatePlayReview(playDto);
		mv.setViewName(Constants.REVIEW_REDIRECT_MOVIE);
		return mv;
	}

//	게시글 삭제 과정
	@RequestMapping(value = "/deletePlayReview-process", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView processDeletePlayBoard(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) int playReviewID) {
		reviewService.deletePlayReviewLike_delete(playReviewID);
		reviewService.deletePlayReview(playReviewID);
		mv.setViewName(Constants.REVIEW_REDIRECT_MOVIE);
		return mv;
	}

//	내가 쓴 게시물 확인
	@RequestMapping(value="/myPlayBoard", method = {RequestMethod.GET, RequestMethod.POST}) 
	public ModelAndView myBoard(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int pageNum, 
			@RequestParam(required = false, defaultValue = "10") int contentNum, 
			@RequestParam(required = false) String searchType, 
			@RequestParam(required = false) String keyword) {
	  
		  HttpSession httpSession = request.getSession();
		  UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		  String userID = userDto.getUserID();

		  ReviewSearch reviewSearch = new ReviewSearch();
		  reviewSearch.setSearchType(searchType); 
		  reviewSearch.setKeyword(keyword);
		  reviewSearch.setUserID(userID);
		  
		  HashMap<String, Object> map = new HashMap<String, Object>();
		  map.put("keyword", keyword);
		  map.put("userID", userID);
		  map.put("searchType", searchType);
		  
		  int totalCount = this.reviewService.myPlayPageCount(map);
		  
		  reviewSearch.pageInfo(pageNum, contentNum, totalCount); 
		  List<PlayReviewDto> myPlayReviewList = this.reviewService.getAll_myPlay(reviewSearch);
		  
		  mv.addObject("user", userDto); 
		  mv.addObject("myPlayReviewList", myPlayReviewList); 
		  mv.addObject("reviewSearch", reviewSearch);
		  mv.setViewName("mypage/myPlayBoard"); 
		  return mv;
	}
	
//	작성자  게시판 확인
	@RequestMapping(value="/userPlayBoard", method = {RequestMethod.GET, RequestMethod.POST}) 
	public ModelAndView userBoard(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int pageNum, 
			@RequestParam(required = false, defaultValue = "10") int contentNum, 
			@RequestParam(required = false) String searchType, 
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String writerID) {

		  HttpSession httpSession = request.getSession();
		  UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		  
		  ReviewSearch reviewSearch = new ReviewSearch();
		  reviewSearch.setSearchType(searchType); 
		  reviewSearch.setKeyword(keyword);
		  reviewSearch.setUserID(writerID);
		  
		  HashMap<String, Object> map = new HashMap<String, Object>();
		  map.put("keyword", keyword);
		  map.put("userID", writerID);
		  map.put("searchType", searchType);
		  
		  int totalCount = this.reviewService.privatePlayPageCount(map);
		  
		  reviewSearch.pageInfo(pageNum, contentNum, totalCount); 
		  List<PlayReviewDto> userPlayReviewList = this.reviewService.getAll_privatePlay(reviewSearch);

		  mv.addObject("user", userDto); 
		  mv.addObject("writerID", writerID);
		  mv.addObject("userPlayReviewList", userPlayReviewList); 
		  mv.addObject("reviewSearch", reviewSearch);
		  mv.setViewName("user/userPlayBoard"); 
		  return mv;
	}
	
//	게시글 추천 기능
	@RequestMapping(value="/playReviewLike", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Integer> playReviewLike(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) int playReviewID) {
	
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();
		
		PlayReviewLikeDto playLike = new PlayReviewLikeDto(playReviewID, userID);
		int playReviewLike = reviewService.getDetial_play(playReviewID).getPlayReviewLike();
		int count = reviewService.findPlayReviewLike(playLike);
		
		if (count == 0) {
			playReviewLike += 1;
			reviewService.insertPlayReviewLike(playLike);
			reviewService.plusPlayReviewLike(playReviewID);
		}
		else {
			playReviewLike -= 1;
			reviewService.deletePlayReviewLike(playLike);
			reviewService.minusPlayReviewLike(playReviewID);
		}
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("count", count);
		map.put("playReviewLike", playReviewLike);
		
		return map;
	}
	
//	게시글 상세보기를 할 때, 해당 게시글에 대한 추천수를 표시하기 위함
	@RequestMapping(value="/playCheckHeart", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public int checkHeart(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) int playReviewID) {
	
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();

		PlayReviewLikeDto playLike = new PlayReviewLikeDto(playReviewID, userID);
		int count = reviewService.findPlayReviewLike(playLike);
		
		return count;
	}
	
//	내가 추천한 게시글 확인
	@RequestMapping(value="/userLikesPlay", method = {RequestMethod.GET, RequestMethod.POST}) 
	public ModelAndView userLikesPlay(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int pageNum, 
			@RequestParam(required = false, defaultValue = "10") int contentNum, 
			@RequestParam(required = false) String searchType, 
			@RequestParam(required = false) String keyword) {

		  HttpSession httpSession = request.getSession();
		  UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		  String userID = userDto.getUserID();
		  
		  ReviewSearch reviewSearch = new ReviewSearch();
		  reviewSearch.setSearchType(searchType); 
		  reviewSearch.setKeyword(keyword);
		  
		  int totalCount = this.reviewService.userLikesPlayCount(userID);
		  System.out.println(totalCount);
		 
		  reviewSearch.pageInfo(pageNum, contentNum, totalCount); 
		  List<PlayReviewDto> userLikesPlayReviewList = this.reviewService.getAll_LikePlay(userID);
		  
		  for(int i = 0; i < userLikesPlayReviewList.size(); i++) {
			  System.out.println(userLikesPlayReviewList.get(i).getPlayTitle());
		  }
		  
		  mv.addObject("user", userDto); 
		  mv.addObject("userLikesPlayReviewList", userLikesPlayReviewList); 
		  mv.addObject("reviewSearch", reviewSearch);
		  mv.setViewName("like/likePlayBoard"); 
		  return mv;
	}
}