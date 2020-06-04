package jy.project.review.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jy.project.review.dto.ShowReplyDto;
import jy.project.review.dto.ShowReplyLikeDto;
import jy.project.review.mapper.ShowReplyMapper;
import jy.project.review.page.pageMaker;
@Service
public class ShowReplyService {
	@Autowired
	private ShowReplyMapper replyMapper;

	public List<ShowReplyDto> getShowReplyList(pageMaker page) {
		return this.replyMapper.getShowReplyList(page);
	}
	
	public void insertShowReply(ShowReplyDto replyDto) {
		this.replyMapper.insertShowReply(replyDto);
	}
	
	public void updateShowReply(ShowReplyDto replyDto) {
		this.replyMapper.updateShowReply(replyDto);
	}
	
	public void deleteShowReply(int showReplyID) {
		this.replyMapper.deleteShowReply(showReplyID);
	}
	
	public ShowReplyDto getShowReply(int showReplyID) {
		return this.replyMapper.getShowReply(showReplyID);
	}
	
	public void setShowReplyOrder(HashMap<String, Object> map) {
		this.replyMapper.setShowReplyOrder(map);
	}
	
	public int showReplyCount(int showReviewID) {
		return this.replyMapper.showReplyCount(showReviewID);
	}
	
	public void plusShowReplyLike(int showReplyID) {
		this.replyMapper.plusShowReplyLike(showReplyID);
	}
	
	public void minusShowReplyLike(int showReplyID) {
		this.replyMapper.minusShowReplyLike(showReplyID);
	}
	
	public void insertShowReplyLike(ShowReplyLikeDto showReplyLike) {
		this.replyMapper.insertShowReplyLike(showReplyLike);
	}
	
	public void deleteShowReplyLike(ShowReplyLikeDto showReplyLike) {
		this.replyMapper.deleteShowReplyLike(showReplyLike);
	}
	
	public int findShowReplyLike(ShowReplyLikeDto showReplyLike) {
		return this.replyMapper.findShowReplyLike(showReplyLike);
	}
	
	public void deleteShowReply_delete(int showReplyID) {
		this.replyMapper.deleteShowReply_delete(showReplyID);
	}
	
	public void updateSecretComment(ShowReplyDto replyDto) {
		this.replyMapper.updateSecretComment(replyDto);
	}
	
	public String findShowReplyUser(int showReplyID) {
		return this.replyMapper.findShowReplyUser(showReplyID);
	}
	
	public int findLastShowReplyID() {
		return this.replyMapper.findLastShowReplyID();
	}
	
	public int countSameGID(int showReplyID) {
		return this.replyMapper.countSameGID(showReplyID);
	}
}
