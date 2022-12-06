package com.SpringProject.core.Repository;

import com.SpringProject.core.Entity.UsersEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
  UsersEntity findByLoginAndPassword (String Login, String Password);
  UsersEntity findByLogin(String Login);
}