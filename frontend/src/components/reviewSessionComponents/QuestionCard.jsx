import MultipleChoiceQuestion from "../questions/MultipleChoiceQuestion";
import TrueFalseQuestion from "../questions/TrueFalseQuestion";
import { useEffect, useRef, useState } from "react";
import { useStopwatch } from "react-timer-hook";
import { formatAverageTime } from "../../utils/progressTrackingUtils";
import { Link } from "react-router-dom";
import api from "../../api/modules.js";
import { handleError } from "../../api/apiUtils.js";
import { useAuthContext } from "../AuthContext.jsx";
import ProgressBar from "../trendComponents/ProgressBar";

const createQPT = async (courseTrackerId, lessonId, aqtId, questionId) => {
    try {
        await api.get(`/progress/create-question-tracker/${courseTrackerId}/${lessonId}/${aqtId}/${questionId}`);
        const response = await api.get(`/progress/get-question-tracker/${courseTrackerId}/${lessonId}/${questionId}`);
        return response.data;
    } catch(err) {
        handleError(err);
    }
}

const createAQT = async questionId => {
    try {
        await api.get(`/progress/create-aggregate-question-tracker/${questionId}`);
        const response = await api.get(`/progress/get-aggregate-question-tracker/${questionId}`);
        return response.data;
    } catch(err) {
        handleError(err);
    }
}

const sendReview = async (userTrackerId, questionTrackerId, milliseconds, currentQuestionCorrect, userRating) => {
    try {
        const reviewDTO = {
            questionProgressTrackerId: questionTrackerId,
            reviewDate: new Date().toISOString().split("T")[0],
            questionTimeMillis: milliseconds,
            correct: currentQuestionCorrect,
            userRating: userRating
        };
        await api.post(`/review/${userTrackerId}/question/${questionTrackerId}`, reviewDTO);
    } catch(err) {
        handleError(err);
    }
}

const updateSpacedRepetition = async (questionTrackerId, userRating) => {
    try {
        await api.patch(
            `/progress/update-user-rating/${questionTrackerId}`, 
            userRating,
            { headers: { "Content-Type": "application/json" } }
        );
    } catch(err) {
        handleError(err);
    }
}

const updateQuestionTracker = async (questionTrackerId, answered, isCorrect, questionTimeMillis) => {
    try {
        const updateDTO = {
            answered: answered,
            wasCorrect: isCorrect,
            questionTime: questionTimeMillis,
        };
        const response = await api.patch(`/progress/update-question/${questionTrackerId}`, updateDTO);
        return response.data;
    } catch(err) {
        handleError(err);
    }
}

const getMetadata = (question, aqt) => {

    return (
        <div className="flex flex-col gap-3 w-full h-fit jusitfy-between">
            <div className="flex flex-row items-center justify-between w-full h-fit gap-4">
                <h1 className="font-semibold italic text-lg text-lavender">Session {question.sectionPosition}, Lesson {question.lessonPosition} - Question {question.lessonNumber}</h1>
                {
                    question.core ?
                    <div className="border-3 border-lavender px-2 rounded-md font-semibold text-lavender">core</div>
                    : null
                }
            </div>
            <div className="flex flex-row gap-4 w-full h-fit items-center justify-between">
                <p className="font-semibold italic">Global:</p>
                <div className="flex flex-row gap-4 w-fit h-fit">
                    <p>📈 Frequency: <span className="font-semibold italic">{aqt.numberOfAttempts}</span></p>
                </div>
                <div className="flex flex-row gap-4 w-fit h-fit">
                    <p>🎯 <span className="font-semibold italic">{aqt.accuracy}%</span> answered correctly</p>
                </div>
                <div className="flex flex-row gap-4 w-fit h-fit">
                    <p>⏱️ Avg. Time: <span className="font-semibold italic">{formatAverageTime(aqt.averageTimeMillis)}</span></p>
                </div>
            </div>
        </div>
    );
};

const getUserRatingInputBox = (userRating, toggle) => {

    const baseClass = "flex flex-col items-center justify-center text-lg font-bold w-8 h-8 p-2 hover:cursor-pointer hover:rounded-full";
    const activeClass = "text-offWhite bg-slateBlue rounded-full";

    return userRating.map((ratingFlag, i) => (
        <div
            className={`${baseClass} ${ratingFlag ? activeClass : "hover:bg-slateBlue/10"}`}
            onClick={() => toggle(i)}
        >
            {i}
        </div>
    ));
};

