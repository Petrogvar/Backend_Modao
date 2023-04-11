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
    eventDto.setType(event.getType());
    eventDto.setDescription(event.getDescription());
    eventDto.setStatus(event.getStatus());
    eventDto.setTime(event.getCreatedAt().toLocalDateTime());
    return eventDto;
  }

  public static List<EventDto> toEventDtoList(List<Event> eventList){
    List<EventDto> eventDtoList = new ArrayList<>();
    for (Event event : eventList) {
      eventDtoList.add(toEventDto(event));
    }
    return eventDtoList;
  }
}
