package com.SpringProject.core.controllers;

import com.SpringProject.core.Services.CommonService;
import com.SpringProject.core.Services.EventService;
import com.SpringProject.core.controllers.Error.NotRightException;
import com.SpringProject.core.dto.EventDto;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class EventController {
  private final EventService eventService;
  private final CommonService commonService;

  public EventController(EventService eventService, CommonService commonService) {
    this.eventService = eventService;
    this.commonService = commonService;
  }

  @PostMapping ("/create")//+++ time
  public Long createEvent(@RequestBody EventDto eventDto) {
    String userLoginCreator = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    return eventService.createEvent(eventDto, userLoginCreator);
  }

  // time who
  @PutMapping("/confirmation/{eventId}")
  public void confirmationEvent(@PathVariable Long eventId) {
    String userLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    if (!commonService.userIsOrganizerByLoginAndEventId(userLogin, eventId))
      throw new NotRightException();
    eventService.confirmationEvent(userLogin, eventId);
  }


  @PutMapping("/unconfirmation/{eventId}")
  public void unconfirmationEvent(@PathVariable Long eventId) {
    String userLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    if (!commonService.userIsOrganizerByLoginAndEventId(userLogin, eventId))
      throw new NotRightException();
    eventService.unconfirmationEvent(userLogin, eventId);
  }
  @GetMapping("/listEventsConfirmed/{groupId}") // time
  public List<EventDto> GetСonfirmedEventList(@PathVariable Long groupId){
    String userLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    if (!commonService.userInGroupByLoginAndGroupId(userLogin, groupId))
      throw new NotRightException();
    return eventService.GetСonfirmedEventList(groupId);
  }

  @GetMapping("/listEventsUnconfirmed/{groupId}") // time
  public List<EventDto> GetUnconfirmedEventList(@PathVariable Long groupId){
    String userLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    if (!commonService.userInGroupByLoginAndGroupId(userLogin, groupId))
      throw new NotRightException();
    return eventService.GetUnconfirmedEventList(groupId);
  }


  @GetMapping("/info/{eventId}") // time
  public EventDto getEvent(@PathVariable Long eventId){
    String userLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    if (!commonService.userInGroupByLoginAndEventId(userLogin, eventId))
      throw new NotRightException();
    return eventService.GetEvent(eventId);
  }
}
