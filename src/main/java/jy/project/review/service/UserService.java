package jy.project.review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jy.project.review.dto.UserDto;
import jy.project.review.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper; 
	
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public UserDto getUser(UserDto memberDto) {
		return this.userMapper.getUser(memberDto);
	}
	
	public void insertUser(UserDto userDto) {
		if (userDto.isFilled()) this.userMapper.insertUser(userDto);
		else logger.info("UserDto is not filled");
	}
	
	public int findUser(String userID) {
		return this.userMapper.findUser(userID);
	}
	
	public void updateUser(UserDto userDto) {
		this.userMapper.updateUser(userDto);
	}
	
	public void deleteUser(String userID) {
		this.userMapper.deleteUser(userID);
	}
}
