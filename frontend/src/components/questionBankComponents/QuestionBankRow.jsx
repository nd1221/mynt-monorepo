import { Link } from "react-router-dom";
import { formatDecimal, formatAverageTime } from "../../utils/progressTrackingUtils.js";

const getReviewSessionState = (question, attempted, qpt) => {
    const state = {
        numberOfCoreQuestions: question.core ? 1 : 0,
        questionProgressTrackerIds: attempted ? [qpt.id] : [],
        unseenQuestionIds: attempted ? [] : [question.id],
    };
    return state;
}

const getCoreAnsweredIcon = () => {
    return (
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
            <path stroke-linecap="round" stroke-linejoin="round" d="M9 12.75 11.25 15 15 9.75M21 12c0 1.268-.63 2.39-1.593 3.068a3.745 3.745 0 0 1-1.043 3.296 3.745 3.745 0 0 1-3.296 1.043A3.745 3.745 0 0 1 12 21c-1.268 0-2.39-.63-3.068-1.593a3.746 3.746 0 0 1-3.296-1.043 3.745 3.745 0 0 1-1.043-3.296A3.745 3.745 0 0 1 3 12c0-1.268.63-2.39 1.593-3.068a3.745 3.745 0 0 1 1.043-3.296 3.746 3.746 0 0 1 3.296-1.043A3.746 3.746 0 0 1 12 3c1.268 0 2.39.63 3.068 1.593a3.746 3.746 0 0 1 3.296 1.043 3.746 3.746 0 0 1 1.043 3.296A3.745 3.745 0 0 1 21 12Z" />
        </svg>
    );
}
const getAnsweredIcon = () => {
    return (
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" class="size-6">
            <path stroke-linecap="round" stroke-linejoin="round" d="m4.5 12.75 6 6 9-13.5" />
        </svg>
    );
}
const getLockedIcon = () => {
    return (
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
            <path fill-rule="evenodd" d="M12 1.5a5.25 5.25 0 0 0-5.25 5.25v3a3 3 0 0 0-3 3v6.75a3 3 0 0 0 3 3h10.5a3 3 0 0 0 3-3v-6.75a3 3 0 0 0-3-3v-3c0-2.9-2.35-5.25-5.25-5.25Zm3.75 8.25v-3a3.75 3.75 0 1 0-7.5 0v3h7.5Z" clip-rule="evenodd" />
        </svg>
    );
}

const getIcon = (core, answered, locked) => {
    
    let icon;
    let colour;

    if (locked) {
        icon = getLockedIcon();
    } else if (answered) {
        if (core) {
            icon = getCoreAnsweredIcon();
            colour = "text-lavender";
        } else {
            icon = getAnsweredIcon();
            colour = "text-teal";
        }
    } else {
        icon = null;
        colour = "";
    }
    
    return (
        <div className={`col-span-1 flex flex-col items-center justify-center ${locked && "opacity-40"} ${colour}`}>
            {icon}
        </div>
    );
}

const getQuestionTitle = (question, locked, reviewSessionState, courseTrackerId, prevNav, attempted, questionBankFilters) => {

    const questionTitle = `Lesson ${question.lessonPosition} - Question ${question.lessonNumber}`;
    const titleColour = attempted ? question.core ? 
        "text-lavender font-semibold active:lavenderDark" : 
        "text-teal font-semibold active:tealDark" : "";

    return (
        <div className="col-span-5 flex flex-row gap-4 p-2 justify-between items-center">
            {
                locked ?
                <h1 className="hover:cursor-default hover:text-lavender">{questionTitle}</h1>
                :
                <Link
                    to={`/review-session/${courseTrackerId}`}
                    state={{
                        sessionState: reviewSessionState,
                        prevNav: prevNav,
                        filters: questionBankFilters,
                    }} 
                    className={`${titleColour} italic basis-3/4 transition duration-100 h-full hover:underline hover:cursor-pointer`}
                >
                    {questionTitle}
                </Link>
            }
            {
                question.core ?
                    <div className="basis-1/4 flex flex-col items-end justify-center font-semibold text-lavender">
                        <div className="border-2 border-lavender/75 w-fit h-fit px-1 rounded-sm">
                            core
                        </div>
                    </div>
                : null
            }
        </div>
    );
}

const getSection = sectionId => {
    return (
        <div className="col-span-1 flex flex-col items-center justify-center">{sectionId}</div>
    );
}

const getDifficulty = globalAccuracy => {

    let difficulty;
    let colour;

    if (globalAccuracy <= 33.33) {
        difficulty = "Hard";
        colour = "bg-slateBlue";
    } else if (globalAccuracy <= 66.66) {
        difficulty = "Medium";
        colour = "bg-lavender"
    } else {
        difficulty = "Easy";
        colour = "bg-teal";
    }

    return (
        <div className="col-span-1 flex flex-col justify-center w-full h-full">
            <div className={`w-fit h-fit p-1 px-2 rounded-xl ${colour} text-sm text-white font-bold`}>{difficulty}</div>
        </div>
    );
}

const getAccuracy = (globalAccuracy, userAccuracy, attempted) => {
    return (
        <div className="col-span-2 flex flex-row gap-8 justify-center px-4 w-full text-sm font-medium italic items-center">
            <p className={`text-end w-full ${attempted ? "" : "opacity-50"}`}>{attempted ? `${formatDecimal(userAccuracy, 1)}%` : "--"}</p>
            <p className="text-end w-full">{formatDecimal(globalAccuracy, 1)}%</p>
        </div>
    );
}

const getAvgTime = (globalTime, userTime, attempted) => {
    return (
        <div className="col-span-2 flex flex-row gap-8 justify-center px-4 w-full text-sm font-medium italic items-center">
            <p className={`text-end w-full ${attempted ? "" : "opacity-50"}`}>{attempted ? formatAverageTime(userTime) : "--"}</p>
            <p className="text-end w-full">{formatAverageTime(globalTime)}</p>
        </div>
    );
}

const getAttempts = (globalAttempts, userAttempts, attempted) => {
    return (
        <div className="col-span-2 flex flex-row gap-8 justify-center px-4 w-full text-sm font-medium italic items-center">
            <p className={`text-end w-full ${attempted ? "" : "opacity-50"}`}>{attempted ? userAttempts : "--"}</p>
            <p className="text-end w-full">{globalAttempts}</p>
        </div>
    );
}

export default function QuestionBankRow({
    data,
    index,
    courseTrackerId,
    prevNav,
    questionBankFilters
}) {

    const {aqt, qpt, question} = data;
    const attempted = qpt != null;
    const locked = question.locked;
    const reviewSessionState = getReviewSessionState(question, attempted, qpt);

    return (
        <div className={`grid w-full h-full lg:max-h-10 xl:max-h-10 grid-cols-14 grid-rows-1 gap-4 px-2 ${index % 2 == 0 ? "bg-slateBlue/10" : "bg-offWhite"}`}>
            {getIcon(question.core, attempted, locked)}
            {getQuestionTitle(question, locked, reviewSessionState, courseTrackerId, prevNav, attempted, questionBankFilters)}
            {getSection(question.sectionPosition)}
            {getDifficulty(aqt.accuracy)}
            {getAccuracy(aqt.accuracy, qpt ? qpt.accuracy : 0, attempted)}
            {getAvgTime(aqt.averageTimeMillis, qpt ? qpt.averageQuestionTimeMillis : 0, attempted)}
            {getAttempts(aqt.numberOfAttempts, qpt ? qpt.totalCount : 0, attempted)}
        </div>
    );
}