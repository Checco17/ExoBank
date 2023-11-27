import React, { useEffect, useState } from 'react'
import { useHistory } from 'react-router-dom/cjs/react-router-dom.min'
import { insertTransazione, findAllTipoTransazione } from '../service/transazioneService'
import { useDispatch, useSelector } from 'react-redux'
import { ERROR_PAGE, HOME_PAGE_CLIENTE } from '../utility/Route';
import { findAllContoCorrente } from '../service/contoCorrenteService';
import { setMessage } from '../store/slice/messageSlice';


function OperazioneCliente({ setErrore }) {

  const [listaTipoOperazione, setListaTipoTransazione] = useState([]);
  const [listaContiCorrenti, setListaContiCorrenti] = useState([])  
  const contoCorrente = useSelector(state => state.contoCorrente)
  const [beneficiario, setBeneficiario] = useState('')
  const [descrizione, setDescrizione] = useState('')
  const [errorMessage, setErrorMessage] = useState('');
  const [tipo, setTipo] = useState();
  const [importo, setImporto] = useState();
  const user = useSelector(state => state.user)
  const dispatch = useDispatch();
  const history = useHistory()

  useEffect(() => {

    if((!user.utenteId) || user.ruoloId.ruoloId === 1) {
      const errore =  {
        titoloErrore: 'NON HAI I PERMESSI PER ACCEDERE A QUESTA PAGINA',
        descrizione: 'EFFETTUA LA LOGIN DA CLIENTE'      
      } 
      setErrore(errore);
      history.push(ERROR_PAGE)  

    } else if (user.ruoloId.ruoloId === 2) {

      findAllTipoTransazione(setListaTipoTransazione);
  
      findAllContoCorrente(setListaContiCorrenti);
      
    }
    

  }, []);

  function effettuaOperazione() {
    console.log(beneficiario)

    if(importo <= 0 || importo >= 1000 || importo == undefined) {
      setErrorMessage("Non puoi fare una transazione inferiore a 1€ o superiore a 1000€!");
      return;
    }

    if(importo > contoCorrente.saldo){
      setErrorMessage("Saldo non sufficiente!");
      return;

    }
    if(tipo == undefined){
      setErrorMessage("Seleziona il tipo di operazione!")
      return;
    }   
    
    const contoCorrenteBeneficiario = listaContiCorrenti[beneficiario-1] 

    if(tipo == 3 && beneficiario === undefined){
      setErrorMessage("Selezionare il conto beneficiario!")
      return;
    } 

    if(tipo == 4 && descrizione.length < 10) {
      setErrorMessage("Inserisce un numero di telefono valido")
      return;
    }

    if(tipo == 5 && descrizione.length < 10) {
      setErrorMessage("Inserisce una causale")
      return;
    }

    const tipoTransazionee = {
      id: tipo,
      tipoTransazione: listaTipoOperazione[tipo - 1].tipoTransazione,
    }

    const transazione = {
      importo: importo,
      tipoTransazione: tipoTransazionee,
      contoCorrente: contoCorrente,
      contoCorrenteBeneficiario: contoCorrenteBeneficiario,
      descrizione: descrizione
    }
    console.log(tipo)
    console.log(transazione)

    insertTransazione(transazione, history, setMessage, dispatch);
    
  }

  function handleChange() {

    if (tipo == 3) {
      return (
        <>
          <label >
            <b>SELEZIONA IL CONTO BENEFICIARIO</b>
          </label>
          <select
            onChange={(e) => setBeneficiario(e.target.value)
            } >
            <option value="">Seleziona il conto beneficiario</option>
            {listaContiCorrenti ? (
              listaContiCorrenti.filter(conto => conto.statoContoCorrente.statoContoCorrenteId == 1 && conto.id !== contoCorrente.id).map((conto) => (
                <option key={conto.id} value={conto.id}>
                  {conto.numeroConto}
                </option>
              ))
            ) : (
              <>
              </>
            )}
          </select>

          <br /> <br />
        </>
      )    

    } else if (tipo == 4) {
      return (
        <>
          <label >
            <b>INSERISCI IL NUMERO TELEFONICO</b>
          </label>
         
      <input
        type="number"
        placeholder="Inserisci il tuo numero"
        onChange={(e) => setDescrizione(e.target.value)}
      />
          <br /> <br />
        </>
      )
    } else if (tipo == 5) {
      return (
        <>
          <label >
            <b>INSERISCI CAUSALE</b>
          </label>
          
      <textarea
        
        placeholder="Inserisci la causale"
        onChange={(e) => setDescrizione(e.target.value)}
      />

          <br /> <br />
        </>

      )
    }
  }


  return (

    <div className='containerOperazioniCliente'>
      <h1>Effettua una operazione ExoBank</h1>

      <label >
        <b>SELEZIONA IL TIPO DI OPERAZIONE</b>
      </label>
      <select
        onChange={(e) => setTipo(e.target.value)
        } >
        <option value="">Seleziona il tipo di operazione</option>
        {listaTipoOperazione ? (
          listaTipoOperazione.map((tipoTransazione) => (
            <option key={tipoTransazione.id} value={tipoTransazione.id}>
              {tipoTransazione.tipoTransazione}
            </option>
          ))
        ) : (
          <>
          </>
        )}


      </select>

      <br /> <br />

      <label>
        <b>INSERISCI L'IMPORTO</b>
      </label>
      <input
        type="number"
        placeholder="Inserisci l'importo"
        value={importo}
        onChange={(e) => setImporto(e.target.value)}
      />

      <br /> <br />

      {handleChange()}

      {errorMessage && <div style={{color : 'red'}}>{errorMessage}</div>}

      <br />


      <button type='button' onClick={() => { effettuaOperazione() }}>CONFERMA</button>
      <button type='button' onClick={() => history.push(HOME_PAGE_CLIENTE)}>INDIETRO</button>
    </div >

  )
}

export default OperazioneCliente