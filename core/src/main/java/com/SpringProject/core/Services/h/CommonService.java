package com.SpringProject.core.Services.h;

import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.Entity.User;


public interface CommonService {
  Boolean userInGroupByUserIdAndGroupId(Long userId, Long groupId);

  Integer getRoleInGroup(Long userId, Long groupId);

  Boolean userInGroup(User user, Group group);

  Boolean usersIsFriend(Long userId1, Long userId2);
  Boolean userIsOrganizer(User user, Group group);

  Boolean userIsOrganizerByUserIdAndGroupId (Long userId, Long groupId);


  Boolean userHaveRightGetDebt(Long userId, Long groupId, Long userUserIdOrg);

}
