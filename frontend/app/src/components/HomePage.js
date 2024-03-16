import React from 'react';
import { Link } from 'react-router-dom';
import '../css/HomePage.css'

function HomePage() {
    return (
        <div className="container">
          <h2>Welcome to Posts App</h2>
          <p>Please select an option:</p>
          <ul>
            <li><Link to="/login" className="link-btn">Login</Link></li>
            <li><Link to="/register" className="link-btn">Register</Link></li>
          </ul>
        </div>
      );
}

export default HomePage;
