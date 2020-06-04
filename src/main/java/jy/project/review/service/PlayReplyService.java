package jy.project.review.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jy.project.review.dto.PlayReplyDto;
import jy.project.review.dto.PlayReplyLikeDto;
import jy.project.review.mapper.PlayReplyMapper;
import jy.project.review.page.pageMaker;
@Service
public class PlayReplyService {
	@Autowired
	private PlayReplyMapper replyMapper;

	public List<PlayReplyDto> getPlayReplyList(pageMaker page) {
		return this.replyMapper.getPlayReplyList(page);
	}
	
	public void insertPlayReply(PlayReplyDto replyDto) {
		this.replyMapper.insertPlayReply(replyDto);
	}
	
	public void updatePlayReply(PlayReplyDto replyDto) {
		this.replyMapper.updatePlayReply(replyDto);
	}
	
	public void deletePlayReply(int playReplyID) {
		this.replyMapper.deletePlayReply(playReplyID);
	}
	
	public PlayReplyDto getPlayReply(int playReplyID) {
		return this.replyMapper.getPlayReply(playReplyID);
	}
	
	public void setPlayReplyOrder(HashMap<String, Object> map) {
		this.replyMapper.setPlayReplyOrder(map);
	}
	
	public int playReplyCount(int playReviewID) {
		return this.replyMapper.playReplyCount(playReviewID);
	}
	
	public void plusPlayReplyLike(int playReplyID) {
		this.replyMapper.plusPlayReplyLike(playReplyID);
	}
	
	public void minusPlayReplyLike(int playReplyID) {
		this.replyMapper.minusPlayReplyLike(playReplyID);
	}
	
	public void insertPlayReplyLike(PlayReplyLikeDto playReplyLike) {
		this.replyMapper.insertPlayReplyLike(playReplyLike);
	}
	
	public void deletePlayReplyLike(PlayReplyLikeDto playReplyLike) {
		this.replyMapper.deletePlayReplyLike(playReplyLike);
	}
	
	public int findPlayReplyLike(PlayReplyLikeDto playReplyLike) {
		return this.replyMapper.findPlayReplyLike(playReplyLike);
	}
	
	public void deletePlayReply_delete(int playReplyID) {
		this.replyMapper.deletePlayReply_delete(playReplyID);
	}
	
	public void updateSecretComment(PlayReplyDto replyDto) {
		this.replyMapper.updateSecretComment(replyDto);
	}
	
	public String findPlayReplyUser(int playReplyID) {
		return this.replyMapper.findPlayReplyUser(playReplyID);
	}
	
	public int findLastPlayReplyID() {
		return this.replyMapper.findLastPlayReplyID();
	}
	
	public int countSameGID(int playReplyID) {
		return this.replyMapper.countSameGID(playReplyID);
	}
}
