import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/LoginPage.css';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleInputChange = (setValue) => (e) => {
    setValue(e.target.value);
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await axios.post('/api/auth/login', { username, password }, {
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
        }
      });
      setLoading(false);
      const { token } = response.data;

      if (token) {
        localStorage.setItem('token', token);
        navigate('/posts'); // Navigate to /posts route
      } else {
        toast.error('Token is empty or invalid!');
        console.error('Empty token received'); // Log an error if token is empty
      }

      toast.success('Login successful');

    } catch (error) {
      setLoading(false);
      console.error('Failed to login:', error);
      toast.error('Failed to login. Please try again.');
    }
  };

  return (
    <div className="container">
      <h2>Login</h2>
      <div className="input-group">
        <label htmlFor="username" style={{ display: username ? 'none' : 'block' }}>Username</label>
        <input type="text" id="username" value={username} onChange={handleInputChange(setUsername)} />
        <span></span>
      </div>
      <div className="input-group">
        <label htmlFor="password" style={{ display: password ? 'none' : 'block' }}>Password</label>
        <input type="password" id="password" value={password} onChange={handleInputChange(setPassword)} />
        <span></span>
      </div>
      <button className="btn" onClick={handleLogin} disabled={loading}>
        {loading ? 'Logging in...' : 'Login'}
      </button>
      <ToastContainer />
    </div>
  );
}

export default LoginPage;
