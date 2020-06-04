package jy.project.review.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class BookReviewDto {
	private int bookReviewID;
	private String userID;
	private String bookReviewTitle;
	private String bookReviewContents;
	private String bookReviewPreview;
	private String bookImage;
	private String bookTitle;
	private double bookScore;
	private String favoriteWord;
	private Timestamp bookBoardDate;
	private int bookReviewLike;
	private boolean bookSecretReview;
	private int bookReplyCnt;
	private int bookReviewViews;
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
	public String getBookReviewTitle() {
		return bookReviewTitle;
	}
	public void setBookReviewTitle(String bookReviewTitle) {
		this.bookReviewTitle = bookReviewTitle;
	}
	public String getBookReviewContents() {
		return bookReviewContents;
	}
	public void setBookReviewContents(String bookReviewContents) {
		this.bookReviewContents = bookReviewContents;
	}
	public String getBookReviewPreview() {
		return bookReviewPreview;
	}
	public void setBookReviewPreview(String bookReviewPreview) {
		this.bookReviewPreview = bookReviewPreview;
	}
	public String getBookImage() {
		return bookImage;
	}
	public void setBookImage(String bookImage) {
		this.bookImage = bookImage;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public double getBookScore() {
		return bookScore;
	}
	public void setBookScore(double bookScore) {
		this.bookScore = bookScore;
	}
	public String getFavoriteWord() {
		return favoriteWord;
	}
	public void setFavoriteWord(String favoriteWord) {
		this.favoriteWord = favoriteWord;
	}
	public String getBookBoardDate() {
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
		String time = format1.format(bookBoardDate);
		return time;
	}
	public void setBookBoardDate(Timestamp bookBoardDate) {
		this.bookBoardDate = bookBoardDate;
	}
	public int getBookReviewLike() {
		return bookReviewLike;
	}
	public void setBookReviewLike(int bookReviewLike) {
		this.bookReviewLike = bookReviewLike;
	}
	public boolean isBookSecretReview() {
		return bookSecretReview;
	}
	public void setBookSecretReview(boolean bookSecretReview) {
		this.bookSecretReview = bookSecretReview;
	}
	public int getBookReplyCnt() {
		return bookReplyCnt;
	}
	public void setBookReplyCnt(int bookReplyCnt) {
		this.bookReplyCnt = bookReplyCnt;
	}
	public int getBookReviewViews() {
		return bookReviewViews;
	}
	public void setBookReviewViews(int bookReviewViews) {
		this.bookReviewViews = bookReviewViews;
	}
	public BookReviewDto(int bookReviewID, String userID, String bookReviewTitle, String bookReviewContents,
			String bookReviewPreview, String bookImage, String bookTitle, double bookScore, String favoriteWord,
			Timestamp bookBoardDate, int bookReviewLike, boolean bookSecretReview, int bookReplyCnt,
			int bookReviewViews) {
		super();
		this.bookReviewID = bookReviewID;
		this.userID = userID;
		this.bookReviewTitle = bookReviewTitle;
		this.bookReviewContents = bookReviewContents;
		this.bookReviewPreview = bookReviewPreview;
		this.bookImage = bookImage;
		this.bookTitle = bookTitle;
		this.bookScore = bookScore;
		this.favoriteWord = favoriteWord;
		this.bookBoardDate = bookBoardDate;
		this.bookReviewLike = bookReviewLike;
		this.bookSecretReview = bookSecretReview;
		this.bookReplyCnt = bookReplyCnt;
		this.bookReviewViews = bookReviewViews;
	}
	public BookReviewDto() {
		super();
	}
	@Override
	public String toString() {
		return "BookReviewDto [bookReviewID=" + bookReviewID + ", userID=" + userID + ", bookReviewTitle="
				+ bookReviewTitle + ", bookReviewContents=" + bookReviewContents + ", bookReviewPreview="
				+ bookReviewPreview + ", bookImage=" + bookImage + ", bookTitle=" + bookTitle + ", bookScore="
				+ bookScore + ", favoriteWord=" + favoriteWord + ", bookBoardDate=" + bookBoardDate
				+ ", bookReviewLike=" + bookReviewLike + ", bookSecretReview=" + bookSecretReview + ", bookReplyCnt="
				+ bookReplyCnt + ", bookReviewViews=" + bookReviewViews + "]";
	}
}
