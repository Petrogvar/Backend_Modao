package com.SpringProject.core.mapper;

import com.SpringProject.core.Entity.User;
import com.SpringProject.core.dto.UserDto;
import java.util.AbstractMap;
import java.util.ArrayList;

public class UserMapperImpl {

  public static UserDto toUserDto(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setUsername(user.getUsername());
    userDto.setLogin(user.getLogin());
    userDto.setPassword(user.getPassword());
    userDto.setIdPicture(user.getIdPicture());
    userDto.setPhone_number(user.getPhone_number());
    userDto.setBank(user.getBank());
    userDto.setGroups(new ArrayList<>());
    AbstractMap.SimpleEntry<Long, String> entry;
    //Pair<Long, String> pair;
     for (int i = 0; i < user.getGroup().size(); i++) {
       entry = new AbstractMap.SimpleEntry<>(user.getGroup().get(i).getGroup().getId(), user.getGroup().get(i).getGroup().getGroupName());
       userDto.getGroups().add(entry);
    }
    return userDto;
  }

  public static User toUser(UserDto userDto) {
    User user = new User();
    user.setId(userDto.getId());
    user.setUsername(userDto.getUsername());
    user.setLogin(userDto.getLogin());
    user.setPassword(userDto.getPassword());
    user.setIdPicture(userDto.getIdPicture());
    user.setPhone_number(user.getPhone_number());
    user.setBank(userDto.getBank());
    return user;
  }
}
