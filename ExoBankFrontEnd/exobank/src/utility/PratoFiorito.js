export const createGrigliaVuota = (numeroRighe, numeroColonne) => {
    const newGriglia = Array.from({ length: numeroRighe }, () =>
      Array(numeroColonne).fill({ isMine: false, isRevealed: false, isFlagged: false })
    );
    return newGriglia;
  };

  export const countMineCircostanti = (riga, colonna, griglia, numeroRighe, numeroColonne) => {
    let count = 0;
    for (let y = -1; y <= 1; y++) {
      for (let x = -1; x <= 1; x++) {
        const newRow = riga + y;
        const newCol = colonna + x;
        if (
          newRow >= 0 &&
          newRow < numeroRighe &&
          newCol >= 0 &&
          newCol < numeroColonne &&
          griglia[newRow][newCol].isMine
        ) {
          count++;
        }
      }
    }
    return count;
  };

  export const controllaVittoria = (numeroRighe, numeroColonne, griglia, setGameOver, setTimeRun, setMess, message) => {
    let vittoria = true;
    for (let i = 0; i < numeroRighe; i++) {
      for (let j = 0; j < numeroColonne; j++) {
        const cella = griglia[i][j];
        if (!cella.isMine && !cella.isRevealed) {
          vittoria = false;
          break;
        }
      }
      if (!vittoria) {
        break;
      }
    }
  
    if (vittoria) {
      setGameOver(true);
      setTimeRun(false);
      setMess({ giocoVinto: message.giocoVinto });
    }
  };


  export const addMine = (numeroRighe, numeroColonne, numeroMine, setGriglia) => {
    setGriglia((prevGriglia) => {
      const newGriglia = [...prevGriglia];
      for (let i = 0; i < numeroMine; i++) {
        let riga, colonna
        let check = false
        do {
          riga = Math.floor(Math.random() * numeroRighe);
          colonna = Math.floor(Math.random() * numeroColonne);
          if (newGriglia[riga][colonna].isMine === false) {
            newGriglia[riga][colonna] = { ...newGriglia[riga][colonna], isMine: true };
            check = true;
          }
        } while (!check);
      
      }
      return newGriglia;
    });
  };


  export const revealedRicorsive = (riga, colonna, newGriglia, numeroRighe, numeroColonne, countMineCircostanti, griglia) => {
    if (
      riga >= 0 &&
      riga < numeroRighe &&
      colonna >= 0 &&
      colonna < numeroColonne &&
      !newGriglia[riga][colonna].isRevealed
    ) {
      if (countMineCircostanti(riga, colonna, griglia, numeroRighe, numeroColonne) === 0) {
        newGriglia[riga][colonna] = { ...newGriglia[riga][colonna], isRevealed: true };

        for (let y = -1; y <= 1; y++) {
          for (let x = -1; x <= 1; x++) {
            if (y !== 0 || x !== 0) {
              const newRiga = riga + y;
              const newColonna = colonna + x;
              revealedRicorsive(newRiga, newColonna, newGriglia, numeroRighe, numeroColonne, countMineCircostanti, griglia);
            }
          }
        }
      } else {
        newGriglia[riga][colonna] = { ...newGriglia[riga][colonna], isRevealed: true };
      }
    }
  };