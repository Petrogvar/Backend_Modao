package com.SpringProject.core.controllers;

import com.SpringProject.core.Services.InvitationService;
import com.SpringProject.core.Services.h.CommonService;
import com.SpringProject.core.controllers.Error.NotRightException;
import com.SpringProject.core.dto.InvitationFriendDto;
import com.SpringProject.core.dto.InvitationInGroupDto;
import com.SpringProject.core.dto.domain.JwtAuthentication;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invitation")
public class InvitationController {
private final InvitationService invitationService;
private final CommonService commonService;

  public InvitationController(InvitationService invitationService, CommonService commonService) {
    this.invitationService = invitationService;
    this.commonService = commonService;
  }

  @PostMapping("/createInvitationFriend/{uuid}")
  void createInvitationFriend(@PathVariable String uuid){
    Long userIdCreator = ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication()).getId();
    invitationService.createInvitationFriend(userIdCreator, uuid);
  }

  @PostMapping("/createInvitationFriendByGroup/{groupId}/{userId}")
  void createInvitationFriendByGroup(@PathVariable Long groupId, @PathVariable Long userId){
    Long userIdCreator = ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication()).getId();
    invitationService.createInvitationFriendByGroup(userIdCreator, groupId, userId);
  }

  @PostMapping("/declineInvitationFriend/{invitationId}")
  void declineInvitationFriend(@PathVariable Long invitationId){
    Long userIdCreator = ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication()).getId();
    invitationService.declineInvitationFriend(userIdCreator, invitationId);
  }

  @PostMapping("/acceptInvitationFriend/{invitationId}")
  void acceptInvitationFriend(@PathVariable Long invitationId){
    Long userIdCreator = ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication()).getId();
    invitationService.acceptInvitationFriend(userIdCreator, invitationId);
  }

  @PostMapping("/createInvitationInGroup/{groupId}/{userIdFriends}")
  void createInvitationInGroupByFriends(@PathVariable Long groupId, @PathVariable Long userIdFriends){
    Long userIdCreator = ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication()).getId();
    if(!commonService.userInGroupByUserIdAndGroupId(userIdCreator, groupId))
      throw new NotRightException();
    invitationService.createInvitationInGroupByFriends(userIdCreator, groupId, userIdFriends);
  }

  @PostMapping("/declineInvitationInGroup/{invitationId}")
  void declineInvitationInGroup(@PathVariable Long invitationId){
    Long userIdCreator = ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication()).getId();
    invitationService.declineInvitationInGroup(userIdCreator, invitationId);
  }

  @PostMapping("/acceptInvitationInGroup/{invitationId}")
  void acceptInvitationInGroup(@PathVariable Long invitationId){
    Long userIdCreator = ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication()).getId();
    invitationService.acceptInvitationInGroup(userIdCreator, invitationId);
  }


  @GetMapping("/getInvitationsInGroup")
  List<InvitationInGroupDto> getInvitationsInGroup(){
    Long userIdCreator = ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication()).getId();
    return invitationService.getInvitationsInGroup(userIdCreator);
  }


  @GetMapping("/getInvitationsFriend")
  List<InvitationFriendDto> getInvitationsFriend(){
    Long userIdCreator = ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication()).getId();
    return invitationService.getInvitationsFriend(userIdCreator);
  }
}
