package jy.project.review.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jy.project.review.dto.ShowReviewLikeDto;
import jy.project.review.dto.ShowReviewDto;
import jy.project.review.mapper.ShowReviewMapper;
import jy.project.review.page.ReviewSearch;

@Service
public class ShowReviewService {
	@Autowired
	private ShowReviewMapper reviewMapper;
	
	public int showPageCount(HashMap<String, Object> map) {
		return this.reviewMapper.showPageCount(map);
	}
	
	public List<ShowReviewDto> getAll_show(ReviewSearch reviewSearch) {
		return this.reviewMapper.getAll_show(reviewSearch);
	}
	
	public void insertShowReview(ShowReviewDto showDto) {
		this.reviewMapper.insertShowReview(showDto);
	}
	
	public ShowReviewDto getDetial_show(int contentNO) {
		return this.reviewMapper.getDetial_show(contentNO);
	}
	
	public void updateShowReview(ShowReviewDto showDto) {
		this.reviewMapper.updateShowReview(showDto);
	}
	
	public void deleteShowReview(int showReviewID) {
		this.reviewMapper.deleteShowReview(showReviewID);
	}
	
	public int privateShowPageCount(HashMap<String, Object> map) {
		return reviewMapper.privateShowPageCount(map);
	}
	public List<ShowReviewDto> getAll_privateShow(ReviewSearch reviewSearch) {
		return this.reviewMapper.getAll_privateShow(reviewSearch);
	}
	public void insertShowReviewLike(ShowReviewLikeDto showLikeDto) {
		this.reviewMapper.insertShowReviewLike(showLikeDto);
	}
	public void deleteShowReviewLike(ShowReviewLikeDto showLikeDto) {
		this.reviewMapper.deleteShowReviewLike(showLikeDto);
	}
	public void plusShowReviewLike(int showReviewID) {
		this.reviewMapper.plusShowReviewLike(showReviewID);
	}
	public void minusShowReviewLike(int showReviewID) {
		this.reviewMapper.minusShowReviewLike(showReviewID);
	}
	public int findShowReviewLike(ShowReviewLikeDto showLikeDto) {
		return this.reviewMapper.findShowReviewLike(showLikeDto);
	}
	public int userLikesShowCount(String showReviewID) {
		return this.reviewMapper.userLikesShowCount(showReviewID);
	}
	public List<ShowReviewDto> getAll_LikeShow(String showReviewID) {
		return this.reviewMapper.getAll_LikeShow(showReviewID);
	}
	public void plusReplyCnt(int showReviewID) {
		this.reviewMapper.plusReplyCnt(showReviewID);
	}
	public void minusReplyCnt(int showReviewID) {
		this.reviewMapper.minusReplyCnt(showReviewID);
	}
	public void showReviewViews(int showReviewID) {
		this.reviewMapper.showReviewViews(showReviewID);
	}
	public void deleteShowReviewLike_delete(int showReviewID) {
		this.reviewMapper.deleteShowReviewLike_delete(showReviewID);
	}
	public String getShowReviewWriter(int showReviewID) {
		return this.reviewMapper.getShowReviewWriter(showReviewID);
	}
	public int myShowPageCount(HashMap<String, Object> map) {
		return this.reviewMapper.myShowPageCount(map);
	}
	public List<ShowReviewDto> getAll_myShow(ReviewSearch reviewSearch){
		return this.reviewMapper.getAll_myShow(reviewSearch);
	}
}
