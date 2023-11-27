import axios from "axios"
import { DOWNLOAD_DOCX_TRANSAZIONE_BY_UTENTE_ID, DOWNLOAD_PDF_TRANSAZIONE_BY_UTENTE_ID, DOWNLOAD_TRANSAZIONE_BY_UTENTE_ID, FIND_ALL_STATO_TRANSAZIONE, FIND_ALL_TIPO_TRANSAZIONE, FIND_ALL_TRANSAZIONE, FIND_TRANSAZIONE_BY_ID, FIND_TRANSAZIONE_BY_UTENTE_ID, INSERT_TRANSAZIONE, UPDATE_TRANSAZIONE } from "../utility/EndPoint"
import { HOME_PAGE_CLIENTE } from "../utility/Route";

export function findAllTransazione(setListaTransazioni){
    return axios.get(FIND_ALL_TRANSAZIONE).then((response) => {
        console.log(response.data)
        setListaTransazioni(response.data)
      }).catch((error) => {
        console.error('Errore nella richiesta:', error.response.data);
      });
  ;
}
export function findTransazioneById(transazione){
    return axios.post(FIND_TRANSAZIONE_BY_ID, transazione);
}
export function insertTransazione(transazione, history, setMessage, dispatch){
    return axios.post(INSERT_TRANSAZIONE, transazione).then((response) => {
        console.log(response.data)
        dispatch(setMessage({data : 'Transazione andata a buon fine!'}))
        history.push(HOME_PAGE_CLIENTE)
      }).catch(error => {
        console.error('Errore nella richiesta: ' + error.response.data)
      });
}

export function updateTransazione(transazione, aggiornamento, setAggiornamento){
    return axios.post(UPDATE_TRANSAZIONE, transazione).then((response) => {
        console.log(response.data);
        setAggiornamento(!aggiornamento)
  
      }).catch((error) => {
        console.error('Errore update conto: ', error.response.data);
      });;
}

export function findAllStatoTransazione(setListaStatoTransazioni){
    return axios.get(FIND_ALL_STATO_TRANSAZIONE).then((response) => {
        console.log(response.data)
        setListaStatoTransazioni(response.data)
      }).catch((error) => {
        console.error('Errore nella richiesta:', error.response.data);
      });;
}

export function findAllTipoTransazione(setListaTipoTransazione){
    return axios.get(FIND_ALL_TIPO_TRANSAZIONE).then((response) => {
        console.log(response.data)
        setListaTipoTransazione(response.data)
      }).catch(error => {
        console.error('Errore nella richiesta: ' + error.response.data)
      });
}

export function findTransazioneByUtenteId(user, setListaTransazioniUtente){
    return axios.post(FIND_TRANSAZIONE_BY_UTENTE_ID, user).then((response) => {
        console.log(response.data)
        setListaTransazioniUtente(response.data)
      }).catch((error) => {
        console.error('Errore nella richiesta:', error.response.data);
      });;
}


export function downloadXlsxTransazioneByUtenteId(user){
    return axios.post(DOWNLOAD_TRANSAZIONE_BY_UTENTE_ID, user, { responseType: 'blob' })
    .then(response => {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const a = document.createElement('a');
      a.href = url;
      a.download = 'riepilogoTransazioni.xlsx';
      document.body.appendChild(a);
      a.click();
      window.URL.revokeObjectURL(url);
    })
    .catch(error => {
      console.error('Errore nel download:', error);
    });;
}

export function downloadDocxTransazioneByUtenteId(user){
    return axios.post(DOWNLOAD_DOCX_TRANSAZIONE_BY_UTENTE_ID, user, { responseType: 'blob' })
    .then(response => {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const a = document.createElement('a');
      a.href = url;
      a.download = 'riepilogoTransazioni.docx';
      document.body.appendChild(a);
      a.click();
      window.URL.revokeObjectURL(url);
    })
    .catch(error => {
      console.error('Errore nel download:', error);
    });
}

export function downloadPdfTransazioneByUtenteId(user){
  return axios.post(DOWNLOAD_PDF_TRANSAZIONE_BY_UTENTE_ID, user, { responseType: 'blob' })
  .then(response => {
    const url = window.URL.createObjectURL(new Blob([response.data]));
    const a = document.createElement('a');
    a.href = url;
    a.download = 'riepilogoTransazioni.pdf';
    document.body.appendChild(a);
    a.click();
    window.URL.revokeObjectURL(url);
  })
  .catch(error => {
    console.error('Errore nel download:', error);
  });
}

