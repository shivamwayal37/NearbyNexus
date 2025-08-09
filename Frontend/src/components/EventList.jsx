import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';  // Changed from window.location
import { getEvents } from '../api';
import { 
  TextField, 
  Button, 
  Grid, 
  Card, 
  CardContent, 
  Typography,
  CircularProgress,
  Box
} from '@mui/material';

const EventList = () => {
  const [events, setEvents] = useState([]);
  const [city, setCity] = useState('Bangalore');
  const [category, setCategory] = useState('Tech');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const fetchEvents = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await getEvents(city, category);
      setEvents(response.data);
    } catch (error) {
      console.error('Error fetching events:', error);
      setError('Failed to fetch events. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchEvents();
  }, []); // Empty dependency array for initial load

  const handleJoinChat = (eventId) => {
    navigate(`/chat/${eventId}`);  // Better than window.location
  };

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" gutterBottom>Event Finder</Typography>
      
      {/* Search Controls */}
      <Grid container spacing={2} sx={{ mb: 3 }}>
        <Grid item xs={12} sm={6} md={4}>
          <TextField
            fullWidth
            label="City"
            value={city}
            onChange={(e) => setCity(e.target.value)}
          />
        </Grid>
        <Grid item xs={12} sm={6} md={4}>
          <TextField
            fullWidth
            label="Category"
            value={category}
            onChange={(e) => setCategory(e.target.value)}
          />
        </Grid>
        <Grid item xs={12} sm={6} md={4}>
          <Button 
            fullWidth 
            variant="contained" 
            onClick={fetchEvents}
            disabled={loading}
            sx={{ height: '56px' }}
          >
            {loading ? <CircularProgress size={24} /> : 'Search'}
          </Button>
        </Grid>
      </Grid>

      {/* Loading and Error States */}
      {loading && <CircularProgress sx={{ display: 'block', mx: 'auto', my: 4 }} />}
      {error && (
        <Typography color="error" align="center" sx={{ my: 2 }}>
          {error}
        </Typography>
      )}

      {/* Events Grid */}
      <Grid container spacing={3}>
        {events.map((event) => (
          <Grid item xs={12} sm={6} md={4} key={event.id}>
            <Card sx={{ height: '100%', display: 'flex', flexDirection: 'column' }}>
              <CardContent sx={{ flexGrow: 1 }}>
                <Typography variant="h6" gutterBottom>{event.title}</Typography>
                <Typography paragraph sx={{ mb: 2 }}>{event.description}</Typography>
                <Typography>Date: {new Date(event.date).toLocaleString()}</Typography>
                <Typography>Location: {event.location}</Typography>
                <Typography sx={{ mb: 2 }}>Category: {event.category}</Typography>
                <Button
                  fullWidth
                  variant="outlined"
                  onClick={() => handleJoinChat(event.id)}
                >
                  Join Chat
                </Button>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Box>
  );
};

export default EventList;