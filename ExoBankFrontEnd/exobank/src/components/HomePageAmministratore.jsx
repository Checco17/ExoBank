import React, { useEffect } from 'react'
import { useHistory } from 'react-router-dom/cjs/react-router-dom.min';
import { ERROR_PAGE, LISTA_CONTI_CORRENTI, LISTA_TRANSAZIONI, LISTA_UTENTI } from '../utility/Route';
import { useSelector } from 'react-redux';

function HomePageAmministratore({ setErrore }) {

  const user = useSelector(state => state.user);
  const history = useHistory();


  useEffect(() => {

    if (user.ruoloId === 2 || (!user.utenteId)) {
        const errore = {
        titoloErrore: 'NON HAI I PERMESSI PER ACCEDERE A QUESTA PAGINA',
        descrizione: 'EFFETTUA LA LOGIN DA AMMINISTRATORE'
      }
      setErrore(errore);
      history.push(ERROR_PAGE);
    }

  }, [user])


  return (
    <div className="containerHomePageAmministratore">
      <h1>Homepage Amministratore</h1>
      <button type="button" onClick={() => history.push(LISTA_CONTI_CORRENTI)}>LISTA CONTI CORRENTE</button> <br />
      <button type="button" onClick={() => history.push(LISTA_TRANSAZIONI)}>LISTA TRANSAZIONI</button> <br />
      <button type="button" onClick={() => history.push(LISTA_UTENTI)}>LISTA UTENTI</button> <br />
    </div>
  )
}

export default HomePageAmministratore
