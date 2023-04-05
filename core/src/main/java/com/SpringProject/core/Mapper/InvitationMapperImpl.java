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
    int size = invitationInGroupList.size();
    List<InvitationInGroupDto> invitationInGroupDtoList = new ArrayList<>();
    for(int i = 0; i<size; i++){
      invitationInGroupDtoList.add(toGroupDto(invitationInGroupList.get(i)));
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
    int size = invitationFriendList.size();
    List<InvitationFriendDto> invitationFriendDtoList = new ArrayList<>();
    for(int i = 0; i<size; i++){
      invitationFriendDtoList.add(toFriendDto(invitationFriendList.get(i)));
    }
    return invitationFriendDtoList;
  }
}
