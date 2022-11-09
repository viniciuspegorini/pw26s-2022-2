import { useState, useEffect } from "react";
import { api } from "../../lib/axios";
import history from "../../config/history";
import { AuthenticatedUser, AuthenticationResponse, UserLogin } from "../../commons/types";

export function useAuth() {
  const [authenticated, setAuthenticated] = useState(false);
  const [authenticatedUser, setAuthenticatedUser] = useState<AuthenticatedUser>();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (token) {
      api.defaults.headers.common["Authorization"] = `Bearer ${JSON.parse(
        token
      )}`;
      setAuthenticated(true);
    }

    setLoading(false);
  }, []);



  async function handleLoginOld(user: UserLogin) {
    try {
      setLoading(true);
      const response = await api.post("/login", user);
      setLoading(false);

      localStorage.setItem("token", JSON.stringify(response.data.token));
      api.defaults.headers.common[
        "Authorization"
      ] = `Bearer ${response.data.token}`;

      console.log(response.data.user);
      setAuthenticatedUser(response.data.user);

      setAuthenticated(true);
      console.log("Passou");
      history.push("/home");
    } catch (error) {
      console.log("Err:" + error);
      setLoading(false);
    }
  }

  async function handleLoginSocial(idToken: string) {
    setLoading(true);
    api.defaults.headers.common["Auth-Id-Token"] = `Bearer ${idToken}`;
    const response = await api.post("/auth");
    console.log(response);
    setLoading(false);

    api.defaults.headers.common["Auth-Id-Token"] = "";
    localStorage.setItem("token", JSON.stringify(response.data.token));
    api.defaults.headers.common[
      "Authorization"
    ] = `Bearer ${response.data.token}`;
    setAuthenticated(true);
    history.push("/home");
  }

  function handleLogout() {
    setAuthenticated(false);
    localStorage.removeItem("token");
    api.defaults.headers.common["Authorization"] = "";
    setAuthenticatedUser(undefined);
  }

  function handleLogin(response: AuthenticationResponse) {
    localStorage.setItem("token", JSON.stringify(response.token));
    localStorage.setItem("user", JSON.stringify(response.user));
    api.defaults.headers.common[
      "Authorization"
    ] = `Bearer ${response.token}`;
    console.log(response.user);
    setAuthenticatedUser(response.user);
    setAuthenticated(true);
  }

  function checkIsAuthenticated() {
    if (localStorage.getItem("token") && localStorage.getItem("user")) {
      const token = JSON.parse(localStorage.getItem("token") || '{}');
      const user = JSON.parse(localStorage.getItem("user") || '{}');

      api.defaults.headers.common[
        "Authorization"
      ] = `Bearer ${token}`;
      api.defaults.headers.common[
        "Authorization"
      ] = `Bearer ${token}`;
      setAuthenticatedUser(user);
      setAuthenticated(true);
    }
  }

  return {
    authenticated,
    authenticatedUser,
    loading,
    handleLogin,
    handleLoginSocial,
    handleLogout,
    checkIsAuthenticated
  };
}
