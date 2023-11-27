import React, { useEffect, useState } from 'react'
import { useHistory } from 'react-router-dom/cjs/react-router-dom.min';
import { findAllStatoTransazione, findAllTransazione, updateTransazione } from '../service/transazioneService';
import { ERROR_PAGE, HOME_PAGE_AMMINISTRATORE } from '../utility/Route';
import { useSelector } from 'react-redux';
import { formattaData } from '../utility/Utils';


function ListaTransazioni({ setErrore }) {

  const history = useHistory();
  const [listaTransazioni, setListaTransazioni] = useState([])
  const [listaStatoTransazioni, setListaStatoTransazioni] = useState([])
  const [stato, setStato] = useState()
  const user = useSelector(state => state.user)
  const [aggiornamento, setAggiornamento] = useState()


  useEffect(() => {

    if ((!user.utenteId) || user.ruoloId === 2) {
      const errore = {
        titoloErrore: 'NON HAI I PERMESSI PER ACCEDERE A QUESTA PAGINA',
        descrizione: 'EFFETTUA LA LOGIN DA AMMINISTRATORE'
      }
      setErrore(errore);
      history.push(ERROR_PAGE)

    } else {

      findAllTransazione(setListaTransazioni);

      findAllStatoTransazione(setListaStatoTransazioni);

    }

  }, [aggiornamento]);

  function modificaTransazione(transazione) {

    transazione.statoTransazione.id = stato;
    console.log(stato)
    console.log(transazione)


    updateTransazione(transazione, aggiornamento, setAggiornamento);

  }


  return (
    <>

      <div className='containerListaTransazioni'>

        <h1>Lista Transazioni in Corso ExoBank</h1>
        <table>
          <thead>
            <tr>
              <th>Data Transazione</th>
              <th>importo</th>
              <th>Numero Conto Corrente</th>
              <th>Stato Transazionee</th>
              <th>Tipo Transazione</th>
              <th>Conto Beneficiario</th>
              <th>Modifica Stato</th>
              <th>Conferma</th>
            </tr>
          </thead>
          <tbody>
            {listaTransazioni.filter(transazione => transazione.statoTransazione.statoTransazione !== 'APPROVATA').map((transazione) => (
              <tr key={transazione.id}>
                <td> {formattaData(transazione.dataTransazione)}</td>
                <td> {(transazione.tipoTransazione.tipoTransazione === "DEPOSITO") ? transazione.importo : -transazione.importo} €</td>
                <td> {transazione.contoCorrente.numeroConto}</td>                
                <td>{transazione.tipoTransazione.tipoTransazione}</td>
                <td> {transazione.statoTransazione.statoTransazione} </td>
                <td> {(null == transazione.contoCorrenteBeneficiario) ? "---" : transazione.contoCorrenteBeneficiario.numeroConto}</td>



                <td><select
                  onChange={(e) => setStato(e.target.value)
                  } >
                  <option value="">Seleziona uno stato</option>
                  {listaStatoTransazioni ? (
                    listaStatoTransazioni.map((statoTransazione) => (
                      <option key={statoTransazione.id} value={statoTransazione.id}>
                        {statoTransazione.statoTransazione}
                      </option>
                    ))
                  ) : (
                    <>
                    </>
                  )}


                </select>

                </td>
                <td>
                  <button type="button" onClick={() => modificaTransazione(transazione)}>✔️</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <br /><br /><br />
        <h1>Lista Transazioni Approvate ExoBank</h1>
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
            {listaTransazioni.filter(transazione => transazione.statoTransazione.statoTransazione === 'APPROVATA').map((transazione) => (
              <tr key={transazione.id}>
                <td> {formattaData(transazione.dataTransazione)}</td>
                <td> {(transazione.tipoTransazione.tipoTransazione === "DEPOSITO") ? transazione.importo : -transazione.importo} €</td>
                <td> {transazione.contoCorrente.numeroConto}</td>
                <td>{transazione.tipoTransazione.tipoTransazione}</td>
                <td> {transazione.statoTransazione.statoTransazione} </td>                
                <td> {(null == transazione.contoCorrenteBeneficiario) ? "---" : transazione.contoCorrenteBeneficiario.numeroConto}</td>



              </tr>
            ))}
          </tbody>
        </table>

        <br /><br /><br />

        <button
          type="button"
          className="back-button"
          placeholder=""
          onClick={() => {
            history.push(HOME_PAGE_AMMINISTRATORE);
          }}
        >
          INDIETRO
        </button>

      </div>



    </>
  )
}

export default ListaTransazioni
