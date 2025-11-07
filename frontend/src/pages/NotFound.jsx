import { Link } from "react-router-dom";

export default function NotFound() {

    return (
        <>
            <div className="flex flex-col gap-10 justify-center items-center pt-10">
                <h1 className="font-bold text-6xl text-slateBlue">404</h1>
                <Link
                    to={"/"}
                    className="
                        block my-auto text-center h-fit w-fit
                        bg-transparent text-slateBlue border-slateBlue border-3 p-4 px-8 text-xl font-bold rounded-2xl
                        hover:bg-slateBlue hover:text-offWhite duration-100 active:scale-98
                    "
                >
                    <p>Home</p>
                </Link>
            </div>
        </>
    );
}