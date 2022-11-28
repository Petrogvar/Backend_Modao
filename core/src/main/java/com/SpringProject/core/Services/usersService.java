package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.usersEntity;
import com.SpringProject.core.dto.usersDto;

public interface usersService {
  usersEntity getUsers(Long id);
  Long create(usersEntity usersTable);
}
