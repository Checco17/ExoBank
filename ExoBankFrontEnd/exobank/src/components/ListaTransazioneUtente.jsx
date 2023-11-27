import React, { useEffect, useState } from 'react'
import { downloadDocxTransazioneByUtenteId, downloadPdfTransazioneByUtenteId, downloadXlsxTransazioneByUtenteId, findTransazioneByUtenteId } from '../service/transazioneService';
import { useSelector } from 'react-redux';
import { ERROR_PAGE, HOME_PAGE_CLIENTE } from '../utility/Route';
import { useHistory } from 'react-router-dom'
import { formattaData } from '../utility/Utils';




function ListaTransazioneUtente({ setErrore }) {

  const history = useHistory();
  const [listaTransazioniUtente, setListaTransazioniUtente] = useState([])
  const user = useSelector(state => state.user)


  useEffect(() => {

    if ((!user.utenteId) || user.ruoloId === 1) {
      const errore = {
        titoloErrore: 'NON HAI I PERMESSI PER ACCEDERE A QUESTA PAGINA',
        descrizione: 'EFFETTUA LA LOGIN DA CLIENTE'
      }
      setErrore(errore);
      history.push(ERROR_PAGE);

    } else {

      findTransazioneByUtenteId(user, setListaTransazioniUtente);

     
    }


  }, [user, history, setErrore]);

  function download() {

    downloadXlsxTransazioneByUtenteId(user);

  }


  function downloadDocx() {

    downloadDocxTransazioneByUtenteId(user);
    
    }


  function downloadPdf(){

    downloadPdfTransazioneByUtenteId(user);
  }


    return (
      <>

        <div className='containerListaTransazioniUtente'>

          <h1>Lista Transazioni Utente ExoBank</h1>
          <p><b>Nome Utente: </b> {user.nome}</p>
          <p><b>Cognome Utente: </b> {user.cognome}</p>
          <table>
            <thead>
              <tr>
                <th>Data Transazione</th>
                <th>importo</th>
                <th>Numero Conto Corrente</th>
                <th>Stato Transazionee</th>
                <th>Tipo Transazione</th>
                <th>Conto Beneficiario</th>
                

              </tr>
            </thead>
            <tbody>
              {listaTransazioniUtente.map((transazione) => (
                <tr key={transazione.id}>
                  <td> {formattaData(transazione.dataTransazione)}</td>
                  <td> {(transazione.tipoTransazione.tipoTransazione === "DEPOSITO") ? transazione.importo : -transazione.importo} €</td>
                  <td> {transazione.contoCorrente.numeroConto}</td>
                  <td> {transazione.tipoTransazione.tipoTransazione}</td>
                  <td> {transazione.statoTransazione.statoTransazione} </td>
                  <td> {(null == transazione.contoCorrenteBeneficiario) ? "---" : transazione.contoCorrenteBeneficiario.numeroConto}</td>
                  

                </tr>
              ))}
            </tbody>
          </table>
          <br /><br />
          <button
            type="button"
            className="download-button"
            onClick={() => download()}
          >
            <b>Scarica File XLSX <i class="fa-regular fa-file-excel"></i></b>
          </button>

          <button
            type="button"
            className="download-button"
            onClick={() => downloadDocx()}
          >
            <b>Scarica File DOCX <i class="fa-regular fa-file-word"></i></b>
          </button>

          <button
            type="button"
            className="download-button"
            onClick={() => downloadPdf()}
          >
            <b>Scarica File PDF <i class="fa-regular fa-file-pdf"></i></b>
          </button>

          <button
            type="button"
            className="back-button"
            placeholder=""
            onClick={() => {
              history.push(HOME_PAGE_CLIENTE);
            }}
          >
            Indietro
          </button>

        </div>



      </>
    )
  }

  export default ListaTransazioneUtente