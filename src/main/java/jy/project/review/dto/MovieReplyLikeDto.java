package jy.project.review.dto;

public class MovieReplyLikeDto {
	private int movieReplyID;
	private String userID;
	
	public int getMovieReplyID() {
		return movieReplyID;
	}
	public void setMovieReplyID(int movieReplyID) {
		this.movieReplyID = movieReplyID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public MovieReplyLikeDto(int movieReplyID, String userID) {
		super();
		this.movieReplyID = movieReplyID;
		this.userID = userID;
	}
	public MovieReplyLikeDto() {
		super();
	}
	@Override
	public String toString() {
		return "MovieReplyLikeDto [movieReplyID=" + movieReplyID + ", userID=" + userID + "]";
	}
}
