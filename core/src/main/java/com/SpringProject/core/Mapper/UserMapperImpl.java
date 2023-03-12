package com.SpringProject.core.Mapper;

import com.SpringProject.core.Entity.User;
import com.SpringProject.core.dto.UserDto;
import com.SpringProject.core.dto.my.CustomPairIdName;
import java.util.ArrayList;
public class  UserMapperImpl {

  public static UserDto toUserDtoWithGroupWithoutPasswordAndLogin(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setUsername(user.getUsername());
//    userDto.setLogin(user.getLogin());
//    userDto.setPassword(user.getPassword());
    userDto.setIdPicture(user.getIdPicture());
    userDto.setPhoneNumber(user.getPhoneNumber());
    userDto.setBank(user.getBank());
    userDto.setGroupCustomPairIdNameList(new ArrayList<>());
    CustomPairIdName customPairIdName;
    for (int i = 0; i < user.getUserGroupsList().size(); i++) {
      customPairIdName = new CustomPairIdName();
      customPairIdName.setId(user.getUserGroupsList().get(i).getGroup().getId());
      customPairIdName.setName(user.getUserGroupsList().get(i).getGroup().getGroupName());
      userDto.getGroupCustomPairIdNameList().add(customPairIdName);
    }
    return userDto;
  }

  public static UserDto toUserDto(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setUsername(user.getUsername());
    userDto.setLogin(user.getLogin());
    userDto.setPassword(user.getPassword());
    userDto.setIdPicture(user.getIdPicture());
    userDto.setPhoneNumber(user.getPhoneNumber());
    userDto.setBank(user.getBank());
    return userDto;
  }

  public static UserDto toUserDtoWithoutPasswordAndLogin(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setUsername(user.getUsername());
//    userDto.setLogin(user.getLogin());
//    userDto.setPassword(user.getPassword());
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
