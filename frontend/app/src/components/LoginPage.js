import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/LoginPage.css'
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import { useToken } from '../TokenContext';
import 'react-toastify/dist/ReactToastify.css';

function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false); 
  const navigate = useNavigate();

  const { setToken } = useToken();

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await axios.post('/api/auth/login', { username, password }, { headers: { 'Content-Type': 'application/json' } });
      setLoading(false);
      const { token } = response.data;
      if (token) {
        navigate('/posts'); // Navigate to /posts route
      } else {
        toast.error('Token is empty or invalid!')
        console.error('Empty token received'); // Log an error if token is empty
      }
      setToken(token)
      toast.success('Login successful');
      
    } catch (error) {
      setLoading(false);
      console.error('Failed to login:', error);
      toast.error('Failed to login. Please try again.');
    }
  };

  const handleLogout = () => {
    setToken(''); // Clear token
    toast.success('Logged out successfully');
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
      <button className="btn" onClick={handleLogin} disabled={loading}>
        {loading ? 'Logging in...' : 'Login'}
      </button>
      <ToastContainer />
      <button onClick={handleLogout}>Logout</button> {/* Logout button */}
    </div>
  );
}

export default LoginPage;