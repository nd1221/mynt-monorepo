import UserProfileHeader from "./headers/UserProfileHeader";
import Footer from "./Footer";
import { Outlet, useLocation } from "react-router-dom";
import AuthLayer from "./AuthContext";

export default function Layout() {

    const location = useLocation();

    return (
        <>
            <AuthLayer>
                <div className="flex flex-col p-8 gap-8 min-h-screen">
                    <UserProfileHeader />
                    <main className="flex-grow flex flex-col items-center">
                        <Outlet />
                    </main>
                    <Footer />
                </div>
            </AuthLayer>
        </>
    );
}