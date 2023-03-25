package com.SpringProject.core.controllers;


import com.SpringProject.core.Services.Auth.AuthService;
import com.SpringProject.core.Services.GroupService;
import com.SpringProject.core.Services.h.CommonService;
import com.SpringProject.core.controllers.Error.NotRightException;
import com.SpringProject.core.dto.GroupDto;
import com.SpringProject.core.dto.UserDto;
import com.SpringProject.core.dto.domain.JwtAuthentication;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
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
  private final CommonService commonService;

  public GroupController(GroupService groupService, AuthService authService,
      CommonService commonService) {
    this.groupService = groupService;
    this.commonService = commonService;
  }


  @GetMapping("/info/{groupId}") //may ++
  public GroupDto getGroup(@PathVariable Long groupId) {
    Long userId = ((JwtAuthentication)SecurityContextHolder.getContext().getAuthentication()).getId();
    int role = commonService.getRoleInGroup(userId, groupId);
    return groupService.getGroup(groupId, role);
  }

  @PostMapping("/create")
  public Long createGroup(@RequestBody GroupDto groupDto) {
    Long userId = ((JwtAuthentication)SecurityContextHolder.getContext().getAuthentication()).getId();
   // System.out.println(((JwtAuthentication)SecurityContextHolder.getContext().getAuthentication()).getId());
    return groupService.createGroup(groupDto, userId);
  }

//  @DeleteMapping("/{groupId}") /// +++
//  void deleteGroup(@PathVariable Long groupId) {
//    groupService.deleteGroup(groupId);
//  }
//
//  @PutMapping("/{groupId}") /// ++++
//  void updateGroup(@PathVariable Long groupId, @RequestBody GroupDto groupDto) {
//    groupService.updateGroup(groupId, groupDto);
//  }

  @GetMapping("/listUsers/{groupId}")
  List<UserDto> getUsersInGroup(@PathVariable Long groupId){
    Long userId = ((JwtAuthentication)SecurityContextHolder.getContext().getAuthentication()).getId();
    return groupService.getUsersInGroup(groupId, userId);
  }

  @GetMapping("/listOrganizers/{groupId}")
  List<UserDto> getOrganizersInGroup(@PathVariable Long groupId){
    Long userId = ((JwtAuthentication)SecurityContextHolder.getContext().getAuthentication()).getId();
    if(!commonService.userInGroupByUserIdAndGroupId(userId, groupId))
      throw new NotRightException();
    return groupService.getOrganizersInGroup(groupId, userId);
  }


  @PutMapping("/addUserInGroup/{uuid}")
  void addUserInGroup(@PathVariable String uuid) {
    Long userId = ((JwtAuthentication)SecurityContextHolder.getContext().getAuthentication()).getId();
    groupService.addUserInGroupByUuid(userId, uuid);
  }

  @PutMapping("/getNewUuid/{groupId}")
  GroupDto getNewUuid(@PathVariable Long groupId) {
    Long userId = ((JwtAuthentication)SecurityContextHolder.getContext().getAuthentication()).getId();
    if(!commonService.userIsOrganizerByUserIdAndGroupId(userId, groupId)){
      throw new NotRightException();
    }
    return groupService.getNewUuid(groupId);
  }
}
