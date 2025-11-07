import { NavLink, Link } from "react-router-dom";
import { useAuthContext } from "../AuthContext";

export default function Header() {

    const authContext = useAuthContext();
    const user = authContext.currentUser;
    const loggedIn = !!user?.isAuthenticated;

    return (
        <div className="flex clex-col bg-slateBlue h-26 p-8 px-12 rounded-full justify-between items-center">
            <Link to="/" className="font-poppins font-medium font text-3xl text-teal">mynt finance</Link>
            <div className="flex flex-row gap-8 items-center">
                <NavLink to="about" className={({isActive}) => (isActive ? "activeLink" : "navLink")}>about</NavLink>
                <NavLink to="paths" end className={({isActive}) => (isActive ? "activeLink" : "navLink")}>our courses</NavLink>
                { loggedIn ?
                    <>
                        <NavLink to="/" className={() => "navLink"} onClick={() => authContext.logout()}>logout</NavLink>
                        <NavLink to="/user-profile/courses" className={({isActive}) => (
                            `block w-10 h-10 bg-cover bg-center 
                            ${isActive ? "bg-[url('./assets/user-icon-purple.png')]" : "bg-[url('./assets/user-icon-white.png')]"} 
                            hover:bg-[url('./assets/user-icon-purple.png')] duration-75`)} 
                        />
                    </>
                : <NavLink to="login" className={({isActive}) => (isActive ? "activeLink" : "navLink")}>login</NavLink> }
            </div>
        </div>
    );
}