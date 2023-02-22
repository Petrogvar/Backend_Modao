package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.Debt;
import com.SpringProject.core.Entity.Event;
import com.SpringProject.core.Entity.Expense;
import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.Entity.User;
import com.SpringProject.core.Entity.UserEvent;
import com.SpringProject.core.Repository.DebtRepository;
import com.SpringProject.core.Repository.EventRepository;
import com.SpringProject.core.Repository.GroupRepository;
import com.SpringProject.core.Repository.UserRepository;
import com.SpringProject.core.controllers.Error.BadRequestException;
import com.SpringProject.core.controllers.Error.NotFoundException;
import com.SpringProject.core.controllers.Error.UserNotGroupException;
import com.SpringProject.core.dto.EventDto;
import com.SpringProject.core.dto.ExpenseDto;
import com.SpringProject.core.dto.my.CustomPairIdCoefficient;
import com.SpringProject.core.mapper.EventMapperImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

  private final EventRepository eventRepository;
  private final DebtRepository debtRepository;
  private final GroupRepository groupRepository;
  private final UserRepository userRepository;

  @Override
  public Long createEvent(EventDto eventDto) {
    Optional<Group> optionalGroup = groupRepository.findById(eventDto.getGroupId());
    if (optionalGroup.isEmpty()) {
      throw new NotFoundException();
    } else {
      Double sum = 0D;
      Group group = optionalGroup.get();
      List<Optional<User>> optionalUserList = new ArrayList<>();
      List<Long> userIdList = new ArrayList<>();
      Integer size = eventDto.getCustomPairIdCoefficientList().size();
      for (int i = 0; i < size; i++) {
        optionalUserList.add(userRepository.findById(eventDto.getCustomPairIdCoefficientList().get(i).getId()));
        if (optionalUserList.get(i).isEmpty()) {
          throw new NotFoundException();  //kkk
        }
        sum += eventDto.getCustomPairIdCoefficientList().get(i).getCoefficient();
      }
      int countUserInGroup = group.getUserGroupList().size();
      for (int i = 0; i < countUserInGroup; i++) {
        userIdList.add(group.getUserGroupList().get(i).getUser().getId());
      }
      for (int i = 0; i < size; i++) {
        if (!userIdList.contains(eventDto.getCustomPairIdCoefficientList().get(i).getId())) {
          throw new UserNotGroupException(); //kkk
        }
      }
      Event event = new Event();
      event.setEventName(eventDto.getName());
      event.setStatus(0);
      event.setPrice(eventDto.getPrice());
      event.setGroup(group);
      event.setExpenseList(new ArrayList<>());
      User paying = optionalUserList.get(0).get();
      event.setUserPayingId(paying.getId());
      event.setUsernamePaying(paying.getUsername());
      for (int i = 1; i < size; i++) {
        User participant = optionalUserList.get(i).get();
        Expense expense = new Expense();
        expense.setEvent(event);
        expense.setUserFrom(participant);
        expense.setUserTo(paying);
        expense.setTransferAmount(
            eventDto.getCustomPairIdCoefficientList().get(i).getCoefficient() / sum * eventDto.getPrice());
        event.getExpenseList().add(expense);

        UserEvent userEvent = new UserEvent();
        userEvent.setCoefficient(eventDto.getCustomPairIdCoefficientList().get(i).getCoefficient());
        userEvent.setUser(participant);
        userEvent.setEvent(event);
        participant.getUserEventList().add(userEvent);
      }
      UserEvent userEvent = new UserEvent();
      userEvent.setCoefficient(eventDto.getCustomPairIdCoefficientList().get(0).getCoefficient());
      userEvent.setUser(paying);
      userEvent.setEvent(event);
      paying.getUserEventList().add(userEvent);

      group.getEventList().add(event);
      return eventRepository.save(event).getId();
    }
  }

  @Override
  public void confirmationEvent(Long userId, Long eventId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    Optional<Event> optionalEvent = eventRepository.findById(eventId);
    if (optionalUser.isEmpty() || optionalEvent.isEmpty()) {
      throw new NotFoundException();
    } else {
      if (optionalEvent.get().getStatus() != 0) {
        throw new BadRequestException();
      } else {
        optionalEvent.get().setStatus(1);
        for (int i = 0; i < optionalEvent.get().getExpenseList().size(); i++) {
          Expense expense = optionalEvent.get().getExpenseList().get(i);
          Debt debt = debtRepository.findByGroupAndUserFromAndUserTo
              (expense.getEvent().getGroup(), expense.getUserFrom(), expense.getUserTo());
          debt.setDebt(debt.getDebt() + expense.getTransferAmount());
          debtRepository.save(debt);
          debt = debtRepository.findByGroupAndUserFromAndUserTo
              (expense.getEvent().getGroup(), expense.getUserTo(), expense.getUserFrom());
          debt.setDebt(debt.getDebt() - expense.getTransferAmount());
          debtRepository.save(debt);
        }
      }
    }
  }
  @Override
  public List<EventDto> GetСonfirmedEventList(Long groupId){
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (optionalGroup.isEmpty())
      throw new NotFoundException();

    List<Integer> statusList = new ArrayList<>();
    statusList.add(1);
    statusList.add(-1);
    return EventMapperImpl.toEventDtoList(eventRepository.findAllByGroupAndStatusIn(optionalGroup.get(), statusList));
  }

  @Override
  public EventDto GetEvent(Long eventId){
    Optional<Event> optionalEvent = eventRepository.findById(eventId);
    if (optionalEvent.isEmpty())
      throw new NotFoundException();
    EventDto eventDto = EventMapperImpl.toEventDto(optionalEvent.get());
    eventDto.setExpenseDtoList(new ArrayList<>());
    for (int i=0; i<optionalEvent.get().getUserEventList().size(); i++){
      ExpenseDto expenseDto = new ExpenseDto();
      expenseDto.setUserId(optionalEvent.get().getUserEventList().get(i).getUser().getId());
      expenseDto.setUsername(userRepository.findById(expenseDto.getUserId()).get().getUsername());
      expenseDto.setCoefficient(optionalEvent.get().getUserEventList().get(i).getCoefficient());
      Boolean bool = true;
      for (int j = 0; j < optionalEvent.get().getExpenseList().size(); j++){
        if (optionalEvent.get().getExpenseList().get(j).getUserTo().getId() == expenseDto.getUserId()){
          expenseDto.setTransferAmount(optionalEvent.get().getExpenseList().get(j).getTransferAmount());
          bool = false;
          break;
        }
      }
      if (bool)
        expenseDto.setTransferAmount(0D);
      eventDto.getExpenseDtoList().add(expenseDto);
    }
    return eventDto;
  }
}
