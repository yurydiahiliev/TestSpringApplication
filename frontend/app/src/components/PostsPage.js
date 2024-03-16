import React, { useState, useEffect } from 'react';
import '../css/PostsPage.css'
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function PostsPage({token}) {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(false); 

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const response = await axios.get('/api/posts', {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        setPosts(response.data);
        setLoading(false);
  
      } catch (error) {
        setLoading(false);
        toast.error('Failed to getting posts!')
        console.error('Failed to fetch posts:', error);
      }
    };

    if (token) {
      fetchPosts();
    }
  }, [token]); // Fetch posts whenever the token changes

  return (
    <div className="container">
      <h2>Posts</h2>
      {loading ? (
        <p>Loading posts...</p>
      ) : (
        <ul>
          {posts.map((post) => (
            <li key={post.id} className="post-item">
              <h3>{post.title}</h3>
              <p>{post.content}</p>
            </li>
        ))}
      </ul>
      )}
      <ToastContainer />
    </div>
  );
}

export default PostsPage;
