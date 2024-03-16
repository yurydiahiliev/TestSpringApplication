import React from 'react';
import './index.css';

import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginPage from './components/LoginPage';
import RegisterPage from './components/RegisterPage';
import HomePage from './components/HomePage';
import PostsPage from './components/PostsPage';

const App = () => {
  return (
    <Router>
        <Routes>
          <Route exact path="/" element={<HomePage/>} />
          <Route exact path="/register" element={<RegisterPage/>}/>
          <Route exact path="/login" element={<LoginPage/>}/>
          <Route exact path="/posts" element={<PostsPage/>}/>
        </Routes>
    </Router>
  );
}

export default App;