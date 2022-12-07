package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.Entity.UserGroup;
import com.SpringProject.core.Entity.User;
import com.SpringProject.core.Repository.GroupRepository;
import com.SpringProject.core.Repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

  private final GroupRepository groupRepository;
  private final UserRepository usersRepository;

  @Override
  public Group getGroups(Long id){
    return groupRepository.findById(id).get();
  }


  @Override
  public Long createGroups(Group groupsTable, Long id) {
    User a = usersRepository.findById(id).get();
    if (a==null)
      return -1L;
    UserGroup ug = new UserGroup();
    ug.setUser(a);
    ug.setGroup(groupsTable);
    ug.setRole(0);
    a.getGroup().add(ug);
    List listU = new ArrayList();
    groupsTable.setUser(listU);
    groupsTable.getUser().add(ug);
    return groupRepository.save(groupsTable).getId();
  }

  @Override
  public void updateGroups(Long id, Group groupsTable) {
    Group old = groupRepository.findById(id).get();
    old.setTypeGroup(groupsTable.getTypeGroup());
    groupRepository.save(old);
  }

  @Override
  public void deleteGroups(Long id) {
    groupRepository.deleteById(id);
  }
}
