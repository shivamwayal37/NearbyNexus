import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { getChatHistory } from '../api';
import { TextField, Button, Typography, List, ListItem, ListItemText } from '@mui/material';

const Chat = () => {
  const { eventId } = useParams();
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState('');
  const [client, setClient] = useState(null);

  useEffect(() => {
    const jwtToken = localStorage.getItem('token');
    if (!jwtToken) {
      console.error('No JWT token found');
      window.location.href = '/login';
      return;
    }

    const fetchHistory = async () => {
      try {
        const response = await getChatHistory(eventId);
        setMessages(response.data);
      } catch (error) {
        console.error('Error fetching chat history:', error);
      }
    };
    fetchHistory();

    const stompClient = new Client({
      brokerURL: `ws://localhost:8080/chat?token=${jwtToken}`,
      onConnect: () => {
        stompClient.subscribe(`/topic/event/${eventId}`, (msg) => {
          const newMessage = JSON.parse(msg.body);
          setMessages((prev) => [...prev, newMessage]);
        });
      },
      onStompError: (error) => console.error('STOMP error:', error),
    });
    stompClient.activate();
    setClient(stompClient);

    return () => {
      stompClient.deactivate();
    };
  }, [eventId]);

  const sendMessage = () => {
    if (message && client) {
      client.publish({
        destination: `/app/chat/${eventId}`,
        body: JSON.stringify({ userId: '1', message }), // userId set by backend
      });
      setMessage('');
    }
  };

  return (
    <div>
      <Typography variant="h4">Chat for Event {eventId}</Typography>
      <List style={{ maxHeight: '400px', overflowY: 'auto'}}>
        {messages.map((msg, index) => (
          <ListItem key={index}>
            <ListItemText primary={`${msg.userId}: ${msg.message}`} secondary={new Date(msg.timestamp).toLocaleString()} />
          </ListItem>
        ))}
      </List>
      <TextField
        label="Message"
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        fullWidth
      />
      <Button variant="contained" onClick={sendMessage} style={{ marginTop: '10px' }}>
        Send
      </Button>
    </div>
  );
};

export default Chat;