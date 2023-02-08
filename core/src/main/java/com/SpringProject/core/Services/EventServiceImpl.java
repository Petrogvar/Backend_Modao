package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.Debt;
import com.SpringProject.core.Entity.Event;
import com.SpringProject.core.Entity.Expense;
import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.Entity.User;
import com.SpringProject.core.Entity.UserGroup;
import com.SpringProject.core.Repository.DebtRepository;
import com.SpringProject.core.Repository.EventRepository;
import com.SpringProject.core.Repository.GroupRepository;
import com.SpringProject.core.Repository.UserRepository;
import com.SpringProject.core.controllers.Error.ThereIsNoSuchUserException;
import com.SpringProject.core.dto.EventDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{
  private final EventRepository eventRepository;
  private final DebtRepository debtRepository;
  private final GroupRepository groupRepository;
  private final UserRepository userRepository;

  @Override
  public Long createEvent(EventDto eventDto, Long groupId){
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (optionalGroup.isEmpty()) {
      throw new ThereIsNoSuchUserException();
    } else {
      Group group = optionalGroup.get();
      Event event = new Event();
      event.setEventName(eventDto.getName());
      event.setStatus(0);
      event.setPrice(eventDto.getPrice());
      event.setGroup(group);
      Double sum = 0D;
      event.setExpenses(new ArrayList<>());
      User paying = userRepository.findById(eventDto.getParticipants_id().get(0)).get(); //kk
      for (int i=0; i < eventDto.getParticipants_k().size(); i++)
        sum+=eventDto.getParticipants_k().get(i);
      for (int i=1; i < eventDto.getParticipants_k().size(); i++) {
        User participant = userRepository.findById(eventDto.getParticipants_id().get(i)).get(); //kk
        Expense expense = new Expense();
        expense.setEvent(event);
        expense.setUser1(participant);
        expense.setUser2(paying);
        expense.setTransferAmount(eventDto.getParticipants_k().get(i)/sum*eventDto.getPrice());
        event.getExpenses().add(expense);
      }
      group.getEvent().add(event);
      return eventRepository.save(event).getId();
    }
  }
  @Override
  public void confirmationEvent(Long userId, Long eventId){
    Optional<User> optionalUser = userRepository.findById(userId);
    Optional<Event> optionalEvent = eventRepository.findById(eventId);
    if (optionalUser.isEmpty() || optionalEvent.isEmpty()) {
      throw new ThereIsNoSuchUserException();
    } else {
      optionalEvent.get().setStatus(1);
      for (int i=0; i<optionalEvent.get().getExpenses().size(); i++){
        Expense expense = optionalEvent.get().getExpenses().get(i);
        Debt debt = debtRepository.findByGroupAndUserFromAndUserTo
            (expense.getEvent().getGroup(), expense.getUser1(), expense.getUser2());
        debt.setDebt(debt.getDebt()+expense.getTransferAmount());
        debtRepository.save(debt);
        debt = debtRepository.findByGroupAndUserFromAndUserTo
            (expense.getEvent().getGroup(), expense.getUser2(), expense.getUser1());
        debt.setDebt(debt.getDebt()-expense.getTransferAmount());
        debtRepository.save(debt);

      }
    }
  }
}
