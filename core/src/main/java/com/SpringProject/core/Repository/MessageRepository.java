package com.SpringProject.core.Repository;

import com.SpringProject.core.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}