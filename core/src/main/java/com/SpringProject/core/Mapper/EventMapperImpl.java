package com.SpringProject.core.Mapper;

import com.SpringProject.core.Entity.Event;
import com.SpringProject.core.dto.EventDto;
import java.util.ArrayList;
import java.util.List;

public class EventMapperImpl {
  public static EventDto toEventDto(Event event){
    EventDto eventDto = new EventDto();
    eventDto.setId(event.getId());
    eventDto.setName(event.getEventName());
    eventDto.setPrice(event.getPrice());
    eventDto.setUsernamePaying(event.getUsernamePaying());
    eventDto.setUserPayingId(event.getUserPayingId());
    eventDto.setUserCreatorId(event.getUserCreatorId());
    eventDto.setUsernameCreator(event.getUsernameCreator());
    eventDto.setType(eventDto.getType());
    eventDto.setDescription(eventDto.getDescription());
//    eventDto.setGroupId(event.getGroup().getId());
    return eventDto;
  }

  public static List<EventDto> toEventDtoList(List<Event> eventList){
    List<EventDto> eventDtoList = new ArrayList<>();
    for (int i=0; i < eventList.size(); i++ ){
      eventDtoList.add(toEventDto(eventList.get(i)));
    }
    return eventDtoList;
  }
}
