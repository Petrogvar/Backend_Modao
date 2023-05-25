package com.SpringProject.core.Mapper;

import com.SpringProject.core.Entity.Event;
import com.SpringProject.core.dto.EventDto;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;


public class EventMapperImpl  {
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
    eventDto.setDeleteId(eventDto.getDeleteId());
    return eventDto;
  }

  public static List<EventDto> toEventDtoList(List<Event> eventList){
    List<EventDto> eventDtoList = new ArrayList<>();
    for (Event event : eventList) {
      eventDtoList.add(toEventDto(event));
    }
    return eventDtoList;
  }
  public static Page<EventDto> fromEntityPage(Page<Event> entityPage) {
    List<EventDto> dtoList = entityPage.getContent().stream()
        .map(entity -> new EventDto(entity.getId(), entity.getEventName(),
            entity.getDescription(), entity.getPrice(), entity.getType(),
            entity.getCreatedAt().toLocalDateTime(), entity.getDeleteId(), entity.getStatus()) )
        .collect(Collectors.toList());
    return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
  }
}
