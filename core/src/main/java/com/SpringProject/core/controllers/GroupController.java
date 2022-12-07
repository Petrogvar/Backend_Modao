package com.SpringProject.core.controllers;


import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.Services.GroupService;
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
  public Group getGroups(@PathVariable Long id) {
    return groupService.getGroups(id);
  }

  @PostMapping({"/{id}"})
  public Long createGroups(@RequestBody Group groupsTable, @PathVariable Long id){
    return groupService.createGroups(groupsTable, id);
  }

  @DeleteMapping("/{id}")
  void deleteGroups(@PathVariable Long id){
    groupService.deleteGroups(id);
  }

  @PutMapping("/{id}")
  void updateGroups(@PathVariable Long id, @RequestBody Group groupsTable){
    groupService.updateGroups(id, groupsTable);
  }
}
