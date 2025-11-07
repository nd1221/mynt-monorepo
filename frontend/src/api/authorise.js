import api from "../api/modules.js";
import { redirect } from "react-router-dom";

export async function authorise() {
    try {
        await api.get("/auth/check-session");
    } catch(err) {
        if (err.response && err.response.status === 401) {
            const protectedPath = window.location.pathname;
            throw redirect(`/login?returnTo=${encodeURIComponent(protectedPath)}`);
        }
    }
}