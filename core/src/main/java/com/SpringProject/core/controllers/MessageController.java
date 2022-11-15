package com.SpringProject.core.controllers;

import com.SpringProject.core.Services.MessageService;
import com.SpringProject.core.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {
  private final MessageService messageService;
  @GetMapping("/hello/{id}")
  public Message sayHello(@PathVariable Long id) {
    return messageService.getMessage(id);
  }
}
