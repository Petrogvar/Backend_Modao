package com.SpringProject.core.Repository;

import com.SpringProject.core.Entity.UserGroup;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
  Optional<UserGroup> findByUserIdAndGroupId(Long userId, Long GroupId);

}
