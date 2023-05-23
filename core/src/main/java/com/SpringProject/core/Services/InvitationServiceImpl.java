package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.Debt;
import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.Entity.InvitationFriend;
import com.SpringProject.core.Entity.InvitationInGroup;
import com.SpringProject.core.Entity.User;
import com.SpringProject.core.Entity.UserGroup;
import com.SpringProject.core.Mapper.InvitationMapperImpl;
import com.SpringProject.core.Repository.DebtRepository;
import com.SpringProject.core.Repository.GroupRepository;
import com.SpringProject.core.Repository.InvitationFriendRepository;
import com.SpringProject.core.Repository.InvitationInGroupRepository;
import com.SpringProject.core.Repository.UserRepository;
import com.SpringProject.core.Services.Notification.Notification;
import com.SpringProject.core.Services.h.CommonService;
import com.SpringProject.core.controllers.Error.Exception.BadRequestException;
import com.SpringProject.core.controllers.Error.Exception.NotFoundException;
import com.SpringProject.core.dto.InvitationFriendDto;
import com.SpringProject.core.dto.InvitationInGroupDto;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

  private final UserRepository userRepository;
  private final InvitationFriendRepository invitationFriendRepository;
  private final InvitationInGroupRepository invitationInGroupRepository;
  private final GroupRepository groupRepository;
  private final CommonService commonService;

  private final Notification notification;

  private final DebtRepository debtRepository;

  @Override
  public void createInvitationFriend(Long userId, String uuid) {
    Optional<User> optionalUser = userRepository.findById(userId);
    Optional<User> optionalUserFriends = userRepository.getByUuid(uuid);

    if (!optionalUser.isPresent() || !optionalUserFriends.isPresent()) {
      throw new NotFoundException();
    }
    Optional<InvitationFriend> optionalInvitation = invitationFriendRepository.getByUserIdAndUser(
        optionalUser.get().getId(), optionalUserFriends.get());
    if (optionalInvitation.isPresent()) {
      throw new BadRequestException("приглашение уже отправлено");
    }
    if (optionalUser.get().getFriends().contains(optionalUserFriends.get())) {
      throw new BadRequestException("пользователь уже в списке друзей");
    }

    notification.newNotificationFriends(optionalUserFriends.get(), optionalUser.get());

    InvitationFriend invitation = new InvitationFriend();
    invitation.setUserId(userId);
    invitation.setUsername(optionalUser.get().getUsername());
    invitation.setUser(optionalUserFriends.get());
    optionalUserFriends.get().getInvitationFriendList().add(invitation);
    userRepository.save(optionalUserFriends.get());
  }

  @Override
  public void createInvitationInGroupByFriends(Long userId, Long groupId, Long userIdFriends) {
    Optional<User> optionalUser = userRepository.findById(userId);
    Optional<User> optionalUserFriends = userRepository.findById(userIdFriends);
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (!optionalUserFriends.isPresent() || !optionalUser.isPresent() || !optionalGroup.isPresent()) {
      throw new NotFoundException();
    }
//    int size = optionalUser.get().getFriends().size();
//    boolean userIsFriend = false;
//    System.out.println(optionalUser.get().getFriends().get(0).getId());
//    System.out.println(optionalUserFriends.get().getId());
//    for (int i=0; i<size; i++){
//      if (Objects.equals(optionalUser.get().getFriends().get(i).getId(),
//          optionalUserFriends.get().getId())){
//        userIsFriend =true;
//        break;
//      }
//    }
//    if (!userIsFriend)
//      throw new NotFoundException();
    if (!optionalUser.get().getFriends().contains(optionalUserFriends.get())) {
      throw new NotFoundException();
    }
    if (commonService.userInGroup(optionalUserFriends.get(), optionalGroup.get())) {
      throw new BadRequestException("пользователь уже в группе");
    }
    Optional<InvitationInGroup> optionalInvitation = invitationInGroupRepository.getByUserIdAndUserAndGroupId(
        userId, optionalUserFriends.get(), groupId);
    if (optionalInvitation.isPresent()) {
      throw new BadRequestException("приглашение уже отправлено");
    }

    InvitationInGroup invitation = new InvitationInGroup();
    invitation.setNameGroup(optionalGroup.get().getGroupName());
    invitation.setUsername(optionalUser.get().getUsername());
    invitation.setUserId(userId);
    invitation.setGroupId(groupId);
    invitation.setUser(optionalUserFriends.get());
    optionalUserFriends.get().getInvitationInGroupList().add(invitation);

    notification.newNotificationGroup(optionalUserFriends.get(), optionalUser.get(), optionalGroup.get());

    userRepository.save(optionalUserFriends.get());
  }

  @Override
  public void declineInvitationFriend(Long userId, Long invitationId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (!optionalUser.isPresent()) {
      throw new NotFoundException();
    }
    Optional<InvitationFriend> optionalInvitation = invitationFriendRepository.getByIdAndUser(
        invitationId, optionalUser.get());
    if (!optionalInvitation.isPresent()) {
      throw new NotFoundException();
    }
    invitationFriendRepository.delete(optionalInvitation.get());
   // String p = optionalInvitation.get().getUsername();
   // System.out.println(p);
  }

  @Override
  public void declineInvitationInGroup(Long userId, Long invitationId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (!optionalUser.isPresent()) {
      throw new NotFoundException();
    }
    Optional<InvitationInGroup> optionalInvitation = invitationInGroupRepository.getByIdAndUser(
        invitationId, optionalUser.get());
    if (!optionalInvitation.isPresent()) {
      throw new NotFoundException();
    }
    invitationInGroupRepository.delete(optionalInvitation.get());
  }


  @Override
  public void acceptInvitationFriend(Long userId, Long invitationId) {
    Optional<User> optionalUserFriend = userRepository.findById(userId);
    if (!optionalUserFriend.isPresent()) {
      throw new NotFoundException();
    }
    Optional<InvitationFriend> optionalInvitation = invitationFriendRepository.getByIdAndUser(
        invitationId, optionalUserFriend.get());
    if (!optionalInvitation.isPresent()) {
      throw new NotFoundException();
    }
    Optional<User> optionalUser = userRepository.findById(optionalInvitation.get().getUserId());
    if (!optionalUser.isPresent())
      throw new BadRequestException("123");

    Optional<InvitationFriend> optionalInvitationOld =  invitationFriendRepository.getByUserIdAndUser_Id(optionalUserFriend.get().getId(),
        optionalInvitation.get().getUserId());
    optionalInvitationOld.ifPresent(invitationFriendRepository::delete);

    invitationFriendRepository.delete(optionalInvitation.get());
    optionalUserFriend.get().getFriends().add(optionalUser.get());
    optionalUser.get().getFriends().add(optionalUserFriend.get());
    userRepository.save(optionalUserFriend.get());
  }

  @Override
  public void acceptInvitationInGroup(Long userId, Long invitationId) {
    Optional<User> optionalUserFriend = userRepository.findById(userId);
    if (!optionalUserFriend.isPresent()) {
      throw new NotFoundException();
    }
    Optional<InvitationInGroup> optionalInvitation = invitationInGroupRepository.getByIdAndUser(
        invitationId, optionalUserFriend.get());
    if (!optionalInvitation.isPresent()) {
      throw new NotFoundException();
    }
    Optional<Group> optionalGroup = groupRepository.findById(optionalInvitation.get().getGroupId());
    if (!optionalGroup.isPresent())
      throw new BadRequestException("123");
    invitationInGroupRepository.delete(optionalInvitation.get());
    invitationInGroupRepository.deleteAllByUserAndGroupId(optionalUserFriend.get(), optionalGroup.get().getId());
    for (int i = 0; i < optionalGroup.get().getUserGroupList().size(); i++) {
      Debt debt = new Debt();
      Debt debtBack = new Debt();
      debtBack.setDebt(0D);
      debt.setDebt(0D); 
      debtBack.setGroup(optionalGroup.get());
      debt.setGroup(optionalGroup.get());
      debtBack.setUserFrom(optionalUserFriend.get());
      debtBack.setUserTo(optionalGroup.get().getUserGroupList().get(i).getUser());
      debt.setUserFrom(optionalGroup.get().getUserGroupList().get(i).getUser());
      debt.setUserTo(optionalUserFriend.get());
      debtRepository.save(debt);
      debtRepository.save(debtBack);
    }
    UserGroup userGroup = new UserGroup();
    if (optionalGroup.get().getTypeGroup() == 1) {
      userGroup.setRole(1);
    } else {
      userGroup.setRole(0);
    }
    userGroup.setUser(optionalUserFriend.get());
    userGroup.setGroup(optionalGroup.get());
    optionalUserFriend.get().getUserGroupsList().add(userGroup);
    userRepository.save(optionalUserFriend.get());
  }

  @Override
  public List<InvitationInGroupDto> getInvitationsInGroup(Long userIdCreator) {
    Optional<User> optionalUser = userRepository.findById(userIdCreator);
    if(!optionalUser.isPresent())
      throw new NotFoundException();
    return InvitationMapperImpl.toGroupDtoList(optionalUser.get().getInvitationInGroupList());
  }

  @Override
  public List<InvitationFriendDto> getInvitationsFriend(Long userIdCreator) {
    Optional<User> optionalUser = userRepository.findById(userIdCreator);
    if(!optionalUser.isPresent())
      throw new NotFoundException();
    return InvitationMapperImpl.toFriendDtoList(optionalUser.get().getInvitationFriendList());
  }

  @Override
  public void createInvitationFriendByGroup(Long userIdCreator, Long groupId, Long userId) {
    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    Optional<User> optionalUser = userRepository.findById(userIdCreator);
    Optional<User> optionalUserFriend = userRepository.findById(userId);
    if (!optionalGroup.isPresent())
      throw new NotFoundException();
    int size = optionalGroup.get().getUserGroupList().size();
    long id;
    User userCreator  =null;
    User user = null;
    for(int i=0; i<size; i++){
      id  =  optionalGroup.get().getUserGroupList().get(i).getUser().getId();
      if (id == userIdCreator){
        userCreator = optionalGroup.get().getUserGroupList().get(i).getUser();
      }
      if (id == userId){
        user = optionalGroup.get().getUserGroupList().get(i).getUser();
      }
    }
    if(user == null || userCreator == null){
      throw new BadRequestException("123");
    }
    Optional<InvitationFriend> optionalInvitation = invitationFriendRepository.getByUserIdAndUser(
        userIdCreator, user);
    if (optionalInvitation.isPresent()) {
      throw new BadRequestException("приглашение уже отправлено");
    }
    if (userCreator.getFriends().contains(user)) {
      throw new BadRequestException("пользователь уже в списке друзей");
    }
    //notification.newNotificationFriends(optionalUserFriends.get(), optionalUser.get());
    InvitationFriend invitation = new InvitationFriend();
    invitation.setUsername(userCreator.getUsername());
    invitation.setUserId(userCreator.getId());
    invitation.setUser(user);
    user.getInvitationFriendList().add(invitation);
    userRepository.save(user);
  }
}
