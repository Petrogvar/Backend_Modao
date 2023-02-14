package com.SpringProject.core.controllers;


import com.SpringProject.core.Services.GroupService;
import com.SpringProject.core.dto.GroupDto;
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


  public GroupController(GroupService groupService) {
    this.groupService = groupService;
  }

  @GetMapping("/{groupId}")
  public GroupDto getGroup(@PathVariable Long groupId) {
    return groupService.getGroup(groupId);
  }

  @PostMapping({"/{userId}"})
  public Long createGroup(@RequestBody GroupDto groupDto, @PathVariable Long userId) {
    return groupService.createGroup(groupDto, userId);
  }
  @DeleteMapping("/{groupId}")
  void deleteGroup(@PathVariable Long groupId) {
    groupService.deleteGroup(groupId);
  }

  @PutMapping("/{groupId}")
  void updateGroup(@PathVariable Long groupId, @RequestBody GroupDto groupDto) {
    groupService.updateGroup(groupId, groupDto);
  }

  @PutMapping("/{userOrgId}/{groupId}/{userId}")
  void addUserInGroup(@PathVariable Long userOrgId, @PathVariable Long groupId, @PathVariable Long userId) {
    groupService.addUserInGroup(userOrgId, groupId, userId);
  }
}
