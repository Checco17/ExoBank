import { createSlice } from '@reduxjs/toolkit'

const initialTransazione = {}

const transazioneSlice = createSlice({
  name: 'transazione',
  initialState : initialTransazione,
  reducers: {

    setTransazione : (state, action) => {
        return state = action.payload

    }, 
    resetTransazione: (state, action ) => {
        return (state = initialTransazione)
    }

  }
});

export const {

    setTransazione,
    resetTransazione
} = transazioneSlice.actions

export default transazioneSlice.reducer