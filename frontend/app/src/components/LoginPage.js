// LoginPage.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/LoginPage.css'
import axios from 'axios';

function LoginPage({setToken}) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('/api/auth/login', { username, password });
      const { token } = response.data;
      setToken(token);
      navigate('/posts')
    } catch (error) {
      console.error('Failed to login:', error);
    }
  };

  return (
    <div className="container">
      <h2>Login</h2>
      <div className="input-group">
        <label htmlFor="username">Username</label>
        <input type="text" id="username" value={username} onChange={(e) => setUsername(e.target.value)} />
      </div>
      <div className="input-group">
        <label htmlFor="password">Password</label>
        <input type="password" id="password" value={password} onChange={(e) => setPassword(e.target.value)} />
      </div>
      <button className="btn" onClick={handleLogin}>Login</button>
    </div>
  );
}

export default LoginPage;