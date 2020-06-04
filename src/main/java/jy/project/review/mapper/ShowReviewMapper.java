package jy.project.review.mapper;

import java.util.HashMap;
import java.util.List;

import jy.project.review.dto.ShowReviewLikeDto;
import jy.project.review.dto.ShowReviewDto;
import jy.project.review.page.ReviewSearch;

public interface ShowReviewMapper {
	public int showPageCount(HashMap<String, Object> map);
	public List<ShowReviewDto> getAll_show(ReviewSearch reviewSearch);
	public void insertShowReview(ShowReviewDto showDto);
	public ShowReviewDto getDetial_show(int contentNO);
	public void updateShowReview(ShowReviewDto showDto);
	public void deleteShowReview(int showReviewID);
	public int privateShowPageCount(HashMap<String, Object> map);
	public List<ShowReviewDto> getAll_privateShow(ReviewSearch reviewSearch);
	public void insertShowReviewLike(ShowReviewLikeDto showLikeDto);
	public void deleteShowReviewLike(ShowReviewLikeDto showLikeDto);
	public void plusShowReviewLike(int showReviewID);
	public void minusShowReviewLike(int showReviewID);
	public int findShowReviewLike(ShowReviewLikeDto showLikeDto);
	public int userLikesShowCount(String showReviewID);
	public List<ShowReviewDto> getAll_LikeShow(String showReviewID); 
	public void plusReplyCnt(int showReviewID);
	public void minusReplyCnt(int showReviewID);
	public void showReviewViews(int showReviewID);
	public void deleteShowReviewLike_delete(int showReviewID);
	public String getShowReviewWriter(int showReviewID);
	public int myShowPageCount(HashMap<String, Object> map);
	public List<ShowReviewDto> getAll_myShow(ReviewSearch reviewSearch);
}
