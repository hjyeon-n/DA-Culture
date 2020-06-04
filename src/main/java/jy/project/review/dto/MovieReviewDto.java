package jy.project.review.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class MovieReviewDto {
	private int movieReviewID;
	private String userID;
	private String movieReviewTitle;
	private String movieReviewContents;
	private String movieReviewPreview;
	private String movieImage;
	private String movieTitle;
	private double movieScore;
	private String favoriteWord;
	private Timestamp movieBoardDate;
	private int movieReviewLike;
	private boolean movieSecretReview;
	private int movieReplyCnt;
	private int movieReviewViews;
	
	public MovieReviewDto() {
		super();
	}

	public MovieReviewDto(int movieReviewID, String userID, String movieReviewTitle, String movieReviewContents,
			String movieReviewPreview, String movieImage, String movieTitle, double movieScore, String favoriteWord,
			Timestamp movieBoardDate, int movieReviewLike, boolean movieSecretReview, int movieReplyCnt, int movieReviewViews) {
		super();
		this.movieReviewID = movieReviewID;
		this.userID = userID;
		this.movieReviewTitle = movieReviewTitle;
		this.movieReviewContents = movieReviewContents;
		this.movieReviewPreview = movieReviewPreview;
		this.movieImage = movieImage;
		this.movieTitle = movieTitle;
		this.movieScore = movieScore;
		this.favoriteWord = favoriteWord;
		this.movieBoardDate = movieBoardDate;
		this.movieReviewLike = movieReviewLike;
		this.movieSecretReview = movieSecretReview;
		this.movieReplyCnt = movieReplyCnt;
		this.movieReviewViews = movieReviewViews;
	}

	public int getMovieReviewID() {
		return movieReviewID;
	}

	public void setMovieReviewID(int movieReviewID) {
		this.movieReviewID = movieReviewID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getMovieReviewTitle() {
		return movieReviewTitle;
	}

	public void setMovieReviewTitle(String movieReviewTitle) {
		this.movieReviewTitle = movieReviewTitle;
	}

	public String getMovieReviewContents() {
		return movieReviewContents;
	}

	public void setMovieReviewContents(String movieReviewContents) {
		this.movieReviewContents = movieReviewContents;
	}

	public String getMovieReviewPreview() {
		return movieReviewPreview;
	}

	public void setMovieReviewPreview(String movieReviewPreview) {
		this.movieReviewPreview = movieReviewPreview;
	}

	public String getMovieImage() {
		return movieImage;
	}

	public void setMovieImage(String movieImage) {
		this.movieImage = movieImage;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public double getMovieScore() {
		return movieScore;
	}

	public void setMovieScore(double movieScore) {
		this.movieScore = movieScore;
	}

	public String getFavoriteWord() {
		return favoriteWord;
	}

	public void setFavoriteWord(String favoriteWord) {
		this.favoriteWord = favoriteWord;
	}

	public String getMovieBoardDate() {
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
		String time = format1.format(movieBoardDate);
		return time;
	}

	public void setMovieBoardDate(Timestamp movieBoardDate) {		
		this.movieBoardDate = movieBoardDate;
	}

	public int getMovieReviewLike() {
		return movieReviewLike;
	}

	public void setMovieReviewLike(int movieReviewLike) {
		this.movieReviewLike = movieReviewLike;
	}
	
	public boolean isMovieSecretReview() {
		return movieSecretReview;
	}
	
	public void setMovieSecretReview(boolean movieSecretReview) {
		this.movieSecretReview = movieSecretReview;
	}
	
	public int getMovieReplyCnt() {
		return movieReplyCnt;
	}

	public void setMovieReplyCnt(int movieReplyCnt) {
		this.movieReplyCnt = movieReplyCnt;
	}

	public int getMovieReviewViews() {
		return movieReviewViews;
	}

	public void setMovieReviewViews(int movieReviewViews) {
		this.movieReviewViews = movieReviewViews;
	}

	@Override
	public String toString() {
		return "MovieReviewDto [movieReviewID=" + movieReviewID + ", userID=" + userID + ", movieReviewTitle=" + movieReviewTitle
				+ ", movieReviewContents=" + movieReviewContents + ", movieReviewPreview=" + movieReviewPreview
				+ ", movieImage=" + movieImage + ", movieTitle=" + movieTitle + ", movieScore=" + movieScore
				+ ", favoriteWord=" + favoriteWord + ", movieBoardDate=" + movieBoardDate + ", movieReviewLike="
				+ movieReviewLike + ", movieSecretReview=" + movieSecretReview + movieReplyCnt + movieReviewViews +"]";
	}
}
