import { useParams, useLocation, useLoaderData, Link } from "react-router-dom";
import { useState } from "react";
import api from "../api/modules.js";
import { handleError } from "../api/apiUtils.js";
import TrueFalseQuestion from "../components/questions/TrueFalseQuestion";
import MultipleChoiceQuestion from "../components/questions/MultipleChoiceQuestion";
import { authorise } from "../api/authorise.js";

const fetchQuestion = async questionId => {
    try {
        // Fetch Question
        return await api.get(`/questions/${questionId}`);
    } catch (err) {
        handleError(err);
        throw err;
    }
}

export async function loader({params}) {
    const questionId = params.questionId;
    const lessonTrackerId = params.lessonProgressTrackerId;
    // Authorise
    await authorise();
    const question = await fetchQuestion(questionId);
    return [question.data, lessonTrackerId];
}

const getPrevLink = (prevId, courseTrackerId) => {
    return (
        <Link
        to={`/course-units/${prevId}/${courseTrackerId}`}
        className="
            text-center h-fit w-full
            bg-transparent text-lavenderDark/90 border-lavender border-3 p-4 px-8 text-lg font-bold rounded-lg
            hover:bg-lavender hover:text-offWhite duration-100 active:scale-98
            flex flex-row gap-4 justify-center items-center
        "
        >
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                <path fill-rule="evenodd" d="M11.03 3.97a.75.75 0 0 1 0 1.06l-6.22 6.22H21a.75.75 0 0 1 0 1.5H4.81l6.22 6.22a.75.75 0 1 1-1.06 1.06l-7.5-7.5a.75.75 0 0 1 0-1.06l7.5-7.5a.75.75 0 0 1 1.06 0Z" clip-rule="evenodd" />
            </svg>
            <p>prev unit</p>
        </Link>
    );
}

const getNextLink = (nextId, isTestNext, courseId, sectionId, courseTrackerId) => {

    let path;

	if (isTestNext) {
		path = `/courses/${courseId}/sections/${sectionId}/section-test`;
	} else if (nextId) {
		path = `/course-units/${nextId}/${courseTrackerId}`;
	} else {
		path = "/not-found";
	}

    // Build next link element
    return (
        <Link
            to={path}
            className="
                text-center h-fit w-full
                bg-transparent text-lavenderDark/90 border-lavender border-3 p-4 px-8 text-lg font-bold rounded-lg
                hover:bg-lavender hover:text-offWhite duration-100 active:scale-98
            flex flex-row gap-4 justify-center items-center
            "
        >
            <p>{isTestNext ? "section test" : "next unit"}</p>
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                <path fill-rule="evenodd" d="M12.97 3.97a.75.75 0 0 1 1.06 0l7.5 7.5a.75.75 0 0 1 0 1.06l-7.5 7.5a.75.75 0 1 1-1.06-1.06l6.22-6.22H3a.75.75 0 0 1 0-1.5h16.19l-6.22-6.22a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
            </svg>
        </Link>
    );
}

export default function QuestionPage() {

    const questionId = useParams().questionId;
    const [question, lessonTrackerId] = useLoaderData();

    // // Location state for navigation
    const location = useLocation().state;
    const courseId = location?.courseId;
    const prevId = location?.prevId;
    const courseTrackerId = location?.courseTrackerId;
    const nextId = location?.nextId;
    const isTestNext = location?.isTestNext;
    const sectionId = location?.sectionId;

    // State for question logic
    const [submitted, setSubmitted] = useState(false);
    
    const handleSubmit = () => {
        setSubmitted(true);
    }

    return (
        <div className="flex-grow flex flex-col gap-4 w-6xl h-full justify-between items-center py-6">
            <h1 className="font-bold text-slateBlue text-4xl my-4">Check your understanding</h1>
            <div className="flex flex-col gap-20 w-8/10 min-h-96 bg-white border-1 border-slateBlue/15 shadow-md rounded-lg py-16 px-16 items-center justify-between mb-4">
                {
                    question.questionType === "MULTIPLE_CHOICE" ? 
                        <MultipleChoiceQuestion question={question} submitted={submitted}/>
                    :
                        <TrueFalseQuestion question={question} submitted={submitted}/>
                }
                <button 
                    className="text-xl font-bold w-fit bg-tealDark/90 text-offWhite px-8 py-4 rounded-lg hover:cursor-pointer hover:opacity-85 active:scale-98 transition duration-50"
                    disabled={submitted}
                    onClick={() => {handleSubmit()}}
                >submit</button>
            </div>
            <div className="grid grid-cols-4 gap-8 w-8/10 p-4 h-fit items-center justify-between">
                {getPrevLink(prevId, courseTrackerId)}
                <Link
                    to={`/courses/${courseId}`}
                    className="
                        text-center h-fit w-full
                        bg-transparent text-slateBlue border-slateBlue border-3 p-4 px-8 text-lg font-bold rounded-lg
                        hover:bg-slateBlue hover:text-offWhite duration-100 active:scale-98
                        flex flex-row gap-4 justify-center items-center
                    "
                    state={null}
                >
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-7">
                        <path fill-rule="evenodd" d="M9.53 2.47a.75.75 0 0 1 0 1.06L4.81 8.25H15a6.75 6.75 0 0 1 0 13.5h-3a.75.75 0 0 1 0-1.5h3a5.25 5.25 0 1 0 0-10.5H4.81l4.72 4.72a.75.75 0 1 1-1.06 1.06l-6-6a.75.75 0 0 1 0-1.06l6-6a.75.75 0 0 1 1.06 0Z" clip-rule="evenodd" />
                    </svg>
                    <p>course</p>
                </Link>
                <Link
                    to={`/user-profile/${courseTrackerId}/unit-overview/${lessonTrackerId}`}
                    className="
                        text-center h-fit w-full
                        bg-transparent text-slateBlue border-slateBlue border-3 p-4 px-8 text-lg font-bold rounded-lg
                        hover:bg-slateBlue hover:text-offWhite duration-100 active:scale-98 
                        flex flex-row gap-4 justify-center items-center
                    "
                >
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                        <path fill-rule="evenodd" d="M1.5 7.125c0-1.036.84-1.875 1.875-1.875h6c1.036 0 1.875.84 1.875 1.875v3.75c0 1.036-.84 1.875-1.875 1.875h-6A1.875 1.875 0 0 1 1.5 10.875v-3.75Zm12 1.5c0-1.036.84-1.875 1.875-1.875h5.25c1.035 0 1.875.84 1.875 1.875v8.25c0 1.035-.84 1.875-1.875 1.875h-5.25a1.875 1.875 0 0 1-1.875-1.875v-8.25ZM3 16.125c0-1.036.84-1.875 1.875-1.875h5.25c1.036 0 1.875.84 1.875 1.875v2.25c0 1.035-.84 1.875-1.875 1.875h-5.25A1.875 1.875 0 0 1 3 18.375v-2.25Z" clip-rule="evenodd" />
                    </svg>
                    <p>dashboard</p>
                </Link>
                {getNextLink(nextId, isTestNext, courseId, sectionId, courseTrackerId)}
            </div>
        </div>
    );
}