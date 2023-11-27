import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom'
import { useDispatch, useSelector } from "react-redux";
import { setUser } from '../store/slice/utenteSlice';
import { userLogin, userLoginFromJsf } from '../service/utenteService';
import '../custom/App.css';
import { HOME_PAGE_AMMINISTRATORE, HOME_PAGE_CLIENTE, REGISTRA_UTENTE } from '../utility/Route';
import { resetMessage } from '../store/slice/messageSlice';
import { useLocation } from 'react-router-dom/cjs/react-router-dom.min';


function LoginPage() {

  const validEmail = /^[A-Za-z0-9._%+-]{4,}@([A-Za-z0-9-]{4,}\.)+[A-Za-z]{2,}$/;
  const validPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&!])[A-Za-z\d@#$%^&!]+$/;
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const history = useHistory();
  const dispatch = useDispatch();
  const utente = useSelector((state) => state.user)
  const message = useSelector((state) => state.message)
  const [messRegistrazione, setMessRegistrazione] = useState('');
  const [messError, setMessError] = useState('');
  const [errorEmail, setErrorEmail] = useState('');
  const [errorPassword, setErrorPassword] = useState('');
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const utenteParam = searchParams.get("utente");
  const utenteObject = JSON.parse(decodeURIComponent(utenteParam));
  
  // useEffect(() => {

  //  userLoginFromJsf(dispatch, history, setUser, resetMessage);

  // }, [])
  
  useEffect(() => { 
    
  
    if(utenteParam){
      dispatch(setUser(utenteObject));
      if (utenteObject.ruoloId.nomeRuolo === 'CLIENTE') {
        history.push(HOME_PAGE_CLIENTE);
        dispatch(resetMessage());
      } else if (utenteObject.ruoloId.nomeRuolo === 'AMMINISTRATORE') {
        history.push(HOME_PAGE_AMMINISTRATORE);
      }
    }

    if (message.data) {
      setMessRegistrazione(message.data);
    } else {
      setMessRegistrazione('');
    }

  }, [])


  function login() {

    let checkEmail = false;
    let checkPassword = false;

    if (!validEmail.test(email) || email === '') {
      setErrorEmail("Inserire email valida");
      checkEmail = false;
    } else {
      checkEmail = true;
      setErrorEmail('');

    }

    if (!validPassword.test(password) || password === '') {
      setErrorPassword('Inserire una password valida');
      checkPassword = false;
    } else {
      checkPassword = true;
      setErrorPassword('');

    }

    if (!checkEmail || !checkPassword) {
      return;
    }

    const user = {
      email: email,
      password: password,
    };

    userLogin(user, history, dispatch, setUser, setMessError, resetMessage);

  };

  return (
    <div className="containerLogin">

      <h1>Benvenuto in ExoBank</h1>
      {messRegistrazione && (
        <div style={{ color: 'green' }}>
          <b>{messRegistrazione}</b>
        </div>
      )}
      {messError && (
        <div style={{ color: 'red' }}>
          {messError}
        </div>
      )}
      {errorEmail && (
        <div style={{ color: 'red' }}>
          {errorEmail}
        </div>
      )}
      {errorPassword && (
        <div style={{ color: 'red' }}>
          {errorPassword}
        </div>
      )}
      <form>
        <div>
          <label>
            <span>Username</span>
          </label>
          <input
            id="login__username"
            type="email"
            name="username"
            placeholder="Email"
            value={email}
            onChange={(e) => {
              setEmail(e.target.value);
            }}
            required
          />
        </div>
        <div>
          <label>
            <span>Password</span>
          </label>
          <input
            id="login__password"
            type="password"
            name="password"
            placeholder="Password"
            value={password}
            onChange={(e) => {
              setPassword(e.target.value);
            }}
            required
          />
        </div>
        <div>
          <input type="button" onClick={() => login()} value="ACCEDI" />
        </div>
        <div>
          <input type="button" onClick={() => history.push(REGISTRA_UTENTE)} value="REGISTRATI" />
        </div>
        <div>
          <input type="button" onClick={() => window.location.href = 'http://localhost:8080/ExoBankJSF/'} value="TORNA A JSF" />
        </div>

      </form>
    </div>

  );
}

export default LoginPage;
