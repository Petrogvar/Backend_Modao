package com.SpringProject.core.Repository;

import com.SpringProject.core.Entity.InvitationFriend;
import com.SpringProject.core.Entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationFriendRepository extends JpaRepository<InvitationFriend, Long> {
  Optional<InvitationFriend> getByUserIdAndUser(Long userId, User user);
  Optional<InvitationFriend> getByUserIdAndUser_Id(Long userId, Long user_id);
  Optional<InvitationFriend>  deleteByIdAndUser(Long id, User user);

  Optional<InvitationFriend>  getByIdAndUser(Long id, User user);


}
