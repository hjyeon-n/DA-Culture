package jy.project.review.dto;

public class ShowReplyLikeDto {
	private int showReplyID;
	private String userID;
	
	public int getShowReplyID() {
		return showReplyID;
	}
	public void setShowReplyID(int showReplyID) {
		this.showReplyID = showReplyID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public ShowReplyLikeDto(int showReplyID, String userID) {
		super();
		this.showReplyID = showReplyID;
		this.userID = userID;
	}
	public ShowReplyLikeDto() {
		super();
	}
	@Override
	public String toString() {
		return "ShowReplyLikeDto [showReplyID=" + showReplyID + ", userID=" + userID + "]";
	}
}
