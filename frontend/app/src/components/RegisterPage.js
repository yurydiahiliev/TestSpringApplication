import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/RegisterPage.css'
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function RegisterPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [first_name, setFirstName] = useState('');
  const [last_name, setLastName] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleRegister = (e) => {
    e.preventDefault();
    setLoading(true);
    axios
      .post('/api/auth/register', { username, password, first_name, last_name })
      .then(() => {
        setLoading(false);
        toast.success('User created successfully!');
        navigate('/login');
      })
      .catch((error) => {
        setLoading(false);
        console.error('Failed to register:', error);
        toast.error('Failed to register. Please try again.');
      });
  };

  return (
    <div className="container">
      <h2>Sign in a new user</h2>
      <div className="input-group">
        <input type="text" id="username" value={username} onChange={(e) => setUsername(e.target.value)} />
      </div>
      <div className="input-group">
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
      <button className="btn" onClick={handleRegister} disabled={loading}>
      {loading ? 'Registering...' : 'Register'}
      </button>
      <ToastContainer />
    </div>
  );
}

export default RegisterPage;