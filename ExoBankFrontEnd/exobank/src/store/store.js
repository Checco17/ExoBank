import { configureStore } from "@reduxjs/toolkit";
import userSlice from "./slice/utenteSlice";
import contoCorrenteSlice from "./slice/contoCorrenteSlice";
import transazioneSlice from "./slice/transazioneSlice";
import messageSlice from "./slice/messageSlice";

export const store = configureStore({
    reducer: {
        user: userSlice,
        contoCorrente : contoCorrenteSlice,
        transazione : transazioneSlice,
        message: messageSlice

        }
})