package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.GroupsEntity;
import com.SpringProject.core.Entity.UserGroup;
import com.SpringProject.core.Entity.UsersEntity;
import com.SpringProject.core.Repository.GroupsRepository;
import com.SpringProject.core.Repository.UsersRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupsServiceImpl implements GroupsService{

  private final GroupsRepository groupsRepository;
  private final UsersRepository usersRepository;

  @Override
  public GroupsEntity getGroups(Long id){
    return groupsRepository.findById(id).get();
  }


  @Override
  public Long createGroups(GroupsEntity groupsTable, Long id) {
    UsersEntity a = usersRepository.findById(id).get();
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
    return groupsRepository.save(groupsTable).getId();
  }

  @Override
  public void updateGroups(Long id, GroupsEntity groupsTable) {
    GroupsEntity old = groupsRepository.findById(id).get();
    old.setType_group(groupsTable.getType_group());
    groupsRepository.save(old);
  }

  @Override
  public void deleteGroups(Long id) {
    groupsRepository.deleteById(id);
  }
}
