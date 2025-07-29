package com.NearbyNexus.Backend.repository;

import com.NearbyNexus.Backend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@RedisHash("events")
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByLocationContainingIgnoreCaseAndCategory(String location, String category);
    List<Event> findByLocationContainingIgnoreCase(String location);
    List<Event> findByCategory(String category);
}