import React, { useState, useEffect } from 'react';
import { findAllUtente } from '../service/utenteService';
import { useHistory } from 'react-router-dom/cjs/react-router-dom';
import { useSelector } from 'react-redux';
import { ERROR_PAGE } from '../utility/Route';



function ListaUser({setErrore}) {
  const [listaUser, setListaUser] = useState([]);
  const user = useSelector(state => state.user)
  const history = useHistory();

  useEffect(() => { 
    if((!user.utenteId) || user.ruoloId.ruoloId === 2) {
      const errore =  {
        titoloErrore: 'NON HAI I PERMESSI PER ACCEDERE A QUESTA PAGINA',
        descrizione: 'EFFETTUA LA LOGIN DA AMMINISTRATORE'      
      } 
      setErrore(errore);
      history.push(ERROR_PAGE)  

    } else if (user.ruoloId.ruoloId === 1) {
      
    findAllUtente(setListaUser);

    }
  }, []); 

  
  return (
    <div className='containerListaUtenti'>
  
      <h1>Lista Utenti ExoBank</h1>
  <table>
    <thead>
      <tr>
        <th>ID</th>
        <th>Nome</th>
        <th>Cognome</th>
        <th>Email</th>
        <th>ID Ruolo</th>
        <th>Ruolo</th>
      </tr>
    </thead>
    <tbody>
      {listaUser.map((user) => (
        <tr key={user.utenteId}>
          <td>{user.utenteId}</td>
          <td>{user.nome}</td>
          <td>{user.cognome}</td>
          <td>{user.email}</td>
          <td>{user.ruoloId.ruoloId}</td>
          <td>{user.ruoloId.nomeRuolo}</td>
        </tr>
      ))}
    </tbody>
  </table>
  <button
    type="button"
    className="back-button"
    placeholder=""
    onClick={() => {
      history.push("/homePageAmministratore");
    }}
  >
    INDIETRO
  </button>

</div>
    
  );
}

export default ListaUser;
