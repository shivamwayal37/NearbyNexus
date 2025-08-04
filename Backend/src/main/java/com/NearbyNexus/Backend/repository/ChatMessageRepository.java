package com.NearbyNexus.Backend.repository;

import com.NearbyNexus.Backend.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByEventId(String eventId);

}
