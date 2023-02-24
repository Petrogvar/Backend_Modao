package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.Event;
import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.Entity.User;
import com.SpringProject.core.Repository.EventRepository;
import com.SpringProject.core.Repository.GroupRepository;
import com.SpringProject.core.Repository.UserRepository;
import com.SpringProject.core.controllers.Error.NotFoundException;
import com.SpringProject.core.controllers.Error.NotRightException;
import java.util.Optional;
import liquibase.pro.packaged.G;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class СommonServiceImpl implements CommonService{

  private final UserRepository userRepository;

  private final GroupRepository groupRepository;

  private final EventRepository eventRepository;
  @Override
  public Boolean userInGroup(User user, Group group) {
    Boolean bool = false;
    int sizeGroup = group.getUserGroupList().size();
    for (int i=0; i<sizeGroup; i++){
      if (group.getUserGroupList().get(i).getUser().getId() == user.getId()) {
        bool = true;
        break;
      }
    }
    return bool;
  }
  @Override
  public Boolean userInGroupByLoginAndGroupId(String userLogin, Long groupId) {
    Optional<User> optionalUserCreator = userRepository.getByLogin(userLogin);
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (optionalGroup.isEmpty() || optionalUserCreator.isEmpty()){
      throw  new NotFoundException();
    }
    return userInGroup(optionalUserCreator.get() ,optionalGroup.get());
  }

  @Override
  public Boolean userInGroupByLoginAndEventId(String userLogin, Long eventId) {
    Optional<User> optionalUserCreator = userRepository.getByLogin(userLogin);
    Optional<Event> optionalEvent = eventRepository.findById(eventId);
    if (optionalEvent.isEmpty() || optionalUserCreator.isEmpty()){
      throw  new NotFoundException();
    }
    return userInGroup(optionalUserCreator.get() ,optionalEvent.get().getGroup());
  }

  @Override
  public Boolean userIsOrganizer(User user, Group group) {
    int sizeGroup = group.getUserGroupList().size();
    for (int i=0; i<sizeGroup; i++){
      if (group.getUserGroupList().get(i).getUser().getId() == user.getId()) {
        return  (group.getUserGroupList().get(i).getRole() == 1);
      }
    }
    return false; //
  }

  @Override
  public Boolean userIsOrganizerByLoginAndGroupId(String userLoginCreator, Long groupId) {
    Optional<User> optionalUserCreator = userRepository.getByLogin(userLoginCreator);
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (optionalGroup.isEmpty() || optionalUserCreator.isEmpty()){
      throw  new NotFoundException();
    }
    return userIsOrganizer(optionalUserCreator.get(), optionalGroup.get());
  }

  @Override
  public Boolean userIsOrganizerByLoginAndEventId(String userLoginCreator, Long eventId) {
    Optional<User> optionalUserCreator = userRepository.getByLogin(userLoginCreator);
    Optional<Event> optionalEvent = eventRepository.findById(eventId);
    if (optionalEvent.isEmpty() || optionalUserCreator.isEmpty()){
      throw  new NotFoundException();
    }
    return userIsOrganizer(optionalUserCreator.get(), optionalEvent.get().getGroup());
  }

  @Override
  public Boolean userHaveRightGetDebt(Long userId, Long groupId, String userLogin){
    Optional<User> optionalUser = userRepository.findById(userId);
    Optional<User> optionalUserCreator = userRepository.getByLogin(userLogin);
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (optionalGroup.isEmpty() || optionalUserCreator.isEmpty() || optionalUser.isEmpty())
      throw new NotFoundException();
    if (optionalUser.get().getId() != optionalUserCreator.get().getId() &&
        !(userInGroup(optionalUserCreator.get(), optionalGroup.get()) &&
            userIsOrganizer(optionalUserCreator.get(), optionalGroup.get()))) {
      throw new NotRightException();
    }
    return true;
  }
}
