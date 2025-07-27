package com.NearbyNexus.Backend.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@Document(collection = "chat_messages")
public class ChatMessage {
    private String eventId;
    private String userId;
    private String message;
    private Date timestamp;
}