package jy.project.review.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jy.project.review.dto.MovieReplyDto;
import jy.project.review.dto.MovieReplyLikeDto;
import jy.project.review.mapper.MovieReplyMapper;
import jy.project.review.page.pageMaker;
@Service
public class MovieReplyService {
	@Autowired
	private MovieReplyMapper replyMapper;

	public List<MovieReplyDto> getMovieReplyList(pageMaker page) {
		return this.replyMapper.getMovieReplyList(page);
	}
	
	public void insertMovieReply(MovieReplyDto replyDto) {
		this.replyMapper.insertMovieReply(replyDto);
	}
	
	public void updateMovieReply(MovieReplyDto replyDto) {
		this.replyMapper.updateMovieReply(replyDto);
	}
	
	public void deleteMovieReply(int movieReplyID) {
		this.replyMapper.deleteMovieReply(movieReplyID);
	}
	
	public MovieReplyDto getMovieReply(int movieReplyID) {
		return this.replyMapper.getMovieReply(movieReplyID);
	}
	
	public void setMovieReplyOrder(HashMap<String, Object> map) {
		this.replyMapper.setMovieReplyOrder(map);
	}
	
	public int movieReplyCount(int movieReviewID) {
		return this.replyMapper.movieReplyCount(movieReviewID);
	}
	
	public void plusMovieReplyLike(int movieReplyID) {
		this.replyMapper.plusMovieReplyLike(movieReplyID);
	}
	
	public void minusMovieReplyLike(int movieReplyID) {
		this.replyMapper.minusMovieReplyLike(movieReplyID);
	}
	
	public void insertMovieReplyLike(MovieReplyLikeDto movieReplyLike) {
		this.replyMapper.insertMovieReplyLike(movieReplyLike);
	}
	
	public void deleteMovieReplyLike(MovieReplyLikeDto movieReplyLike) {
		this.replyMapper.deleteMovieReplyLike(movieReplyLike);
	}
	
	public int findMovieReplyLike(MovieReplyLikeDto movieReplyLike) {
		return this.replyMapper.findMovieReplyLike(movieReplyLike);
	}
	
	public void deleteMovieReply_delete(int movieReplyID) {
		this.replyMapper.deleteMovieReply_delete(movieReplyID);
	}
	
	public void updateSecretComment(MovieReplyDto replyDto) {
		this.replyMapper.updateSecretComment(replyDto);
	}
	
	public String findMovieReplyUser(int movieReplyID) {
		return this.replyMapper.findMovieReplyUser(movieReplyID);
	}
	
	public int findLastMovieReplyID() {
		return this.replyMapper.findLastMovieReplyID();
	}
	
	public int countSameGID(int movieReplyID) {
		return this.replyMapper.countSameGID(movieReplyID);
	}
}
