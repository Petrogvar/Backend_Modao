package com.SpringProject.core.controllers;

import com.SpringProject.core.Entity.UsersEntity;
import com.SpringProject.core.Services.UsersService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/users")
public class UsersController {
  private final UsersService usersService;

  public UsersController(UsersService usersService) {
    this.usersService = usersService;
  }


  @GetMapping("/{id}")
  public UsersEntity getById(@PathVariable Long id) {
    return usersService.getUsers(id);
  }

  @PostMapping("/in")
  public Long authorization(@RequestBody UsersEntity usersTable){
    return usersService.findUser(usersTable);
  }
  @PostMapping
  public Long create(@RequestBody UsersEntity usersTable ){
    return usersService.createUsers(usersTable);
  }
  @DeleteMapping("/{id}")
  void delete(@PathVariable Long id){
    usersService.deleteUsers(id);
  }
  @PutMapping("/{id}")
  void update(@PathVariable Long id, @RequestBody UsersEntity user){
    usersService.updateUsers(id, user);
  }
}
