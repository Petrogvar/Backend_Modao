package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.Entity.User;

public interface CommonService {
  public Boolean userInGroupByLoginAndGroupId(String userLogin, Long groupId);
  Boolean userInGroup(User user, Group group);

  Boolean userInGroupByLoginAndEventId(String userLogin, Long eventId);
  Boolean userIsOrganizer(User user, Group group);

  Boolean userIsOrganizerByLoginAndGroupId (String userLoginCreator, Long groupId);
  Boolean userIsOrganizerByLoginAndEventId(String userLoginCreator, Long eventId);
  Boolean userHaveRightGetDebt(Long userId, Long groupId, String userLogin);
}
