package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.Entity.UserGroup;
import com.SpringProject.core.Entity.User;
import com.SpringProject.core.Repository.GroupRepository;
import com.SpringProject.core.Repository.UserRepository;
import com.SpringProject.core.controllers.Error.ThereIsNoSuchUserException;
import com.SpringProject.core.dto.GroupDto;
import com.SpringProject.core.mapper.GroupMapperImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

  private final GroupRepository groupRepository;
  private final UserRepository usersRepository;

  @Override
  public GroupDto getGroup(Long id) {
    Optional<Group> optionalGroup = groupRepository.findById(id);
    if (optionalGroup.isEmpty()) {
      throw new ThereIsNoSuchUserException();
    } else {
      return GroupMapperImpl.toGroupDto(optionalGroup.get());
    }
  }


  @Override
  public Long createGroup(GroupDto groupDto, Long id) {
    Group group = GroupMapperImpl.toGroup(groupDto);
    Optional<User> optionalUser = usersRepository.findById(id);
    if (optionalUser.isEmpty()) {
      throw new ThereIsNoSuchUserException();
    } else {
      User user = optionalUser.get();
      UserGroup userGroup = new UserGroup();
      userGroup.setUser(user);
      userGroup.setGroup(group);
      userGroup.setRole(0);
      user.getGroup().add(userGroup);
      List listU = new ArrayList();
      group.setUser(listU);
      group.getUser().add(userGroup);
      return groupRepository.save(group).getId();
    }
  }

  @Override
  public void updateGroup(Long id, GroupDto groupDto) {
    Group group = groupRepository.findById(id).get();
    group.setDescription(groupDto.getDescription());
    group.setGroupName(groupDto.getGroupName());
    groupRepository.save(group);
  }

  @Override
  public void deleteGroup(Long id) {
    groupRepository.deleteById(id);
  }
}
