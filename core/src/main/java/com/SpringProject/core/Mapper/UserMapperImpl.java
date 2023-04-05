package com.SpringProject.core.Mapper;

import com.SpringProject.core.Entity.User;
import com.SpringProject.core.dto.UserDto;
import com.SpringProject.core.dto.my.CustomPairIdName;
import java.util.ArrayList;
public class  UserMapperImpl {

  public static UserDto toUserDto(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setUsername(user.getUsername());
    userDto.setLogin(user.getLogin());
    userDto.setPassword(user.getPassword());
    userDto.setIdPicture(user.getIdPicture());
    userDto.setPhoneNumber(user.getPhoneNumber());
    userDto.setBank(user.getBank());
    userDto.setUuid(user.getUuid());
    return userDto;
  }

  public static UserDto toUserDtoMyInfo(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setUsername(user.getUsername());
    userDto.setIdPicture(user.getIdPicture());
    userDto.setPhoneNumber(user.getPhoneNumber());
    userDto.setBank(user.getBank());
    userDto.setUuid(user.getUuid());
    return userDto;
  }

  public static UserDto toUserDtoWithoutUuid(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setUsername(user.getUsername());
    userDto.setIdPicture(user.getIdPicture());
    userDto.setPhoneNumber(user.getPhoneNumber());
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
    user.setPhoneNumber(user.getPhoneNumber());
    user.setBank(userDto.getBank());
    return user;
  }
}
