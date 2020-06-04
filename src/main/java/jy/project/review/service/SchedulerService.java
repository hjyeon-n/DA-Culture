package jy.project.review.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jy.project.review.dto.BookReviewDto;
import jy.project.review.dto.MovieReviewDto;
import jy.project.review.dto.PlayReviewDto;
import jy.project.review.dto.ShowReviewDto;
import jy.project.review.mapper.SchedulerMapper;
@Service
public class SchedulerService {
	@Autowired
	private SchedulerMapper schedulerMapper;
	
	public List<MovieReviewDto> get_bestMovieReview(String date) {
		return this.schedulerMapper.get_bestMovieReview(date);
	}
	
	public List<BookReviewDto> get_bestBookReview(String date) {
		return this.schedulerMapper.get_bestBookReview(date);
	}
	
	public List<ShowReviewDto> get_bestShowReview(String date) {
		return this.schedulerMapper.get_bestShowReview(date);
	}
	
	public List<PlayReviewDto> get_bestPlayReview(String date){
		return this.schedulerMapper.get_bestPlayReview(date);
	}
}
