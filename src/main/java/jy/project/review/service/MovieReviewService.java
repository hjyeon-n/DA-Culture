package jy.project.review.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jy.project.review.dto.MovieReviewLikeDto;
import jy.project.review.dto.MovieReviewDto;
import jy.project.review.mapper.MovieReviewMapper;
import jy.project.review.page.ReviewSearch;

@Service
public class MovieReviewService {
	@Autowired
	private MovieReviewMapper reviewMapper;
	
	public int moviePageCount(HashMap<String, Object> map) {
		return this.reviewMapper.moviePageCount(map);
	}
	
	public List<MovieReviewDto> getAll_movie(ReviewSearch reviewSearch) {
		return this.reviewMapper.getAll_movie(reviewSearch);
	}
	
	public void insertMovieReview(MovieReviewDto movieDto) {
		this.reviewMapper.insertMovieReview(movieDto);
	}
	
	public MovieReviewDto getDetial_movie(int contentNO) {
		return this.reviewMapper.getDetial_movie(contentNO);
	}
	
	public void updateMovieReview(MovieReviewDto movieDto) {
		this.reviewMapper.updateMovieReview(movieDto);
	}
	
	public void deleteMovieReview(int movieReviewID) {
		this.reviewMapper.deleteMovieReview(movieReviewID);
	}
	
	public int privateMoviePageCount(HashMap<String, Object> map) {
		return reviewMapper.privateMoviePageCount(map);
	}
	public List<MovieReviewDto> getAll_privateMovie(ReviewSearch reviewSearch) {
		return this.reviewMapper.getAll_privateMovie(reviewSearch);
	}
	public void insertMovieReviewLike(MovieReviewLikeDto movieLikeDto) {
		this.reviewMapper.insertMovieReviewLike(movieLikeDto);
	}
	public void deleteMovieReviewLike(MovieReviewLikeDto movieLikeDto) {
		this.reviewMapper.deleteMovieReviewLike(movieLikeDto);
	}
	public void plusMovieReviewLike(int movieReviewID) {
		this.reviewMapper.plusMovieReviewLike(movieReviewID);
	}
	public void minusMovieReviewLike(int movieReviewID) {
		this.reviewMapper.minusMovieReviewLike(movieReviewID);
	}
	public int findMovieReviewLike(MovieReviewLikeDto movieLikeDto) {
		return this.reviewMapper.findMovieReviewLike(movieLikeDto);
	}
	public int userLikesMovieCount(String movieReviewID) {
		return this.reviewMapper.userLikesMovieCount(movieReviewID);
	}
	public List<MovieReviewDto> getAll_LikeMovie(String movieReviewID) {
		return this.reviewMapper.getAll_LikeMovie(movieReviewID);
	}
	public void plusReplyCnt(int movieReviewID) {
		this.reviewMapper.plusReplyCnt(movieReviewID);
	}
	public void minusReplyCnt(int movieReviewID) {
		this.reviewMapper.minusReplyCnt(movieReviewID);
	}
	public void movieReviewViews(int movieReviewID) {
		this.reviewMapper.movieReviewViews(movieReviewID);
	}
	public void deleteMovieReviewLike_delete(int movieReviewID) {
		this.reviewMapper.deleteMovieReviewLike_delete(movieReviewID);
	}
	public String getMovieReviewWriter(int movieReviewID) {
		return this.reviewMapper.getMovieReviewWriter(movieReviewID);
	}
	public int myMoviePageCount(HashMap<String, Object> map) {
		return this.reviewMapper.myMoviePageCount(map);
	}
	public List<MovieReviewDto> getAll_myMovie(ReviewSearch reviewSearch) {
		return this.reviewMapper.getAll_myMovie(reviewSearch);
	}
}
