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
import jy.project.review.dto.BookReviewLikeDto;
import jy.project.review.dto.BookReviewDto;
import jy.project.review.dto.UserDto;
import jy.project.review.page.ReviewSearch;
import jy.project.review.parserDto.BookParser;
import jy.project.review.service.BookReviewService;

@RestController
public class BookReviewAppController {

	private Logger logger = LoggerFactory.getLogger(BookReviewAppController.class);

	@Autowired
	private BookReviewService reviewService;
	
//	게시판
	@RequestMapping(value = "/bookBoard", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView bookBoard(ModelAndView mv, HttpServletRequest request,
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

		int totalCount = this.reviewService.bookPageCount(map);

		reviewSearch.pageInfo(pageNum, contentNum, totalCount);
		List<BookReviewDto> bookReviewList = this.reviewService.getAll_book(reviewSearch);

		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);

		mv.addObject("user", userDto);
		mv.addObject("bookReviewList", bookReviewList);
		mv.addObject("reviewSearch", reviewSearch);
		mv.setViewName("board/bookBoard");
		return mv;
	}

//	게시판 게시글 작성
	@RequestMapping(value = "/bookBoardForm", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView bookBoardForm(ModelAndView mv, HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		mv.addObject("user", userDto);
		mv.setViewName("board/bookBoardForm");
		return mv;
	}
	
//	제목 자동완성 -> json parsing
	@RequestMapping(value = "/bookAPI", method = { RequestMethod.GET, RequestMethod.POST })
	public List<BookParser> bookAPI(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) String keyword, RedirectAttributes rttr) {
		
		String clientId = Constants.API_CLIENTID;
        String clientSecret = Constants.API_CLIENTPW;
        StringBuffer response = new StringBuffer();
        List<BookParser> bookList = new ArrayList<>();
        try {
            String text = URLEncoder.encode(keyword, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/book?query="+ text; // json 결과
            //String apiURL = "https://openapi.naver.com/v1/search/book.xml?query="+ text; // xml 결과
            //String apiURL =https://openapi.naver.com/v1/search/book_adv.xml // 상세 정보 xml
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
            
            for (int i = 0; i < arr.length(); i++) {
            	JSONObject item = (JSONObject)arr.get(i);
            	
            	String title = item.getString("title");
            	String image = item.getString("image");
            	String author = item.getString("author");
            	String publisher = item.getString("publisher");
           
            	BookParser bookParser = new BookParser(title, image, author, publisher);
            	bookList.add(bookParser);
            }           
        } catch (Exception e) {
            System.out.println(e);
        }
        return bookList;
    }

//	게시글 저장
	@RequestMapping(value = "/bookBoard/save", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView bookBoardForm_save(ModelAndView mv, HttpServletRequest request,
			@ModelAttribute("bookDto") BookReviewDto bookDto, RedirectAttributes rttr) {
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();
		bookDto.setUserID(userID);
		System.out.println(bookDto.toString());
		reviewService.insertBookReview(bookDto);

		mv.setViewName(Constants.REVIEW_REDIRECT_MOVIE);
		return mv;
	}

//  게시글 상세보기
	@RequestMapping(value = "/bookBoardDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView detailBookReview(ModelAndView mv, HttpServletRequest request,
			@RequestParam("bookReviewID") int bookReviewID) {
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		reviewService.bookReviewViews(bookReviewID);
     
		mv.addObject("user", userDto);
		mv.addObject("detailBookReview", reviewService.getDetial_book(bookReviewID));
		mv.setViewName("board/bookBoardDetail");
		return mv;
	}

//	게시글 수정
	@RequestMapping(value = "/bookReview-modify", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView bookReviewModify(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) int bookReviewID) {
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		mv.addObject("user", userDto);
		mv.addObject("detailBookReview", reviewService.getDetial_book(bookReviewID));
		mv.setViewName("board/bookBoard_modify");
		return mv;
	}

//	게시글 수정 수행
	@RequestMapping(value = "/updateBookReview-process", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView processUpdateBookBoard(ModelAndView mv, HttpServletRequest request,
			@ModelAttribute("bookDto") BookReviewDto bookDto) {
		logger.info(bookDto.getBookReviewPreview());
		reviewService.updateBookReview(bookDto);
		mv.setViewName(Constants.REVIEW_REDIRECT_MOVIE);
		return mv;
	}

//	게시글 삭제 과정
	@RequestMapping(value = "/deleteBookReview-process", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView processDeleteBookBoard(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) int bookReviewID) {
		reviewService.deleteBookReviewLike_delete(bookReviewID);
		reviewService.deleteBookReview(bookReviewID);
		mv.setViewName(Constants.REVIEW_REDIRECT_MOVIE);
		return mv;
	}

//	내가 쓴 게시물 확인
	@RequestMapping(value="/myBookBoard", method = {RequestMethod.GET, RequestMethod.POST}) 
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
		  
		  int totalCount = this.reviewService.myBookPageCount(map);
		  
		  reviewSearch.pageInfo(pageNum, contentNum, totalCount); 
		  List<BookReviewDto> myBookReviewList = this.reviewService.getAll_myBook(reviewSearch);
		  
		  mv.addObject("user", userDto); 
		  mv.addObject("myBookReviewList", myBookReviewList); 
		  mv.addObject("reviewSearch", reviewSearch);
		  mv.setViewName("mypage/myBookBoard"); 
		  return mv;
	}
	
//	작성자  게시판 확인
	@RequestMapping(value="/userBookBoard", method = {RequestMethod.GET, RequestMethod.POST}) 
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
		  
		  int totalCount = this.reviewService.privateBookPageCount(map);
		  
		  reviewSearch.pageInfo(pageNum, contentNum, totalCount); 
		  List<BookReviewDto> userBookReviewList = this.reviewService.getAll_privateBook(reviewSearch);

		  mv.addObject("user", userDto); 
		  mv.addObject("writerID", writerID);
		  mv.addObject("userBookReviewList", userBookReviewList); 
		  mv.addObject("reviewSearch", reviewSearch);
		  mv.setViewName("user/userBookBoard"); 
		  return mv;
	}
	
//	게시글 추천 기능
	@RequestMapping(value="/bookReviewLike", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Integer> bookReviewLike(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) int bookReviewID) {
	
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();
		
		BookReviewLikeDto bookLike = new BookReviewLikeDto(bookReviewID, userID);
		int bookReviewLike = reviewService.getDetial_book(bookReviewID).getBookReviewLike();
		int count = reviewService.findBookReviewLike(bookLike);
		
		if (count == 0) {
			bookReviewLike += 1;
			reviewService.insertBookReviewLike(bookLike);
			reviewService.plusBookReviewLike(bookReviewID);
		}
		else {
			bookReviewLike -= 1;
			reviewService.deleteBookReviewLike(bookLike);
			reviewService.minusBookReviewLike(bookReviewID);
		}
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("count", count);
		map.put("bookReviewLike", bookReviewLike);
		
		return map;
	}
	
//	게시글 상세보기를 할 때, 해당 게시글에 대한 추천수를 표시하기 위함
	@RequestMapping(value="/bookCheckHeart", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public int checkHeart(ModelAndView mv, HttpServletRequest request,
			@RequestParam(required = false) int bookReviewID) {
	
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String userID = userDto.getUserID();

		BookReviewLikeDto bookLike = new BookReviewLikeDto(bookReviewID, userID);
		int count = reviewService.findBookReviewLike(bookLike);
		
		return count;
	}
	
//	내가 추천한 게시글 확인
	@RequestMapping(value="/userLikesBook", method = {RequestMethod.GET, RequestMethod.POST}) 
	public ModelAndView userLikesBook(ModelAndView mv, HttpServletRequest request,
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
		  
		  int totalCount = this.reviewService.userLikesBookCount(userID);
		  System.out.println(totalCount);
		 
		  reviewSearch.pageInfo(pageNum, contentNum, totalCount); 
		  List<BookReviewDto> userLikesBookReviewList = this.reviewService.getAll_LikeBook(userID);
		  
		  for(int i = 0; i < userLikesBookReviewList.size(); i++) {
			  System.out.println(userLikesBookReviewList.get(i).getBookTitle());
		  }
		  
		  mv.addObject("user", userDto); 
		  mv.addObject("userLikesBookReviewList", userLikesBookReviewList); 
		  mv.addObject("reviewSearch", reviewSearch);
		  mv.setViewName("like/likeBookBoard"); 
		  return mv;
	}
}