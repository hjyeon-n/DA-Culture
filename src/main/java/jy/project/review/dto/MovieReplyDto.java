package jy.project.review.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class MovieReplyDto {
	private int movieReplyID;
	private int movieReviewID;
	private String movieReplyContents;
	private String userID;
	private Timestamp movieReplyDate;
	private boolean movieSecretReply;
	private int movieReplyGID;
	private int movieReplyOrder;
	private int movieReplyParents;
	private int movieReplyLike;
	private String movieReviewWriter;
	private String movieParentsWriter;
	
	public int getMovieReplyID() {
		return movieReplyID;
	}
	public void setMovieReplyID(int movieReplyID) {
		this.movieReplyID = movieReplyID;
	}
	public int getMovieReviewID() {
		return movieReviewID;
	}
	public void setMovieReviewID(int movieReviewID) {
		this.movieReviewID = movieReviewID;
	}
	public String getMovieReplyContents() {
		return movieReplyContents;
	}
	public void setMovieReplyContents(String movieReplyContents) {
		this.movieReplyContents = movieReplyContents;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getMovieReplyDate() {
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
		String time = format1.format(movieReplyDate);
		return time;
	}
	public void setMovieReplyDate(Timestamp movieReplyDate) {
		this.movieReplyDate = movieReplyDate;
	}
	public boolean isMovieSecretReply() {
		return movieSecretReply;
	}
	public void setMovieSecretReply(boolean movieSecretReply) {
		this.movieSecretReply = movieSecretReply;
	}
	public int getMovieReplyGID() {
		return movieReplyGID;
	}
	public void setMovieReplyGID(int movieReplyGID) {
		this.movieReplyGID = movieReplyGID;
	}
	public int getMovieReplyOrder() {
		return movieReplyOrder;
	}
	public void setMovieReplyOrder(int movieReplyOrder) {
		this.movieReplyOrder = movieReplyOrder;
	}
	public int getMovieReplyParents() {
		return movieReplyParents;
	}
	public void setMovieReplyParents(int movieReplyParents) {
		this.movieReplyParents = movieReplyParents;
	}
	public int getMovieReplyLike() {
		return movieReplyLike;
	}
	public void setMovieReplyLike(int movieReplyLike) {
		this.movieReplyLike = movieReplyLike;
	}
	public String getMovieReviewWriter() {
		return movieReviewWriter;
	}
	public void setMovieReviewWriter(String movieReviewWriter) {
		this.movieReviewWriter = movieReviewWriter;
	}
	public String getMovieParentsWriter() {
		return movieParentsWriter;
	}
	public void setMovieParentsWriter(String movieParentsWriter) {
		this.movieParentsWriter = movieParentsWriter;
	}
	public MovieReplyDto(int movieReplyID, int movieReviewID, String movieReplyContents, String userID,
			Timestamp movieReplyDate, boolean movieSecretReply, int movieReplyGID, int movieReplyOrder,
			int movieReplyParents, int movieReplyLike, String movieReviewWriter, String movieParentsWriter) {
		super();
		this.movieReplyID = movieReplyID;
		this.movieReviewID = movieReviewID;
		this.movieReplyContents = movieReplyContents;
		this.userID = userID;
		this.movieReplyDate = movieReplyDate;
		this.movieSecretReply = movieSecretReply;
		this.movieReplyGID = movieReplyGID;
		this.movieReplyOrder = movieReplyOrder;
		this.movieReplyParents = movieReplyParents;
		this.movieReplyLike = movieReplyLike;
		this.movieReviewWriter = movieReviewWriter;
		this.movieParentsWriter = movieParentsWriter;
	}
	public MovieReplyDto() {
		super();
	}
	@Override
	public String toString() {
		return "MovieReplyDto [movieReplyID=" + movieReplyID + ", movieReviewID=" + movieReviewID
				+ ", movieReplyContents=" + movieReplyContents + ", userID=" + userID + ", movieReplyDate="
				+ movieReplyDate + ", movieSecretReply=" + movieSecretReply + ", movieReplyGID=" + movieReplyGID
				+ ", movieReplyOrder=" + movieReplyOrder + ", movieReplyParents=" + movieReplyParents
				+ ", movieReplyLike=" + movieReplyLike + ", movieReviewWriter=" + movieReviewWriter
				+ ", movieParentsWriter=" + movieParentsWriter + "]";
	}
}
