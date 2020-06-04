package jy.project.review.mapper;

import java.util.HashMap;
import java.util.List;

import jy.project.review.dto.PlayReviewLikeDto;
import jy.project.review.dto.PlayReviewDto;
import jy.project.review.page.ReviewSearch;

public interface PlayReviewMapper {
	public int playPageCount(HashMap<String, Object> map);
	public List<PlayReviewDto> getAll_play(ReviewSearch reviewSearch);
	public void insertPlayReview(PlayReviewDto playDto);
	public PlayReviewDto getDetial_play(int contentNO);
	public void updatePlayReview(PlayReviewDto playDto);
	public void deletePlayReview(int playReviewID);
	public int privatePlayPageCount(HashMap<String, Object> map);
	public List<PlayReviewDto> getAll_privatePlay(ReviewSearch reviewSearch);
	public void insertPlayReviewLike(PlayReviewLikeDto playLikeDto);
	public void deletePlayReviewLike(PlayReviewLikeDto playLikeDto);
	public void plusPlayReviewLike(int playReviewID);
	public void minusPlayReviewLike(int playReviewID);
	public int findPlayReviewLike(PlayReviewLikeDto playLikeDto);
	public int userLikesPlayCount(String playReviewID);
	public List<PlayReviewDto> getAll_LikePlay(String playReviewID); 
	public void plusReplyCnt(int playReviewID);
	public void minusReplyCnt(int playReviewID);
	public void playReviewViews(int playReviewID);
	public void deletePlayReviewLike_delete(int playReviewID);
	public String getPlayReviewWriter(int playReviewID);
	public int myPlayPageCount(HashMap<String, Object> map);
	public List<PlayReviewDto> getAll_myPlay(ReviewSearch reviewSearch);
}
