package jy.project.review.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ShowReplyDto {
	private int showReplyID;
	private int showReviewID;
	private String showReplyContents;
	private String userID;
	private Timestamp showReplyDate;
	private boolean showSecretReply;
	private int showReplyGID;
	private int showReplyOrder;
	private int showReplyParents;
	private int showReplyLike;
	private String showReviewWriter;
	private String showParentsWriter;
	
	public int getShowReplyID() {
		return showReplyID;
	}
	public void setShowReplyID(int showReplyID) {
		this.showReplyID = showReplyID;
	}
	public int getShowReviewID() {
		return showReviewID;
	}
	public void setShowReviewID(int showReviewID) {
		this.showReviewID = showReviewID;
	}
	public String getShowReplyContents() {
		return showReplyContents;
	}
	public void setShowReplyContents(String showReplyContents) {
		this.showReplyContents = showReplyContents;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getShowReplyDate() {
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
		String time = format1.format(showReplyDate);
		return time;
	}
	public void setShowReplyDate(Timestamp showReplyDate) {
		this.showReplyDate = showReplyDate;
	}
	public boolean isShowSecretReply() {
		return showSecretReply;
	}
	public void setShowSecretReply(boolean showSecretReply) {
		this.showSecretReply = showSecretReply;
	}
	public int getShowReplyGID() {
		return showReplyGID;
	}
	public void setShowReplyGID(int showReplyGID) {
		this.showReplyGID = showReplyGID;
	}
	public int getShowReplyOrder() {
		return showReplyOrder;
	}
	public void setShowReplyOrder(int showReplyOrder) {
		this.showReplyOrder = showReplyOrder;
	}
	public int getShowReplyParents() {
		return showReplyParents;
	}
	public void setShowReplyParents(int showReplyParents) {
		this.showReplyParents = showReplyParents;
	}
	public int getShowReplyLike() {
		return showReplyLike;
	}
	public void setShowReplyLike(int showReplyLike) {
		this.showReplyLike = showReplyLike;
	}
	public String getShowReviewWriter() {
		return showReviewWriter;
	}
	public void setShowReviewWriter(String showReviewWriter) {
		this.showReviewWriter = showReviewWriter;
	}
	public String getShowParentsWriter() {
		return showParentsWriter;
	}
	public void setShowParentsWriter(String showParentsWriter) {
		this.showParentsWriter = showParentsWriter;
	}
	public ShowReplyDto(int showReplyID, int showReviewID, String showReplyContents, String userID,
			Timestamp showReplyDate, boolean showSecretReply, int showReplyGID, int showReplyOrder,
			int showReplyParents, int showReplyLike, String showReviewWriter, String showParentsWriter) {
		super();
		this.showReplyID = showReplyID;
		this.showReviewID = showReviewID;
		this.showReplyContents = showReplyContents;
		this.userID = userID;
		this.showReplyDate = showReplyDate;
		this.showSecretReply = showSecretReply;
		this.showReplyGID = showReplyGID;
		this.showReplyOrder = showReplyOrder;
		this.showReplyParents = showReplyParents;
		this.showReplyLike = showReplyLike;
		this.showReviewWriter = showReviewWriter;
		this.showParentsWriter = showParentsWriter;
	}
	public ShowReplyDto() {
		super();
	}
	@Override
	public String toString() {
		return "ShowReplyDto [showReplyID=" + showReplyID + ", showReviewID=" + showReviewID + ", showReplyContents="
				+ showReplyContents + ", userID=" + userID + ", showReplyDate=" + showReplyDate + ", showSecretReply="
				+ showSecretReply + ", showReplyGID=" + showReplyGID + ", showReplyOrder=" + showReplyOrder
				+ ", showReplyParents=" + showReplyParents + ", showReplyLike=" + showReplyLike + ", showReviewWriter="
				+ showReviewWriter + ", showParentsWriter=" + showParentsWriter + "]";
	}
}
