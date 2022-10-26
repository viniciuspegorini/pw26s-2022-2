import { ChangeEvent, useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { UserLogin } from "../../commons/types";
import { ButtonWithProgress } from "../../components/ButtonWithProgress";
import { Input } from "../../components/Input";
import { AuthContext } from "../../context/AuthContext";
import { CredentialResponse, GoogleLogin } from "@react-oauth/google";

export function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [apiError, setApiError] = useState("");
  const [pendingApiCall, setPendingApiCall] = useState(false);
  const { handleLogin, handleLoginSocial, loading } = useContext(AuthContext);

  const onSuccess = (response: CredentialResponse) => {
    console.log(response);

    if (response.credential) {
      handleLoginSocial(response.credential);
    }
  };

  useEffect(() => {
    setApiError("");
  }, [username, password]);

  useEffect(() => {
    setPendingApiCall(loading);
    if (!loading && username) {
      setApiError("Usuário ou senha inválidos!");
    }
  }, [loading]);

  const onClickLogin = () => {
    const userLogin: UserLogin = {
      username: username,
      password: password,
    };

    handleLogin(userLogin);
  };

  let disableSubmit = false;
  if (username === "") {
    disableSubmit = true;
  }
  if (password === "") {
    disableSubmit = true;
  }

  return (
    <div className="container  text-center">
      <div className="text-center">
        <h1>Login</h1>
      </div>
      <div className="col-12 mb-3">
        <Input
          label="Informe o seu nome"
          className="form-control"
          type="text"
          placeholder="Your username"
          value={username}
          onChange={(event: ChangeEvent<HTMLInputElement>) =>
            setUsername(event.target.value)
          }
          name="username"
          hasError={false}
          error=""
        />
      </div>
      <div className="col-12 mb-3">
        <Input
          label="Informe a sua senha"
          className="form-control"
          type="password"
          placeholder="Your password"
          value={password}
          onChange={(event: ChangeEvent<HTMLInputElement>) =>
            setPassword(event.target.value)
          }
          name="password"
          hasError={false}
          error=""
        />
      </div>

      <div className="container">
        <div className="row">
          <div className="col text-end">
            <ButtonWithProgress
              className="btn btn-primary"
              onClick={onClickLogin}
              disabled={pendingApiCall || disableSubmit}
              text="Login"
              pendingApiCall={pendingApiCall}
            />
          </div>

          <div className="col text-start">
            <GoogleLogin
              locale="pt-BR"
              onSuccess={onSuccess}
              onError={() => {
                setApiError("Falha ao autenticar-se com o Google");
                console.log("Login Failed");
              }}
            />
          </div>
        </div>
      </div>

      {apiError && (
        <div className="col-12 mb-3">
          <div className="alert alert-danger">{apiError}</div>
        </div>
      )}
      <div className="text-center">
        não possui cadastro? <Link to="/signup">Cadastrar-se</Link>
      </div>
    </div>
  );
}
