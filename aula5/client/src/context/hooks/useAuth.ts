import { useState, useEffect } from "react";
import { api } from '../../lib/axios'
import history from '../../config/history'
import { UserLogin } from "../../commons/types";


export function useAuth() {
  const [authenticated, setAuthenticated] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (token) {
      api.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(token)}`;
      setAuthenticated(true);
    }

    setLoading(false);
  }, []);

  async function handleLogin( user: UserLogin ) {
    const response = await api.post("/login", user);
    console.log(response);

    localStorage.setItem("token", JSON.stringify(response.data.token));
    api.defaults.headers.common['Authorization'] = `Bearer ${response.data.token}`;
    setAuthenticated(true);
    history.push("/home");
  }

  function handleLogout() {
    setAuthenticated(false);
    localStorage.removeItem("token");
    api.defaults.headers.common['Authorization'] = '';
    history.push("/login");
  }

  return { authenticated, loading, handleLogin, handleLogout };
}
