package com.SpringProject.core.mapper;

import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.dto.GroupDto;

public class GroupMapperImpl {
  public static GroupDto toGroupDto(Group group){
    GroupDto groupDto = new GroupDto();
    groupDto.setGroupName(group.getGroupName());
    groupDto.setTypeGroup(group.getTypeGroup());
    groupDto.setDescription(groupDto.getDescription());
    return groupDto;
  }

  public static Group toGroup(GroupDto groupDto){
    Group group =  new Group();
    group.setGroupName(groupDto.getGroupName());
    group.setTypeGroup(groupDto.getTypeGroup());
    group.setDescription(groupDto.getDescription());
    return group;
  }
}
