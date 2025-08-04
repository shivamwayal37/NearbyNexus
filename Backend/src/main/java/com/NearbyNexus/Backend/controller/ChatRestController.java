package com.NearbyNexus.Backend.controller;

import com.NearbyNexus.Backend.entity.ChatMessage;
import com.NearbyNexus.Backend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {
    @Autowired
    private ChatService chatService;

    @GetMapping("/{eventId}")
    public ResponseEntity<List<ChatMessage>> getChatHistory(@PathVariable String eventId) {
        return ResponseEntity.ok(chatService.getMessagesByEventId(eventId));
    }
}