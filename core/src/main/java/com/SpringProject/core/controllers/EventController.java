package com.SpringProject.core.controllers;

import com.SpringProject.core.Services.EventService;
import com.SpringProject.core.dto.EventDto;
import com.SpringProject.core.dto.GroupDto;
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

  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @PostMapping({"/{id}"})
  public Long createEvent(@RequestBody EventDto eventDto, @PathVariable Long id) {
    return eventService.createEvent(eventDto, id);
  }

  @PutMapping({"/{userId}/{eventId}"})
  public void confirmationEvent(@PathVariable Long userId, @PathVariable Long eventId) {
    eventService.confirmationEvent(userId, eventId);
  }

}
