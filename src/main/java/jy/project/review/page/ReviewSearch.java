package jy.project.review.page;

public class ReviewSearch extends pageMaker{
	private String searchType;
	private String keyword;
	private String userID;
	private int movieReviewID;
	private int bookReviewID;
	private int showReviewID;
	private int playReviewID;
	
	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public int getMovieReviewID() {
		return movieReviewID;
	}

	public void setMovieReviewID(int movieReviewID) {
		this.movieReviewID = movieReviewID;
	}

	public int getBookReviewID() {
		return bookReviewID;
	}

	public void setBookReviewID(int bookReviewID) {
		this.bookReviewID = bookReviewID;
	}

	public int getShowReviewID() {
		return showReviewID;
	}

	public void setShowReviewID(int showReviewID) {
		this.showReviewID = showReviewID;
	}

	public int getPlayReviewID() {
		return playReviewID;
	}

	public void setPlayReviewID(int playReviewID) {
		this.playReviewID = playReviewID;
	}	
}
