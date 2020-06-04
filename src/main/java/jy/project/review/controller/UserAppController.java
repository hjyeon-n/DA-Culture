package jy.project.review.controller;

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
import jy.project.review.dto.UserDto;
import jy.project.review.service.UserService;

@RestController
public class UserAppController {
	private Logger logger = LoggerFactory.getLogger(BookReviewAppController.class);
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/join", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView join(ModelAndView mv) {
		UserDto userDto = new UserDto();
		mv.setViewName("join");
		mv.getModel().put("memberDto", userDto);
		return mv;
	}

	@RequestMapping(value = "/checkID", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public int checkID(ModelAndView mv, @RequestParam("userID") String userID) {
		return userService.findUser(userID);
	}

	@RequestMapping(value = "/join-process", method = RequestMethod.POST)
	public ModelAndView processJoin(@ModelAttribute("userDto") UserDto userDto, ModelAndView mv,
			HttpServletRequest request) {
		logger.info("process-join");
		logger.info(userDto.toString());
		userDto.sanitize();
		userService.insertUser(userDto);

		mv.setViewName(Constants.REVIEW_REDIRECT_ROOT);
		return mv;
	}

	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView login(ModelAndView mv, HttpServletRequest request) {
		String referer = request.getHeader("Referer");
		request.getSession().setAttribute("redirectURI", referer);
		mv.setViewName("login");
		return mv;
	}

	@RequestMapping(value = "/login-process", method = RequestMethod.POST)
	public ModelAndView processLogin(@ModelAttribute("userDto") UserDto userDto, ModelAndView mv,
			HttpServletRequest request) {
		logger.info("process-login");
		logger.info(userDto.toString());
		UserDto matchedUserDto = this.userService.getUser(userDto);
		if (matchedUserDto != null) {
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute(Constants.USER_SESSION_KEY, matchedUserDto);
			httpSession.setAttribute(Constants.USERID_SESSION_KEY, matchedUserDto.getUserID()) ;
//			로그인한 후 그 전에 실행했던 페이지로 돌아가기 위해 설정
			String previousURI = (String) request.getSession().getAttribute("redirectURI");
			previousURI = previousURI.replace("http://localhost:8085/", "");
			logger.info(previousURI);
			mv.setViewName("redirect:" + previousURI);
		} else {
			mv.setViewName("login");
			mv.addObject("loginfail", true);
			logger.info("fail");
		}
		return mv;
	}

	@RequestMapping(value = "/logout-process", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView processLogout(ModelAndView mv, HttpServletRequest request) {
		logger.info("sign-out");
		if (request.isRequestedSessionIdValid()) {
			HttpSession httpSession = request.getSession();
			httpSession.removeAttribute("user");
			httpSession.invalidate();
		}
		mv.setViewName(Constants.REVIEW_REDIRECT_ROOT);
		return mv;
	}

	@RequestMapping(value = "/myPage", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView myPage(ModelAndView mv, HttpServletRequest request) {
		mv.setViewName("myPage");
		return mv;
	}

//	패스워드 확인 후 회원정보 수정 가능
	@RequestMapping(value = "/myPage-modify", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView myPageModify(ModelAndView mv, HttpServletRequest request,
			@RequestParam("confirmPW") String confirmPW) {
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		String sessionPW = userDto.getPwd();
		if (confirmPW.equals(sessionPW)) {
			logger.info("OK");
			mv.addObject("user", userDto);
			mv.setViewName("myPage_modify");
		} else {
			mv.addObject("passError", true);
			mv.setViewName("myPage");
		}
		return mv;
	}

//	회원 탈퇴 기능은 아직 제대로 수행하지 않음. -> 추후 수정할 것
	@RequestMapping(value = "/delete-process", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView processDeleteUser(ModelAndView mv, HttpServletRequest request) {
		System.out.println("delete");
		if (request.isRequestedSessionIdValid()) {
			HttpSession httpSession = request.getSession();
			UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
			String userID = userDto.getUserID();
			userService.deleteUser(userID);
			httpSession.invalidate();
		}
		mv.setViewName(Constants.REVIEW_REDIRECT_ROOT);
		return mv;
	}

//	회원정보 수정
	@RequestMapping(value = "/updateUser-process", method = RequestMethod.POST)
	public ModelAndView processUpdateUser(@ModelAttribute("userDto") UserDto userDto, ModelAndView mv,
			HttpServletRequest request) {
		userService.updateUser(userDto);
		logger.info("update");
		HttpSession httpSession = request.getSession();
		httpSession.invalidate();
		mv.setViewName(Constants.REVIEW_REDIRECT_ROOT);
		return mv;
	}

}
