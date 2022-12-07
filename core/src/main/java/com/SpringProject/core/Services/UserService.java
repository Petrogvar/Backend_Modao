package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.User;
import com.SpringProject.core.dto.UserDto;

public interface UserService {
  UserDto getUser(Long id);
  Long createUser(UserDto user);
  void deleteUser(Long id);
  void updateUser(Long id, UserDto user);
  Long authorizationUser (UserDto user);
}