const getUserRatingPrompt = (userRating, toggle) => {
    return (
        <div className="flex flex-col w-full h-fit gap-4 items-center justify-center">
            <div className="flex flex-col gap-2 w-full h-fit items-center">
                <h1 className="text-lg mb-4">How easy did you find this question?</h1>
                <div className="flex flex-row items-center h-fit w-3/5 gap-2 px-2">
                    <p className="text-md">hard</p>
                    <div className="grid grid-cols-6 gap-8 w-3/5 mx-auto">
                        {getUserRatingInputBox(userRating, toggle)}
                    </div>
                    <p className="text-md">easy</p>
                </div>
            </div>
        </div>
    );
};

const getFinishedSessionNavigation = (current, toggleCurrent, isLast, courseTrackerId, prevNav, filters) => {

    // Purely for layout styling
    const getPlaceholder = () => {
        return (
            <div className="flex flex-row gap-4 items-center justify-center text-md font-bold w-full border-2 border-transparent text-transparent px-6 py-3 rounded-md hover:bg-transparent">
                placeholder
            </div>
        );
    }

    return (
        <div className="flex flex-row gap-4 w-full h-fit justify-between">
            {
                current === 0 ?
                getPlaceholder()
                :
                <button 
                    className="flex flex-row gap-4 items-center justify-center text-md font-bold w-full border-2 border-lavender text-lavender px-6 py-3 rounded-md hover:bg-lavender hover:text-offWhite hover:cursor-pointer active:scale-99 transition duration-100"
                    onClick={() => toggleCurrent(current - 1)}
                >
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                        <path fill-rule="evenodd" d="M11.03 3.97a.75.75 0 0 1 0 1.06l-6.22 6.22H21a.75.75 0 0 1 0 1.5H4.81l6.22 6.22a.75.75 0 1 1-1.06 1.06l-7.5-7.5a.75.75 0 0 1 0-1.06l7.5-7.5a.75.75 0 0 1 1.06 0Z" clip-rule="evenodd" />
                    </svg>
                    <p>prev</p>
                </button>
            }
            <Link
                to={`/question-bank/${courseTrackerId}`}
                state={{prevNav, filters}}
                className="flex flex-row gap-4 items-center justify-center text-md font-bold w-full border-2 border-slateBlue text-slateBlue px-6 py-3 rounded-md hover:bg-slateBlue hover:text-offWhite hover:cursor-pointer active:scale-99 transition duration-100"
            >
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                    <path d="M5.625 3.75a2.625 2.625 0 1 0 0 5.25h12.75a2.625 2.625 0 0 0 0-5.25H5.625ZM3.75 11.25a.75.75 0 0 0 0 1.5h16.5a.75.75 0 0 0 0-1.5H3.75ZM3 15.75a.75.75 0 0 1 .75-.75h16.5a.75.75 0 0 1 0 1.5H3.75a.75.75 0 0 1-.75-.75ZM3.75 18.75a.75.75 0 0 0 0 1.5h16.5a.75.75 0 0 0 0-1.5H3.75Z" />
                </svg>
                <p>question bank</p>
            </Link>
            {
                isLast ?
                getPlaceholder()
                :
                <button 
                    className="flex flex-row gap-4 items-center justify-center text-md font-bold w-full border-2 border-lavender text-lavender px-6 py-3 rounded-md hover:bg-lavender hover:text-offWhite hover:cursor-pointer active:scale-99 transition duration-100"
                    onClick={() => toggleCurrent(current + 1)}
                >
                    <p>next</p>
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                        <path fill-rule="evenodd" d="M12.97 3.97a.75.75 0 0 1 1.06 0l7.5 7.5a.75.75 0 0 1 0 1.06l-7.5 7.5a.75.75 0 1 1-1.06-1.06l6.22-6.22H3a.75.75 0 0 1 0-1.5h16.19l-6.22-6.22a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
                    </svg>
                </button>
            }
        </div>
    );
}

