package com.SpringProject.core.Services;

import com.SpringProject.core.dto.EventDto;
import java.util.List;

public interface EventService {
  Long createEvent(EventDto eventDto, Long userIdCreator);
  void confirmationEvent(Long userId, Long groupId,  Long eventId);
  List<EventDto> getСonfirmedEventList(Long groupId, int mode, int type);
  EventDto getEvent(Long groupId, Long eventId);

  List<EventDto> getUnconfirmedEventList(Long groupId);

  void unconfirmationEvent(Long userId, Long groupId,  Long eventId);
}

