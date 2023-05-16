package com.SpringProject.core.controllers;

import com.SpringProject.core.Services.h.CommonService;
import com.SpringProject.core.Services.EventService;
import com.SpringProject.core.controllers.Error.NotRightException;
import com.SpringProject.core.dto.DescriptionDto;
import com.SpringProject.core.dto.EventDto;
import com.SpringProject.core.dto.domain.JwtAuthentication;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @PostMapping("/create")//+++ time
  public Long createEvent(@RequestBody EventDto eventDto) {
    Long userIdCreator = ((JwtAuthentication) SecurityContextHolder.getContext()
        .getAuthentication()).getId();
    int role = commonService.getRoleInGroup(userIdCreator, eventDto.getGroupId());
    Long eventId = eventService.createEvent(eventDto, userIdCreator);
    if (role == 1) {
      eventService.confirmationEvent(userIdCreator, eventDto.getGroupId(), eventId);
    }
    return eventId;
  }

  @PutMapping("/delete/{groupId}/{eventId}")//+++ time
  public Long deleteEvent(@RequestBody DescriptionDto descriptionDto,
      @PathVariable Long groupId, @PathVariable Long eventId) {
    Long userIdCreator = ((JwtAuthentication) SecurityContextHolder.getContext()
        .getAuthentication()).getId();
    if (!commonService.userIsOrganizerByUserIdAndGroupId(userIdCreator, groupId)) {
      throw new NotRightException();
    }
    return eventService.deleteEvent(descriptionDto, userIdCreator, groupId, eventId);
  }

  // time who
  @PutMapping("/confirmation/{groupId}/{eventId}")
  public void confirmationEvent(@PathVariable Long groupId, @PathVariable Long eventId) {
    Long userId = ((JwtAuthentication) SecurityContextHolder.getContext()
        .getAuthentication()).getId();
    if (!commonService.userIsOrganizerByUserIdAndGroupId(userId, groupId)) {
      throw new NotRightException();
    }
    eventService.confirmationEvent(userId, groupId, eventId);
  }


  @PutMapping("/unconfirmation/{groupId}/{eventId}")
  public void unconfirmationEvent(@PathVariable Long groupId, @PathVariable Long eventId) {
    Long userId = ((JwtAuthentication) SecurityContextHolder.getContext()
        .getAuthentication()).getId();
    if (!commonService.userIsOrganizerByUserIdAndGroupId(userId, groupId)) {
      throw new NotRightException();
    }
    eventService.unconfirmationEvent(userId, groupId, eventId);
  }

  @GetMapping("/listEventsConfirmed/0/{groupId}/{type}") // time
  public Page<EventDto> getСonfirmedEventMod0List(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit" , defaultValue = "20") @Min(0) @Max(10) Integer limit,
      @PathVariable Long groupId, @PathVariable int type) {
    Long userId = ((JwtAuthentication) SecurityContextHolder.getContext()
        .getAuthentication()).getId();
    if (!commonService.userInGroupByUserIdAndGroupId(userId, groupId)) {
      throw new NotRightException();
    }
    return eventService.getСonfirmedEventMod0List(groupId, type, offset, limit);
  }

  @GetMapping("/listEventsConfirmed/1/{groupId}/{type}") // time
  public List<EventDto> getСonfirmedEventMod1List(@PathVariable Long groupId,
      @PathVariable int type) {
    Long userId = ((JwtAuthentication) SecurityContextHolder.getContext()
        .getAuthentication()).getId();
    if (!commonService.userInGroupByUserIdAndGroupId(userId, groupId)) {
      throw new NotRightException();
    }
    return eventService.getСonfirmedEventMod1List(groupId, userId, type);
  }

  @GetMapping("/listEventsUnconfirmed/{groupId}") // time
  public List<EventDto> getUnconfirmedEventList(@PathVariable Long groupId) {
    Long userId = ((JwtAuthentication) SecurityContextHolder.getContext()
        .getAuthentication()).getId();
    if (!commonService.userInGroupByUserIdAndGroupId(userId, groupId)) {
      throw new NotRightException();
    }
    return eventService.getUnconfirmedEventList(groupId);
  }


  @GetMapping("/info/{groupId}/{eventId}") // time
  public EventDto getEvent(@PathVariable Long groupId, @PathVariable Long eventId) {
    Long userId = ((JwtAuthentication) SecurityContextHolder.getContext()
        .getAuthentication()).getId();
    if (!commonService.userInGroupByUserIdAndGroupId(userId, groupId)) {
      throw new NotRightException();
    }
    return eventService.getEvent(groupId, eventId);
  }
}
