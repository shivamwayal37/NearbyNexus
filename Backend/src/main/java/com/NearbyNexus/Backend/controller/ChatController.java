package com.NearbyNexus.Backend.controller;

import com.NearbyNexus.Backend.config.UsernamePrincipal;
import com.NearbyNexus.Backend.entity.ChatMessage;
import com.NearbyNexus.Backend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat/{eventId}")
    @SendTo("/topic/event/{eventId}")
    public ChatMessage sendMessage(@DestinationVariable String eventId, ChatMessage message, UsernamePrincipal principal) {
        if (principal == null) {
            throw new IllegalStateException("User not authenticated");
        }
        message.setEventId(eventId);
        message.setUserId(principal.getName());// Set the sender's email from the Principal
        message.setTimestamp(new Date());
        chatService.saveMessage(message);
        return message;
    }
} 