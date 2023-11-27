import axios from "axios";
import { INSERT_CLIENTE, LOGIN_UTENTE, FIND_ALL_UTENTE, LOGIN_UTENTE_FROM_JSF } from "../utility/EndPoint";
import { HOME_PAGE_AMMINISTRATORE, HOME_PAGE_CLIENTE, LOGIN_PAGE } from "../utility/Route";

export function userLogin(user, history, dispatch, setUser, setMessError,  resetMessage){
    return axios.post(LOGIN_UTENTE, user).then((response) => {
      
        dispatch(setUser(response.data));      
     
          if (response.data.ruoloId.nomeRuolo === 'CLIENTE') {
            history.push(HOME_PAGE_CLIENTE);
            dispatch(resetMessage());
          } else if (response.data.ruoloId.nomeRuolo === 'AMMINISTRATORE') {
            history.push(HOME_PAGE_AMMINISTRATORE);
          }
        
      }).catch(error => {
        console.error('Errore durante il login:', error.response.data);
        setMessError(error.response.data);        
      });
}

export function registerUser(user, dispatch, history, setMessage){
    return axios.post(INSERT_CLIENTE, user).then((response) => {
        console.log(response.data);
        dispatch(setMessage({data: 'Registrazione avvenuta con successo!'}))
        history.push(LOGIN_PAGE);
      })
      .catch((error) => {
        console.error('Errore durante il registrazione:', error.response.data);
      });;
}

export function findAllUtente(setListaUser) {
    return axios.get(FIND_ALL_UTENTE).then((response) => {
        console.log(response.data)
        setListaUser(response.data);
      })
      .catch((error) => {
        console.error('Errore nella richiesta:', error.response.data);
      });
}

export function userLoginFromJsf(dispatch, history, setUser, resetMessage){
  axios.get(LOGIN_UTENTE_FROM_JSF).then((response) => {
      if (response && response.data && response.data !== '' && response.data !== undefined && response.data !== null) {
        console.log(response.data)
        dispatch(setUser(response.data));

        if (response.data.ruoloId.ruoloId === 2) {
          history.push(HOME_PAGE_CLIENTE);
          dispatch(resetMessage());
        } else if (response.data.ruoloId.ruoloId === 1) {
          history.push(HOME_PAGE_AMMINISTRATORE);
        }
      }
    }).catch(error => {
      console.error('Errore durante il login:', error.response.data);

    });
}

