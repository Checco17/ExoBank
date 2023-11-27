import React, { useState } from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import LoginPage from './components/LoginPage';
import HomePageAmministratore from './components/HomePageAmministratore';
import HomePageCliente from './components/HomePageCliente';
import RegistraUtente from './components/RegistraUtente';
import AperturaConto from './components/AperturaConto';
import ListaUser from './components/ListaUser';
import ListaContiCorrenti from './components/ListaContiCorrenti';
import './custom/App.css'
import ListaTransazioni from './components/ListaTransazioni';
import OperazioneCliente from './components/OperazioneCliente';
import { LOGIN_PAGE, PRATO_FIORITO, HOME_PAGE_AMMINISTRATORE, HOME_PAGE_CLIENTE, REGISTRA_UTENTE, APERTURA_CONTO, LISTA_UTENTI, LISTA_CONTI_CORRENTI, LISTA_TRANSAZIONI, OPERAZIONE_CLIENTE, LISTA_TRANSAZIONI_UTENTE, ERROR_PAGE } from './utility/Route'
import ListaTransazioneUtente from './components/ListaTransazioneUtente';
import ErrorPage from './components/ErrorPage';
import NavBar from './components/NavBar';
import PratoFiorito from './components/PratoFiorito'


function App() {

  const [errore, setErrore] = useState({
    titoloErrore: '',
    descrizione: ''
  })

  return (

    <BrowserRouter>
      <NavBar setErrore= {setErrore} />
      <Switch>
        <Route exact path={LOGIN_PAGE} component={() => <LoginPage setErrore={setErrore} />} />
        <Route exact path={HOME_PAGE_CLIENTE} component={() => <HomePageCliente setErrore={setErrore} />} />
        <Route exact path={HOME_PAGE_AMMINISTRATORE} component={() => <HomePageAmministratore setErrore={setErrore} />} />
        <Route exact path={REGISTRA_UTENTE} component={() => <RegistraUtente  />} />
        <Route exact path={APERTURA_CONTO} component={() => <AperturaConto setErrore={setErrore} />} />
        <Route exact path={LISTA_UTENTI} component={() => <ListaUser setErrore={setErrore} />} />
        <Route exact path={LISTA_CONTI_CORRENTI} component={() => <ListaContiCorrenti setErrore={setErrore} />} />
        <Route exaxt path={LISTA_TRANSAZIONI} component={() => <ListaTransazioni setErrore={setErrore} />} />
        <Route exaxt path={OPERAZIONE_CLIENTE} component={() => <OperazioneCliente setErrore={setErrore} />} />
        <Route exaxt path={LISTA_TRANSAZIONI_UTENTE} component={() => <ListaTransazioneUtente setErrore={setErrore} />} />
        <Route exaxt path={ERROR_PAGE} component={() => <ErrorPage errore={errore} />} />
        <Route exaxt path={PRATO_FIORITO} component={() => <PratoFiorito/>} />


      </Switch>
    </BrowserRouter>
  );
}

export default App;
