package com.SpringProject.core.controllers;

import com.SpringProject.core.Services.EventService;
import com.SpringProject.core.dto.EventDto;
import java.util.List;
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

  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @PostMapping()
  public Long createEvent(@RequestBody EventDto eventDto) {
    return eventService.createEvent(eventDto);
  }

  @PutMapping("/{userId}/{eventId}")
  public void confirmationEvent(@PathVariable Long userId, @PathVariable Long eventId) {
    eventService.confirmationEvent(userId, eventId);
  }
  @GetMapping("/list/{groupId}")
  public List<EventDto> GetСonfirmedEventList(@PathVariable Long groupId){
    return eventService.GetСonfirmedEventList(groupId);
  }

  @GetMapping("/{eventId}")
  public EventDto getEvent(@PathVariable Long eventId){
    return eventService.GetEvent(eventId);
  }
}
