package jy.project.review.dto;

public class BookReplyLikeDto {
	private int bookReplyID;
	private String userID;
	public int getBookReplyID() {
		return bookReplyID;
	}
	public void setBookReplyID(int bookReplyID) {
		this.bookReplyID = bookReplyID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public BookReplyLikeDto(int bookReplyID, String userID) {
		super();
		this.bookReplyID = bookReplyID;
		this.userID = userID;
	}
	public BookReplyLikeDto() {
		super();
	}
	@Override
	public String toString() {
		return "BookReplyLikeDto [bookReplyID=" + bookReplyID + ", userID=" + userID + "]";
	}
}
