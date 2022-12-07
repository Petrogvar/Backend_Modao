package com.SpringProject.core.controllers;

import com.SpringProject.core.Entity.User;
import com.SpringProject.core.Services.UserService;
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
  public User getById(@PathVariable Long id) {
    return userService.getUsers(id);
  }

  @PostMapping("/in")
  public Long authorization(@RequestBody User usersTable){
    return userService.findUser(usersTable);
  }
  @PostMapping
  public Long create(@RequestBody User usersTable ){
    return userService.createUsers(usersTable);
  }
  @DeleteMapping("/{id}")
  void delete(@PathVariable Long id){
    userService.deleteUsers(id);
  }
  @PutMapping("/{id}")
  void update(@PathVariable Long id, @RequestBody User user){
    userService.updateUsers(id, user);
  }
}
