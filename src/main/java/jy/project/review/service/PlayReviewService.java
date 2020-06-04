package jy.project.review.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jy.project.review.dto.PlayReviewLikeDto;
import jy.project.review.dto.PlayReviewDto;
import jy.project.review.mapper.PlayReviewMapper;
import jy.project.review.page.ReviewSearch;

@Service
public class PlayReviewService {
	@Autowired
	private PlayReviewMapper reviewMapper;
	
	public int playPageCount(HashMap<String, Object> map) {
		return this.reviewMapper.playPageCount(map);
	}
	
	public List<PlayReviewDto> getAll_play(ReviewSearch reviewSearch) {
		return this.reviewMapper.getAll_play(reviewSearch);
	}
	
	public void insertPlayReview(PlayReviewDto playDto) {
		this.reviewMapper.insertPlayReview(playDto);
	}
	
	public PlayReviewDto getDetial_play(int contentNO) {
		return this.reviewMapper.getDetial_play(contentNO);
	}
	
	public void updatePlayReview(PlayReviewDto playDto) {
		this.reviewMapper.updatePlayReview(playDto);
	}
	
	public void deletePlayReview(int playReviewID) {
		this.reviewMapper.deletePlayReview(playReviewID);
	}
	
	public int privatePlayPageCount(HashMap<String, Object> map) {
		return reviewMapper.privatePlayPageCount(map);
	}
	public List<PlayReviewDto> getAll_privatePlay(ReviewSearch reviewSearch) {
		return this.reviewMapper.getAll_privatePlay(reviewSearch);
	}
	public void insertPlayReviewLike(PlayReviewLikeDto playLikeDto) {
		this.reviewMapper.insertPlayReviewLike(playLikeDto);
	}
	public void deletePlayReviewLike(PlayReviewLikeDto playLikeDto) {
		this.reviewMapper.deletePlayReviewLike(playLikeDto);
	}
	public void plusPlayReviewLike(int playReviewID) {
		this.reviewMapper.plusPlayReviewLike(playReviewID);
	}
	public void minusPlayReviewLike(int playReviewID) {
		this.reviewMapper.minusPlayReviewLike(playReviewID);
	}
	public int findPlayReviewLike(PlayReviewLikeDto playLikeDto) {
		return this.reviewMapper.findPlayReviewLike(playLikeDto);
	}
	public int userLikesPlayCount(String playReviewID) {
		return this.reviewMapper.userLikesPlayCount(playReviewID);
	}
	public List<PlayReviewDto> getAll_LikePlay(String playReviewID) {
		return this.reviewMapper.getAll_LikePlay(playReviewID);
	}
	public void plusReplyCnt(int playReviewID) {
		this.reviewMapper.plusReplyCnt(playReviewID);
	}
	public void minusReplyCnt(int playReviewID) {
		this.reviewMapper.minusReplyCnt(playReviewID);
	}
	public void playReviewViews(int playReviewID) {
		this.reviewMapper.playReviewViews(playReviewID);
	}
	public void deletePlayReviewLike_delete(int playReviewID) {
		this.reviewMapper.deletePlayReviewLike_delete(playReviewID);
	}
	public String getPlayReviewWriter(int playReviewID) {
		return this.reviewMapper.getPlayReviewWriter(playReviewID);
	}
	public int myPlayPageCount(HashMap<String, Object> map) {
		return this.reviewMapper.myPlayPageCount(map);
	}
	public List<PlayReviewDto> getAll_myPlay(ReviewSearch reviewSearch){
		return this.reviewMapper.getAll_myPlay(reviewSearch);
	}
}
