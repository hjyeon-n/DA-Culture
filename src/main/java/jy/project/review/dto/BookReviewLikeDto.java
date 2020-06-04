package jy.project.review.dto;

public class BookReviewLikeDto {
	private int bookReviewID;
	private String userID;
	public int getBookReviewID() {
		return bookReviewID;
	}
	public void setBookReviewID(int bookReviewID) {
		this.bookReviewID = bookReviewID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public BookReviewLikeDto(int bookReviewID, String userID) {
		super();
		this.bookReviewID = bookReviewID;
		this.userID = userID;
	}
	public BookReviewLikeDto() {
		super();
	}
	@Override
	public String toString() {
		return "BookReviewLikeDto [bookReviewID=" + bookReviewID + ", userID=" + userID + "]";
	}
}
