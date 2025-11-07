import { useLoaderData, useLocation } from "react-router-dom";
import { authorise } from "../api/authorise";
import api from "../api/modules.js";
import { handleError } from "../api/apiUtils.js";
import { useState, useEffect } from "react";
import ReviewSessionProgressBar from "../components/reviewSessionComponents/ReviewSessionProgressBar.jsx";
import QuestionCard from "../components/reviewSessionComponents/QuestionCard.jsx";
import QuestionAndSessionProgressSidebar from "../components/reviewSessionComponents/QuestionAndSessionProgressSidebar.jsx";

const fetchSession = async (courseTrackerId, questionTrackerIds, unseenQuestionIds) => {
    try {
        const response = await api.post(`/review/get-session/${courseTrackerId}`, {
            questionTrackerIds,
            unseenQuestionIds
        });
        return response.data;
    } catch(err) {
        handleError(err);
    }
}

export async function loader({params}) {
    // Authorise
    await authorise();
    return params.courseProgressTrackerId;
}

const initialiseSessionTracker = () => {
    const sessionTracker = {
        streak: 0,
        longestStreak: 0,
        correct: 0,
        totalSeen: 0,
        skipped: 0,
        totalTimeTaken: 0,
    };
    return sessionTracker;
}

const getUpdatedSessionTracker = (prev, wasAnswered, wasCorrect, time) => {
    
    const streak = wasCorrect ? prev.streak + 1 : 0;
    const longestStreak = streak > prev.longestStreak ? streak : prev.longestStreak;
    const correct = wasCorrect ? prev.correct + 1 : prev.correct;
    const totalSeen = prev.totalSeen + 1;
    const skipped = wasAnswered ? prev.skipped : prev.skipped + 1;
    const totalTimeTaken = prev.totalTimeTaken + time;
    
    return {
        streak,
        longestStreak,
        correct,
        totalSeen,
        skipped,
        totalTimeTaken,
    };
}

export default function ReviewSession() {

    const location = useLocation();
    const sessionState = location.state?.sessionState || {};
    const prevNav = location.state?.prevNav;
    const questionBankFilters = location.state?.filters;

    const courseProgressTrackerId = useLoaderData();

    const [session, setSession] = useState(null);
    const [inSession, toggleInSession] = useState(true);
    
    // Update QPT / AQT after review posted for displaying results or if doesn't exist
    const updateQuestionTrackers = (currentQuestion, updatedTracker, tracker) => {
        setSession(prev => prev.map((sessionData, i) => currentQuestion === i ? {...sessionData, [tracker]: updatedTracker} : sessionData));
    }

    const [currentQuestion, setCurrentQuestion] = useState(0);
    const toggleCurrent = index => {
        if (index >= 0 && index <= session.length - 1) {
            setCurrentQuestion(index);
        }
    }

    const [answered, setAnswered] = useState(0);
    const toggleAnswered = () => {
        if (answered < session.length) {
            setAnswered(prevAnswered => prevAnswered + 1);
        }
    };

    // Array tracking correct/incorrect answers
    const [correctArray, setCorrectArray] = useState(null);
    const toggleCorrectArray = (bool, index) => {
        setCorrectArray(prevCorrect => prevCorrect.map((prev, i) => index === i ? bool : prev));
    };

    // Array tracking user's submitted answers
    const [answers, setAnswers] = useState(null);
    const toggleAnswers = (newAnswer, index) => {
        setAnswers(prev => prev.map((prevAnswer, i) => index === i ? newAnswer : prevAnswer));
    }

    // Array tracking which questions have been submitted
    const [submittedArray, setSubmittedArray] = useState(null);
    const togglesubmittedArray = (bool, index) => {
        setSubmittedArray(prevSubmitted => prevSubmitted.map((prev, i) => index === i ? bool : prev));
    };

    // Fetch session data
    useEffect(() => {
        const getSession = async () => {
            const session = await fetchSession(courseProgressTrackerId, sessionState.questionProgressTrackerIds, sessionState.unseenQuestionIds);
            setSession(session);
            setCorrectArray(new Array(session.length).fill(null));
            setSubmittedArray(new Array(session.length).fill(false));
            setReviews(new Array(session.length).fill(null));
            setAnswers(new Array(session.length).fill(null));
        };
        getSession();
    }, []);

    // State for tracking current review session
    const [sessionTracker, setSessionTracker] = useState(initialiseSessionTracker());
    const updateSessionTracker = (wasAnswered, wasCorrect, time) => {
        setSessionTracker(prev => getUpdatedSessionTracker(prev, wasAnswered, wasCorrect, time));
    }

    // State to keep hold of question reviews for displaying to user if they want to review session before returning to bank
    // However, each review is sent to backend for updating immediately to display updated question tracker
    const [reviews, setReviews] = useState(null);
    const updateReviews = newReview => {
        setReviews(prev => prev.map((review, i) => currentQuestion === i ? newReview : review));
    }

    return (
        <div className="grid grid-cols-[2fr_5fr_4fr] gap-8 w-full max-h-screen h-full">
            {
                session ?
                    <>
                        <ReviewSessionProgressBar 
                            data={session} 
                            correct={correctArray} 
                            sessionState={sessionState} 
                            answered={answered} 
                            courseTrackerId={courseProgressTrackerId}
                            toggleCurrent={toggleCurrent}
                            prevNav={prevNav}
                            questionBankFilters={questionBankFilters}
                        />
                        <QuestionCard 
                            data={session[currentQuestion]} 
                            isLast={currentQuestion === session.length - 1} 
                            current={currentQuestion} 
                            toggleCurrent={toggleCurrent} 
                            correctArray={correctArray} 
                            toggleCorrect={toggleCorrectArray} 
                            submittedArray={submittedArray} 
                            toggleSubmitted={togglesubmittedArray}
                            inSession={inSession}
                            toggleSession={toggleInSession} 
                            toggleAnswered={toggleAnswered}
                            updateReviews={updateReviews}
                            updateSessionTracker={updateSessionTracker}
                            courseTrackerId={courseProgressTrackerId}
                            userAnswers={answers}
                            toggleAnswers={toggleAnswers}
                            updateQuestionTrackers={updateQuestionTrackers}
                            prevNav={prevNav}
                            questionBankFilters={questionBankFilters}
                        />
                        <QuestionAndSessionProgressSidebar 
                            data={session[currentQuestion]}
                            review={reviews[currentQuestion]} 
                            sessionTracker={sessionTracker}
                            reviewDependency={session}
                        />
                    </>
                : null
            }
        </div>
    );
}