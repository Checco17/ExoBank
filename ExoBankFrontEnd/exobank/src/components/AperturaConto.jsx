import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { apriContoCorrente } from '../service/contoCorrenteService';
import { ERROR_PAGE, HOME_PAGE_CLIENTE } from '../utility/Route';
import { setMessage, resetMessage } from '../store/slice/messageSlice';

function AperturaConto({setErrore}) {
  const user = useSelector(state => state.user);
  const history = useHistory();
  const dispatch = useDispatch();
  const [saldo, setSaldo] = useState('');
  const [numeroIncrementale, setNumeroIncremantale] = useState(1000);
  const [errorMessage, setErrorMessage] = useState('');
  const numeroContoCorrente = user.nome.slice(0,2) + user.cognome.slice(0,2) + 'EXO' + numeroIncrementale;

  useEffect(() => {
    
    if((!user.utenteId)  || user.ruoloId === 2){

      const errore =  {
        titoloErrore: 'NON HAI I PERMESSI PER ACCEDERE A QUESTA PAGINA',
        descrizione: 'EFFETTUA LA LOGIN DA CLIENTE'         
      } 
      setErrore(errore);
      history.push(ERROR_PAGE);

    }

    dispatch(resetMessage());

  }, [user.utenteId, user.ruoloId, history, setErrore])


  function apriConto() {

    if(saldo >= 10000 || saldo <= 0){
      setErrorMessage('Non puoi depositare una cifra inferiore a 1€ o superiore a 10.000€');
      return;
    } else {
      setErrorMessage('');
      
    }

    const contoCorrente = {
      numeroConto: numeroContoCorrente,
      saldo: saldo,
      utente: user
    }

    console.log(contoCorrente)   

    apriContoCorrente(contoCorrente, history, setMessage, resetMessage, dispatch, numeroContoCorrente, setNumeroIncremantale);

  }


return (
  <div className="containerAperturaConto">
    <h1>Apertura Conto</h1>
    <p style={{ textAlign: 'left' }}><b>Nome: </b>: {user.nome}</p>
    <p style={{ textAlign: 'left' }}><b>Cognome: </b> {user.cognome}</p>
    <p style={{ textAlign: 'left' }}><b>Email: </b> {user.email}</p>
    <br/>

    <labe>
      Numero del tuo conto corrente:  
    </labe>
    <br /><br />
    <input
      type="text"
      value={numeroContoCorrente}
      
    />
    <br />
    <br />
    <labe>
      Deposito iniziale: 
    </labe>
    <br /><br />
    <input
      type="text"
      placeholder="Deposito Iniziale"
      value={saldo}
      onChange={(e) => setSaldo(e.target.value)}
    />

    
    {errorMessage && <div style={{color : 'red'}}>{errorMessage}</div>}

    <br />
    <button type="button" onClick={() => {apriConto()}}>
      Apri Conto
    </button>
    <button type='button' placeholder='' onClick={() => { history.push(HOME_PAGE_CLIENTE) }}>Indietro </button>
  </div>
);
}

export default AperturaConto;
