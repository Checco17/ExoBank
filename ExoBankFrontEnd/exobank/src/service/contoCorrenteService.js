import axios from "axios"
import { INSERT_CONTO_CORRENTE, FIND_ALL_CONTO_CORRENTE, UPDATE_CONTO_CORRENTE, FIND_CONTO_BY_ID_UTENTE, FIND_ALL_STATO_CONTO_CORRENTE } from "../utility/EndPoint"
import { HOME_PAGE_CLIENTE } from "../utility/Route";

export function apriContoCorrente(contoCorrente, history, setMessage, resetMessage, dispatch, numeroContoCorrente, setNumeroIncremantale){
    return axios.post(INSERT_CONTO_CORRENTE, contoCorrente).then((response) => {
        console.log(response.data);
        setNumeroIncremantale(numeroContoCorrente +1)
        let messaggio = 'Apertura conto avvenuta con successo!';
        dispatch(setMessage({data: messaggio}));      
        history.push(HOME_PAGE_CLIENTE)
      }).catch(error => {
        dispatch(resetMessage())
        console.error('Errore durante l\'apertura del conto:', error.response.data);
      })
  ;
}

export function findAllContoCorrente(setListaContiCorrenti){
    return axios.get(FIND_ALL_CONTO_CORRENTE).then((response) => {
        console.log(response.data)
        setListaContiCorrenti(response.data)
      }).catch((error) => {
        console.error('Errore nella richiesta:', error.response.data);
      });;
}

export function findAllStatoContoCorrente(setListaStatoContoCorrente){
    return axios.get(FIND_ALL_STATO_CONTO_CORRENTE).then((response) => {
        console.log(response.data)
        setListaStatoContoCorrente(response.data)
      }).catch((error) => {
        console.error('Errore nella richiesta:', error.response.data);
      });;
}

export function findContoByIdUtente(user, dispatch, setContoCorrente, setMess){
    return axios.post(FIND_CONTO_BY_ID_UTENTE, user).then((response) => {
        console.log(response)
        dispatch(setContoCorrente(response.data));
      }).catch((error) => {
        console.error('Errore nella richiesta:', error.response.data);
        setMess(error.response.data)
      });;
}

export function updateConto(conto, setAggiornamento, aggiornamento){
    return axios.post(UPDATE_CONTO_CORRENTE, conto).then((response) => {
        console.log(response.data);
        setAggiornamento(!aggiornamento)
  
      }).catch((error) => {
        console.error('Errore update conto: ', error.response.data);
      });
}
