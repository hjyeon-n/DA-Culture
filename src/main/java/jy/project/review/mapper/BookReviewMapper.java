package jy.project.review.mapper;

import java.util.HashMap;
import java.util.List;

import jy.project.review.dto.BookReviewDto;
import jy.project.review.dto.BookReviewLikeDto;
import jy.project.review.page.ReviewSearch;

public interface BookReviewMapper {
	public int bookPageCount(HashMap<String, Object> map);
	public List<BookReviewDto> getAll_book(ReviewSearch reviewSearch);
	public void insertBookReview(BookReviewDto bookDto);
	public BookReviewDto getDetial_book(int contentNO);
	public void updateBookReview(BookReviewDto bookDto);
	public void deleteBookReview(int bookReviewID);
	public int privateBookPageCount(HashMap<String, Object> map);
	public List<BookReviewDto> getAll_privateBook(ReviewSearch reviewSearch);
	public void insertBookReviewLike(BookReviewLikeDto bookLikeDto);
	public void deleteBookReviewLike(BookReviewLikeDto bookLikeDto);
	public void plusBookReviewLike(int bookReviewID);
	public void minusBookReviewLike(int bookReviewID);
	public int findBookReviewLike(BookReviewLikeDto bookLikeDto);
	public int userLikesBookCount(String bookReviewID);
	public List<BookReviewDto> getAll_LikeBook(String bookReviewID); 
	public void plusReplyCnt(int bookReviewID);
	public void minusReplyCnt(int bookReviewID);
	public void bookReviewViews(int bookReviewID);
	public void deleteBookReviewLike_delete(int bookReviewID);
	public String getBookReviewWriter(int bookReviewID);
	public int myBookPageCount(HashMap<String, Object> map);
	public List<BookReviewDto> getAll_myBook(ReviewSearch reviewSearch);
}
