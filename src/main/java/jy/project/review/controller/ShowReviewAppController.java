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
import jy.project.review.dto.ShowReviewLikeDto;
import jy.project.review.dto.ShowReviewDto;
import jy.project.review.dto.UserDto;
import jy.project.review.page.ReviewSearch;
import jy.project.review.service.ShowReviewService;

@RestController
public class ShowReviewAppController {

	private Logger logger = LoggerFactory.getLogger(ShowReviewAppController.class);

	@Autowired
	private ShowReviewService reviewService;
	
//	게시판
	@RequestMapping(value = "/showBoard", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showBoard(ModelAndView mv, HttpServletRequest request,
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

		int totalCount = this.reviewService.showPageCount(map);

		reviewSearch.pageInfo(pageNum, contentNum, totalCount);
		List<ShowReviewDto> showReviewList = this.reviewService.getAll_show(reviewSearch);

		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);

		mv.addObject("user", userDto);
		mv.addObject("showReviewList", showReviewList);
		mv.addObject("reviewSearch", reviewSearch);
		mv.setViewName("board/showBoard");
		return mv;
	}

//	게시판 게시글 작성
	@RequestMapping(value = "/showBoardForm", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showBoardForm(ModelAndView mv, HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		mv.addObject("user", userDto);
		mv.setViewName("board/showBoardForm");
		return mv;
	}

//	게시글 저장
	@RequestMapping(value = "/showBoard/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showBoardForm_save(ModelAndView mv, HttpServletRequest request,
			@ModelAttribute("showDto") ShowReviewDto showDto, RedirectAttributes rttr) {
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();
		showDto.setUserID(userID);
		System.out.println(showDto.toString());
		reviewService.insertShowReview(showDto);

		mv.setViewName(Constants.REVIEW_REDIRECT_MOVIE);
		return mv;
	}

//  게시글 상세보기
	@RequestMapping(value = "/showBoardDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView detailShowReview(ModelAndView mv, HttpServletRequest request,
			@RequestParam("showReviewID") int showReviewID) {
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		reviewService.showReviewViews(showReviewID);
     
		mv.addObject("user", userDto);
		mv.addObject("detailShowReview", reviewService.getDetial_show(showReviewID));
		mv.setViewName("board/showBoardDetail");
		return mv;
	}

//	게시글 수정
	@RequestMapping(value = "/showReview-modify", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showReviewModify(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) int showReviewID) {
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		mv.addObject("user", userDto);
		mv.addObject("detailShowReview", reviewService.getDetial_show(showReviewID));
		mv.setViewName("board/showBoard_modify");
		return mv;
	}

//	게시글 수정 수행
	@RequestMapping(value = "/updateShowReview-process", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView processUpdateShowBoard(ModelAndView mv, HttpServletRequest request,
			@ModelAttribute("showDto") ShowReviewDto showDto) {
		logger.info(showDto.getShowReviewPreview());
		reviewService.updateShowReview(showDto);
		mv.setViewName(Constants.REVIEW_REDIRECT_MOVIE);
		return mv;
	}

//	게시글 삭제 과정
	@RequestMapping(value = "/deleteShowReview-process", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView processDeleteShowBoard(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) int showReviewID) {
		reviewService.deleteShowReviewLike_delete(showReviewID);
		reviewService.deleteShowReview(showReviewID);
		mv.setViewName(Constants.REVIEW_REDIRECT_MOVIE);
		return mv;
	}

//	내가 쓴 게시물 확인
	@RequestMapping(value="/myShowBoard", method = {RequestMethod.GET, RequestMethod.POST}) 
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
		  
		  int totalCount = this.reviewService.myShowPageCount(map);
		  
		  reviewSearch.pageInfo(pageNum, contentNum, totalCount); 
		  List<ShowReviewDto> myShowReviewList = this.reviewService.getAll_myShow(reviewSearch);
		  
		  mv.addObject("user", userDto); 
		  mv.addObject("myShowReviewList", myShowReviewList); 
		  mv.addObject("reviewSearch", reviewSearch);
		  mv.setViewName("mypage/myShowBoard"); 
		  return mv;
	}
	
//	작성자  게시판 확인
	@RequestMapping(value="/userShowBoard", method = {RequestMethod.GET, RequestMethod.POST}) 
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
		  
		  int totalCount = this.reviewService.privateShowPageCount(map);
		  
		  reviewSearch.pageInfo(pageNum, contentNum, totalCount); 
		  List<ShowReviewDto> userShowReviewList = this.reviewService.getAll_privateShow(reviewSearch);

		  mv.addObject("user", userDto); 
		  mv.addObject("writerID", writerID);
		  mv.addObject("userShowReviewList", userShowReviewList); 
		  mv.addObject("reviewSearch", reviewSearch);
		  mv.setViewName("user/userShowBoard"); 
		  return mv;
	}
	
//	게시글 추천 기능
	@RequestMapping(value="/showReviewLike", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Integer> showReviewLike(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) int showReviewID) {
	
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();
		
		ShowReviewLikeDto showLike = new ShowReviewLikeDto(showReviewID, userID);
		int showReviewLike = reviewService.getDetial_show(showReviewID).getShowReviewLike();
		int count = reviewService.findShowReviewLike(showLike);
		
		if (count == 0) {
			showReviewLike += 1;
			reviewService.insertShowReviewLike(showLike);
			reviewService.plusShowReviewLike(showReviewID);
		}
		else {
			showReviewLike -= 1;
			reviewService.deleteShowReviewLike(showLike);
			reviewService.minusShowReviewLike(showReviewID);
		}
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("count", count);
		map.put("showReviewLike", showReviewLike);
		
		return map;
	}
	
//	게시글 상세보기를 할 때, 해당 게시글에 대한 추천수를 표시하기 위함
	@RequestMapping(value="/showCheckHeart", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public int checkHeart(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) int showReviewID) {
	
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();

		ShowReviewLikeDto showLike = new ShowReviewLikeDto(showReviewID, userID);
		int count = reviewService.findShowReviewLike(showLike);
		
		return count;
	}
	
//	내가 추천한 게시글 확인
	@RequestMapping(value="/userLikesShow", method = {RequestMethod.GET, RequestMethod.POST}) 
	public ModelAndView userLikesShow(ModelAndView mv, HttpServletRequest request,
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
		  
		  int totalCount = this.reviewService.userLikesShowCount(userID);
		  System.out.println(totalCount);
		 
		  reviewSearch.pageInfo(pageNum, contentNum, totalCount); 
		  List<ShowReviewDto> userLikesShowReviewList = this.reviewService.getAll_LikeShow(userID);
		  
		  for(int i = 0; i < userLikesShowReviewList.size(); i++) {
			  System.out.println(userLikesShowReviewList.get(i).getShowTitle());
		  }
		  
		  mv.addObject("user", userDto); 
		  mv.addObject("userLikesShowReviewList", userLikesShowReviewList); 
		  mv.addObject("reviewSearch", reviewSearch);
		  mv.setViewName("like/likeShowBoard"); 
		  return mv;
	}
}