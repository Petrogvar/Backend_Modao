package com.SpringProject.core.Repository;

import com.SpringProject.core.Entity.usersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface usersRepository extends JpaRepository<usersEntity, Long> {
}