const getNavigationButtons = (handleNext, current, scrollRef) => {

    return (
        <button 
            ref={scrollRef}
            className="flex flex-row gap-4 items-center text-md font-bold w-fit border-2 border-teal text-teal px-6 py-3 rounded-md hover:bg-teal hover:text-offWhite hover:cursor-pointer active:scale-99 transition duration-100"
            onClick={() => handleNext(current + 1)}
        >
            <p>next question</p>
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                <path fill-rule="evenodd" d="M12.97 3.97a.75.75 0 0 1 1.06 0l7.5 7.5a.75.75 0 0 1 0 1.06l-7.5 7.5a.75.75 0 1 1-1.06-1.06l6.22-6.22H3a.75.75 0 0 1 0-1.5h16.19l-6.22-6.22a.75.75 0 0 1 0-1.06Z" clip-rule="evenodd" />
            </svg>
        </button>
    );
}

const getQuestionBorderStyle = (submitted, isCorrect) => {
    if (!submitted) {
        return "border-slateBlue";
    } else {
        if (isCorrect === null) {
            return "border-lavender";
        } else if (isCorrect) {
            return "border-green-600";
        } else {
            return "border-red-600";
        }
    }
}

const getStoredReview = (answered, correct, time, rating) => {
    return {
        wasAnswered: answered,
        wasCorrect: correct,
        timeTaken: time,
        userRating: rating,
    };
}

const getUserRating = userRating => {
    for (let i = 0; i < userRating.length; i++) {
        if (userRating[i]) {
            return i;
        }
    }
    return 0;
}

const getProgressBarDecimal = (current, totalQuestions) => {
    return (current) / totalQuestions;
}

