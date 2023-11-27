import { createSlice } from '@reduxjs/toolkit'

const initialMessage = {
    data: ''
}

 export const messageSlice = createSlice({
  name: 'message',
  initialState : initialMessage,
  reducers: {
    setMessage: (state, action) => {
        return state = action.payload;
    },

    resetMessage: (state, action) => {
        return (state = initialMessage);
    }
  }
});

export const {
    setMessage,
    resetMessage
} = messageSlice.actions

export default messageSlice.reducer