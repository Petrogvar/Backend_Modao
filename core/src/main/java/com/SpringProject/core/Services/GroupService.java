package com.SpringProject.core.Services;

import com.SpringProject.core.dto.GroupDto;
import org.springframework.web.bind.annotation.PathVariable;

public interface GroupService {

  GroupDto getGroup(Long id);

  Long createGroup(GroupDto groupDto, Long id);

  void deleteGroup(Long id);

  void updateGroup(Long id, GroupDto groupDto);
  void addUserInGroup(Long userOrgId, Long groupId, Long userId);
}
