package com.SpringProject.core.Repository;

import com.SpringProject.core.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByLoginAndPassword (String Login, String Password);
  User findByLogin(String Login);
}