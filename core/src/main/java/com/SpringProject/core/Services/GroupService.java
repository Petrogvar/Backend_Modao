package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.dto.GroupDto;

public interface GroupService {

  GroupDto getGroup(Long id);

  Long createGroup(GroupDto groupDto, Long id);

  void deleteGroup(Long id);

  void updateGroup(Long id, GroupDto groupDto);

}
