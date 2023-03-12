package com.SpringProject.core.Services;

import java.util.List;
import com.SpringProject.core.dto.GroupDto;
import com.SpringProject.core.dto.UserDto;
import org.springframework.web.bind.annotation.PathVariable;

public interface GroupService {

  GroupDto getGroup(Long groupId);

  Long createGroup(GroupDto groupDto, String userLoginCreator);

  void deleteGroup(Long groupId);

  List<UserDto> getUsersInGroup(Long groupId, String userLoginCreator);

  void updateGroup(Long groupId, GroupDto groupDto);
  void addUserInGroup(Long userOrgId, Long groupId, Long userId);

  List<UserDto> getOrganizersInGroup(Long groupId, String userLoginCreator);
}
