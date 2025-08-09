# NearbyNexus: Local Event Finder with Real-Time Chat
A full-stack MVP for discovering local events and chatting with organizers in real-time.

## Tech Stack
- Backend: Spring Boot, MySQL, MongoDB, Redis, WebSockets
- Frontend: React, Axios, SockJS, STOMP, Material-UI, Tailwind CSS

## Week 1 Progress
- Initialized Spring Boot and React projects.
- Set up MySQL (users, events, rsvps) and MongoDB (chat_messages).
- Implemented `/api/auth/register` and `/api/auth/login` with JWT.
- Connected Redis for caching.

## Week 2 Progress
- Implemented `Event` entity and `/api/events` APIs.
- Added Redis caching for event listings (e.g., `events::Bangalore:Tech`, 5-minute TTL).
- Resolved WSL Redis connectivity issues.

## Week 3 Progress
- Implemented real-time chat with Spring WebSocket, STOMP, and MongoDB.
- Added `ChatService` for message persistence and retrieval.
- Fixed WebSocket JWT authentication issues (`403`, `MalformedJwtException`).

## Week 4 Progress
- Built React Event Listing Page to fetch events from `/api/events?city=X&category=Y`.
- Created Chat Component with WebSocket integration for real-time messaging.
- Added Login Component to authenticate users and store JWT.
- Tested frontend-backend integration for events and chat.

## API Documentation
- **POST /api/auth/login**
  - Body: `{ "email": "string", "password": "string" }`
  - Response: `{ "token": "jwt_token" }`
- **GET /api/events?city=X&category=Y**
  - Headers: `Authorization: Bearer <jwt_token>`
  - Response: List of events
- **GET /api/chat/{eventId}**
  - Headers: `Authorization: Bearer <jwt_token>`
  - Response: List of chat messages
- **WebSocket /chat**
  - Connect: `ws://localhost:8080/chat`
  - Headers: `Authorization: Bearer <jwt_token>`
  - Subscribe: `/topic/event/{eventId}`
  - Send: `/app/chat/{eventId}` with `{ "userId": "string", "message": "string" }`

## Setup Instructions
1. **Backend**:
   - Install Java 21, Maven, MySQL, MongoDB, Redis.
   - Update `application.properties` with credentials.
   - Run: `mvn spring-boot:run`
2. **Frontend**:
   - Install Node.js, run `npm install` in `frontend`.
   - Install dependencies: `npm install axios @mui/material @emotion/react @emotion/styled react-router-dom sockjs-client @stomp/stompjs`
   - Start: `npm start`
3. **Testing**:
   - Open `http://localhost:3000`, log in, search events, and join chat.
   - Use Postman for WebSocket: `ws://localhost:8080/chat` with `Authorization: Bearer <token>`.

## Troubleshooting
- **WebSocket 403**: Ensure valid JWT in `Authorization` header. Regenerate via `/api/auth/login`.
- **Frontend API Errors**: Verify JWT in `localStorage` and backend running on `localhost:8080`.