export default function QuestionCard({
    data, 
    isLast, 
    current, 
    toggleCurrent, 
    correctArray, 
    toggleCorrect, 
    submittedArray, 
    toggleSubmitted, 
    inSession, 
    toggleSession,
    toggleAnswered,
    updateReviews,
    updateSessionTracker,
    courseTrackerId,
    userAnswers,
    toggleAnswers,
    updateQuestionTrackers,
    prevNav,
    questionBankFilters
}) {
    
    const {aqt, qpt, question} = data;

    // Create QPT and AQT if they do not exist yet
    useEffect(() => {
        const ensureTrackersExist = async () => {
            
            let newAQT;
            if (aqt == null) {
                newAQT = await createAQT(question.id);
                updateQuestionTrackers(current, newAQT, "aqt");
            }

            const aqtId = aqt == null ? newAQT.id : aqt.id;
            console.log("AQT");
            console.log(aqt);
            
            if (qpt == null) {
                const newQPT = await createQPT(courseTrackerId, question.lessonId, aqtId, question.id);
                updateQuestionTrackers(current, newQPT, "qpt");
            }
        };
        ensureTrackersExist();
    }, [current]);

    const authContext = useAuthContext();
    const currentUser = authContext?.currentUser;
    const userTrackerId = currentUser.userProgressTrackerId || null;

    const [currentOptionSelectedCorrect, setCurrentOptionSelectedCorrect] = useState(null);
    const handleFlagAnswer = (_, isCorrect, choice) => {
        setCurrentOptionSelectedCorrect(isCorrect);
        toggleAnswers(choice, current);
    }

    const {
        totalSeconds,
        start: startStopwatch,
        pause: pauseStopwatch,
        reset: resetStopwatch,
    } = useStopwatch({autoStart: false});
    
    // State to track if current question has been submitted for resetting the stopwatch
    const [currentSubmitted, setCurrentSubmitted] = useState(false);
    const [questionTime, setQuestionTime] = useState(0);

    // Immediately start stopwatch
    useEffect(() => {
        startStopwatch();
    }, []);
    
    // Reset stopwatch
    useEffect(() => {
        if (currentSubmitted) {
            pauseStopwatch();
            setQuestionTime(totalSeconds);
            resetStopwatch(0, false);
        } else {
            startStopwatch();
        }
    }, [currentSubmitted]);

    const handleSubmit = () => {
        const wasAnswered = currentOptionSelectedCorrect === null ? false : true;
        toggleSubmitted(wasAnswered, current);
        toggleCorrect(currentOptionSelectedCorrect, current);
        setCurrentSubmitted(true);
    }
    
    const [userRating, setUserRating] = useState([true, ...Array(5).fill(false)]);
    const toggleUserRating = i => {
        setUserRating(prevUserRating => prevUserRating.map(
            (_, index) => index === i
        ));
    };

    // Update Current Question Stats Card
    useEffect(() => {
        
        const updateCurrentQuestionStats = () => {
            const inMemoryReview = getStoredReview(
                submittedArray[current],
                correctArray[current],
                questionTime,
                getUserRating(userRating)
            )
            updateReviews(inMemoryReview);
        };
        
        if (currentSubmitted) {
            updateCurrentQuestionStats();
        }
    }, [userRating, questionTime]);
    
    // Update Current Session Stats Card
    useEffect(() => {

        const updateCurrentSessionStats = () => {
            updateSessionTracker(
                submittedArray[current],
                correctArray[current],
                questionTime,
            );
        }
        
        if (currentSubmitted) {
            updateCurrentSessionStats();
        }
    }, [questionTime]);

    // Handle get next question
    // Sends user review to backend to update QuestionProgressTracker
    const handleNext = async newIndex => {

        const prevCurrent = current;

        // Run this first for a smoother UI, removes flickering/stuttering
        toggleCurrent(newIndex);
        if (isLast) {
            toggleSession(false);
        }
        
        toggleAnswered();
        toggleUserRating(0);
        setCurrentOptionSelectedCorrect(null);
        setCurrentSubmitted(false);
        setQuestionTime(0);
        
        // Send review
        await sendReview(userTrackerId, qpt.id, questionTime * 1000, correctArray[prevCurrent], getUserRating(userRating));
        await updateSpacedRepetition(qpt.id, getUserRating(userRating));
        const updatedQPT = await updateQuestionTracker(qpt.id, submittedArray[prevCurrent], correctArray[prevCurrent], questionTime * 1000);
        updateQuestionTrackers(prevCurrent, updatedQPT, "qpt");
    }

    // Ensure next question button scrolls into view if question card overflows
    const scrollRef = useRef(null);
    useEffect(() => {
        if (scrollRef.current) {
            scrollRef.current.scrollIntoView({behavior: "smooth", block: "center"});
        }
    }, [submittedArray]);
    
    return (
        <div className="flex flex-col gap-4 lg:h-[80vh] xl:h-[80vh]">

            <div className="flex flex-col w-full lg:h-9 xl:h-9 items-center">
                <h1 className="text-2xl font-semibold italic">{inSession ? "Review Session In Progress" : "Review Session Finished"}</h1>
                <ProgressBar
                    progressDecimal={inSession ? getProgressBarDecimal(current, submittedArray.length) : 1.0}
                    containerStyle={"flex-1 w-19/20 h-1 bg-slateBlue/20 rounded-lg"}
                    barStyle={"h-full bg-lavender rounded-l-lg"}
                /> 
            </div>

            <div className="flex flex-col overflow-auto gap-6 h-full items-center rounded-lg bg-slateBlue/5 p-6 shadow-md border-1 border-slateBlue/6 hover:shadow-xl">
                {getMetadata(question, aqt)}
                <div className={`flex flex-col gap-4 w-full h-full items-center justify-between p-8 rounded-md border-3 ${getQuestionBorderStyle(submittedArray[current], correctArray[current])}`}>
                    <div className="flex-1 flex flex-col items-center justify-center w-full h-fit gap-10">
                        {
                            question.questionType === "MULTIPLE_CHOICE" ? 
                                <MultipleChoiceQuestion
                                    key={question.id}
                                    question={question}
                                    started={true}
                                    submitted={!inSession || currentSubmitted}
                                    flagAnswer={handleFlagAnswer}
                                    prevAnswer={userAnswers[current]}
                                />
                            :
                                <TrueFalseQuestion
                                    key={question.id}
                                    question={question}
                                    started={true}
                                    submitted={!inSession || currentSubmitted}
                                    flagAnswer={handleFlagAnswer}
                                    prevAnswer={userAnswers[current]}
                                />
                            }
                    </div>
                    {inSession ?
                    
                        currentSubmitted ?
                            <>
                                {getUserRatingPrompt(userRating, toggleUserRating)}
                                <hr className="w-4/5 border-slateBlue/20"/>
                                {getNavigationButtons(handleNext, current, scrollRef)}
                            </>
                        :
                        <button 
                            className="text-lg font-bold w-fit bg-tealDark/90 text-offWhite px-6 py-3 rounded-md hover:opacity-85 hover:cursor-pointer active:scale-99 transition duration-50"
                            onClick={() => handleSubmit()}
                        >
                            submit
                        </button>
                    
                    : 
                        getFinishedSessionNavigation(current, toggleCurrent, isLast, courseTrackerId, prevNav, questionBankFilters)
                    }
                </div>
            </div>
        </div>
    );
}