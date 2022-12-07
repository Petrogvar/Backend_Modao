package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.User;

public interface UserService {
  User getUsers(Long id);
  Long createUsers(User usersTable);
  void deleteUsers(Long id);
  void updateUsers(Long id, User usersTable);
  Long findUser (User user);
}
