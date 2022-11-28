package com.SpringProject.core.controllers;

import com.SpringProject.core.Services.usersService;
import com.SpringProject.core.Entity.usersEntity;
import com.SpringProject.core.dto.usersDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class usersController {
  private final usersService usersService;
  @GetMapping("/hello2/{id}")
  public usersEntity sayHello(@PathVariable Long id) {
    return usersService.getUsers(id);
  }

  @PostMapping("/del")
  public Long create(@RequestBody usersEntity usersTable ){
    return usersService.create(usersTable);
  }
}
