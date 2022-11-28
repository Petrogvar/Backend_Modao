package com.SpringProject.core.Repository;

import com.SpringProject.core.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}