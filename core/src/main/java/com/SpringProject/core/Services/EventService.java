package com.SpringProject.core.Services;

import com.SpringProject.core.dto.DescriptionDto;
import com.SpringProject.core.dto.EventDto;
import java.util.List;
import org.springframework.data.domain.Page;

public interface EventService {
  Long createEvent(EventDto eventDto, Long userIdCreator);
  void confirmationEvent(Long userId, Long groupId,  Long eventId);
  Page<EventDto> getСonfirmedEventMod0List(Long groupId, int type, Integer offset, Integer limit,
   Long time1, Long time2 );
  EventDto getEvent(Long groupId, Long eventId);

  List<EventDto> getUnconfirmedEventList(Long groupId);

  void unconfirmationEvent(Long userId, Long groupId,  Long eventId);

  Long deleteEvent(DescriptionDto descriptionDto, Long userIdCreator, Long groupId, Long eventId);

  Page<EventDto> getСonfirmedEventMod1List(Long groupId, Long userId, int type, Integer offset,
      Integer limit, Long time1, Long time2 );
}

