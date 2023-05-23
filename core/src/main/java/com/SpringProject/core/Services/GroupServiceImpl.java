package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.Debt;
import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.Entity.UserGroup;
import com.SpringProject.core.Entity.User;
import com.SpringProject.core.Repository.DebtRepository;
import com.SpringProject.core.Repository.GroupRepository;
import com.SpringProject.core.Repository.InvitationInGroupRepository;
import com.SpringProject.core.Repository.UserGroupRepository;
import com.SpringProject.core.Repository.UserRepository;
import com.SpringProject.core.Services.h.CommonService;
import com.SpringProject.core.Services.h.DataVerification;
import com.SpringProject.core.Services.h.Uid;
import com.SpringProject.core.controllers.Error.Exception.BadRequestException;
import com.SpringProject.core.controllers.Error.Exception.NotFoundException;
import com.SpringProject.core.controllers.Error.Exception.NotRightException;
import com.SpringProject.core.dto.GroupDto;
import com.SpringProject.core.dto.UserDto;
import com.SpringProject.core.Mapper.GroupMapperImpl;
import com.SpringProject.core.Mapper.UserMapperImpl;
import java.sql.Timestamp;
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
  private final DebtRepository debtRepository;

  private final CommonService commonService;

  private final DataVerification dataVerification;
  private final InvitationInGroupRepository invitationRepository;
  private final UserGroupRepository userGroupRepository;

  @Override
  public GroupDto getGroup(Long groupId, int role) {
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (!optionalGroup.isPresent()) {
      throw new NotFoundException();
    }
    if (role == 1) {
      return GroupMapperImpl.toGroupDto(optionalGroup.get());
    }
    return GroupMapperImpl.toGroupDtoWithoutUuid(optionalGroup.get());
  }


  @Override
  public Long createGroup(GroupDto groupDto, Long userIdCreator) {

    dataVerification.group(groupDto);

    Group group = GroupMapperImpl.toGroup(groupDto);

    String uuid = Uid.getUuid();
    Optional<Group> optionalGroup = groupRepository.getByUuid(uuid);
    while (optionalGroup.isPresent()) {
      uuid = Uid.getUuid();
      optionalGroup = groupRepository.getByUuid(uuid);
    }
    group.setTypeGroup(0);
    group.setUuid(uuid);
    group.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    group.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    Optional<User> optionalUser = usersRepository.findById(userIdCreator);
    if (!optionalUser.isPresent()) {
      throw new NotFoundException();
    }

    User user = optionalUser.get();
    UserGroup userGroup = new UserGroup();
    userGroup.setUser(user);
    userGroup.setGroup(group);
    userGroup.setRole(1);
    user.getUserGroupsList().add(userGroup);
    group.setUserGroupList(new ArrayList<>());
    group.getUserGroupList().add(userGroup);

    return groupRepository.save(group).getId();
  }

  @Override
  public void updateGroup(Long groupId, GroupDto groupDto) {
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (!optionalGroup.isPresent()) {
      throw new NotFoundException();
    }

    dataVerification.group(groupDto);
    Group group = optionalGroup.get();
    group.setDescription(groupDto.getDescription());
    group.setGroupName(groupDto.getGroupName());
    groupRepository.save(group);
  }

  @Override
  public void deleteGroup(Long id) {
    groupRepository.deleteById(id);
  }

  @Override
  public List<UserDto> getUsersInGroup(Long groupId, Long userIdCreator) {
    Optional<User> optionalUserCreator = usersRepository.findById(userIdCreator);
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (!optionalGroup.isPresent() || !optionalUserCreator.isPresent()) {
      throw new NotFoundException();
    }
    if (!commonService.userInGroup(optionalUserCreator.get(), optionalGroup.get())) {
      throw new NotRightException();
    }
    List<UserDto> userDtoList = new ArrayList<>();
    for (UserGroup userGroup:optionalGroup.get().getUserGroupList()) {
      userDtoList.add(UserMapperImpl.toUserDtoWithoutUuid(userGroup.getUser()));
    }
    return userDtoList;
  }

  @Override
  public void addUserInGroupByUuid(Long userId, String uuid) {
    Optional<Group> optionalGroup = groupRepository.getByUuid(uuid);
    if (!optionalGroup.isPresent()) {
      throw new NotFoundException();
    }
    Optional<User> optionalUser = usersRepository.findById(userId);
    if (!optionalUser.isPresent()) {
      throw new NotFoundException();
    }
    if (commonService.userInGroup(optionalUser.get(), optionalGroup.get())) {
      throw new BadRequestException("пользователь уже в группе");
    }
    invitationRepository.deleteAllByUserAndGroupId(optionalUser.get(), optionalGroup.get().getId());
    for (UserGroup userGroup: optionalGroup.get().getUserGroupList()) {
      Debt debt = new Debt();
      Debt debtBack = new Debt();
      debtBack.setDebt(0D);
      debt.setDebt(0D);
      debtBack.setGroup(optionalGroup.get());
      debt.setGroup(optionalGroup.get());
      debtBack.setUserFrom(optionalUser.get());
      debtBack.setUserTo(userGroup.getUser());
      debt.setUserFrom(userGroup.getUser());
      debt.setUserTo(optionalUser.get());
      debtRepository.save(debt);
      debtRepository.save(debtBack);
    }
    UserGroup userGroup = new UserGroup();
    if (optionalGroup.get().getTypeGroup() == 1) {
      userGroup.setRole(1);
    } else {
      userGroup.setRole(0);
    }
    userGroup.setUser(optionalUser.get());
    userGroup.setGroup(optionalGroup.get());
    optionalUser.get().getUserGroupsList().add(userGroup);
    usersRepository.save(optionalUser.get());
  }

  @Override
  public List<UserDto> getOrganizersInGroup(Long groupId, Long userIdCreator) {
    Optional<User> optionalUserCreator = usersRepository.findById(userIdCreator);
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (!optionalGroup.isPresent() || !optionalUserCreator.isPresent()) {
      throw new NotFoundException();
    }
    List<UserDto> userDtoList = new ArrayList<>();
    for (UserGroup userGroup:optionalGroup.get().getUserGroupList()) {
      if (userGroup.getRole() == 1) {
        userDtoList.add(UserMapperImpl.toUserDtoWithoutUuid(userGroup.getUser()));
      }
    }
    return userDtoList;
  }

  @Override
  public GroupDto getNewUuid(Long groupId) {
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (!optionalGroup.isPresent()) {
      throw new NotFoundException();
    }
    String uuid = Uid.getUuid();
    Optional<Group> optionalGroupTemp = groupRepository.getByUuid(uuid);
    while (optionalGroupTemp.isPresent()) {
      uuid = Uid.getUuid();
      optionalGroupTemp = groupRepository.getByUuid(uuid);
    }
    optionalGroup.get().setUuid(uuid);
    return GroupMapperImpl.toGroupDto(optionalGroup.get());
  }

  @Override
  public void archiveGroup(Long groupId) {
    Optional<Group> optionalGroup =  groupRepository.findById(groupId);
    if(!optionalGroup.isPresent()){
      throw new NotFoundException();
    }
    optionalGroup.get().setTypeGroup(1);
    groupRepository.save(optionalGroup.get());
  }

  @Override
  public void archiveNoGroup(Long groupId) {
    Optional<Group> optionalGroup =  groupRepository.findById(groupId);
    if(!optionalGroup.isPresent()){
      throw new NotFoundException();
    }
    optionalGroup.get().setTypeGroup(0);
    groupRepository.save(optionalGroup.get());
  }

  @Override
  public void deleteUserInGroup(Long groupId, Long userId) {
    Optional<UserGroup> optionalUserGroup = userGroupRepository.findByUserIdAndGroupId(userId, groupId);
    if (!optionalUserGroup.isPresent())
      throw new NotFoundException();
    if (optionalUserGroup.get().getRole() == 1)
      throw new NotRightException();
    userGroupRepository.delete(optionalUserGroup.get());
  }


}

