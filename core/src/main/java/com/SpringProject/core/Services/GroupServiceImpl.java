package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.Debt;
import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.Entity.UserGroup;
import com.SpringProject.core.Entity.User;
import com.SpringProject.core.Repository.DebtRepository;
import com.SpringProject.core.Repository.GroupRepository;
import com.SpringProject.core.Repository.UserRepository;
import com.SpringProject.core.controllers.Error.NotFoundException;
import com.SpringProject.core.controllers.Error.NotRightException;
import com.SpringProject.core.dto.GroupDto;
import com.SpringProject.core.dto.UserDto;
import com.SpringProject.core.Mapper.GroupMapperImpl;
import com.SpringProject.core.Mapper.UserMapperImpl;
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

  @Override
  public GroupDto getGroup(Long groupId) {
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (optionalGroup.isEmpty()) {
      throw new NotFoundException();
    } else {
      return GroupMapperImpl.toGroupDto(optionalGroup.get());
    }
  }


  @Override
  public Long createGroup(GroupDto groupDto, String userLoginCreator) {
    Group group = GroupMapperImpl.toGroup(groupDto);
    Optional<User> optionalUser = usersRepository.getByLogin(userLoginCreator);
    if (optionalUser.isEmpty()) {
      throw new NotFoundException();
    } else {
      User user = optionalUser.get();
      UserGroup userGroup = new UserGroup();
      userGroup.setUser(user);
      userGroup.setGroup(group);
      userGroup.setRole(1);
      user.getUserGroupsList().add(userGroup);
      List listU = new ArrayList();
      group.setUserGroupList(listU);
      group.getUserGroupList().add(userGroup);
      return groupRepository.save(group).getId();
    }
  }

  @Override
  public void updateGroup(Long groupId, GroupDto groupDto) {
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (optionalGroup.isEmpty()) {
      throw new NotFoundException();
    } else {
      Group group = optionalGroup.get();
      group.setDescription(groupDto.getDescription());
      group.setGroupName(groupDto.getGroupName());
      groupRepository.save(group);
    }
  }

  @Override
  public void deleteGroup(Long id) {
    groupRepository.deleteById(id);
  }

  @Override
  public List<UserDto> getUsersInGroup(Long groupId, String userLoginCreator) {
    Optional<User> optionalUserCreator = usersRepository.getByLogin(userLoginCreator);
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (optionalGroup.isEmpty() || optionalUserCreator.isEmpty())
      throw new NotFoundException();
    if (!commonService.userInGroup(optionalUserCreator.get(), optionalGroup.get()))
      throw new NotRightException();
    List<UserDto> userDtoList = new ArrayList<>();
    int groupSize = optionalGroup.get().getUserGroupList().size();
    for (int i =0 ; i < groupSize; i++){
      userDtoList.add(UserMapperImpl.toUserDtoWithoutPasswordAndLogin(
          optionalGroup.get().getUserGroupList().get(i).getUser()));
    }
    return userDtoList;
  }

  @Override
  public void addUserInGroup(Long userOrgId, Long groupId, Long userId) {
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (optionalGroup.isEmpty()) {
      throw new NotFoundException();
    } else {
      Optional<User> optionalUser = usersRepository.findById(userId);
      if (optionalUser.isEmpty()) {
        throw new NotFoundException();
      } else {
        for(int i=0; i<optionalGroup.get().getUserGroupList().size(); i++) {
          Debt debt = new Debt();
          Debt debtBack = new Debt();
          debtBack.setDebt(0D);
          debt.setDebt(0D);
          debtBack.setGroup(optionalGroup.get());
          debt.setGroup(optionalGroup.get());
          debtBack.setUserFrom(optionalUser.get());
          debtBack.setUserTo(optionalGroup.get().getUserGroupList().get(i).getUser());
          debt.setUserFrom(optionalGroup.get().getUserGroupList().get(i).getUser());
          debt.setUserTo(optionalUser.get());
//          optionalUser.get().getDebtList().add(debt);
          debtRepository.save(debt);
          debtRepository.save(debtBack);
        }
        UserGroup userGroup =  new UserGroup();
        userGroup.setRole(0);
        userGroup.setUser(optionalUser.get());
        userGroup.setGroup(optionalGroup.get());
        optionalUser.get().getUserGroupsList().add(userGroup);
        usersRepository.save(optionalUser.get());
      }
    }
  }

  @Override
  public List<UserDto> getOrganizersInGroup(Long groupId, String userLoginCreator) {
    Optional<User> optionalUserCreator = usersRepository.getByLogin(userLoginCreator);
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (optionalGroup.isEmpty() || optionalUserCreator.isEmpty())
      throw new NotFoundException();
    if (!commonService.userInGroup(optionalUserCreator.get(), optionalGroup.get())
        || !commonService.userIsOrganizer(optionalUserCreator.get(), optionalGroup.get()))
      throw new NotRightException();
    List<UserDto> userDtoList = new ArrayList<>();
    int groupSize = optionalGroup.get().getUserGroupList().size();
    for (int i =0 ; i < groupSize; i++){
      if(optionalGroup.get().getUserGroupList().get(i).getRole() == 1) {
        userDtoList.add(UserMapperImpl.toUserDtoWithoutPasswordAndLogin(
            optionalGroup.get().getUserGroupList().get(i).getUser()));
      }
    }
    return userDtoList;
  }
}

