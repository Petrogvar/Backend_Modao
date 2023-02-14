package com.SpringProject.core.Repository;

import com.SpringProject.core.Entity.Event;
import com.SpringProject.core.Entity.Group;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
List<Event> findAllByGroupAndStatusIn(Group group, List<Integer> statusList);

}