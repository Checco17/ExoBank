import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom'
import { findContoByIdUtente } from '../service/contoCorrenteService';
import { setContoCorrente } from '../store/slice/contoCorrenteSlice';
import { APERTURA_CONTO, OPERAZIONE_CLIENTE, LISTA_TRANSAZIONI_UTENTE, ERROR_PAGE } from '../utility/Route';
import { resetMessage, setMessage } from '../store/slice/messageSlice';



function HomePageCliente({ setErrore }) {

  const user = useSelector((state) => state.user);
  const message = useSelector((state) => state.message)
  const contoCorrente = useSelector(state => state.contoCorrente);
  const [mess, setMess] = useState('')
  const dispatch = useDispatch();
  const history = useHistory();
 
  useEffect(() => {   
    
    if ((!user.utenteId) || user.ruoloId === 1) {
      const errore = {
        titoloErrore: 'NON HAI I PERMESSI PER ACCEDERE A QUESTA PAGINA',
        descrizione: 'EFFETTUA LA LOGIN DA CLIENTE'
      }
      setErrore(errore);
      history.push(ERROR_PAGE);
    } else if (user.ruoloId.ruoloId === 2) {
      findContoByIdUtente(user, dispatch, setContoCorrente, setMess);     
    }
  }, [user]);

  useEffect(()=>{

  }, [contoCorrente])

  function apriConto() {   
      history.push(APERTURA_CONTO);    
  }

  function effettuaOperazione() {
      console.log(contoCorrente.statoContoCorrente.statoContoCorrenteId);
    if (contoCorrente.statoContoCorrente.statoContoCorrenteId === 1) {
      history.push(OPERAZIONE_CLIENTE);
      dispatch(resetMessage());    
    } else{
      dispatch(setMessage({data:'Il tuo conto è stato creato ma non è stato ancora approvato!'}))
      return;      
    }    
  }  

  return (
    <div className="containerHomePageCliente">
      <h1>Homepage Cliente:</h1>

      {message.data === 'Il tuo conto è stato creato ma non è stato ancora approvato!' ? (
        <div style={{ color: 'red' }}>
          <b>{message.data}</b>
        </div>
      ) : (<>
        <div style={{ color: 'green' }}>
          <b>{message.data}</b>
        </div>
      </>)}
      <p style={{ textAlign: 'left' }}><b>Nome Cliente:</b> {user.nome} </p>
      <p style={{ textAlign: 'left' }}><b>Cognome Cliente:</b> {user.cognome} </p>
      <p style={{ textAlign: 'left' }}><b>Email Cliente:</b> {user.email} </p>

      {contoCorrente.numeroConto ? <div>
        <h1>Dati del tuo conto:  </h1>
        <p style={{ textAlign: 'left' }}><b>Numero Conto:</b> {contoCorrente.numeroConto}</p>
        <p style={{ textAlign: 'left' }}><b>Saldo Conto:</b> {contoCorrente.saldo} €</p>
        <button type="button" onClick={() => effettuaOperazione()}>EFFETTUA UNA OPERAZIONE</button>
        <button type="button" onClick={() => { history.push(LISTA_TRANSAZIONI_UTENTE); dispatch(resetMessage()) }}>LISTA TUE TRANSAZIONI</button>

      </div>
        : <div><p style={{ textAlign: 'center', color: 'red' }}><b>{mess}</b></p>
          <button type="button" onClick={() => apriConto()}>APRI CONTO</button> </div>
      }
    </div>
  )
}

export default HomePageCliente
