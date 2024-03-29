package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.Debt;
import com.SpringProject.core.Entity.Event;
import com.SpringProject.core.Entity.Expense;
import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.Entity.User;
import com.SpringProject.core.Entity.UserEvent;
import com.SpringProject.core.Entity.UserGroup;
import com.SpringProject.core.Repository.DebtRepository;
import com.SpringProject.core.Repository.EventRepository;
import com.SpringProject.core.Repository.GroupRepository;
import com.SpringProject.core.Repository.UserGroupRepository;
import com.SpringProject.core.Repository.UserRepository;
import com.SpringProject.core.Services.Notification.Notification;
import com.SpringProject.core.Services.h.DataVerification;
import com.SpringProject.core.controllers.Error.Exception.BadRequestException;
import com.SpringProject.core.controllers.Error.Exception.NotFoundException;
import com.SpringProject.core.dto.EventDto;
import com.SpringProject.core.dto.ExpenseDto;
import com.SpringProject.core.Mapper.EventMapperImpl;
import com.SpringProject.core.dto.my.CustomPairIdCoefficient;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

  private final EventRepository eventRepository;
  private final DebtRepository debtRepository;
  private final GroupRepository groupRepository;
  private final UserRepository userRepository;
  private final DataVerification dataVerification;
  private final UserGroupRepository userGroupRepository;
  private final Notification notification;

  @Override
  public Long createEvent(EventDto eventDto, Long userIdCreator) {
    Optional<User> optionalUserCreator = userRepository.findById(userIdCreator);
    dataVerification.event(eventDto);
    Optional<User> optionalUserPaying = userRepository.findById(
        eventDto.getCustomPairIdCoefficientPaying().getId());
    Optional<Group> optionalGroup = groupRepository.findById(eventDto.getGroupId());
    if (!optionalGroup.isPresent() || !optionalUserCreator.isPresent()
        || !optionalUserPaying.isPresent()) {
      throw new NotFoundException();
    }
    if (optionalGroup.get().getTypeGroup() == 1) {
      throw new BadRequestException("TypeGroup() == 1");
    }

    Double sum = eventDto.getCustomPairIdCoefficientPaying().getCoefficient();
    Group group = optionalGroup.get();
    List<Optional<User>> optionalUserList = new ArrayList<>();
    int size = eventDto.getCustomPairIdCoefficientList().size();
    for (CustomPairIdCoefficient IdCoefficientUser : eventDto.getCustomPairIdCoefficientList()) {
      Optional<User> optionalUser = userRepository.findById(IdCoefficientUser.getId());
      optionalUserList.add(optionalUser);
      sum += IdCoefficientUser.getCoefficient();
    }
    if (sum == 0) {
      throw new BadRequestException("сумма коэффициентов = 0");
    }

    List<Long> userIdList = new ArrayList<>();
    for (UserGroup userGroup : group.getUserGroupList()) {
      userIdList.add(userGroup.getUser().getId());
    }
    for (CustomPairIdCoefficient customPairIdCoefficient : eventDto.getCustomPairIdCoefficientList()) {
      if (!userIdList.contains(customPairIdCoefficient.getId())) {
        throw new BadRequestException("UserNotGroup");
      }
    }

    if (!userIdList.contains(eventDto.getCustomPairIdCoefficientPaying().getId())) {
      throw new BadRequestException("UserNotGroup");
    }

    Event event = new Event();
    event.setCreatedAt(new Timestamp(System.currentTimeMillis()+ 1000*60*60*3));
    event.setEventName(eventDto.getName());
    event.setStatus(0);
    event.setPrice(eventDto.getPrice());
    event.setGroup(group);
    event.setExpenseList(new ArrayList<>());
    User paying = optionalUserPaying.get();
    event.setUserPayingId(paying.getId());
    event.setType(eventDto.getType());
    event.setUsernamePaying(paying.getUsername());
    event.setUserCreatorId(optionalUserCreator.get().getId());
    event.setUsernameCreator(optionalUserCreator.get().getUsername());

    for (int i = 0; i < size; i++) {

      User participant = optionalUserList.get(i).get();
      Expense expense = new Expense();
      expense.setEvent(event);
      expense.setUserFrom(participant);
      expense.setUserTo(paying);
      expense.setTransferAmount(
          (eventDto.getCustomPairIdCoefficientList().get(i).getCoefficient()
              / sum * eventDto.getPrice()));
      event.getExpenseList().add(expense);

      UserEvent userEvent = new UserEvent();
      userEvent.setCoefficient(eventDto.getCustomPairIdCoefficientList().get(i).getCoefficient());
      userEvent.setUser(participant);
      userEvent.setEvent(event);
      userEvent.setGroupId(eventDto.getGroupId());
      participant.getUserEventList().add(userEvent);

    }

    UserEvent userEvent = new UserEvent();
    userEvent.setCoefficient(eventDto.getCustomPairIdCoefficientPaying().getCoefficient());
    userEvent.setUser(paying);
    userEvent.setEvent(event);
    userEvent.setGroupId(eventDto.getGroupId());
    paying.getUserEventList().add(userEvent);

    group.getEventList().add(event);
    Optional<UserGroup> optionalUserGroup = userGroupRepository.findByUserIdAndGroupId(
        optionalUserCreator.get().getId(), optionalGroup.get().getId());
    if (!optionalUserGroup.isPresent()) {
      throw new NotFoundException();
    }
    if (optionalUserGroup.get().getRole() != 1) {
      for (UserGroup userGroup : optionalGroup.get().getUserGroupList()) {
        if (userGroup.getRole() == 1) {
          notification.newNotificationEvent(userGroup.getUser(), event);
        }
      }
    }
    return eventRepository.save(event).getId();

  }

  @Override
  public void confirmationEvent(Long userId, Long groupId, Long eventId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    Optional<Event> optionalEvent = eventRepository.findById(eventId);
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (!optionalUser.isPresent() || !optionalEvent.isPresent() || !optionalGroup.isPresent()) {
      throw new NotFoundException();
    }
    if (optionalGroup.get().getTypeGroup() == 1) {
      throw new BadRequestException("TypeGroup() == 1");
    }
    if (!Objects.equals(optionalEvent.get().getGroup().getId(), groupId)) {
      throw new BadRequestException("событие не в группе");
    }

    if (optionalEvent.get().getStatus() != 0) {
      throw new BadRequestException("status != 0");
    }

    optionalGroup.get().setUpdateTime(new Timestamp(System.currentTimeMillis()));
    optionalEvent.get().setStatus(1);
    for (Expense expense : optionalEvent.get().getExpenseList()) {
      Debt debt = debtRepository.findByGroupAndUserFromAndUserTo
          (expense.getEvent().getGroup(), expense.getUserFrom(), expense.getUserTo());
      debt.setDebt(debt.getDebt() - expense.getTransferAmount());
      debtRepository.save(debt);
      debt = debtRepository.findByGroupAndUserFromAndUserTo
          (expense.getEvent().getGroup(), expense.getUserTo(), expense.getUserFrom());
      debt.setDebt(debt.getDebt() + expense.getTransferAmount());
      debtRepository.save(debt);
    }
  }


  @Override
  public Page<EventDto> getСonfirmedEventMod0List(Long groupId, int type, Integer offset,
      Integer limit, Long time1, Long time2) {
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (!optionalGroup.isPresent()) {
      throw new NotFoundException();
    }

    List<Integer> statusList = new ArrayList<>();
    statusList.add(1);
    statusList.add(-1);
    statusList.add(-2);
    List<Integer> typeList = new ArrayList<>();
    switch (type) {
      case 0:
        typeList.add(0);
        break;
      case 1:
        typeList.add(1);
        break;
      case 2:
        typeList.add(0);
        typeList.add(1);
        break;
      default:
        throw new BadRequestException("invalid type");
    }
    Pageable pageable = PageRequest.of(offset, limit, Sort.by(Direction.DESC, "createdAt"));
    Page<Event> eventPage = eventRepository.findAllByGroupAndStatusInAndTypeInAndCreatedAtBetween(
        optionalGroup.get(), statusList,
        typeList, pageable, new Timestamp(time1), new Timestamp(time2));
    return EventMapperImpl.fromEntityPage(eventPage);
  }

  @Override
  public EventDto getEvent(Long groupId, Long eventId) {
    Optional<Event> optionalEvent = eventRepository.findById(eventId);
    if (!optionalEvent.isPresent()) {
      throw new NotFoundException();
    }
    if (!Objects.equals(optionalEvent.get().getGroup().getId(), groupId)) {
      throw new BadRequestException("событие не в группе");
    }

    EventDto eventDto = EventMapperImpl.toEventDto(optionalEvent.get());
    eventDto.setExpenseDtoList(new ArrayList<>());
    for (UserEvent userEvent : optionalEvent.get().getUserEventList()) {
      ExpenseDto expenseDto = new ExpenseDto();
      expenseDto.setUserId(userEvent.getUser().getId());
      expenseDto.setUsername(userRepository.findById(expenseDto.getUserId()).get().getUsername());
      expenseDto.setCoefficient(userEvent.getCoefficient());
      if (optionalEvent.get().getStatus() != -2) {
        boolean bool = true;

        for (Expense expense : optionalEvent.get().getExpenseList()) {
          if (Objects.equals(expense.getUserFrom().getId(),
              expenseDto.getUserId())) {
            DecimalFormat df = new DecimalFormat("#.##");
            expenseDto.setTransferAmount((df.format(
                expense.getTransferAmount())));
            bool = false;
            break;
          }
        }
        if (bool) {
          expenseDto.setTransferAmount("0");
        }
      }
      else {
        boolean bool = true;

        for (Expense expense : optionalEvent.get().getExpenseList()) {
          if (Objects.equals(expense.getUserTo().getId(),
              expenseDto.getUserId())) {
            DecimalFormat df = new DecimalFormat("#.##");
            expenseDto.setTransferAmount((df.format(
                expense.getTransferAmount())));
            bool = false;
            break;
          }
        }
        if (bool) {
          expenseDto.setTransferAmount("0");
        }
      }
      eventDto.getExpenseDtoList().add(expenseDto);
    }
    return eventDto;
  }

  @Override
  public List<EventDto> getUnconfirmedEventList(Long groupId) {
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (!optionalGroup.isPresent()) {
      throw new NotFoundException();
    }
    List<Integer> statusList = new ArrayList<>();
    statusList.add(0);
    List<Integer> typeList = new ArrayList<>();
    typeList.add(0);
    typeList.add(1);
    return EventMapperImpl.toEventDtoList(
        eventRepository.findAllByGroupAndStatusInAndTypeIn(optionalGroup.get(), statusList,
            typeList));
  }

  @Override
  public void unconfirmationEvent(Long userId, Long groupId, Long eventId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    Optional<Event> optionalEvent = eventRepository.findById(eventId);
    if (!optionalUser.isPresent() || !optionalEvent.isPresent()) {
      throw new NotFoundException();
    }
    if (!Objects.equals(optionalEvent.get().getGroup().getId(), groupId)) {
      throw new BadRequestException("событие не в группе");
    }
    if (optionalEvent.get().getStatus() != 0) {
      throw new BadRequestException("status != 0");
    }
    eventRepository.delete(optionalEvent.get());
  }

  @Override
  public Long deleteEvent(Long userIdCreator, Long groupId, Long eventId) {
    Optional<Event> optionalEvent = eventRepository.findById(eventId);
    Optional<User> optionalUserCreator = userRepository.findById(userIdCreator);
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (!optionalEvent.isPresent() || !optionalUserCreator.isPresent()
        || !optionalGroup.isPresent()) {
      throw new NotFoundException();
    }
    if (optionalGroup.get().getTypeGroup() == 1) {
      throw new BadRequestException("archive group");
    }
    if (!Objects.equals(optionalEvent.get().getGroup().getId(), groupId)) {
      throw new BadRequestException("событие не в группе");
    }
    if (optionalEvent.get().getStatus() != 1) {
      throw new BadRequestException("status != 1");
    }

    Event eventNew = new Event();
    eventNew.setCreatedAt(new Timestamp(System.currentTimeMillis()+ 1000*60*60*3));
    eventNew.setEventName("Deleting " + optionalEvent.get().getEventName());

    eventNew.setDescription(optionalEvent.get().getDescription());
    eventNew.setUserCreatorId(userIdCreator);
    eventNew.setUsernameCreator(optionalUserCreator.get().getUsername());
    eventNew.setStatus(-2);
    eventNew.setType(optionalEvent.get().getType());
    eventNew.setPrice(optionalEvent.get().getPrice());
    eventNew.setGroup(optionalEvent.get().getGroup());
    eventNew.setUsernamePaying(optionalEvent.get().getUsernamePaying());
    eventNew.setUserPayingId(optionalEvent.get().getUserPayingId());
    eventNew.setDeleteId(eventId);
    eventNew.setExpenseList(new ArrayList<>());

    optionalEvent.get().setStatus(-1);


    for (Expense expenseOld : optionalEvent.get().getExpenseList()) {

      Expense expense = new Expense();
      expense.setTransferAmount(expenseOld.getTransferAmount());
      expense.setEvent(eventNew);
      expense.setUserFrom(expenseOld.getUserTo());
      expense.setUserTo(expenseOld.getUserFrom());
      eventNew.getExpenseList().add(expense);
    }

    UserEvent userEvent;

    for (UserEvent userEventOld : optionalEvent.get().getUserEventList()) {
      userEvent = new UserEvent();
      userEvent.setCoefficient(userEventOld.getCoefficient());
      userEvent.setUser(userEventOld.getUser());
      userEvent.setEvent(eventNew);
      userEvent.setGroupId(userEventOld.getGroupId());
      userEventOld.getUser().getUserEventList().add(userEvent);
    }

    optionalGroup.get().setUpdateTime(new Timestamp(System.currentTimeMillis()));

    for (Expense expense1 : eventNew.getExpenseList()) {
      Debt debt = debtRepository.findByGroupAndUserFromAndUserTo
          (expense1.getEvent().getGroup(), expense1.getUserFrom(), expense1.getUserTo());
      debt.setDebt(debt.getDebt() - expense1.getTransferAmount());
      debt = debtRepository.findByGroupAndUserFromAndUserTo
          (expense1.getEvent().getGroup(), expense1.getUserTo(), expense1.getUserFrom());
      debt.setDebt(debt.getDebt() + expense1.getTransferAmount());
    }
    Long id = eventRepository.save(eventNew).getId();
    optionalEvent.get().setDeleteId(id);
    eventRepository.save(optionalEvent.get());
    return id;
  }

  @Override
  public Page<EventDto> getСonfirmedEventMod1List(Long groupId, Long userId, int type,
      Integer offset,
      Integer limit, Long time1, Long time2) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (!optionalUser.isPresent()) {
      throw new NotFoundException();
    }
    List<UserEvent> userEventList = optionalUser.get().getUserEventList();
    List<Integer> statusList = new ArrayList<>();
    statusList.add(1);
    statusList.add(-1);
    statusList.add(-2);
    List<Integer> typeList = new ArrayList<>();
    switch (type) {
      case 0:
        typeList.add(0);
        break;
      case 1:
        typeList.add(1);
        break;
      case 2:
        typeList.add(0);
        typeList.add(1);
        break;
      default:
        throw new BadRequestException("invalid type");
    }
    List<EventDto> eventDtoList = new ArrayList<>();
    Event event;
    for (UserEvent userEvent : userEventList) {
      if (Objects.equals(userEvent.getGroupId(), groupId)) {
        event = userEvent.getEvent();
        if (typeList.contains(event.getType()) && statusList.contains(event.getStatus())
            && event.getCreatedAt().getTime() >= time1
            && event.getCreatedAt().getTime() <= time2) {
          eventDtoList.add(EventMapperImpl.toEventDto(event));
        }
      }
    }
    List<EventDto> eventDtoList1;
    eventDtoList.sort((s1, s2) -> {
      return s2.getTime().compareTo(s1.getTime());
    });
    if (offset * limit > eventDtoList.size()) {
      throw new BadRequestException("offset*limit > eventDtoList.size()");
    }
    if (((offset + 1) * limit) <= eventDtoList.size()) {
      eventDtoList1 = eventDtoList.subList(offset * limit, (((offset + 1) * limit)));
    } else {
      eventDtoList1 = eventDtoList.subList(offset * limit, eventDtoList.size());
    }

    Pageable pageable = PageRequest.of(offset, limit);
    return new PageImpl<>(eventDtoList1, pageable, eventDtoList1.size());
  }

}
