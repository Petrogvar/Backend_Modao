package com.SpringProject.core.Services.h;

import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.Entity.User;
import com.SpringProject.core.Entity.UserGroup;
import com.SpringProject.core.Repository.UserGroupRepository;
import com.SpringProject.core.Repository.UserRepository;
import com.SpringProject.core.controllers.Error.NotFoundException;
import com.SpringProject.core.controllers.Error.NotRightException;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class СommonServiceImpl implements CommonService {

  private final UserGroupRepository userGroupRepository;
  private final UserRepository userRepository;

  @Override
  public Boolean userInGroup(User user, Group group) {
    boolean bool = false;
    for (UserGroup userGroup: group.getUserGroupList()) {
      if (Objects.equals(userGroup.getUser().getId(), user.getId())) {
        bool = true;
        break;
      }
    }
    return bool;
  }

  @Override
  public Boolean usersIsFriend(Long userId1, Long userId2) {
    Optional<User> optionalUser = userRepository.findById(userId1);
    if(!optionalUser.isPresent())
      throw new NotFoundException();
    for(User friend: optionalUser.get().getFriends()){
      System.out.println(friend.getId());
      if (Objects.equals(friend.getId(), userId2))
        return true;
    }
    return false;
  }

  @Override
  public Boolean userInGroupByUserIdAndGroupId(Long userId, Long groupId) {
    return userGroupRepository.findByUserIdAndGroupId(userId, groupId).isPresent();
  }

  @Override
  public Integer getRoleInGroup(Long userId, Long groupId) {
    Optional<UserGroup> optionalUserGroup = userGroupRepository.findByUserIdAndGroupId(userId,
        groupId);
    if (!optionalUserGroup.isPresent()) {
      throw new NotRightException();
    }
    return optionalUserGroup.get().getRole();
  }

  @Override
  public Boolean userIsOrganizer(User user, Group group) {
    for (UserGroup userGroup : group.getUserGroupList()) {
      if (Objects.equals(userGroup.getUser().getId(), user.getId())) {
        return (userGroup.getRole() == 1);
      }
    }
    return false; //
  }

  @Override
  public Boolean userIsOrganizerByUserIdAndGroupId(Long userId, Long groupId) {
    Optional<UserGroup> optionalUserGroup = userGroupRepository.findByUserIdAndGroupId(userId,
        groupId);
    if (!optionalUserGroup.isPresent()) {
      throw new NotRightException();
    }
    return optionalUserGroup.get().getRole() == 1;
  }

  @Override
  public Boolean userHaveRightGetDebt(Long userId, Long groupId, Long userIdCreator) {
    return (Objects.equals(userIdCreator, userId)) || userIsOrganizerByUserIdAndGroupId(userIdCreator, groupId);
  }

}
