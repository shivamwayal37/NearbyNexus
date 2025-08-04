import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { Route, Routes } from 'react-router-dom'
import ChatTest from './components/ChatTest'

function App() {

  return (
        <div>
            <ChatTest eventId="1" jwtToken="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvcmdhbml6ZXJAZXhhbXBsZS5jb20iLCJpYXQiOjE3NTQzMDE5MjQsImV4cCI6MTc1NDM4ODMyNH0.MvHJDXgpo8VWaAcS_Rbm41y7CnDsVcU1dZrlCkaFXAM" />
        </div>
    );
}

export default App
