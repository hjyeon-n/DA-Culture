package jy.project.review.dto;

public class PlayReplyLikeDto {
	private int playReplyID;
	private String userID;
	
	public int getPlayReplyID() {
		return playReplyID;
	}
	public void setPlayReplyID(int playReplyID) {
		this.playReplyID = playReplyID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public PlayReplyLikeDto(int playReplyID, String userID) {
		super();
		this.playReplyID = playReplyID;
		this.userID = userID;
	}
	public PlayReplyLikeDto() {
		super();
	}
	@Override
	public String toString() {
		return "PlayReplyLikeDto [playReplyID=" + playReplyID + ", userID=" + userID + "]";
	}
}
