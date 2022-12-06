package com.SpringProject.core.controllers;


import com.SpringProject.core.Entity.GroupsEntity;
import com.SpringProject.core.Services.GroupsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
public class GroupsController {
  private final GroupsService groupsService;

  public GroupsController(GroupsService groupsService) {
    this.groupsService = groupsService;
  }

  @GetMapping("/{id}")
  public GroupsEntity getGroups(@PathVariable Long id) {
    return groupsService.getGroups(id);
  }

  @PostMapping({"/{id}"})
  public Long createGroups(@RequestBody GroupsEntity groupsTable, @PathVariable Long id){
    return groupsService.createGroups(groupsTable, id);
  }

  @DeleteMapping("/{id}")
  void deleteGroups(@PathVariable Long id){
    groupsService.deleteGroups(id);
  }

  @PutMapping("/{id}")
  void updateGroups(@PathVariable Long id, @RequestBody GroupsEntity groupsTable){
    groupsService.updateGroups(id, groupsTable);
  }
}
