package com.SpringProject.core.Services;

import com.SpringProject.core.Repository.MessageRepository;
import com.SpringProject.core.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

  private final MessageRepository messageRepository;
  @Override
  public Message getMessage(Long id){
    return messageRepository.findById(id).get();
  }
}
