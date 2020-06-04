package jy.project.review.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import jy.project.review.constants.Constants;
import jy.project.review.dto.BookReviewDto;
import jy.project.review.dto.MovieReviewDto;
import jy.project.review.dto.PlayReviewDto;
import jy.project.review.dto.ShowReviewDto;
import jy.project.review.dto.UserDto;
import jy.project.review.scheduler.Scheduler;
import jy.project.review.service.SchedulerService;

@RestController
public class SchedulerAppController {
	private Logger logger = LoggerFactory.getLogger(SchedulerAppController.class);

	@Autowired
	private SchedulerService schedulerService;
	
	@RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView main(ModelAndView mv, HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto) httpSession.getAttribute(Constants.USER_SESSION_KEY);
		
		Scheduler schedule = new Scheduler();
		String date = schedule.cronScheduler();
		
		List<MovieReviewDto> bestMovieReviewList = this.schedulerService.get_bestMovieReview(date);
		List<BookReviewDto> bestBookReviewList = this.schedulerService.get_bestBookReview(date);
		List<ShowReviewDto> bestShowReviewList = this.schedulerService.get_bestShowReview(date);
		List<PlayReviewDto> bestPlayReviewList = this.schedulerService.get_bestPlayReview(date);
		
		
		mv.addObject("bestMovieReviewList", bestMovieReviewList);
		mv.addObject("bestBookReviewList", bestBookReviewList);
		mv.addObject("bestShowReviewList", bestShowReviewList);
		mv.addObject("bestPlayReviewList", bestPlayReviewList);
		
		mv.addObject("user", userDto);
		mv.setViewName("main");
		return mv;
	}

}
