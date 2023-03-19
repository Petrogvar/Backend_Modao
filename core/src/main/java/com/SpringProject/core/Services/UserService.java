package com.SpringProject.core.Services;

import com.SpringProject.core.dto.GroupDto;
import com.SpringProject.core.dto.UserDto;
import java.util.List;

public interface UserService {

  UserDto getUser(Long userId);

  Long createUser(UserDto userDto);



  void deleteUser(Long userId);

  void updateUser(Long id, UserDto userDto);

  List<GroupDto> getGroups(Long userId);

  UserDto getUserMyInfo(Long userIdCreator);

  //Long authorizationUser(UserDto userDto);
}
