import { useState } from "react";
import { Link, useLocation } from "react-router-dom";
import api from "../api/modules.js";
import Alert from "../components/uiComponents/Alert.jsx";
import { useAuthContext } from "../components/AuthContext.jsx";

import lockIcon from "../assets/lock.svg";

export default function Login() {
    
    const authContext = useAuthContext();

    const [showAlert, setShowAlert] = useState(false);
    const [type, setType] = useState(null);
    const [message, setMessage] = useState(null);

    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const targetPath = searchParams.get("returnTo") || "/user-profile/courses";

    const logInUser = async user => {
        try {
            const response = await api.post("/auth/login", user);
            setType("success");
            setMessage(response.data);
            setShowAlert(true);
            // Get and update session in global context
            authContext.updateCurrentUser();
        } catch(err) {
            setType("error");
            setMessage(err.response.data);
            setShowAlert(true);
        }
    }

    const handleSubmit = event => {
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        const existingUser = {
            email: formData.get("email"),
            password: formData.get("password")
        }
        logInUser(existingUser);
    }
    
    return (
        <>
            <div className="flex flex-col gap-4 h-fit py-10 w-150">
                <img className="w-12 mx-auto mt-6" src={lockIcon} alt="Lock login icon" />
                <h1 className="text-3xl text-slateBlue mx-auto mb-4">Sign into your account</h1>
                <form onSubmit={handleSubmit} className="h-fit w-full bg-white mx-auto my-6 rounded-xl shadow-lg">
                    <div className="flex flex-col rounded-lg p-8 py-12">
                        <input
                            id="email"
                            name="email"
                            type="email"
                            required
                            autoComplete="email"
                            placeholder="Email address"
                            className="block w-full bg-white rounded-t-lg px-3 py-3 text-xl text-charcoal border-1 outline-slateBlue/80 placeholder:text-charcoal/50 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo/50"
                        />
                        <input
                            id="password"
                            name="password"
                            type="password"
                            required
                            placeholder="Password"
                            className="block w-full bg-white rounded-b-lg px-3 py-3 text-xl text-charcoal border-1 border-t-0 outline-slateBlue/80 placeholder:text-charcoal/50 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo/50"
                        />
                        <button type="submit" className="w-full mt-12 mb-2 bg-lavender text-offWhite font-bold text-2xl p-8 py-6 rounded-lg hover:bg-lavenderDark mx-auto shadow-xl active:shadow-sm">log in</button>
                    </div>
                </form>
                <div className="flex flex-row gap-2 justify-center items-center">
                    <p className="text-xl">Don't have an account?</p>
                    <Link className="text-lavender text-xl font-bold underline-offset-6 hover:underline" to="/register" state={{targetPath}}>Start your journey</Link>
                </div>
            </div>
            {
                showAlert ?
                <Alert type={type} message={message} isHidden={!showAlert} targetPath={targetPath} onClose={() => {setShowAlert(false)}}/>
                : null
            }
        </>
    );
}