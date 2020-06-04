package jy.project.review.mapper;

import java.util.HashMap;
import java.util.List;

import jy.project.review.dto.MovieReplyDto;
import jy.project.review.dto.MovieReplyLikeDto;
import jy.project.review.page.pageMaker;

public interface MovieReplyMapper {
	public List<MovieReplyDto> getMovieReplyList(pageMaker page);
	public void insertMovieReply(MovieReplyDto replyDto);
	public void updateMovieReply(MovieReplyDto replyDto);
	public void deleteMovieReply(int movieReplyID);
	public MovieReplyDto getMovieReply(int movieReplyID);
	public void setMovieReplyOrder(HashMap<String, Object> map);
	public int movieReplyCount(int movieReviewID);
	public void plusMovieReplyLike(int movieReplyID);
	public void minusMovieReplyLike(int movieReplyID);
	public void insertMovieReplyLike(MovieReplyLikeDto movieReplyLike);
	public void deleteMovieReplyLike(MovieReplyLikeDto movieReplyLike);
	public int findMovieReplyLike(MovieReplyLikeDto movieReplyLike);
	public void deleteMovieReply_delete(int movieReplyID);
	public void updateSecretComment(MovieReplyDto replyDto);
	public String findMovieReplyUser(int movieReplyID);
	public int findLastMovieReplyID();
	public int countSameGID(int movieReplyID);
}
