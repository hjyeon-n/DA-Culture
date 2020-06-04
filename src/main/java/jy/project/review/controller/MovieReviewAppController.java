package jy.project.review.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jy.project.review.constants.Constants;
import jy.project.review.dto.MovieReviewLikeDto;
import jy.project.review.dto.MovieReviewDto;
import jy.project.review.dto.UserDto;
import jy.project.review.page.ReviewSearch;
import jy.project.review.parserDto.MovieParser;
import jy.project.review.service.MovieReviewService;

@RestController
public class MovieReviewAppController {

	private Logger logger = LoggerFactory.getLogger(MovieReviewAppController.class);

	@Autowired
	private MovieReviewService reviewService;
	
//	게시판
	@RequestMapping(value = "/movieBoard", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView movieBoard(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int contentNum,
			@RequestParam(required = false) String searchType,
			@RequestParam(required = false) String keyword) {

//		검색 기능
		ReviewSearch reviewSearch = new ReviewSearch();
		reviewSearch.setSearchType(searchType);
		reviewSearch.setKeyword(keyword);
		
//		검색한 결과값을 가져오기 위해 map에 키워드와 검색 타입 저장 후 sql 쿼리에 삽입
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("searchType", searchType);

		int totalCount = this.reviewService.moviePageCount(map);

//		페이징과 검색 기능이 적용된 후의 리스트를 가지고 옴
		reviewSearch.pageInfo(pageNum, contentNum, totalCount);
		List<MovieReviewDto> movieReviewList = this.reviewService.getAll_movie(reviewSearch);

//		현재 세션에서 user 정보를 가져옴
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);

		mv.addObject("user", userDto);
		mv.addObject("movieReviewList", movieReviewList);
		mv.addObject("reviewSearch", reviewSearch);
		mv.setViewName("board/movieBoard");
		return mv;
	}

//	게시판 게시글 작성
	@RequestMapping(value = "/movieBoardForm", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView movieBoardForm(ModelAndView mv, HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		mv.addObject("user", userDto);
		mv.setViewName("board/movieBoardForm");
		return mv;
	}
	
//	제목 자동완성 -> json parsing
	@RequestMapping(value = "/movieAPI", method = { RequestMethod.GET, RequestMethod.POST })
	public List<MovieParser> movieAPI(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) String keyword, RedirectAttributes rttr) {
		
		String clientId = Constants.API_CLIENTID;
        String clientSecret = Constants.API_CLIENTPW;
        StringBuffer response = new StringBuffer();
        List<MovieParser> movieList = new ArrayList<>();
        try {
            String text = URLEncoder.encode(keyword, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/movie?query="+ text; // json 결과
            //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
       
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            System.out.println(responseCode);
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());

            JSONObject jObject = new JSONObject(response.toString());
            JSONArray arr = (JSONArray)jObject.get("items");
            
//            JSON을 파싱해서 필요한 데이터만 가져오기
            for (int i = 0; i < arr.length(); i++) {
            	JSONObject item = (JSONObject)arr.get(i);
            	
            	String title = item.getString("title");
            	String image = item.getString("image");
            	String director = item.getString("director");
            	String actor = item.getString("actor");
            	String pubDate = item.getString("pubDate");
           
            	MovieParser movieParser = new MovieParser(title, image, director, actor, pubDate);
            	movieList.add(movieParser);
            }           
        } catch (Exception e) {
            System.out.println(e);
        }
        return movieList;
    }

//	게시글 저장
	@RequestMapping(value = "/movieBoard/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView movieBoardForm_save(ModelAndView mv, HttpServletRequest request,
			@ModelAttribute("movieDto") MovieReviewDto movieDto, RedirectAttributes rttr) {
//		로그인한 세션의 ID를 가지고 와서 글 작성자의 세션의 ID를 저장함
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();
		movieDto.setUserID(userID);
		System.out.println(movieDto.toString());
//		글이 저장되도록 db 쿼리 실행
		reviewService.insertMovieReview(movieDto);

		mv.setViewName(Constants.REVIEW_REDIRECT_MOVIE);
		return mv;
	}

//  게시글 상세보기
	@RequestMapping(value = "/movieBoardDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView detailMovieReview(ModelAndView mv, HttpServletRequest request,
			@RequestParam("movieReviewID") int movieReviewID) {
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		reviewService.movieReviewViews(movieReviewID);
     
//		네비게이션 바를 위해서 현재 세션의 사용자 정보를 보냄
		mv.addObject("user", userDto);
		mv.addObject("detailMovieReview", reviewService.getDetial_movie(movieReviewID));
		mv.setViewName("board/movieBoardDetail");
		return mv;
	}

//	게시글 수정
	@RequestMapping(value = "/movieReview-modify", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView movieReviewModify(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) int movieReviewID) {
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		mv.addObject("user", userDto);
//		수정 폼에서 이미 작성했던 글이 보여지도록 하게 하기 위해 상세보기 쿼리 이용
		mv.addObject("detailMovieReview", reviewService.getDetial_movie(movieReviewID));
		mv.setViewName("board/movieBoard_modify");
		return mv;
	}

//	게시글 수정 수행
	@RequestMapping(value = "/updateMovieReview-process", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView processUpdateMovieBoard(ModelAndView mv, HttpServletRequest request,
			@ModelAttribute("movieDto") MovieReviewDto movieDto) {
//		ModelAttribute를 이용해서 현재 폼에 작성한대로 db에 수정 저장
		logger.info(movieDto.getMovieReviewPreview());
		reviewService.updateMovieReview(movieDto);
		mv.setViewName(Constants.REVIEW_REDIRECT_MOVIE);
		return mv;
	}

//	게시글 삭제 과정
	@RequestMapping(value = "/deleteMovieReview-process", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView processDeleteMovieBoard(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) int movieReviewID) {
//		게시글 삭제 전 like 참조를 없애기 위해 like 테이블에서 해당 게시글에 해당하는 데이터 삭제
		reviewService.deleteMovieReviewLike_delete(movieReviewID);
		reviewService.deleteMovieReview(movieReviewID);
		mv.setViewName(Constants.REVIEW_REDIRECT_MOVIE);
		return mv;
	}

//	내가 쓴 게시물 확인
	@RequestMapping(value="/myMovieBoard", method = {RequestMethod.GET, RequestMethod.POST}) 
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
		  
//		  userID라는 조건을 제외하고는 일반 게시판과 동일함
		  HashMap<String, Object> map = new HashMap<String, Object>();
		  map.put("keyword", keyword);
		  map.put("userID", userID);
		  map.put("searchType", searchType);
		  
		  int totalCount = this.reviewService.myMoviePageCount(map);
		  
		  reviewSearch.pageInfo(pageNum, contentNum, totalCount); 
		  List<MovieReviewDto> myMovieReviewList = this.reviewService.getAll_myMovie(reviewSearch);
		  
		  mv.addObject("user", userDto); 
		  mv.addObject("myMovieReviewList", myMovieReviewList); 
		  mv.addObject("reviewSearch", reviewSearch);
		  mv.setViewName("mypage/myMovieBoard"); 
		  return mv;
	}
	
//	작성자  게시판 확인
	@RequestMapping(value="/userMovieBoard", method = {RequestMethod.GET, RequestMethod.POST}) 
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
		  
//		  userID라는 조건을 제외하고는 일반 게시판과 동일함
		  HashMap<String, Object> map = new HashMap<String, Object>();
		  map.put("keyword", keyword);
		  map.put("userID", writerID);
		  map.put("searchType", searchType);
		  
