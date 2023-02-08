package com.SpringProject.core.Services;

import com.SpringProject.core.dto.EventDto;
import org.springframework.web.bind.annotation.PathVariable;

public interface EventService {
  Long createEvent(EventDto eventDto, Long groupId);
  void confirmationEvent(Long userId, Long eventId);
}
