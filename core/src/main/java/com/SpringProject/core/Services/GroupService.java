package com.SpringProject.core.Services;

import com.SpringProject.core.dto.GroupDto;
import org.springframework.web.bind.annotation.PathVariable;

public interface GroupService {

  GroupDto getGroup(Long groupId);

  Long createGroup(GroupDto groupDto, Long userId);

  void deleteGroup(Long groupId);

  void updateGroup(Long groupId, GroupDto groupDto);
  void addUserInGroup(Long userOrgId, Long groupId, Long userId);
}