		  int totalCount = this.reviewService.privateMoviePageCount(map);
		  
		  reviewSearch.pageInfo(pageNum, contentNum, totalCount); 
		  List<MovieReviewDto> userMovieReviewList = this.reviewService.getAll_privateMovie(reviewSearch);

		  mv.addObject("user", userDto); 
		  mv.addObject("writerID", writerID);
		  mv.addObject("userMovieReviewList", userMovieReviewList); 
		  mv.addObject("reviewSearch", reviewSearch);
		  mv.setViewName("user/userMovieBoard"); 
		  return mv;
	}
	
//	게시글 추천 기능
	@RequestMapping(value="/movieReviewLike", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Integer> movieReviewLike(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) int movieReviewID) {
	
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();
		
		MovieReviewLikeDto movieLike = new MovieReviewLikeDto(movieReviewID, userID);
//		해당 게시글의 좋아요 개수
		int movieReviewLike = reviewService.getDetial_movie(movieReviewID).getMovieReviewLike();
//		이미 사용자가 게시글에 좋아요를 눌렀는지 누르지 않았는지 판별하기 위해 호출
		int count = reviewService.findMovieReviewLike(movieLike);
		
//		만약 이전에 좋아요를 누르지 않았을 때
//		게시글의 좋아요 개수가 증가하고, like 테이블에 좋아요를 누른 userID와 게시글의 ID가 삽입됨
		if (count == 0) {
			movieReviewLike += 1;
			reviewService.insertMovieReviewLike(movieLike);
			reviewService.plusMovieReviewLike(movieReviewID);
		}
		else {
			movieReviewLike -= 1;
			reviewService.deleteMovieReviewLike(movieLike);
			reviewService.minusMovieReviewLike(movieReviewID);
		}
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("count", count);
//		ajax를 위해 필요
		map.put("movieReviewLike", movieReviewLike);
		
		return map;
	}
	
//	게시글 상세보기를 할 때, 해당 게시글에 대한 추천수를 표시하기 위함
	@RequestMapping(value="/movieCheckHeart", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public int checkHeart(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) int movieReviewID) {
	
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();

//		게시글 상세보기를 했을 때 이전에 좋아요를 누른 기록이 있으면 하트가 이미 채워지고 그렇지 않으면 빈 하트로 남겨두기 위해서 필요
		MovieReviewLikeDto movieLike = new MovieReviewLikeDto(movieReviewID, userID);
		int count = reviewService.findMovieReviewLike(movieLike);
		
		return count;
	}
	
//	내가 추천한 게시글 확인
	@RequestMapping(value="/userLikesMovie", method = {RequestMethod.GET, RequestMethod.POST}) 
	public ModelAndView userLikesMovie(ModelAndView mv, HttpServletRequest request,
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
		  
		  int totalCount = this.reviewService.userLikesMovieCount(userID);
		  System.out.println(totalCount);
		 
		  reviewSearch.pageInfo(pageNum, contentNum, totalCount); 
		  List<MovieReviewDto> userLikesMovieReviewList = this.reviewService.getAll_LikeMovie(userID);
		  
		  mv.addObject("user", userDto); 
		  mv.addObject("userLikesMovieReviewList", userLikesMovieReviewList); 
		  mv.addObject("reviewSearch", reviewSearch);
		  mv.setViewName("like/likeMovieBoard"); 
		  return mv;
	}
}