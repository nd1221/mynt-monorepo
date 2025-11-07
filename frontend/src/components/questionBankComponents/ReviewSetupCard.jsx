import { useState, useEffect } from "react";
import ReviewSetupControl from "./ReviewSetupControl";
import CustomSetupControl from "./CustomSetupControl.jsx";
import api from "../../api/modules.js";
import { handleError } from "../../api/apiUtils";
import { buildSearchParams } from "../../utils/renderReviewSetupUtil.jsx";

const fetchReviewSessionMetadata = async (courseTrackerId, params) => {
    try {
        const response = await api.get(`/review/load-session/${courseTrackerId}`, { params });
        return response.data;
    } catch(err) {
        handleError(err);
    }
}

const fetchCustomSessionMetadata = async (courseTrackerId, params) => {
    try {
        const response = await api.get(`/review/load-custom-session/${courseTrackerId}`, { params });
        return response.data;
    } catch(err) {
        handleError(err);
    }
}

const fetchNumberOfReviewsDueToday = async courseTrackerId => {
    try {
        const response = await api.get(`/review/reviews-due-today/${courseTrackerId}`);
        return response.data;
    } catch(err) {
        handleError(err);
    }
}

const getReviewSetupNav = (showDefault, toggle, dueToday) => {
    return (
        <div className="flex flex-row w-full h-fit justify-between pr-4">
            <div className="flex flex-row gap-4 w-fit h-fit px-4 items-center justify-center italic font-semibold">
                <div 
                    className={`hover:text-lavender hover:cursor-pointer p-2 px-6 border-1 rounded-t-md ${showDefault ? "text-lavender border-b-slateBlue z-10" : "border-transparent"}`}
                    onClick={() => toggle(true)}
                >
                    review
                </div>
                <div 
                    className={`hover:text-teal hover:cursor-pointer p-2 px-6 border-1 rounded-t-md ${showDefault ? "border-transparent" : "text-teal border-b-slateBlue z-10"}`}
                    onClick={() => toggle(false)}
                >
                    custom
                </div>
            </div>
            <h1 className="h-full w-fit font-semibold text-lg">Reviews due today: <span className={showDefault ? "text-lavender" : "text-teal"}>{dueToday}</span></h1>
        </div>
    );
}

const initialiseFilters = () => {
    const filters = {
        section: 0,
        lesson: 0,
        numberRequested: 0,
        difficulty: 0,
    };
    return filters;
}

const initialiseReviewFilters = () => {
    const filters = {
        includeUnseen: false,
        slider: 0.1,
    };
    return filters;
}

const initialiseCustomFilters = () => {
    const filters = {
        priority: 0,
        slider: 0,
    };
    return filters;
}

