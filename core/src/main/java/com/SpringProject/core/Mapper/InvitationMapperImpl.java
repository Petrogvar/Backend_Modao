package com.SpringProject.core.Mapper;

import com.SpringProject.core.Entity.InvitationFriend;
import com.SpringProject.core.Entity.InvitationInGroup;
import com.SpringProject.core.dto.InvitationFriendDto;
import com.SpringProject.core.dto.InvitationInGroupDto;
import java.util.ArrayList;
import java.util.List;

public class InvitationMapperImpl {
  public static InvitationInGroupDto toGroupDto(InvitationInGroup invitationInGroup){
    InvitationInGroupDto invitationInGroupDto = new InvitationInGroupDto();
    invitationInGroupDto.setId(invitationInGroup.getId());
    invitationInGroupDto.setNameGroup(invitationInGroup.getNameGroup());
    invitationInGroupDto.setGroupId(invitationInGroup.getGroupId());
    invitationInGroupDto.setUsername(invitationInGroup.getUsername());
    invitationInGroupDto.setUserId(invitationInGroup.getUserId());
    return invitationInGroupDto;
  }

  public static List<InvitationInGroupDto> toGroupDtoList(List<InvitationInGroup> invitationInGroupList){
    List<InvitationInGroupDto> invitationInGroupDtoList = new ArrayList<>();
    for (InvitationInGroup invitationInGroup : invitationInGroupList) {
      invitationInGroupDtoList.add(toGroupDto(invitationInGroup));
    }
    return invitationInGroupDtoList;
  }
  public static InvitationFriendDto toFriendDto(InvitationFriend invitationFriend){
    InvitationFriendDto invitationFriendDto = new InvitationFriendDto();
    invitationFriendDto.setId(invitationFriend.getId());
    invitationFriendDto.setUsername(invitationFriend.getUsername());
    invitationFriendDto.setUserId(invitationFriend.getUserId());
    return invitationFriendDto;
  }

  public static List<InvitationFriendDto> toFriendDtoList(List<InvitationFriend> invitationFriendList){
    List<InvitationFriendDto> invitationFriendDtoList = new ArrayList<>();
    for (InvitationFriend invitationFriend : invitationFriendList) {
      invitationFriendDtoList.add(toFriendDto(invitationFriend));
    }
    return invitationFriendDtoList;
  }
}
