package jy.project.review.mapper;

import jy.project.review.dto.UserDto;

public interface UserMapper {
	public UserDto getUser(UserDto memberDto);
	public void insertUser(UserDto userDto);
	public int findUser(String userID);
	public void updateUser(UserDto userDto);
	public void deleteUser(String userID);
}
