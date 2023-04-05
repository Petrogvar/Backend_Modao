package com.SpringProject.core.Services;

import com.SpringProject.core.dto.InvitationFriendDto;
import com.SpringProject.core.dto.InvitationInGroupDto;
import java.util.List;

public interface InvitationService {
  void createInvitationFriend(Long userId, String uuid);
  void declineInvitationFriend(Long userId, Long invitationId);
  void acceptInvitationFriend(Long userId, Long invitationId);
  void createInvitationInGroupByFriends(Long userId, Long groupId, Long userIdFriends);

  void declineInvitationInGroup(Long userId, Long invitationId);
  void acceptInvitationInGroup(Long userId, Long invitationId);

  List<InvitationInGroupDto> getInvitationsInGroup(Long userIdCreator);

  List<InvitationFriendDto> getInvitationsFriend(Long userIdCreator);

  void createInvitationFriendByGroup(Long userIdCreator, Long groupId, Long userId);
}
