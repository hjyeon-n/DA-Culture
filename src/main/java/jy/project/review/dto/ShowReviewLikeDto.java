package jy.project.review.dto;

public class ShowReviewLikeDto {
	private int showReviewID;
	private String userID;
	
	public int getShowReviewID() {
		return showReviewID;
	}
	public void setShowReviewID(int showReviewID) {
		this.showReviewID = showReviewID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public ShowReviewLikeDto(int showReviewID, String userID) {
		super();
		this.showReviewID = showReviewID;
		this.userID = userID;
	}
	public ShowReviewLikeDto() {
		super();
	}
	@Override
	public String toString() {
		return "ShowLikeDto [showReviewID=" + showReviewID + ", userID=" + userID + "]";
	}
}
