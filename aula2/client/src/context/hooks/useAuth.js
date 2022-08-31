import axios from 'axios';
import React, { useEffect, useState } from 'react';

export default function useAuth() {
    const [authenticated, setAuthenticated] = useState(false);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            setAuthenticated(true);
        }
    }, []);

    async function handleLogin(user) {
        const response = await axios.post('/login', user);
        console.log(response);
        setAuthenticated(true);
        localStorage.setItem('token', JSON.stringify(response.data.token));
    }

    async function handleLogout() {
        setAuthenticated(false);
        localStorage.removeItem('token');
        
    }

    return {authenticated, loading, handleLogin, handleLogout};

}