package jy.project.review.dto;

public class MovieReviewLikeDto {
	private int movieReviewID;
	private String userID;
	
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
	public MovieReviewLikeDto(int movieReviewID, String userID) {
		super();
		this.movieReviewID = movieReviewID;
		this.userID = userID;
	}
	public MovieReviewLikeDto() {
		super();
	}
	@Override
	public String toString() {
		return "MovieLikeDto [movieReviewID=" + movieReviewID + ", userID=" + userID + "]";
	}
}
