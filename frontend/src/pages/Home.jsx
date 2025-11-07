import { Link } from "react-router-dom";
import { useAuthContext } from "../components/AuthContext.jsx";

import hero from "../assets/hero-image-mask2.png";

export default function Home() {

    const authContext = useAuthContext();
    
    return (
        <div className="relative h-full p-8 rounded-4xl">
            <div className="absolute top-20 -left-80 flex flex-col gap-10 w-220 z-10">
                <h1 className="text-slateBlue text-8xl font-bold w-300 mb-16">
                    <span className="text-lavender">Accelerate</span> Your Finance Career with <span className="text-teal">Interactive Learning</span>
                </h1>
                <h2 className="text-xl w-160">
                    Our comprehensive courses, interactive quizzes, 
                    and spaced repetition system help you prepare for 
                    certifications like CFA, ACCA, and more - at your 
                    own pace, anytime, anywhere.
                </h2>
                <div className="flex flex-row gap-10">
                    <Link to="register" className="heroButton border-lavender text-lavender hover:bg-lavender hover:text-offWhite">
                        start your journey
                    </Link>
                    <Link to="paths" className="heroButton border-teal text-teal hover:bg-teal hover:text-offWhite">
                        see our courses
                    </Link>
                </div>
            </div>
            <img className="relative top-10 left-100 w-210" src={hero} alt="Hero image" />
        </div>
    );
}