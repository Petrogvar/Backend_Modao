package com.SpringProject.core.controllers;

import com.SpringProject.core.Services.UserService;
import com.SpringProject.core.dto.UserDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }


  @GetMapping("/{id}")
  public UserDto getById(@PathVariable Long id) {
    return userService.getUser(id);
  }

  @PostMapping("/in")
  public Long authorization(@RequestBody UserDto userDto){
    return userService.authorizationUser(userDto);
  }
  @PostMapping
  public Long create(@RequestBody UserDto userDto ){
    return userService.createUser(userDto);
  }
  @DeleteMapping("/{id}")
  void delete(@PathVariable Long id){
    userService.deleteUser(id);
  }
  @PutMapping("/{id}")
  void update(@PathVariable Long id, @RequestBody UserDto userDto){
    userService.updateUser(id, userDto);
  }
}
