package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.UsersEntity;

public interface UsersService {
  UsersEntity getUsers(Long id);
  Long createUsers(UsersEntity usersTable);
  void deleteUsers(Long id);
  void updateUsers(Long id, UsersEntity usersTable);
  Long findUser (UsersEntity user);
}
