package com.SpringProject.core.mapper;

import com.SpringProject.core.Entity.User;
import com.SpringProject.core.dto.UserDto;

public class UserMapperImpl {

  public static UserDto toUserDto(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setUsername(user.getUsername());
    userDto.setLogin(user.getLogin());
    userDto.setPassword(user.getPassword());
    userDto.setIdPicture(user.getIdPicture());
    userDto.setBank(user.getBank());
    return userDto;
  }

  public static User toUser(UserDto userDto) {
    User user = new User();
    user.setId(userDto.getId());
    user.setUsername(userDto.getUsername());
    user.setLogin(userDto.getLogin());
    user.setPassword(userDto.getPassword());
    user.setIdPicture(userDto.getIdPicture());
    user.setBank(userDto.getBank());
    return user;
  }
}
