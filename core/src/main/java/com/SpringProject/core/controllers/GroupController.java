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

  @GetMapping("/{id}")
  public GroupDto getGroup(@PathVariable Long id) {
    return groupService.getGroup(id);
  }

  @PostMapping({"/{id}"})
  public Long createGroup(@RequestBody GroupDto groupDto, @PathVariable Long id) {
    return groupService.createGroup(groupDto, id);
  }
  @DeleteMapping("/{id}")
  void deleteGroup(@PathVariable Long id) {
    groupService.deleteGroup(id);
  }

  @PutMapping("/{id}")
  void updateGroup(@PathVariable Long id, @RequestBody GroupDto groupDto) {
    groupService.updateGroup(id, groupDto);
  }

  @PutMapping("/{userOrgId}/{groupId}/{userId}")
  void addUserInGroup(@PathVariable Long userOrgId, @PathVariable Long groupId, @PathVariable Long userId) {
    groupService.addUserInGroup(userOrgId, groupId, userId);
  }
}
