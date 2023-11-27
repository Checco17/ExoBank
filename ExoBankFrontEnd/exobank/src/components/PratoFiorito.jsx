import React, { useState, useEffect } from 'react';
import '../custom/PratoFiorito.css';
import { addMine, controllaVittoria, countMineCircostanti, createGrigliaVuota, revealedRicorsive } from '../utility/PratoFiorito';

function PratoFiorito() {
  const initialTime = 200;
  const numeroRighe = 12;
  const numeroColonne = 15;
  const numeroMine = 10;
  const message = {
    giocoFinito: '❌❌❌ Game over! ❌❌❌',
    tempoScaduto: '⌛⌛⌛ Tempo scaduto! ⌛⌛⌛' ,
    giocoVinto: '🎉🎉🎉 Complimenti, hai vinto! 🎉🎉🎉'
  }
  const [mess, setMess] = useState({
    giocoFinito: '',
    tempoScaduto: '',
    giocoVinto: ''
  })
  const [resetGame, setResetGame] = useState(false)
  const [griglia, setGriglia] = useState([]);
  const [gameOver, setGameOver] = useState(false);
  const [timer, setTimer] = useState(initialTime)
  const [timerRun, setTimeRun] = useState(true);
  const [pause, setPause] = useState(false)
  const [redFlag, setRedFlag] = useState(false)


  useEffect(() => {
    setGriglia(createGrigliaVuota(numeroRighe, numeroColonne));
  }, []);


  useEffect(() => {
    addMine(numeroRighe, numeroColonne, numeroMine, setGriglia);
  }, [numeroRighe, numeroColonne, numeroMine]);


  useEffect(() => {
    const timerInterval = setInterval(() => {
      if (timerRun && !pause) {
        if (timer > 0) {
          setTimer(timer - 1);
        } else {
          setGameOver(true);
          setMess({
            tempoScaduto: message.tempoScaduto,
            giocoFinito: message.giocoFinito
          });
          clearInterval(timerInterval);
        }
      }
    }, 1000);
    return () => clearInterval(timerInterval);
  }, [timer, timerRun, pause]);


  useEffect(() => {
    if (resetGame) {
      const newGriglia = createGrigliaVuota(numeroRighe, numeroColonne);
      setGriglia(newGriglia);
      addMine(numeroRighe, numeroColonne, numeroMine, setGriglia);
      setGameOver(false);
      setTimer(initialTime);
      setRedFlag(false);
      setTimeRun(true);
      setMess({
        giocoFinito: '',
        tempoScaduto: ''
      });
      setResetGame(false);
    }
  }, [resetGame])


  const handleCellClick = (riga, colonna) => {
    const newGriglia = [...griglia];
    if (redFlag) {
      newGriglia[riga][colonna] = { ...newGriglia[riga][colonna], isFlagged: true };
      return;
    } else if (!redFlag) {
      newGriglia[riga][colonna] = { ...newGriglia[riga][colonna], isFlagged: false };
    }

    
    if (newGriglia[riga][colonna].isMine) {
      setTimeRun(false);
      for (let i = 0; i < numeroRighe; i++) {
        for (let j = 0; j < numeroColonne; j++) {
          if (newGriglia[i][j].isMine) {
            newGriglia[i][j].isRevealed = true
          }
        }
      }
      setGameOver(true);
      setMess({ giocoFinito: message.giocoFinito });
      return;

    } else {

      const mineCircostanti = countMineCircostanti(riga, colonna, griglia, numeroRighe, numeroColonne);
      if (mineCircostanti > 0) {
        newGriglia[riga][colonna] = { ...newGriglia[riga][colonna], isRevealed: true };
      } else if (mineCircostanti === 0) {
        revealedRicorsive(riga, colonna, newGriglia, numeroRighe, numeroColonne, countMineCircostanti, griglia)
      }
      setGriglia(newGriglia);
      controllaVittoria(numeroRighe, numeroColonne, griglia, setGameOver, setTimeRun, setMess, message) ;

    }
  };  

  return (
    <div className='containerPratoFiorito'>
      <h1 style={{fontFamily: 'Calculator', fontSize: '40px'}}>Prato Fiorito</h1>
      <p style={{ color: 'red' , fontFamily: 'Calculator' , fontSize: '30px'}}><b>{mess.giocoFinito}</b></p>
      <p style={{ color: 'red' , fontFamily: 'Calculator', fontSize: '30px'}}><b>{mess.tempoScaduto}</b></p>
      <p style={{ color: 'green' , fontFamily: 'Calculator', fontSize: '30px'}}><b>{mess.giocoVinto}</b></p>
      <p className='timer'>Tempo rimanente: {Math.floor(timer / 60)}:{(timer % 60 >= 10) ? timer % 60 : '0' + timer % 60}</p>
      <p style={{ color: 'gold' , fontFamily: 'Calculator', fontSize: '30px'}}><b>{(pause ? '⏸️ PAUSA ⏸️' : '')}</b></p>
      <table className={`grid`}>
        <tbody>

          {griglia.map((row, rowIndex) => (
            <tr key={rowIndex}>
              {row.map((cell, colIndex) => (
                <td
                  key={colIndex}
                  className={`cell ${cell.isRevealed ? 'revealed' : ''} ${pause ? 'paused' : ''}`}
                  onClick={() => ((!gameOver && !pause) ? handleCellClick(rowIndex, colIndex) : '')}
                >
                  {cell.isRevealed && !cell.isMine
                    ? (countMineCircostanti(rowIndex, colIndex, griglia, numeroRighe, numeroColonne) === 0 && !pause) ? '🌷' : countMineCircostanti(rowIndex, colIndex, griglia, numeroRighe, numeroColonne)
                    : (cell.isRevealed && cell.isMine) ? '💣'
                      : cell.isFlagged ? '🚩'
                        : ''}

                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
      <br />
      <button style={{fontFamily: 'Calculator', fontSize: '25px'}} type="button" onClick={() => {setResetGame(true); setPause(false)}}>RICOMINCIA</button>
      <button style={{ backgroundColor: 'yellow', color: 'black', fontFamily: 'Calculator', fontSize: '25px' }} type="button" onClick={() => setPause(!pause)}>{(!pause) ? 'PAUSA' : 'RIPRENDI'}</button>
      <button style={{ backgroundColor: (redFlag)? 'green' : 'black' }} type="button" onClick={() => setRedFlag(!redFlag)}>🚩</button>

    </div>
  );
}

export default PratoFiorito;
