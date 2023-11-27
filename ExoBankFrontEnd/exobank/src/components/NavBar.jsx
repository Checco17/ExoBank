import { useState, useEffect } from "react";
import { resetUser } from "../store/slice/utenteSlice";
import { useHistory } from "react-router-dom";
import { APERTURA_CONTO, ERROR_PAGE, HOME_PAGE_AMMINISTRATORE, HOME_PAGE_CLIENTE, LISTA_CONTI_CORRENTI, LISTA_TRANSAZIONI, LISTA_TRANSAZIONI_UTENTE, LISTA_UTENTI, LOGIN_PAGE, OPERAZIONE_CLIENTE, PRATO_FIORITO } from "../utility/Route";
import iconMale from "../icon/icon_male.png";
import iconFemale from "../icon/icon_female.png";
import { useDispatch, useSelector } from "react-redux";
import { resetContoCorrente } from "../store/slice/contoCorrenteSlice";
import { resetTransazione } from "../store/slice/transazioneSlice";
import { resetMessage } from "../store/slice/messageSlice";

function NavBar({ setErrore }) {
  const user = useSelector((state) => state.user);
  const contoCorrente = useSelector(state => state.contoCorrente)
  const [icon, setIcon] = useState('');
  const [nameUser, setNameUser] = useState('');
  const history = useHistory();
  const dispatch = useDispatch();

  useEffect(() => {
    if (user.utenteId && Object.keys(user).length > 0) {
      switch (user.nome[user.nome.length - 1]) {
        case 'O':
        case 'I':
          setIcon(iconMale);
          break;
        case 'A':
          setIcon(iconFemale);
          break;
        default:
          break;
      }
      setNameUser(user.nome + ' ' + user.cognome);
    }


  }, [user]);

  const selectChange = (event) => {
    const selectedValue = event.target.value;
    switch (selectedValue) {
      case 'LISTA_TRANSAZIONI':
        history.push(LISTA_TRANSAZIONI);
        break;
      case 'LISTA_CONTI_CORRENTI':
        history.push(LISTA_CONTI_CORRENTI);
        break;
      case 'LISTA_UTENTI':
        history.push(LISTA_UTENTI);
        break;
      case 'HOME_PAGE_AMMINISTRATORE':
        history.push(HOME_PAGE_AMMINISTRATORE);
        break;
      case 'HOME_PAGE_CLIENTE':
        history.push(HOME_PAGE_CLIENTE);
        break;
      case 'APERTURA_CONTO':
        if (contoCorrente.numeroConto) {
          const errore = {
            titoloErrore: 'CONTO GIA\' ESISTENTE',
            descrizione: 'NON E\' POSSIBILE POSSEDERE PIU\' DI UN CONTO'
          }
          setErrore(errore);
          history.push(ERROR_PAGE);
        } else {
          history.push(APERTURA_CONTO)
          dispatch(resetMessage());
        }
        break;
      case 'OPERAZIONE_CLIENTE':
        if (!contoCorrente.statoContoCorrente) {
          const errore = {
            titoloErrore: 'IMPOSSIBILE EFFETTUARE OPERAZIONI',
            descrizione: 'NON POSSIEDI ANCORA UN CONTO CORRENTE, APRILO!'
          }
          setErrore(errore);
          history.push(ERROR_PAGE);


        } else if (contoCorrente.statoContoCorrente.statoContoCorrenteId === 2 || contoCorrente.statoContoCorrente.statoContoCorrenteId === 3) {
          const errore = {
            titoloErrore: 'IMPOSSIBILE EFFETTUARE OPERAZIONI',
            descrizione: 'CONTO CORRENTE INATTIVO O SOSPESO'
          }
          setErrore(errore);
          history.push(ERROR_PAGE);

        } else if (contoCorrente.statoContoCorrente.statoContoCorrenteId === 1) {
          history.push(OPERAZIONE_CLIENTE)
          dispatch(resetMessage());
        }
        break;
      case 'LISTA_TRANSAZIONI_UTENTE':
        history.push(LISTA_TRANSAZIONI_UTENTE);
        dispatch(resetMessage());
        break;

      default:

        break;
    }
  }

  const exit = () => {
    dispatch(resetUser())
    dispatch(resetContoCorrente());
    dispatch(resetTransazione())
    dispatch(resetMessage())
    history.push(LOGIN_PAGE);
  };

  const game = () => {
    history.push(PRATO_FIORITO)
  }

  return (
    <>
      {user.utenteId && Object.keys(user).length > 0 ? (
        <nav className="navbar">
          <img
            src={icon}
            style={{ width: '50px', height: '50px', borderRadius: '50%' }}

          />
          <h2 style={{ color: 'white' }}  >
            Benvenuto in ExoBank {nameUser} !
          </h2>

          <div style={{ marginBottom: '20px' }}>
            {user.email && user.ruoloId.nomeRuolo === 'AMMINISTRATORE' ? (
              <select className="selectChange" onChange={selectChange} value="Naviga nel Sito" >
                <option value="">Naviga nel Sito</option>
                <option value="HOME_PAGE_AMMINISTRATORE">Home page</option>
                <option value="LISTA_TRANSAZIONI">Lista Transazioni</option>
                <option value="LISTA_CONTI_CORRENTI">Lista Conti Corrente</option>
                <option value="LISTA_UTENTI">Lista Utenti</option>

              </select>

            ) : (<>

            </>)

            }

            {user.email && user.ruoloId.nomeRuolo === 'CLIENTE' ? (
              <select className="selectChange" onChange={selectChange} value="Naviga nel Sito">
                <option value="">Naviga nel Sito</option>
                <option value="HOME_PAGE_CLIENTE">Home page</option>
                <option value="APERTURA_CONTO">Apri Conto</option>
                <option value="OPERAZIONE_CLIENTE">Fai un operazione</option>
                <option value="LISTA_TRANSAZIONI_UTENTE">Lista Tue Transazioni</option>

              </select>

            ) : (<>

            </>)

            }



            <button
              className="exit-button"
              type="button"
              style={{ backgroundColor: 'orange' }}
              onClick={() => game()}
            >
              <span className="exit-text">Prato Fiorito</span>
            </button>
            <button
              className="exit-button"
              type="button"
              style={{ backgroundColor: 'red' }}
              onClick={() => exit()}
            >
              <span className="exit-text">Logout</span>
            </button>
            
              <input type="button" onClick={() => window.location.href = 'http://localhost:8080/ExoBankJSF/'} value="Torna a Jsf" />
            
          </div>


        </nav>
      ) : (
        <></>
      )}
    </>
  );
}

export default NavBar;