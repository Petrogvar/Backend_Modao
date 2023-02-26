package com.SpringProject.core.controllers;


import com.SpringProject.core.Services.Auth.AuthService;
import com.SpringProject.core.Services.GroupService;
import com.SpringProject.core.dto.GroupDto;
import com.SpringProject.core.dto.UserDto;
import com.SpringProject.core.dto.domain.JwtAuthentication;
import java.security.Security;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
public class GroupController {

  private final GroupService groupService;

  public GroupController(GroupService groupService, AuthService authService) {
    this.groupService = groupService;
  }


  @GetMapping("/info/{groupId}") //may ++
  public GroupDto getGroup(@PathVariable Long groupId) {
    return groupService.getGroup(groupId);
  }

  @PostMapping("/create")
  public Long createGroup(@RequestBody GroupDto groupDto) {
    String userLoginCreator = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    System.out.println(userLoginCreator);
    return groupService.createGroup(groupDto, userLoginCreator);
  }
  @DeleteMapping("/{groupId}") /// +++
  void deleteGroup(@PathVariable Long groupId) {
    groupService.deleteGroup(groupId);
  }

  @PutMapping("/{groupId}") /// ++++
  void updateGroup(@PathVariable Long groupId, @RequestBody GroupDto groupDto) {
    groupService.updateGroup(groupId, groupDto);
  }

  @GetMapping("/listUsers/{groupId}")
  List<UserDto> getUsersInGroup(@PathVariable Long groupId){
    String userLoginCreator = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    return groupService.getUsersInGroup(groupId, userLoginCreator);
  }

  @PutMapping("/addUserInGroup{userOrgId}/{groupId}/{userId}") ///++++
  void addUserInGroup(@PathVariable Long userOrgId, @PathVariable Long groupId, @PathVariable Long userId) {
    groupService.addUserInGroup(userOrgId, groupId, userId);
  }

}
