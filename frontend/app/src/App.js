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
          <Route exact path="/" component={HomePage} />
          <Route exact path="/register" component={RegisterPage}/>
          <Route exact path="/login" component={LoginPage}/>
          <Route exact path="/posts" component={PostsPage}/>
        </Routes>
    </Router>
  );
}

export default App;