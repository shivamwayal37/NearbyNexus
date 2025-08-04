package com.NearbyNexus.Backend.service;

import com.NearbyNexus.Backend.entity.ChatMessage;
import com.NearbyNexus.Backend.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    /**
     * Saves a chat message to MongoDB with the current timestamp.
     * @param message The ChatMessage object containing eventId, userId, and message.
     * @return The saved ChatMessage.
     */
    public ChatMessage saveMessage(ChatMessage message) {
        if (message.getEventId() == null || message.getUserId() == null || message.getMessage() == null) {
            throw new IllegalArgumentException("Event ID, User ID, and message content are required");
        }
        message.setTimestamp(new Date());
        return chatMessageRepository.save(message);
    }

    /**
     * Retrieves all chat messages for a given event ID.
     * @param eventId The ID of the event.
     * @return List of ChatMessage objects.
     */
    public List<ChatMessage> getMessagesByEventId(String eventId) {
        if (eventId == null) {
            throw new IllegalArgumentException("Event ID is required");
        }
        return chatMessageRepository.findByEventId(eventId);
    }
}
