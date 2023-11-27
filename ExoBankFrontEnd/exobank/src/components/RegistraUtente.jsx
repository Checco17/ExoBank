import React, { useEffect, useState } from 'react';
import { registerUser } from '../service/utenteService';
import { useHistory } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { LOGIN_PAGE } from '../utility/Route';
import { setMessage } from '../store/slice/messageSlice';

function RegistraUtente() {

  const validNome = /^[A-Za-z]+\s?[A-Za-z]*$/;
  const validEmail = /^[A-Za-z0-9._%+-]{4,}@([A-Za-z0-9-]{4,}\.)+[A-Za-z]{2,}$/ ;
  const validPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&!])[A-Za-z\d@#$%^&!]+$/;
  const history = useHistory();
  const [nome, setNome] = useState('');
  const [cognome, setCognome] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confermaPassword, setConfermaPassword] = useState('');
  const [nomeError, setNomeError] = useState('');
  const [cognomeError, setCognomeError] = useState('');
  const [emailError, setEmailError] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const [passwordMatchMessage, setPasswordMatchErrorMessage] = useState('');  
  const dispatch = useDispatch();


  useEffect(() => {

    if (password !== confermaPassword) {
      setPasswordMatchErrorMessage('Le password non corrispondono');
    } else {
      setPasswordMatchErrorMessage('Le password corrispondono');
    }
  }, [password, confermaPassword]);


  function register() {

    let checkNome = false;
    let checkCognome = false;
    let checkEmail = false;
    let checkPassword = false;
    let checkMatchPass = false;

    

    if (!validNome.test(nome) || nome.length <= 3 || nome === '' || nome.length >= 20) {
      setNomeError('Inserire un nome valido');
      checkNome = false;
    } else {
      setNomeError('');
      checkNome = true;
    }
    if (!validNome.test(cognome) || cognome.length <= 3 || cognome === '' || cognome.length >= 20) {
      setCognomeError('Inserire un cognome valido');
      checkCognome = false;
    } else {
      setCognomeError('');
      checkCognome = true;
    }
    if (!validEmail.test(email) || email === '' || email.length <= 8 || email.length >= 20) {
      setEmailError("Inserire email valida");
      checkEmail = false;      
    } else {
      setEmailError('');
      checkEmail = true;
    }
    if (!validPassword.test(password) || password === '' || password.length <= 8 || password.length >= 20) {
      setPasswordError('Inserire una password valida');
      checkPassword = false;      
    } else {
      setPasswordError('');
      checkPassword = true;
    }
    if(passwordMatchMessage === 'Le password non corrispondono'){
      checkMatchPass = false;
    } else {
      checkMatchPass = true;
    }

    if(checkNome && checkCognome && checkEmail && checkPassword && checkMatchPass){

      const userData = {
        nome: nome,
        cognome: cognome,
        email: email,
        password: password,
      };
  
      registerUser(userData, dispatch, history, setMessage);        
        
    } else {
      return;
    }

   
  }

  return (
    <div className="containerRegister">
      <h1>Registrazione</h1>
      <div>
        <label>
          <span>Nome</span>
        </label>
        <input
          type="text"
          name="nome"
          value={nome}
          placeholder="Inserisci nome"
          onChange={(e) => {
            setNome(e.target.value);
          }}
        />
        {nomeError && <div style={{ color: 'red' }}>{nomeError}</div>}
      </div>
      <div>
        <label>
          <span>Cognome</span>
        </label>
        <input
          type="text"
          name="cognome"
          value={cognome}
          placeholder="Inserisci cognome"
          onChange={(e) => {
            setCognome(e.target.value);
          }}
        />
        {cognomeError && <div style={{ color: 'red' }}>{cognomeError}</div>}
      </div>
      <div>
        <label>
          <span>Email</span>
        </label>
        <input
          type="email"
          name="email"
          value={email}
          placeholder="Inserisci email"
          onChange={(e) => {
            setEmail(e.target.value);
          }}
        />
        {emailError && <div style={{ color: 'red' }}>{emailError}</div>}
      </div>
      <div>
        <label>
          <span>Password</span>
        </label>
        <input
          type="password"
          name="password"
          value={password}
          placeholder="Inserisci password"
          onChange={(e) => {
            setPassword(e.target.value);
          }}
        />
        {passwordError && <div style={{ color: 'red' }}>{passwordError}</div>}
        <div>
          <label>
            <span>Conferma Password</span>
          </label>
          <input
            type="password"
            name="confermaPassword"
            value={confermaPassword}
            placeholder="Conferma password"
            onChange={(e) => {
              setConfermaPassword(e.target.value);
            }}
            required
          />
          {passwordMatchMessage === 'Le password non corrispondono' && <div style={{color : 'red'}}>{passwordMatchMessage}</div>}
          {passwordMatchMessage === 'Le password corrispondono' && <div style={{color : 'green'}}>{passwordMatchMessage}</div>}
        </div>

        <br /> <br />
      </div>
      <button type="button" onClick={() => register()}>
        REGISTRATI
      </button>
      <button type="button" onClick={() => history.push(LOGIN_PAGE)}>
        INDIETRO
      </button>
    </div>
  );
}

export default RegistraUtente;
