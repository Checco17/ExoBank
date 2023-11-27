import React, { useEffect, useState } from 'react'
import { findAllContoCorrente, updateConto } from '../service/contoCorrenteService';
import { useHistory } from 'react-router-dom/cjs/react-router-dom.min';
import { findAllStatoContoCorrente } from '../service/contoCorrenteService';
import { ERROR_PAGE, HOME_PAGE_AMMINISTRATORE } from '../utility/Route';
import { useSelector } from 'react-redux';

function ListaContiCorrenti({setErrore}) {

  const history = useHistory();
  const [listaContiCorrenti, setListaContiCorrenti] = useState([])
  const [listaStatoContoCorrente, setListaStatoContoCorrente] = useState([])
  const user = useSelector(state => state.user)
  const [stato, setStato] = useState()
  const [aggiornamento, setAggiornamento] = useState()
 

  useEffect(() => {

    if((!user.utenteId) || user.ruoloId === 2) {
      const errore =  {
        titoloErrore: 'NON HAI I PERMESSI PER ACCEDERE A QUESTA PAGINA',
        descrizione: 'EFFETTUA LA LOGIN DA AMMINISTRATORE'      
      } 
      setErrore(errore);
      history.push(ERROR_PAGE)  

    } else {

      findAllContoCorrente(setListaContiCorrenti);
  
      findAllStatoContoCorrente(setListaStatoContoCorrente);
    }
    
  }, [aggiornamento, history, setErrore, user.ruoloId, user.utenteId]);

  function updateContoCorrente(conto) {
    
    conto.statoContoCorrente.statoContoCorrenteId = stato;

    updateConto(conto, setAggiornamento, aggiornamento);
  }


  return (
    <>

      <div className='containerListaContiCorrente'>

        <h1>Lista ContiCorrenti Inattivi o Sospesi ExoBank</h1>
        <table>
          <thead>
            <tr>
              <th>Numero Conto</th>
              <th>Saldo</th>
              <th>Nome Utente</th>
              <th>Email</th>
              <th>StatoConto</th>
              <th>Modifica Stato</th>
              <th>Conferma</th>
            </tr>
          </thead>
          <tbody>
            {listaContiCorrenti.filter(conto => conto.statoContoCorrente.nomeStato !== 'ATTIVO').map((conto) => (
              <tr key={conto.id}>
                <td> {conto.numeroConto}</td>
                <td> {conto.saldo} €</td>
                <td>{conto.utente.nome}</td>
                <td> {conto.utente.email}</td>
                <td> {conto.statoContoCorrente.nomeStato} </td>
                <td><select
                  onChange={(e) =>  setStato(e.target.value) 
                  } >
                  <option value="">Seleziona uno stato</option>
                  {listaStatoContoCorrente ? (
                    listaStatoContoCorrente.map((statoContoCorrente) => (
                      <option key={statoContoCorrente.statoContoCorrenteId} value={statoContoCorrente.statoContoCorrenteId}>
                        {statoContoCorrente.nomeStato}
                      </option>
                    ))
                  ) : (
                    <>
                    </>
                  )}


                </select>

                </td>
                <td>
                  <button type="button" onClick={() => updateContoCorrente(conto)}>✔️</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
                    <br /><br /><br />

                    
        <h1>Lista ContiCorrenti Attivi ExoBank</h1>
        <table>
          <thead>
            <tr>
              <th>Numero Conto</th>
              <th>Saldo</th>
              <th>Nome Utente</th>
              <th>Email</th>
              <th>StatoConto</th>
              
            </tr>
          </thead>
          <tbody>
            {listaContiCorrenti.filter(conto => conto.statoContoCorrente.nomeStato === 'ATTIVO').map((conto) => (
              <tr key={conto.id}>
                <td> {conto.numeroConto}</td>
                <td> {conto.saldo} €</td>
                <td>{conto.utente.nome}</td>
                <td> {conto.utente.email}</td>
                <td> {conto.statoContoCorrente.nomeStato} </td>
                
              </tr>
            ))}
          </tbody>
        </table>

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

export default ListaContiCorrenti
