package com.SpringProject.core.Services;
/*
import com.SpringProject.core.Entity.Event;
import com.SpringProject.core.Entity.GroupsEntity;
import com.SpringProject.core.Entity.UserEvent;
import com.SpringProject.core.Entity.UsersEntity;
import com.SpringProject.core.Repository.EventRepository;
import com.SpringProject.core.Repository.GroupsRepository;
import com.SpringProject.core.Repository.UsersRepository;
import com.SpringProject.core.dto.EventDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
  private final GroupsRepository groupsRepository;
  private final UsersRepository usersRepository;
  private final EventRepository eventRepository;

  @Override
  public Long createEvent(EventDto eventDto, Long user_id) {
    //if (1==1)
      //return eventDto.getParticipants_id().get(1);
    List<Long> listUser = eventDto.getParticipants_id();
    UsersEntity user;
    UserEvent UE;
    Event event = new Event();
    event.setEvent_name(event.getEvent_name());
    //return -1L;
    event.setPat_pay_id(user_id);
    event.setUser(new ArrayList<>());
    GroupsEntity group = groupsRepository.findById(eventDto.getGroup_id()).get();
    event.setGroup(group);
    //return -1L;
    for(int i=0; i<listUser.size(); i++){
      user = usersRepository.findById(listUser.get(i)).get();
      UE = new UserEvent();
      if (user == null)
        return -2L;
      UE.setK(eventDto.getParticipants_k().get(i));
      user.getEvent().add(UE);
      event.getUser().add(UE);
    }
    eventRepository.flush();
    //eventRepository.save(event);
    return  -1L;
    //return eventRepository.save(event).getId();
  }


}*/
