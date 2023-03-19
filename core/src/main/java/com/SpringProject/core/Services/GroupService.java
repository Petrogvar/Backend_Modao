package com.SpringProject.core.Services;

import java.util.List;
import com.SpringProject.core.dto.GroupDto;
import com.SpringProject.core.dto.UserDto;
import org.springframework.web.bind.annotation.PathVariable;

public interface GroupService {

  GroupDto getGroup(Long groupId, int role);

  Long createGroup(GroupDto groupDto, Long userId);

  void deleteGroup(Long groupId);

  List<UserDto> getUsersInGroup(Long groupId, Long userIdCreator);
  void updateGroup(Long groupId, GroupDto groupDto);
  void addUserInGroupByUuid(Long user, String uuid);

  List<UserDto> getOrganizersInGroup(Long groupId, Long userIdCreator);
}
