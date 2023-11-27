import React from 'react'
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom/cjs/react-router-dom.min'
import { LOGIN_PAGE } from '../utility/Route';


function ErrorPage({ errore }) {

  const  history = useHistory();
  const user = useSelector(state => state.user)

  return (
    <div className='containerErrorPage'>

      <h1 style={{color : 'red'}}>ERROR PAGE</h1>
      <p><b>TITOLO ERRORE:  </b> {errore.titoloErrore}</p>
      <p><b>DESCRIZIONE ERRORE:  </b>{errore.descrizione}</p>
      <button type="button" onClick={(user.utenteId) ? () => history.goBack() :  () => history.push(LOGIN_PAGE)} > INDIETRO </button>

    </div>
  )
}

export default ErrorPage