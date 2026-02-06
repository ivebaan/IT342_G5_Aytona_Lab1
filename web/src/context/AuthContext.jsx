import React, { createContext, useState, useContext, useEffect } from 'react';
import { authAPI, userAPI } from '../services/api';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Check if user is logged in on mount
    const token = localStorage.getItem('token');
    if (token) {
      fetchUserProfile();
    } else {
      setLoading(false);
    }
  }, []);

  const fetchUserProfile = async () => {
    try {
      const response = await userAPI.getProfile();
      setUser(response.data);
      setError(null);
    } catch (err) {
      console.error('Failed to fetch user profile:', err);
      localStorage.removeItem('token');
      setUser(null);
    } finally {
      setLoading(false);
    }
  };

  const register = async (userData) => {
    try {
      setError(null);
      const response = await authAPI.register(userData);
      const { token, username, email } = response.data;
      
      localStorage.setItem('token', token);
      setUser({ username, email });
      return { success: true };
    } catch (err) {
      const message = err.response?.data || 'Registration failed';
      setError(message);
      return { success: false, error: message };
    }
  };

  const login = async (credentials) => {
    try {
      setError(null);
      const response = await authAPI.login(credentials);
      const { token, username, email } = response.data;
      
      localStorage.setItem('token', token);
      setUser({ username, email });
      return { success: true };
    } catch (err) {
      const message = err.response?.data || 'Login failed';
      setError(message);
      return { success: false, error: message };
    }
  };

  const logout = () => {
    // Call logout API endpoint
    authAPI.logout().catch(err => console.error('Logout API error:', err));
    
    localStorage.removeItem('token');
    setUser(null);
    setError(null);
  };

  const value = {
    user,
    loading,
    error,
    register,
    login,
    logout,
    isAuthenticated: !!user,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
