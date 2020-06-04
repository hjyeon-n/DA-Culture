package jy.project.review.mapper;

import java.util.HashMap;
import java.util.List;

import jy.project.review.dto.BookReplyDto;
import jy.project.review.dto.BookReplyLikeDto;
import jy.project.review.page.pageMaker;

public interface BookReplyMapper {
	public List<BookReplyDto> getBookReplyList(pageMaker page);
	public void insertBookReply(BookReplyDto replyDto);
	public void updateBookReply(BookReplyDto replyDto);
	public void deleteBookReply(int bookReplyID);
	public BookReplyDto getBookReply(int bookReplyID);
	public void setBookReplyOrder(HashMap<String, Object> map);
	public int bookReplyCount(int bookReviewID);
	public void plusBookReplyLike(int bookReplyID);
	public void minusBookReplyLike(int bookReplyID);
	public void insertBookReplyLike(BookReplyLikeDto bookReplyLike);
	public void deleteBookReplyLike(BookReplyLikeDto bookReplyLike);
	public int findBookReplyLike(BookReplyLikeDto bookReplyLike);
	public void deleteBookReply_delete(int bookReplyID);
	public void updateSecretComment(BookReplyDto replyDto);
	public String findBookReplyUser(int bookReplyID);
	public int findLastBookReplyID();
	public int countSameGID(int bookReplyID);
}
