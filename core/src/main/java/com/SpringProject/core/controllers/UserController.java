package com.SpringProject.core.controllers;

import com.SpringProject.core.Services.UserService;
import com.SpringProject.core.Services.h.CommonService;
import com.SpringProject.core.controllers.Error.Exception.NotRightException;
import com.SpringProject.core.dto.GroupDto;
import com.SpringProject.core.dto.UserDto;
import com.SpringProject.core.dto.domain.JwtAuthentication;
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
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  private final CommonService commonService;

  public UserController(UserService userService, CommonService commonService) {
    this.userService = userService;
    this.commonService = commonService;
  }


  @GetMapping("/myInfo")
  public UserDto getMyInfo() {
    Long userIdCreator = ((JwtAuthentication) SecurityContextHolder.getContext()
        .getAuthentication()).getId();
    return userService.getUserMyInfo(userIdCreator);
  }

  @GetMapping("/InfoByGroup/{groupId}/{userId}")
  public UserDto getInfoByGroup(@PathVariable Long groupId, @PathVariable Long userId) {
    Long userIdCreator = ((JwtAuthentication) SecurityContextHolder.getContext()
        .getAuthentication()).getId();
    if (!commonService.userInGroupByUserIdAndGroupId(userId, groupId) ||
        !commonService.userInGroupByUserIdAndGroupId(userIdCreator, groupId)) {
      throw new NotRightException();
    }
    return userService.getUser(userIdCreator);
  }

  @GetMapping("/InfoFriend/{userId}")
  public UserDto getInfoFriend(@PathVariable Long userId) {
    Long userIdCreator = ((JwtAuthentication) SecurityContextHolder.getContext()
        .getAuthentication()).getId();
    if (!commonService.usersIsFriend(userIdCreator, userId)) {
      throw new NotRightException();
    }
    return userService.getUser(userId);
  }

  @GetMapping("/listGroups/{type}")
  public List<GroupDto> getGroups(@PathVariable Integer type) {
    Long userIdCreator = ((JwtAuthentication) SecurityContextHolder.getContext()
        .getAuthentication()).getId();
    return userService.getGroups(userIdCreator, type);
  }

  @PutMapping("/getNewUuid")
  UserDto getNewUuid() {
    Long userId = ((JwtAuthentication) SecurityContextHolder.getContext()
        .getAuthentication()).getId();
    return userService.getNewUuid(userId);
  }

  @GetMapping("/exitUser")
  public void exitUser() {
    Long userIdCreator = ((JwtAuthentication) SecurityContextHolder.getContext()
        .getAuthentication()).getId();
    userService.exitUser(userIdCreator);
  }

  @GetMapping("/listFriends")
  List<UserDto> getListFriends() {
    Long userId = ((JwtAuthentication) SecurityContextHolder.getContext()
        .getAuthentication()).getId();
    return userService.getListFriends(userId);
  }


  @PostMapping("/reg")
  public Long create(@RequestBody UserDto userDto) {
    return userService.createUser(userDto);
  }

  @PutMapping("/update")
  void update(@RequestBody UserDto userDto) {
    Long userId = ((JwtAuthentication) SecurityContextHolder.getContext()
        .getAuthentication()).getId();
    userService.updateUser(userId, userDto);
  }

}
