package com.SpringProject.core.Repository;

import com.SpringProject.core.Entity.Event;
import com.SpringProject.core.Entity.Group;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
Page<Event> findAllByGroupAndStatusInAndTypeInAndCreatedAtBetween(
    Group group, List<Integer> statusList, List<Integer> typeList, Pageable pageable,
    Timestamp minCreatedAt, Timestamp maxCreatedAt);

List<Event> findAllByGroupAndStatusInAndTypeIn(
    Group group, List<Integer> statusList, List<Integer> typeList);

}