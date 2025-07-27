-- Drop existing tables if they exist
DROP TABLE IF EXISTS rsvps;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS users;

-- Users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ATTENDEE', 'ORGANIZER') NOT NULL
);

-- Events table
CREATE TABLE events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    date DATETIME,
    location VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE,
    category VARCHAR(50),
    organizer_id BIGINT,
    FOREIGN KEY (organizer_id) REFERENCES users(id)
);

-- RSVPs table
CREATE TABLE rsvps (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (event_id) REFERENCES events(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    UNIQUE KEY unique_rsvp (event_id, user_id)
);
