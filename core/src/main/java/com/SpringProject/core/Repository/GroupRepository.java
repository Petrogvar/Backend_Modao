package com.SpringProject.core.Repository;

import com.SpringProject.core.Entity.Group;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
  Optional<Group> getByUuid(String uuid);

}
