package jy.project.review.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class BookReplyDto {
	private int bookReplyID;
	private int bookReviewID;
	private String bookReplyContents;
	private String userID;
	private Timestamp bookReplyDate;
	private boolean bookSecretReply;
	private int bookReplyGID;
	private int bookReplyOrder;
	private int bookReplyParents;
	private int bookReplyLike;
	private String bookReviewWriter;
	private String bookParentsWriter;
	
	public int getBookReplyID() {
		return bookReplyID;
	}
	public void setBookReplyID(int bookReplyID) {
		this.bookReplyID = bookReplyID;
	}
	public int getBookReviewID() {
		return bookReviewID;
	}
	public void setBookReviewID(int bookReviewID) {
		this.bookReviewID = bookReviewID;
	}
	public String getBookReplyContents() {
		return bookReplyContents;
	}
	public void setBookReplyContents(String bookReplyContents) {
		this.bookReplyContents = bookReplyContents;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getBookReplyDate() {
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
		String time = format1.format(bookReplyDate);
		return time;
	}
	public void setBookReplyDate(Timestamp bookReplyDate) {
		this.bookReplyDate = bookReplyDate;
	}
	public boolean isBookSecretReply() {
		return bookSecretReply;
	}
	public void setBookSecretReply(boolean bookSecretReply) {
		this.bookSecretReply = bookSecretReply;
	}
	public int getBookReplyGID() {
		return bookReplyGID;
	}
	public void setBookReplyGID(int bookReplyGID) {
		this.bookReplyGID = bookReplyGID;
	}
	public int getBookReplyOrder() {
		return bookReplyOrder;
	}
	public void setBookReplyOrder(int bookReplyOrder) {
		this.bookReplyOrder = bookReplyOrder;
	}
	public int getBookReplyParents() {
		return bookReplyParents;
	}
	public void setBookReplyParents(int bookReplyParents) {
		this.bookReplyParents = bookReplyParents;
	}
	public int getBookReplyLike() {
		return bookReplyLike;
	}
	public void setBookReplyLike(int bookReplyLike) {
		this.bookReplyLike = bookReplyLike;
	}
	public String getBookReviewWriter() {
		return bookReviewWriter;
	}
	public void setBookReviewWriter(String bookReviewWriter) {
		this.bookReviewWriter = bookReviewWriter;
	}
	public String getBookParentsWriter() {
		return bookParentsWriter;
	}
	public void setBookParentsWriter(String bookParentsWriter) {
		this.bookParentsWriter = bookParentsWriter;
	}
	public BookReplyDto(int bookReplyID, int bookReviewID, String bookReplyContents, String userID,
			Timestamp bookReplyDate, boolean bookSecretReply, int bookReplyGID, int bookReplyOrder,
			int bookReplyParents, int bookReplyLike, String bookReviewWriter, String bookParentsWriter) {
		super();
		this.bookReplyID = bookReplyID;
		this.bookReviewID = bookReviewID;
		this.bookReplyContents = bookReplyContents;
		this.userID = userID;
		this.bookReplyDate = bookReplyDate;
		this.bookSecretReply = bookSecretReply;
		this.bookReplyGID = bookReplyGID;
		this.bookReplyOrder = bookReplyOrder;
		this.bookReplyParents = bookReplyParents;
		this.bookReplyLike = bookReplyLike;
		this.bookReviewWriter = bookReviewWriter;
		this.bookParentsWriter = bookParentsWriter;
	}
	public BookReplyDto() {
		super();
	}
	@Override
	public String toString() {
		return "BookReplyDto [bookReplyID=" + bookReplyID + ", bookReviewID=" + bookReviewID + ", bookReplyContents="
				+ bookReplyContents + ", userID=" + userID + ", bookReplyDate=" + bookReplyDate + ", bookSecretReply="
				+ bookSecretReply + ", bookReplyGID=" + bookReplyGID + ", bookReplyOrder=" + bookReplyOrder
				+ ", bookReplyParents=" + bookReplyParents + ", bookReplyLike=" + bookReplyLike + ", bookReviewWriter="
				+ bookReviewWriter + ", bookParentsWriter=" + bookParentsWriter + "]";
	}
}
