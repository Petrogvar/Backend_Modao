package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.User;
import com.SpringProject.core.dto.UserDto;

public interface UserService {

  UserDto getUser(Long userId);

  Long createUser(UserDto userDto);

  void deleteUser(Long userId);

  void updateUser(Long id, UserDto userDto);

  Long authorizationUser(UserDto userDto);
}
