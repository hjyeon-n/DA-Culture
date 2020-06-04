package jy.project.review.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class PlayReviewDto {
	private int playReviewID;
	private String userID;
	private String playReviewTitle;
	private String playReviewContents;
	private String playReviewPreview;
	private String playImage;
	private String playTitle;
	private double playScore;
	private String favoriteWord;
	private Timestamp playBoardDate;
	private int playReviewLike;
	private boolean playSecretReview;
	private int playReplyCnt;
	private int playReviewViews;
	
	public PlayReviewDto() {
		super();
	}

	public PlayReviewDto(int playReviewID, String userID, String playReviewTitle, String playReviewContents,
			String playReviewPreview, String playImage, String playTitle, double playScore, String favoriteWord,
			Timestamp playBoardDate, int playReviewLike, boolean playSecretReview, int playReplyCnt, int playReviewViews) {
		super();
		this.playReviewID = playReviewID;
		this.userID = userID;
		this.playReviewTitle = playReviewTitle;
		this.playReviewContents = playReviewContents;
		this.playReviewPreview = playReviewPreview;
		this.playImage = playImage;
		this.playTitle = playTitle;
		this.playScore = playScore;
		this.favoriteWord = favoriteWord;
		this.playBoardDate = playBoardDate;
		this.playReviewLike = playReviewLike;
		this.playSecretReview = playSecretReview;
		this.playReplyCnt = playReplyCnt;
		this.playReviewViews = playReviewViews;
	}

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

	public String getPlayReviewTitle() {
		return playReviewTitle;
	}

	public void setPlayReviewTitle(String playReviewTitle) {
		this.playReviewTitle = playReviewTitle;
	}

	public String getPlayReviewContents() {
		return playReviewContents;
	}

	public void setPlayReviewContents(String playReviewContents) {
		this.playReviewContents = playReviewContents;
	}

	public String getPlayReviewPreview() {
		return playReviewPreview;
	}

	public void setPlayReviewPreview(String playReviewPreview) {
		this.playReviewPreview = playReviewPreview;
	}

	public String getPlayImage() {
		return playImage;
	}

	public void setPlayImage(String playImage) {
		this.playImage = playImage;
	}

	public String getPlayTitle() {
		return playTitle;
	}

	public void setPlayTitle(String playTitle) {
		this.playTitle = playTitle;
	}

	public double getPlayScore() {
		return playScore;
	}

	public void setPlayScore(double playScore) {
		this.playScore = playScore;
	}

	public String getFavoriteWord() {
		return favoriteWord;
	}

	public void setFavoriteWord(String favoriteWord) {
		this.favoriteWord = favoriteWord;
	}

	public String getPlayBoardDate() {
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
		String time = format1.format(playBoardDate);
		return time;
	}

	public void setPlayBoardDate(Timestamp playBoardDate) {		
		this.playBoardDate = playBoardDate;
	}

	public int getPlayReviewLike() {
		return playReviewLike;
	}

	public void setPlayReviewLike(int playReviewLike) {
		this.playReviewLike = playReviewLike;
	}
	
	public boolean isPlaySecretReview() {
		return playSecretReview;
	}
	
	public void setPlaySecretReview(boolean playSecretReview) {
		this.playSecretReview = playSecretReview;
	}
	
	public int getPlayReplyCnt() {
		return playReplyCnt;
	}

	public void setPlayReplyCnt(int playReplyCnt) {
		this.playReplyCnt = playReplyCnt;
	}

	public int getPlayReviewViews() {
		return playReviewViews;
	}

	public void setPlayReviewViews(int playReviewViews) {
		this.playReviewViews = playReviewViews;
	}

	@Override
	public String toString() {
		return "PlayReviewDto [playReviewID=" + playReviewID + ", userID=" + userID + ", playReviewTitle=" + playReviewTitle
				+ ", playReviewContents=" + playReviewContents + ", playReviewPreview=" + playReviewPreview
				+ ", playImage=" + playImage + ", playTitle=" + playTitle + ", playScore=" + playScore
				+ ", favoriteWord=" + favoriteWord + ", playBoardDate=" + playBoardDate + ", playReviewLike="
				+ playReviewLike + ", playSecretReview=" + playSecretReview + playReplyCnt + playReviewViews +"]";
	}
}
