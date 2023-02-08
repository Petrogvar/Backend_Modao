package com.SpringProject.core.Repository;

import com.SpringProject.core.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

}