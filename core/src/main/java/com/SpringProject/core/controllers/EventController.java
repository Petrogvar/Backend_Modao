package com.SpringProject.core.controllers;
/*
import com.SpringProject.core.Entity.GroupsEntity;
import com.SpringProject.core.Services.EventService;
import com.SpringProject.core.dto.EventDto;
import org.springframework.web.bind.annotation.DeleteMapping;
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
  /*
    @GetMapping("/{id}")
    public GroupsEntity getGroups(@PathVariable Long id) {
      return eventService.getGroups(id);
    }

    @PostMapping({"/{id}"})
    public Long createGroups(@RequestBody EventDto eventDto, @PathVariable Long id){
      return eventService.createEvent(eventDto, id);
    }
/*
    @DeleteMapping("/{id}")
    void deleteGroups(@PathVariable Long id){
      groupsService.deleteGroups(id);
    }
*/
  /*
    @PutMapping("/{id}")

    void updateGroups(@PathVariable Long id, @RequestBody GroupsEntity groupsTable){
      groupsService.updateGroups(id, groupsTable);
    }

}*/
