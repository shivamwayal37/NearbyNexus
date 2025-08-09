// ChatTest.jsx
import React, { useEffect, useRef, useState } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

const ChatTest = ({ eventId, jwtToken }) => {
  const [connected, setConnected] = useState(false);
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState("");
  const clientRef = useRef(null);

  useEffect(() => {
    // const socketUrl = `http://localhost:8080/chat?token=${jwtToken}`;

    const stompClient = new Client({
      brokerURL: `ws://localhost:8080/chat?token=${jwtToken}`,
      debug: (str) => console.log("STOMP:", str),
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: () => {
        console.log("Connected to WebSocket");
        setConnected(true);

        stompClient.subscribe(`/topic/event/${eventId}`, (message) => {
          try {
            const receivedMessage = JSON.parse(message.body);
            setMessages((prev) => [...prev, receivedMessage]);
          } catch (error) {
            console.error("Error parsing message:", error);
          }
        });
      },
      onDisconnect: () => {
        console.log("Disconnected from WebSocket");
        setConnected(false);
      },
      onStompError: (frame) => {
        console.error("STOMP Error:", frame);
        setConnected(false);
      },
      onWebSocketError: (error) => {
        console.error("WebSocket Error:", error);
        setConnected(false);
      }
    });

    stompClient.activate();
    clientRef.current = stompClient;

    return () => {
      if (clientRef.current?.connected) {
        clientRef.current.deactivate();
      }
    };
  }, [eventId, jwtToken]);

  const sendMessage = () => {
    if (clientRef.current?.connected && input.trim()) {
      clientRef.current.publish({
        destination: `/app/chat/${eventId}`,
        body: JSON.stringify({
          message: input,
          eventId
        })
      });
      setInput("");
    }
  };

  return (
    <div className="max-w-md mx-auto mt-10 p-6 bg-white rounded-2xl shadow-lg">
      <h1 className="text-2xl font-bold text-center mb-4">WebSocket Chat</h1>
      <div className="h-64 overflow-y-auto border border-gray-200 p-3 rounded-md bg-gray-50 mb-4">
        {messages.map((msg, index) => (
          <div key={index} className="text-sm p-1 border-b border-gray-100">
            <span className="font-semibold">{msg.userId || "Anonymous"}: </span>
            {msg.content}
          </div>
        ))}
        {messages.length === 0 && (
          <p className="text-gray-400 text-sm text-center">No messages yet</p>
        )}
      </div>
      <div className="flex gap-2">
        <input
          type="text"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && sendMessage()}
          className="flex-1 border border-gray-300 rounded-md px-3 py-2"
          placeholder="Type your message..."
          disabled={!connected}
        />
        <button
          onClick={sendMessage}
          disabled={!connected}
          className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition disabled:opacity-50"
        >
          Send
        </button>
      </div>
      {!connected && (
        <p className="text-red-500 text-center text-sm mt-4">
          Connecting to WebSocket...
        </p>
      )}
    </div>
  );
};

export default ChatTest;
