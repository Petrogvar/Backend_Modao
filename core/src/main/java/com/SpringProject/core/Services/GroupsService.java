package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.GroupsEntity;

public interface GroupsService {
  GroupsEntity getGroups(Long id);
  Long createGroups(GroupsEntity groupsTable);
  void deleteGroups(Long id);
  void updateGroups(Long id, GroupsEntity groupsTable);

}
