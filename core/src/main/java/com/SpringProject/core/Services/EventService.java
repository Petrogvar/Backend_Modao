package com.SpringProject.core.Services;

import com.SpringProject.core.dto.EventDto;
import java.util.List;

public interface EventService {
  Long createEvent(EventDto eventDto, String userLoginCreator);
  void confirmationEvent(String userlogin, Long eventId);
  List<EventDto> GetСonfirmedEventList(Long groupId);
  EventDto GetEvent(Long eventId);

  List<EventDto> GetUnconfirmedEventList(Long groupId);

  void unconfirmationEvent(String userLogin, Long eventId);
}

