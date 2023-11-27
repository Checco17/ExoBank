import { createSlice } from "@reduxjs/toolkit";

const initialUser = {}

export const userSlice = createSlice({
    name: 'user',
    initialState: initialUser,
    reducers: {
        setUser: (state, action) => {
            return state = action.payload
        },        
        resetUser: (state, action) => {
            return (state = initialUser)
        }
    }
})

export const {
    setUser, 
    resetUser
} = userSlice.actions

export default userSlice.reducer