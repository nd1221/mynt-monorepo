import { Link } from "react-router-dom";

export default function Alert(props) {

    const {type, message, isHidden, targetPath, onClose} = props;

    const typeClass = {
        error: "text-red-700/75",
        success: "text-lavender",
    };

    // Adapted from FlowBite's default modal component
    return (
        <div
            id="default-modal"
            tabIndex={-1}
            aria-hidden={isHidden}
            className={`${isHidden ? 'hidden' : 'flex'} overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full bg-slateBlue bg-opacity-15`}
        >
            <div className="relative p-4 w-full max-w-2xl max-h-full">
                {/* Modal content */}
                <div className="relative pt-4 bg-white rounded-lg shadow-sm dark:bg-gray-700">
                    {/* Modal header - Leave for future refactoring*/}
                    {/* <div className="flex items-center justify-between p-4 md:p-5 border-b rounded-t border-gray-200 dark:border-gray-600">
                        <h3 className="text-xl font-semibold text-gray-900 dark:text-white">
                        Authentication
                        </h3>
                        <button
                            type="button"
                            onClick={onClose}
                            className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white"
                            aria-label="Close modal"
                        >
                        <svg
                            className="w-3 h-3"
                            aria-hidden="true"
                            xmlns="http://www.w3.org/2000/svg"
                            fill="none"
                            viewBox="0 0 14 14"
                        >
                            <path
                                stroke="currentColor"
                                strokeLinecap="round"
                                strokeLinejoin="round"
                                strokeWidth="2"
                                d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
                            />
                        </svg>
                        <span className="sr-only">Close modal</span>
                        </button>
                    </div> */}
            
                    {/* Modal body */}
                    <div className="flex flex-col items-center p-4 md:p-5 space-y-4 my-6">
                        <p className={`text-xl font-bold leading-relaxed ${typeClass[type]}`}>
                            {message}
                        </p>
                    </div>
            
                    {/* Modal footer */}
                    <div className="flex flex-row gap-6 justify-center items-center p-4 md:p-5 border-t border-gray-200 rounded-b dark:border-gray-600">
                        {
                            type === "success" ?
                            <Link
                                type="button"
                                to={targetPath}
                                className="text-white bg-lavender hover:bg-lavenderDark focus:ring-2 focus:outline-none focus:ring-purple-200 font-lg font-bold rounded-md text-lg px-10 py-3 text-center"
                            >
                                ok
                            </Link>
                            :
                            <>
                                <button
                                    type="button"
                                    onClick={onClose}
                                    className="text-white bg-lavender hover:bg-lavenderDark focus:ring-2 focus:outline-none focus:ring-purple-200 font-lg font-bold rounded-md text-lg px-10 py-3 text-center"
                                >
                                    try again
                                </button>
                                <Link
                                    type="button"
                                    to="/"
                                    className="text-white bg-lavender hover:bg-lavenderDark focus:ring-2 focus:outline-none focus:ring-purple-200 font-lg font-bold rounded-md text-lg px-10 py-3 text-center"
                                >
                                    home
                                </Link>
                            </>
                        }
                    </div>
                </div>
            </div>
        </div>
    );
}