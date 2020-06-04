package jy.project.review.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class PlayReplyDto {
	private int playReplyID;
	private int playReviewID;
	private String playReplyContents;
	private String userID;
	private Timestamp playReplyDate;
	private boolean playSecretReply;
	private int playReplyGID;
	private int playReplyOrder;
	private int playReplyParents;
	private int playReplyLike;
	private String playReviewWriter;
	private String playParentsWriter;
	
	public int getPlayReplyID() {
		return playReplyID;
	}
	public void setPlayReplyID(int playReplyID) {
		this.playReplyID = playReplyID;
	}
	public int getPlayReviewID() {
		return playReviewID;
	}
	public void setPlayReviewID(int playReviewID) {
		this.playReviewID = playReviewID;
	}
	public String getPlayReplyContents() {
		return playReplyContents;
	}
	public void setPlayReplyContents(String playReplyContents) {
		this.playReplyContents = playReplyContents;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPlayReplyDate() {
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
		String time = format1.format(playReplyDate);
		return time;
	}
	public void setPlayReplyDate(Timestamp playReplyDate) {
		this.playReplyDate = playReplyDate;
	}
	public boolean isPlaySecretReply() {
		return playSecretReply;
	}
	public void setPlaySecretReply(boolean playSecretReply) {
		this.playSecretReply = playSecretReply;
	}
	public int getPlayReplyGID() {
		return playReplyGID;
	}
	public void setPlayReplyGID(int playReplyGID) {
		this.playReplyGID = playReplyGID;
	}
	public int getPlayReplyOrder() {
		return playReplyOrder;
	}
	public void setPlayReplyOrder(int playReplyOrder) {
		this.playReplyOrder = playReplyOrder;
	}
	public int getPlayReplyParents() {
		return playReplyParents;
	}
	public void setPlayReplyParents(int playReplyParents) {
		this.playReplyParents = playReplyParents;
	}
	public int getPlayReplyLike() {
		return playReplyLike;
	}
	public void setPlayReplyLike(int playReplyLike) {
		this.playReplyLike = playReplyLike;
	}
	public String getPlayReviewWriter() {
		return playReviewWriter;
	}
	public void setPlayReviewWriter(String playReviewWriter) {
		this.playReviewWriter = playReviewWriter;
	}
	public String getPlayParentsWriter() {
		return playParentsWriter;
	}
	public void setPlayParentsWriter(String playParentsWriter) {
		this.playParentsWriter = playParentsWriter;
	}
	public PlayReplyDto(int playReplyID, int playReviewID, String playReplyContents, String userID,
			Timestamp playReplyDate, boolean playSecretReply, int playReplyGID, int playReplyOrder,
			int playReplyParents, int playReplyLike, String playReviewWriter, String playParentsWriter) {
		super();
		this.playReplyID = playReplyID;
		this.playReviewID = playReviewID;
		this.playReplyContents = playReplyContents;
		this.userID = userID;
		this.playReplyDate = playReplyDate;
		this.playSecretReply = playSecretReply;
		this.playReplyGID = playReplyGID;
		this.playReplyOrder = playReplyOrder;
		this.playReplyParents = playReplyParents;
		this.playReplyLike = playReplyLike;
		this.playReviewWriter = playReviewWriter;
		this.playParentsWriter = playParentsWriter;
	}
	public PlayReplyDto() {
		super();
	}
	@Override
	public String toString() {
		return "PlayReplyDto [playReplyID=" + playReplyID + ", playReviewID=" + playReviewID + ", playReplyContents="
				+ playReplyContents + ", userID=" + userID + ", playReplyDate=" + playReplyDate + ", playSecretReply="
				+ playSecretReply + ", playReplyGID=" + playReplyGID + ", playReplyOrder=" + playReplyOrder
				+ ", playReplyParents=" + playReplyParents + ", playReplyLike=" + playReplyLike + ", playReviewWriter="
				+ playReviewWriter + ", playParentsWriter=" + playParentsWriter + "]";
	}
}
