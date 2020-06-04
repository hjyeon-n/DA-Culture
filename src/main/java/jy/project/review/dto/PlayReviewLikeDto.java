package jy.project.review.dto;

public class PlayReviewLikeDto {
	private int playReviewID;
	private String userID;
	
	public int getPlayReviewID() {
		return playReviewID;
	}
	public void setPlayReviewID(int playReviewID) {
		this.playReviewID = playReviewID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public PlayReviewLikeDto(int playReviewID, String userID) {
		super();
		this.playReviewID = playReviewID;
		this.userID = userID;
	}
	public PlayReviewLikeDto() {
		super();
	}
	@Override
	public String toString() {
		return "PlayLikeDto [playReviewID=" + playReviewID + ", userID=" + userID + "]";
	}
}
