package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.GroupsEntity;
import com.SpringProject.core.Repository.GroupsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupsServiceImpl implements GroupsService{

  private final GroupsRepository groupsRepository;

  @Override
  public GroupsEntity getGroups(Long id){
    return groupsRepository.findById(id).get();
  }


  @Override
  public Long createGroups(GroupsEntity groupsTable) {
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
