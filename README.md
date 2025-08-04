# Local Event Finder with Real-Time Chat
A full-stack MVP for discovering local events and chatting with organizers in real-time.

## Tech Stack
- Backend: Spring Boot, MySQL, MongoDB, Redis, WebSockets
- Frontend: React, Google Maps API

## Week 1 Progress
- Initialized Spring Boot and React projects.
- Set up MySQL (users, events, rsvps) and MongoDB (chat_messages).
- Implemented `/api/auth/register` and `/api/auth/login` with JWT.
- Connected Redis for caching.

## Week 2 Progress
- Implemented `Event` entity and repository for MySQL.
- Added `/api/events` APIs for creating and listing events.
- Integrated Redis caching for event listings (e.g., `events::Bangalore:Tech`) with 5-minute TTL.
- Resolved WSL Redis connectivity issues.

### Week 3 Progress

- **Implemented real-time chat** using Spring WebSocket with STOMP protocol for full-duplex communication.
  
- **Configured WebSocket endpoints**:
  - `/chat` WebSocket endpoint with SockJS fallback
  - STOMP messaging routes:
    - Subscriptions: `/topic/event/{eventId}`
    - Message sending: `/app/chat.sendMessage`

- **Security implementation**:
  - Secured WebSocket connections with JWT-based authentication
  - Used `AuthHandshakeInterceptor` to:
    - Extract JWT from `Authorization` header
    - Handle `token` query parameter for SockJS clients
  - Validated tokens during handshake to authenticate users

- **Data persistence**:
  - Stored chat messages in MongoDB (`chat_messages` collection) using `ChatService`
  - Message structure includes:
    - `eventId`
    - `userId`
    - `content`
    - Timestamp

- **Testing & Validation**:
  - Verified end-to-end message flow and persistence
  - Tested with:
    - `wscat` utility
    - Frontend clients
  - Confirmed functionality for:
    - Connection establishment
    - Topic subscription
    - Message sending
    - Data storage

- **Bug Fixes**:
  - Resolved `IllegalArgumentException` by validating:
    - `eventId`
    - `userId` 
    - `content` in incoming `ChatMessage` payload

## API Documentation
- **POST /api/auth/register**
  - Body: `{ "email": "string", "password": "string", "role": "ATTENDEE|ORGANIZER" }`
  - Response: `{ "token": "jwt_token" }`
- **POST /api/auth/login**
  - Body: `{ "email": "string", "password": "string" }`
  - Response: `{ "token": "jwt_token" }`
- **POST /api/events**
  - Headers: `Authorization: Bearer <jwt_token>`
  - Body: `{ "title": "string", "description": "string", "date": "2025-08-15T10:00:00", "location": "string", "latitude": number, "longitude": number, "category": "string" }`
  - Response: Event object
- **GET /api/events?city=X&category=Y**
  - Headers: `Authorization: Bearer <jwt_token>`
  - Response: List of events (cached in Redis)
- **WebSocket /chat**
  - Connect: `ws://localhost:8080/chat`
  - Subscribe: `/topic/event/{eventId}`
  - Send: `/app/chat/{eventId}` with `{ "userId": "string", "message": "string" }`

## Setup Instructions
1. **Backend**:
   - Install Java 21, Maven, MySQL, MongoDB, Redis.
   - Update `application.properties` with credentials (e.g., `spring.redis.host=172.17.0.2` for WSL).
   - Run: `mvn spring-boot:run`
2. **Frontend**:
   - Install Node.js, run `npm install` in `frontend`.
   - Start: `npm start`
3. **WebSocket Testing**:
   - Install `wscat`: `npm install -g wscat`
   - Connect: `wscat -c ws://localhost:8080/chat`
   - Subscribe: `{"destination":"/topic/event/1","data":""}`
   - Send: `{"destination":"/app/chat/1","data":"{\"userId\":\"1\",\"message\":\"Hello\"}"}`
