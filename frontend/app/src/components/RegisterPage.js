import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/RegisterPage.css'
import axios from 'axios';

function RegisterPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [first_name, setFirstName] = useState('');
  const [last_name, setLastName] = useState('');
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      await axios.post('/api/auth/register', { username, password, first_name, last_name });
      navigate('/login');
    } catch (error) {
      console.error('Failed to register:', error);
    }
  };

  return (
    <div className="container">
      <h2>Register</h2>
      <div className="input-group">
        <label htmlFor="username">Username</label>
        <input type="text" id="username" value={username} onChange={(e) => setUsername(e.target.value)} />
      </div>
      <div className="input-group">
        <label htmlFor="password">Password</label>
        <input type="password" id="password" value={password} onChange={(e) => setPassword(e.target.value)} />
      </div>
      <div className="input-group">
        <label htmlFor="first_name">First Name</label>
        <input type="text" id="first_name" value={first_name} onChange={(e) => setFirstName(e.target.value)} />
      </div>
      <div className="input-group">
        <label htmlFor="last_name">Last Name</label>
        <input type="text" id="last_name" value={last_name} onChange={(e) => setLastName(e.target.value)} />
      </div>
      <button className="btn" onClick={handleRegister}>Register</button>
    </div>
  );
}

export default RegisterPage;