package com.SpringProject.core.Repository;

import com.SpringProject.core.Entity.InvitationFriend;
import com.SpringProject.core.Entity.InvitationInGroup;
import com.SpringProject.core.Entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface InvitationInGroupRepository extends JpaRepository<InvitationInGroup, Long> {
//Optional<InvitationInGroup> getByGroupId(Long groupId);
//Optional<InvitationInGroup> getByUserIdAndUser(Long userId, User user);

Optional<InvitationInGroup> getByUserIdAndUserAndGroupId(Long userId, User user, Long groupId);
Optional<InvitationInGroup>  getByIdAndUser(Long id, User user);
@Transactional
void deleteAllByUserAndGroupId(User user, Long groupId);
}
