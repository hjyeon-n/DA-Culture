package jy.project.review.mapper;

import java.util.HashMap;
import java.util.List;

import jy.project.review.dto.MovieReviewLikeDto;
import jy.project.review.dto.MovieReviewDto;
import jy.project.review.page.ReviewSearch;

public interface MovieReviewMapper {
	public int moviePageCount(HashMap<String, Object> map);
	public List<MovieReviewDto> getAll_movie(ReviewSearch reviewSearch);
	public void insertMovieReview(MovieReviewDto movieDto);
	public MovieReviewDto getDetial_movie(int contentNO);
	public void updateMovieReview(MovieReviewDto movieDto);
	public void deleteMovieReview(int movieReviewID);
	public int privateMoviePageCount(HashMap<String, Object> map);
	public List<MovieReviewDto> getAll_privateMovie(ReviewSearch reviewSearch);
	public void insertMovieReviewLike(MovieReviewLikeDto movieLikeDto);
	public void deleteMovieReviewLike(MovieReviewLikeDto movieLikeDto);
	public void plusMovieReviewLike(int movieReviewID);
	public void minusMovieReviewLike(int movieReviewID);
	public int findMovieReviewLike(MovieReviewLikeDto movieLikeDto);
	public int userLikesMovieCount(String movieReviewID);
	public List<MovieReviewDto> getAll_LikeMovie(String movieReviewID); 
	public void plusReplyCnt(int movieReviewID);
	public void minusReplyCnt(int movieReviewID);
	public void movieReviewViews(int movieReviewID);
	public void deleteMovieReviewLike_delete(int movieReviewID);
	public String getMovieReviewWriter(int movieReviewID);
	public int myMoviePageCount(HashMap<String, Object> map);
	public List<MovieReviewDto> getAll_myMovie(ReviewSearch reviewSearch);
}
