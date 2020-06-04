package jy.project.review.mapper;

import java.util.HashMap;
import java.util.List;

import jy.project.review.dto.PlayReplyDto;
import jy.project.review.dto.PlayReplyLikeDto;
import jy.project.review.page.pageMaker;

public interface PlayReplyMapper {
	public List<PlayReplyDto> getPlayReplyList(pageMaker page);
	public void insertPlayReply(PlayReplyDto replyDto);
	public void updatePlayReply(PlayReplyDto replyDto);
	public void deletePlayReply(int playReplyID);
	public PlayReplyDto getPlayReply(int playReplyID);
	public void setPlayReplyOrder(HashMap<String, Object> map);
	public int playReplyCount(int playReviewID);
	public void plusPlayReplyLike(int playReplyID);
	public void minusPlayReplyLike(int playReplyID);
	public void insertPlayReplyLike(PlayReplyLikeDto playReplyLike);
	public void deletePlayReplyLike(PlayReplyLikeDto playReplyLike);
	public int findPlayReplyLike(PlayReplyLikeDto playReplyLike);
	public void deletePlayReply_delete(int playReplyID);
	public void updateSecretComment(PlayReplyDto replyDto);
	public String findPlayReplyUser(int playReplyID);
	public int findLastPlayReplyID();
	public int countSameGID(int playReplyID);
}
