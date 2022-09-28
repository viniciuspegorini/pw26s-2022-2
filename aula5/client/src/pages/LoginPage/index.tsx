import { ChangeEvent, useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { UserLogin } from "../../commons/types";
import { ButtonWithProgress } from "../../components/ButtonWithProgress";
import { Input } from "../../components/Input";
import { AuthContext } from "../../context/AuthContext";

export function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [apiError, setApiError] = useState();
  const [pendingApiCall, setPendingApiCall] = useState(false);
  const { handleLogin } = useContext(AuthContext);

  useEffect(() => {
    // setApiError();
  }, [username, password]);

  const onClickLogin = () => {
    //setPendingApiCall(true);

    const userLogin: UserLogin = {
      username: username,
      password: password
    };
    
    //try {
    handleLogin(userLogin);
    //  setPendingApiCall(false);
    //} catch (error) {
    //  setPendingApiCall(false);
    //}

    /*
    AuthService.login(body).then((response) => {
      setPendingApiCall(false);
      window.location.reload();
      
    }).catch((error) => {
      setPendingApiCall(false);
      setApiError('Login failed');
    });
    */
  };

  let disableSubmit = false;
  if (username === "") {
    disableSubmit = true;
  }
  if (password === "") {
    disableSubmit = true;
  }

  return (
    <div className="container">
      <h1 className="text-center">Login</h1>
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

      <div className="text-center">
        <ButtonWithProgress
          className="btn btn-primary"
          onClick={onClickLogin}
          disabled={pendingApiCall || disableSubmit}
          text="Login"
          pendingApiCall={pendingApiCall}
        />
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
