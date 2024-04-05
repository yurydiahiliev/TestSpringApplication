import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/RegisterPage.css';
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

  const handleInputChange = (setValue) => (e) => {
    setValue(e.target.value);
  };

  const handleRegister = (e) => {
    e.preventDefault();
    setLoading(true);
    axios
      .post('/api/auth/register', { username, password, first_name, last_name }, {
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
        }
      })
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
    <div className="container-register">
      <h2 className="h2-register">Sign up a new user</h2>
      <div className="input-group-register">
        <label htmlFor="username">Username</label>
        <input type="text" id="username" value={username} onChange={handleInputChange(setUsername)} />
      </div>
      <div className="input-group-register">
        <label htmlFor="password">Password</label>
        <input type="password" id="password" value={password} onChange={handleInputChange(setPassword)} />
      </div>
      <div className="input-group-register">
        <label htmlFor="first_name">First Name</label>
        <input type="text" id="first_name" value={first_name} onChange={handleInputChange(setFirstName)} />
      </div>
      <div className="input-group-register">
        <label htmlFor="last_name">Last Name</label>
        <input type="text" id="last_name" value={last_name} onChange={handleInputChange(setLastName)} />
      </div>
      <button className="btn-register" onClick={handleRegister} disabled={loading}>
        {loading ? 'Registering...' : 'Register'}
      </button>
      <ToastContainer />
    </div>
  );
}

export default RegisterPage;
