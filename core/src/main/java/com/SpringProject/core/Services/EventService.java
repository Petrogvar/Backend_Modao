package com.SpringProject.core.Services;

import com.SpringProject.core.dto.EventDto;
import java.util.List;

public interface EventService {
  Long createEvent(EventDto eventDto);
  void confirmationEvent(Long userId, Long eventId);
  List<EventDto> GetСonfirmedEventList(Long groupId);
  EventDto GetEvent(Long eventId);
}

