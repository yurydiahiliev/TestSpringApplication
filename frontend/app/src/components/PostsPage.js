import React, { useState, useEffect } from 'react';
import '../css/PostsPage.css';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function PostsPage() {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [newPostTitle, setNewPostTitle] = useState('');
  const [newPostContent, setNewPostContent] = useState('');
  const token = localStorage.getItem('token');

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
      toast.error('Failed to get posts!');
      console.error('Failed to fetch posts:', error);
    }
  };

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
        toast.error('Failed to get posts!');
        console.error('Failed to fetch posts:', error);
      }
    };

    fetchPosts();
  
  }, [token]);

  const handleCreatePost = async () => {
    try {
      const newPost = { title: newPostTitle, content: newPostContent };
      await axios.post('/api/posts', newPost, { headers: { 'Authorization': `Bearer ${token}` } });
      fetchPosts();
      setNewPostTitle('');
      setNewPostContent('');
      toast.success('Post created successfully!', {
        position: "top-right",
        autoClose: 1000,
        hideProgressBar: true,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        theme: "light",
        });
    } catch (error) {
      toast.error('Failed to create post!');
      console.error('Failed to create post:', error);
    }
  };

  return (
    <div className="container">
      <h2>Posts</h2>
      <div>
        <input
          type="text"
          placeholder="Enter title"
          value={newPostTitle}
          onChange={(e) => setNewPostTitle(e.target.value)}
        />
        <textarea
          placeholder="Enter content"
          value={newPostContent}
          onChange={(e) => setNewPostContent(e.target.value)}
        ></textarea>
        <button onClick={handleCreatePost}>Create Post</button>
      </div>
      {loading ? (
        <p>Loading posts...</p>
      ) : (
        <table className="post-table">
          <thead>
            <tr>
              <th>Title</th>
              <th>Content</th>
            </tr>
          </thead>
          <tbody>
            {posts.map((post) => (
              <tr key={post.id}>
                <td>{post.title}</td>
                <td>{post.content}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
      <ToastContainer />
    </div>
  );
}

export default PostsPage;
