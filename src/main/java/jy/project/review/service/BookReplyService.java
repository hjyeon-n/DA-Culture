package jy.project.review.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jy.project.review.dto.BookReplyDto;
import jy.project.review.dto.BookReplyLikeDto;
import jy.project.review.mapper.BookReplyMapper;
import jy.project.review.page.pageMaker;

@Service
public class BookReplyService {
	@Autowired
	private BookReplyMapper replyMapper;

	public List<BookReplyDto> getBookReplyList(pageMaker page) {
		return this.replyMapper.getBookReplyList(page);
	}
	
	public void insertBookReply(BookReplyDto replyDto) {
		this.replyMapper.insertBookReply(replyDto);
	}
	
	public void updateBookReply(BookReplyDto replyDto) {
		this.replyMapper.updateBookReply(replyDto);
	}
	
	public void deleteBookReply(int bookReplyID) {
		this.replyMapper.deleteBookReply(bookReplyID);
	}
	
	public BookReplyDto getBookReply(int bookReplyID) {
		return this.replyMapper.getBookReply(bookReplyID);
	}
	
	public void setBookReplyOrder(HashMap<String, Object> map) {
		this.replyMapper.setBookReplyOrder(map);
	}
	
	public int bookReplyCount(int bookReviewID) {
		return this.replyMapper.bookReplyCount(bookReviewID);
	}
	
	public void plusBookReplyLike(int bookReplyID) {
		this.replyMapper.plusBookReplyLike(bookReplyID);
	}
	
	public void minusBookReplyLike(int bookReplyID) {
		this.replyMapper.minusBookReplyLike(bookReplyID);
	}
	
	public void insertBookReplyLike(BookReplyLikeDto bookReplyLike) {
		this.replyMapper.insertBookReplyLike(bookReplyLike);
	}
	
	public void deleteBookReplyLike(BookReplyLikeDto bookReplyLike) {
		this.replyMapper.deleteBookReplyLike(bookReplyLike);
	}
	
	public int findBookReplyLike(BookReplyLikeDto bookReplyLike) {
		return this.replyMapper.findBookReplyLike(bookReplyLike);
	}
	
	public void deleteBookReply_delete(int bookReplyID) {
		this.replyMapper.deleteBookReply_delete(bookReplyID);
	}
	
	public void updateSecretComment(BookReplyDto replyDto) {
		this.replyMapper.updateSecretComment(replyDto);
	}
	
	public String findBookReplyUser(int bookReplyID) {
		return this.replyMapper.findBookReplyUser(bookReplyID);
	}
	
	public int findLastBookReplyID() {
		return this.replyMapper.findLastBookReplyID();
	}
	
	public int countSameGID(int bookReplyID) {
		return this.replyMapper.countSameGID(bookReplyID);
	}
}
