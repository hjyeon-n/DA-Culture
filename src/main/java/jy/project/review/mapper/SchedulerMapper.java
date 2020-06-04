package jy.project.review.mapper;

import java.util.List;

import jy.project.review.dto.BookReviewDto;
import jy.project.review.dto.MovieReviewDto;
import jy.project.review.dto.PlayReviewDto;
import jy.project.review.dto.ShowReviewDto;

public interface SchedulerMapper {
	public List<MovieReviewDto> get_bestMovieReview(String date);
	public List<BookReviewDto> get_bestBookReview(String date);
	public List<ShowReviewDto> get_bestShowReview(String date);
	public List<PlayReviewDto> get_bestPlayReview(String date);
}
