package com.SpringProject.core.Services;

import com.SpringProject.core.dto.GroupDto;
import com.SpringProject.core.dto.UserDto;
import java.util.List;

public interface UserService {

  UserDto getUser(Long userId);

  Long createUser(UserDto userDto);

  void exitUser(Long userId);

  void deleteUser(Long userId);

  void updateUser(Long id, UserDto userDto);

  List<GroupDto> getGroups(Long userId, Integer type);

  UserDto getUserMyInfo(Long userIdCreator);

  UserDto getNewUuid(Long userId);

  List<UserDto> getListFriends(Long userId);

  //Long authorizationUser(UserDto userDto);
}