export default function ReviewSetupCard({sectionMetadata, courseTrackerId, passedFilters, prevNav}) {

    const [dueToday, setDueToday] = useState(null);

    const [showDefault, setShowDefault] = useState(true);
    const toggleShowDefault = flag => {
        if (flag !== showDefault) {
            setShowDefault(flag);
        }
        toggleLoad(false);
    }

    const [filters, setFilters] = useState(passedFilters ?? initialiseFilters());
    const toggleFilters = (key, value) => {
        setFilters(prevFilters => (
            {
                ...prevFilters,
                [key]: value,
            }
        ));
        toggleLoad(false);
    }

    const [reviewFilters, setReviewFilters] = useState(initialiseReviewFilters());
    const toggleReviewFilters = (key, value) => {
        setReviewFilters(prevFilters => (
            {
                ...prevFilters,
                [key]: value
            }
        ))
        toggleLoad(false);
    }

    const [customFilters, setCustomFilters] = useState(initialiseCustomFilters());
    const toggleCustomFilters = (key, value) => {
        setCustomFilters(prevFilters => (
            {
                ...prevFilters,
                [key]: value
            }
        ))
        toggleLoad(false);
    }

    const [loadSession, setLoadSession] = useState(false);
    const toggleLoad = bool => {
        if (bool !== loadSession) {
            setLoadSession(bool);
        }
    }

    const [loadedSession, setLoadedSession] = useState(null);

    const resetFilters = () => {
        setFilters(initialiseFilters());
        toggleLoad(false);
    }

    // load session metadata
    useEffect(() => {
        const loadNewSession = async () => {
            const params = showDefault ? buildSearchParams(filters, reviewFilters) : buildSearchParams(filters, customFilters);
            let session;
            if (showDefault) {
                session = await fetchReviewSessionMetadata(courseTrackerId, params);
            } else {
                session = await fetchCustomSessionMetadata(courseTrackerId, params);
            }
            setLoadedSession(session);
        }
        if (loadSession) {
            loadNewSession();
        }
    }, [loadSession]);

    // Get number of reviews due today
    useEffect(() => {
        const getReviewsDueToday = async () => {
            const dueToday = await fetchNumberOfReviewsDueToday(courseTrackerId);
            setDueToday(dueToday);
            toggleLoad(true);
        }
        getReviewsDueToday();
    }, []);

    return (
        <div className="row-span-6 flex flex-col w-full h-full gap-2 bg-slateBlue text-offWhite border-2 border-slateBlue rounded-md p-4 items-center hover:shadow-xl">
            <div className={`flex flex-row w-full h-fit gap-4 items-center justify-center ${showDefault ? "text-lavender" : "text-teal"}`}>
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                    <path d="M11.25 4.533A9.707 9.707 0 0 0 6 3a9.735 9.735 0 0 0-3.25.555.75.75 0 0 0-.5.707v14.25a.75.75 0 0 0 1 .707A8.237 8.237 0 0 1 6 18.75c1.995 0 3.823.707 5.25 1.886V4.533ZM12.75 20.636A8.214 8.214 0 0 1 18 18.75c.966 0 1.89.166 2.75.47a.75.75 0 0 0 1-.708V4.262a.75.75 0 0 0-.5-.707A9.735 9.735 0 0 0 18 3a9.707 9.707 0 0 0-5.25 1.533v16.103Z" />
                </svg>
                <h1 className="font-bold text-lg italic text-center text-offWhite">Review Session Setup</h1>
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                    <path fill-rule="evenodd" d="M12 5.25c1.213 0 2.415.046 3.605.135a3.256 3.256 0 0 1 3.01 3.01c.044.583.077 1.17.1 1.759L17.03 8.47a.75.75 0 1 0-1.06 1.06l3 3a.75.75 0 0 0 1.06 0l3-3a.75.75 0 0 0-1.06-1.06l-1.752 1.751c-.023-.65-.06-1.296-.108-1.939a4.756 4.756 0 0 0-4.392-4.392 49.422 49.422 0 0 0-7.436 0A4.756 4.756 0 0 0 3.89 8.282c-.017.224-.033.447-.046.672a.75.75 0 1 0 1.497.092c.013-.217.028-.434.044-.651a3.256 3.256 0 0 1 3.01-3.01c1.19-.09 2.392-.135 3.605-.135Zm-6.97 6.22a.75.75 0 0 0-1.06 0l-3 3a.75.75 0 1 0 1.06 1.06l1.752-1.751c.023.65.06 1.296.108 1.939a4.756 4.756 0 0 0 4.392 4.392 49.413 49.413 0 0 0 7.436 0 4.756 4.756 0 0 0 4.392-4.392c.017-.223.032-.447.046-.672a.75.75 0 0 0-1.497-.092c-.013.217-.028.434-.044.651a3.256 3.256 0 0 1-3.01 3.01 47.953 47.953 0 0 1-7.21 0 3.256 3.256 0 0 1-3.01-3.01 47.759 47.759 0 0 1-.1-1.759L6.97 15.53a.75.75 0 0 0 1.06-1.06l-3-3Z" clip-rule="evenodd" />
                </svg>
            </div>
            <hr className="w-full border-1 border-ofWhite mt-1 mb-2" />
            {
                dueToday !== null ?
                <div className="flex-1 flex flex-col w-full">
                    {getReviewSetupNav(showDefault, toggleShowDefault, dueToday)}
                    <div className={`w-full h-full rounded-md border-1 -mt-[1px] ${showDefault ? "border-lavender" : "border-teal"}`}>
                        {
                            showDefault ?
                            <ReviewSetupControl sections={sectionMetadata} filters={filters} toggleFilters={toggleFilters} reviewFilters={reviewFilters} toggleReviewFilters={toggleReviewFilters} showDefault={showDefault} load={loadSession} toggleLoad={toggleLoad} resetFilters={resetFilters} metadata={loadedSession} courseTrackerId={courseTrackerId} prevNav={prevNav} />
                            :
                            <CustomSetupControl sections={sectionMetadata} filters={filters} toggleFilters={toggleFilters} customFilters={customFilters} toggleCustomFilters={toggleCustomFilters} showDefault={showDefault} load={loadSession} toggleLoad={toggleLoad} resetFilters={resetFilters} metadata={loadedSession} courseTrackerId={courseTrackerId} prevNav={prevNav} />
                        }
                    </div>
                </div>
                : null
            }
        </div>
    );
}