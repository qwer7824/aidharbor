package com.aidharbor.Repository;

import com.aidharbor.Entity.Catalog;
import com.aidharbor.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository<Event,Long> {

    List<Event> findTop3ByOrderByCreatedAtDesc();
}
