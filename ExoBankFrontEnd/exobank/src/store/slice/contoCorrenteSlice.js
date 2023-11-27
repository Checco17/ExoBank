import { createSlice } from '@reduxjs/toolkit'

const initialContoCorrente = {}

const contoCorrenteSlice = createSlice({
  name: 'contoCorrente',
  initialState : initialContoCorrente,

 reducers : {

    setContoCorrente : (state, action) => {
        return state = action.payload
    },
    resetContoCorrente : (state, action) => {
        return (state = initialContoCorrente)
    }
 }
})

export const {
    setContoCorrente,
    resetContoCorrente
} = contoCorrenteSlice.actions

export default contoCorrenteSlice.reducer