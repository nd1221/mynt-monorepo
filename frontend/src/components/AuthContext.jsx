import { createContext, useContext, useEffect, useState } from "react";
import api from "../api/modules.js";
import { useLoaderData } from "react-router-dom";

export async function loader() {
    try {
        const response = await api.get("/auth/session-data");
        return {user: response.data};
    } catch(err) {
        if (err.response?.status === 401) {
            return {user: null};
        }
        throw err;
    }
}

export const GlobalAuthContext = createContext(null);

export const useAuthContext = () => useContext(GlobalAuthContext);

export default function AuthLayer({children}) {

    const { user } = useLoaderData();
    const [currentUser, setCurrentUser] = useState(null);

    useEffect(() => {
        setCurrentUser(user);
    }, [user]);

    const updateCurrentUser = async () => {
        try{
            const response = await api.get("/auth/session-data");
            setCurrentUser(response.data);
        } catch(err) {
            if (err.response?.status === 401) {
                setCurrentUser(null);
            }
            throw err;
        }
    }

    const logout = async () => {
        setCurrentUser(null);
        await api.post("/auth/logout");
    }

    return (
        <GlobalAuthContext.Provider value={{currentUser, updateCurrentUser, logout}}>
            {children}
        </GlobalAuthContext.Provider>
    );
}