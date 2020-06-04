package jy.project.review.mapper;

import java.util.HashMap;
import java.util.List;

import jy.project.review.dto.ShowReplyDto;
import jy.project.review.dto.ShowReplyLikeDto;
import jy.project.review.page.pageMaker;

public interface ShowReplyMapper {
	public List<ShowReplyDto> getShowReplyList(pageMaker page);
	public void insertShowReply(ShowReplyDto replyDto);
	public void updateShowReply(ShowReplyDto replyDto);
	public void deleteShowReply(int showReplyID);
	public ShowReplyDto getShowReply(int showReplyID);
	public void setShowReplyOrder(HashMap<String, Object> map);
	public int showReplyCount(int showReviewID);
	public void plusShowReplyLike(int showReplyID);
	public void minusShowReplyLike(int showReplyID);
	public void insertShowReplyLike(ShowReplyLikeDto showReplyLike);
	public void deleteShowReplyLike(ShowReplyLikeDto showReplyLike);
	public int findShowReplyLike(ShowReplyLikeDto showReplyLike);
	public void deleteShowReply_delete(int showReplyID);
	public void updateSecretComment(ShowReplyDto replyDto);
	public String findShowReplyUser(int showReplyID);
	public int findLastShowReplyID();
	public int countSameGID(int showReplyID);
}
