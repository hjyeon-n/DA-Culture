package jy.project.review.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jy.project.review.dto.BookReviewDto;
import jy.project.review.dto.BookReviewLikeDto;
import jy.project.review.mapper.BookReviewMapper;
import jy.project.review.page.ReviewSearch;

@Service
public class BookReviewService {
	@Autowired
	private BookReviewMapper reviewMapper;
	
	public int bookPageCount(HashMap<String, Object> map) {
		return this.reviewMapper.bookPageCount(map);
	}
	
	public List<BookReviewDto> getAll_book(ReviewSearch reviewSearch) {
		return this.reviewMapper.getAll_book(reviewSearch);
	}
	
	public void insertBookReview(BookReviewDto bookDto) {
		this.reviewMapper.insertBookReview(bookDto);
	}
	
	public BookReviewDto getDetial_book(int contentNO) {
		return this.reviewMapper.getDetial_book(contentNO);
	}
	
	public void updateBookReview(BookReviewDto bookDto) {
		this.reviewMapper.updateBookReview(bookDto);
	}
	
	public void deleteBookReview(int bookReviewID) {
		this.reviewMapper.deleteBookReview(bookReviewID);
	}
	
	public int privateBookPageCount(HashMap<String, Object> map) {
		return reviewMapper.privateBookPageCount(map);
	}
	public List<BookReviewDto> getAll_privateBook(ReviewSearch reviewSearch) {
		return this.reviewMapper.getAll_privateBook(reviewSearch);
	}
	public void insertBookReviewLike(BookReviewLikeDto bookLikeDto) {
		this.reviewMapper.insertBookReviewLike(bookLikeDto);
	}
	public void deleteBookReviewLike(BookReviewLikeDto bookLikeDto) {
		this.reviewMapper.deleteBookReviewLike(bookLikeDto);
	}
	public void plusBookReviewLike(int bookReviewID) {
		this.reviewMapper.plusBookReviewLike(bookReviewID);
	}
	public void minusBookReviewLike(int bookReviewID) {
		this.reviewMapper.minusBookReviewLike(bookReviewID);
	}
	public int findBookReviewLike(BookReviewLikeDto bookLikeDto) {
		return this.reviewMapper.findBookReviewLike(bookLikeDto);
	}
	public int userLikesBookCount(String bookReviewID) {
		return this.reviewMapper.userLikesBookCount(bookReviewID);
	}
	public List<BookReviewDto> getAll_LikeBook(String bookReviewID) {
		return this.reviewMapper.getAll_LikeBook(bookReviewID);
	}
	public void plusReplyCnt(int bookReviewID) {
		this.reviewMapper.plusReplyCnt(bookReviewID);
	}
	public void minusReplyCnt(int bookReviewID) {
		this.reviewMapper.minusReplyCnt(bookReviewID);
	}
	public void bookReviewViews(int bookReviewID) {
		this.reviewMapper.bookReviewViews(bookReviewID);
	}
	public void deleteBookReviewLike_delete(int bookReviewID) {
		this.reviewMapper.deleteBookReviewLike_delete(bookReviewID);
	}
	public String getBookReviewWriter(int bookReviewID) {
		return this.reviewMapper.getBookReviewWriter(bookReviewID);
	}
	public int myBookPageCount(HashMap<String, Object> map) {
		return this.reviewMapper.myBookPageCount(map);
	}
	public List<BookReviewDto> getAll_myBook(ReviewSearch reviewSearch){
		return this.reviewMapper.getAll_myBook(reviewSearch);
	}
}
