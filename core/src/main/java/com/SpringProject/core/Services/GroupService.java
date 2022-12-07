package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.dto.GroupDto;

public interface GroupService {
  GroupDto getGroups(Long id);
  Long createGroups(GroupDto groupDto, Long id);
  void deleteGroups(Long id);
  void updateGroups(Long id, GroupDto groupDto);

}
