import { useEffect, useState } from 'react';
import axios from 'axios';


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
        setLoading(true);
        const response = await axios.post('/login', user);
        setAuthenticated(true);
        setLoading(false);
        localStorage.setItem('token', JSON.stringify(response.data.token));
    }

    async function handleLogout() {
        setAuthenticated(false);
        localStorage.removeItem('token');
        
    }

    return {authenticated, loading, handleLogin, handleLogout};

}