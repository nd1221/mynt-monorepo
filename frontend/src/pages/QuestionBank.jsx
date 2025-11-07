import { authorise } from "../api/authorise";
import { useLoaderData, useLocation } from "react-router-dom";
import { Link } from "react-router-dom";
import api from "../api/modules.js";
import { handleError } from "../api/apiUtils.js";
import AggregateQuestionStatsCard from "../components/questionBankComponents/AggregateQuestionStatsCard.jsx";
import ReviewSetupCard from "../components/questionBankComponents/ReviewSetupCard.jsx";
import QuestionBankFilter from "../components/questionBankComponents/QuestionBankFilter.jsx";
import { useState, useEffect } from "react";

const fetchQuestionBankMetadata = async courseProgressTrackerId => {
    try {
        const response = await api.get(`/review/question-bank-metadata/${courseProgressTrackerId}`);
        return response.data;
    } catch(err) {
        handleError(err);
    }
}

const fetchAggregateQuestionStats = async courseProgressTrackerId => {
    try {
        const response = await api.get(`/progress/course-progress/${courseProgressTrackerId}/question-bank/aggregate`);
        return response.data;
    } catch(err) {
        handleError(err);
    }
}

export async function loader({params}) {
    // Authorise
    await authorise();

    // Fetch metadata
    const courseTrackerId = params.courseProgressTrackerId;
    const metadata = await fetchQuestionBankMetadata(courseTrackerId);

    return [metadata, courseTrackerId];
}

export default function QuestionBank() {

    const data = useLoaderData();

    const location = useLocation();
    const prevNav = location.state?.prevNav;
    const filters = location.state?.filters;
    const reviewSetupFilters = location.state?.reviewSetupFilters;

    const [metadata, courseTracker] = data;

    const [aggregateStats, setAggregateStats] = useState(null);
    useEffect(() => {
        const getData = async () => {
            const aggregateStats = await fetchAggregateQuestionStats(courseTracker);
            setAggregateStats(aggregateStats);
        };
        getData();
    }, []);

    return (
        <div className="grid grid-cols-[5fr_2fr] gap-8 w-full h-fit">
            <div className="grid grid-cols-1 grid-rows-8 gap-8 w-full lg:h-[80vh] xl:h-[80vh]">
                <div className="row-span-1 grid grid-rows-1 grid-cols-4 gap-12 items-center w-full h-full p-4">
                    <Link
                        to={prevNav.from}
                        className="col-span-1 flex flex-row gap-4 items-center justify-center p-2 py-3 h-fit w-full border-3 rounded-md font-bold text-lg border-slateBlue text-slateblue hover:cursor-pointer hover:text-white hover:bg-slateBlue transition duration-100"
                    >
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-8">
                            <path fill-rule="evenodd" d="M9.53 2.47a.75.75 0 0 1 0 1.06L4.81 8.25H15a6.75 6.75 0 0 1 0 13.5h-3a.75.75 0 0 1 0-1.5h3a5.25 5.25 0 1 0 0-10.5H4.81l4.72 4.72a.75.75 0 1 1-1.06 1.06l-6-6a.75.75 0 0 1 0-1.06l6-6a.75.75 0 0 1 1.06 0Z" clip-rule="evenodd" />
                        </svg>
                        <p>{prevNav.prevPageName}</p>
                    </Link>
                    <div className="col-span-3 flex flex-row w-full h-fit gap-8">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-16">
                            <path d="M5.625 3.75a2.625 2.625 0 1 0 0 5.25h12.75a2.625 2.625 0 0 0 0-5.25H5.625ZM3.75 11.25a.75.75 0 0 0 0 1.5h16.5a.75.75 0 0 0 0-1.5H3.75ZM3 15.75a.75.75 0 0 1 .75-.75h16.5a.75.75 0 0 1 0 1.5H3.75a.75.75 0 0 1-.75-.75ZM3.75 18.75a.75.75 0 0 0 0 1.5h16.5a.75.75 0 0 0 0-1.5H3.75Z" />
                        </svg>
                        <div className="flex flex-col items-start gap-2 font-bold text-3xl">
                            <h1 className="text-lavender">Question Bank</h1>
                            <h1 className="italic text-wrap text-xl">{metadata.courseName}</h1>
                        </div>
                    </div>
                </div>
                <QuestionBankFilter courseTracker={courseTracker} sectionMetadata={metadata.sections} initialFilters={filters} prevNav={prevNav} />
            </div>
            <div className="grid grid-cols-1 grid-rows-9 gap-8 lg:h-[80vh] xl:h-[80vh]">
                <AggregateQuestionStatsCard data={aggregateStats} />
                <ReviewSetupCard sectionMetadata={metadata.sections} courseTrackerId={courseTracker} passedFilters={reviewSetupFilters} prevNav={prevNav} /> 
            </div>
        </div>
    );
}