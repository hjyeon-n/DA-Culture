package jy.project.review.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ShowReviewDto {
	private int showReviewID;
	private String userID;
	private String showReviewTitle;
	private String showReviewContents;
	private String showReviewPreview;
	private String showImage;
	private String showTitle;
	private double showScore;
	private String favoriteWord;
	private Timestamp showBoardDate;
	private int showReviewLike;
	private boolean showSecretReview;
	private int showReplyCnt;
	private int showReviewViews;
	
	public ShowReviewDto() {
		super();
	}

	public ShowReviewDto(int showReviewID, String userID, String showReviewTitle, String showReviewContents,
			String showReviewPreview, String showImage, String showTitle, double showScore, String favoriteWord,
			Timestamp showBoardDate, int showReviewLike, boolean showSecretReview, int showReplyCnt, int showReviewViews) {
		super();
		this.showReviewID = showReviewID;
		this.userID = userID;
		this.showReviewTitle = showReviewTitle;
		this.showReviewContents = showReviewContents;
		this.showReviewPreview = showReviewPreview;
		this.showImage = showImage;
		this.showTitle = showTitle;
		this.showScore = showScore;
		this.favoriteWord = favoriteWord;
		this.showBoardDate = showBoardDate;
		this.showReviewLike = showReviewLike;
		this.showSecretReview = showSecretReview;
		this.showReplyCnt = showReplyCnt;
		this.showReviewViews = showReviewViews;
	}

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

	public String getShowReviewTitle() {
		return showReviewTitle;
	}

	public void setShowReviewTitle(String showReviewTitle) {
		this.showReviewTitle = showReviewTitle;
	}

	public String getShowReviewContents() {
		return showReviewContents;
	}

	public void setShowReviewContents(String showReviewContents) {
		this.showReviewContents = showReviewContents;
	}

	public String getShowReviewPreview() {
		return showReviewPreview;
	}

	public void setShowReviewPreview(String showReviewPreview) {
		this.showReviewPreview = showReviewPreview;
	}

	public String getShowImage() {
		return showImage;
	}

	public void setShowImage(String showImage) {
		this.showImage = showImage;
	}

	public String getShowTitle() {
		return showTitle;
	}

	public void setShowTitle(String showTitle) {
		this.showTitle = showTitle;
	}

	public double getShowScore() {
		return showScore;
	}

	public void setShowScore(double showScore) {
		this.showScore = showScore;
	}

	public String getFavoriteWord() {
		return favoriteWord;
	}

	public void setFavoriteWord(String favoriteWord) {
		this.favoriteWord = favoriteWord;
	}

	public String getShowBoardDate() {
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
		String time = format1.format(showBoardDate);
		return time;
	}

	public void setShowBoardDate(Timestamp showBoardDate) {		
		this.showBoardDate = showBoardDate;
	}

	public int getShowReviewLike() {
		return showReviewLike;
	}

	public void setShowReviewLike(int showReviewLike) {
		this.showReviewLike = showReviewLike;
	}
	
	public boolean isShowSecretReview() {
		return showSecretReview;
	}
	
	public void setShowSecretReview(boolean showSecretReview) {
		this.showSecretReview = showSecretReview;
	}
	
	public int getShowReplyCnt() {
		return showReplyCnt;
	}

	public void setShowReplyCnt(int showReplyCnt) {
		this.showReplyCnt = showReplyCnt;
	}

	public int getShowReviewViews() {
		return showReviewViews;
	}

	public void setShowReviewViews(int showReviewViews) {
		this.showReviewViews = showReviewViews;
	}

	@Override
	public String toString() {
		return "ShowReviewDto [showReviewID=" + showReviewID + ", userID=" + userID + ", showReviewTitle=" + showReviewTitle
				+ ", showReviewContents=" + showReviewContents + ", showReviewPreview=" + showReviewPreview
				+ ", showImage=" + showImage + ", showTitle=" + showTitle + ", showScore=" + showScore
				+ ", favoriteWord=" + favoriteWord + ", showBoardDate=" + showBoardDate + ", showReviewLike="
				+ showReviewLike + ", showSecretReview=" + showSecretReview + showReplyCnt + showReviewViews +"]";
	}
}
