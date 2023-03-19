package com.SpringProject.core.Mapper;

import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.dto.GroupDto;

public class GroupMapperImpl {

  public static GroupDto toGroupDto(Group group) {
    GroupDto groupDto = new GroupDto();
    groupDto.setId(group.getId());
    groupDto.setGroupName(group.getGroupName());
    groupDto.setTypeGroup(group.getTypeGroup());
    groupDto.setDescription(group.getDescription());
    return groupDto;
  }

  public static Group toGroup(GroupDto groupDto) {
    Group group = new Group();
    //group.setId(groupDto.getId());
    group.setGroupName(groupDto.getGroupName());
    group.setTypeGroup(groupDto.getTypeGroup());
    group.setDescription(groupDto.getDescription());
    return group;
  }
}
