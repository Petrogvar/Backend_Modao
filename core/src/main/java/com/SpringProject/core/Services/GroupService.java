package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.Group;

public interface GroupService {
  Group getGroups(Long id);
  Long createGroups(Group groupsTable, Long id);
  void deleteGroups(Long id);
  void updateGroups(Long id, Group groupsTable);

}
