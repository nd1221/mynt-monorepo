import { useParams, useLocation, useLoaderData, Link } from "react-router-dom";
import { useState } from "react";
import api from "../api/modules.js";
import { handleError } from "../api/apiUtils.js";
import TrueFalseQuestion from "../components/questions/TrueFalseQuestion.jsx";
import MultipleChoiceQuestion from "../components/questions/MultipleChoiceQuestion.jsx";
import Timer from "../components/uiComponents/Timer.jsx";
import { authorise } from "../api/authorise.js";
import { formatAverageTime, formatDate, formatDecimal } from "../utils/progressTrackingUtils.js";
import PercentagePieChart from "../components/trendComponents/PercentagePieChart.jsx";
import ReviewCalendar from "../components/trendComponents/ReviewCalendar.jsx";
import Graph from "../components/trendComponents/Graph.jsx";

const fetchTestQuestions = async questionIds => {
	return await Promise.all(
		questionIds.map(async questionId => {
			const fetchedQuestion = await api.get(`questions/${questionId}`);
			return fetchedQuestion.data;
		})
	);
}

const fetchTest = async sectionId => {
    try {
        // Fetch Test
        const fetchedTest = await api.get(`/sections/${sectionId}/test`);
		const test = fetchedTest.data;
        // Fetch questions
        const questions = await fetchTestQuestions(test.questionIds);
		test.questions = questions;
        return test;
    } catch (err) {
        handleError(err);
        throw err;
    }
}

export async function loader({params}) {
    const sectionId = params.sectionId;
    // Authorise
    await authorise();
    return fetchTest(sectionId);
}

export default function TestPage() {

    const test = useLoaderData();

    const courseId = useParams().courseId;

    const location = useLocation();
    const state = location?.state;
    const sectionTitle = state?.sectionTitle;
	
    // State for question logic
	const [started, setStarted] = useState(false);
    const [submitted, setSubmitted] = useState(false);
    
	const [answers, setAnswers] = useState(Object.fromEntries(
		test.questionIds.map(questionId => [questionId, null])
	));
	
	// State for timer
	const [timeExpired, setTimeExpired] = useState(false);
	const [timeTaken, setTimeTaken] = useState(null);

    // Progress tracking state
    const [testProgressTracker, setTestProgressTracker] = useState(null);
    const [reviewHistory, setReviewHistory] = useState(null);

	const createTestUpdateDTO = timeTaken => {
		return {
			sectionId: state?.sectionId,
			score: getScore(),
			questionsAttempted: getAttempted(),
			outOfTime: timeExpired,
			testTimeMillis: timeTaken * 1000
		};
	}

	const handleStart = () => {
		setStarted(true);
	};
	
    const handleSubmit = async secondsRemaining => {
        try {
            setSubmitted(true);
            const timeTaken = test.timeLimit - secondsRemaining;
            setTimeTaken(timeTaken);
            const testProgressTracker = await api.patch(`/progress/update-test/${test.id}/${state?.courseProgressTrackerId}`, createTestUpdateDTO(timeTaken));
            setTestProgressTracker(testProgressTracker.data);
            const reviewHistory = await api.get(`/review/test/past-year/${testProgressTracker.data.id}`);
            setReviewHistory(reviewHistory.data);
        } catch(err) {
            handleError(err);
        }
    };

    const flagAnswer = (questionId, correct) => {
        setAnswers(prevAnswers => {
            return {
				...prevAnswers,
                [questionId]: correct
            };
        });
    };

	const handleTimeExpired = () => {
		setTimeExpired(true);
		handleSubmit();
	};

    const getScore = () => {
        let correctCount = 0;
        for (const [_, value] of Object.entries(answers)) {
            if (value) {
                correctCount += 1;
            }
        }
        return correctCount;
    };

	const getAttempted = () => {
        let correctCount = 0;
        for (const [_, value] of Object.entries(answers)) {
            if (value === true || value === false) {
                correctCount += 1;
            }
        }
        return correctCount;
    };

    const getPercentage = numberCorrect => {
        return Math.round((numberCorrect / test.numberOfQuestions) * 100);
    };

    const formatTimeTaken = () => {
		// Tests are constrained to have a max time limit of 60 mins
        const mins = Math.floor(timeTaken / 60);
		const secs = timeTaken % 60;
		return `${mins} : ${secs < 10 ? "0" + secs : secs}`;
	};

    const getProgressTrackerStats = tpt => {
        return (
            <div className="flex flex-row gap-10 w-full h-fit items-center mb-4">
                <PercentagePieChart percentage={tpt.averagePercentage}/>
                <div className="grow grid grid-cols-2 grid-rows-3 gap-8 h-full bg-white text-xl rounded-lg shadow-lg p-6 border border-gray-200">
                    <div className="flex flex-col w-fit h-fit gap-1">
                        <h3 class="text-sm font-medium text-slateBlue/50">Avg. Time per Test Attempt</h3>
                        <p class="text-3xl font-semibold text-slateBlue">{formatAverageTime(tpt.averageQuestionTimeMillis)} 🕓</p>
                    </div>
                    <div className="flex flex-col w-fit h-fit gap-1">
                        <h3 class="text-sm font-medium text-slateBlue/50">Out of Time Count</h3>
                        <p class="text-3xl font-semibold text-slateBlue">{tpt.outOfTimeCount} ⏱️</p>
                    </div>
                    <div className="flex flex-col w-fit h-fit gap-1">
                        <h3 class="text-sm font-medium text-slateBlue/50">Total Attempts</h3>
                        <p class="text-3xl font-semibold text-slateBlue">{tpt.numberOfAttempts} 🔁</p>
                    </div>
                    <div className="flex flex-col w-fit h-fit gap-1">
                        <h3 class="text-sm font-medium text-slateBlue/50">% Questions Attempted</h3>
                        <p class="text-3xl font-bold text-slateBlue">{formatDecimal(tpt.averagePercentQuestionsAttempted, 1)} 🎯</p>
                    </div>
                    <div className="flex flex-col w-fit h-fit gap-1">
                        <h3 class="text-sm font-medium text-slateBlue/50">First Reviewed</h3>
                        <p class="text-xl font-semibold text-slateBlue">{formatDate(tpt.firstAttempted)} 📅</p>
                    </div>
                    <div className="flex flex-col w-fit h-fit gap-1">
                        <h3 class="text-sm font-medium text-slateBlue/50">Last Reviewed</h3>
                        <p class="text-xl font-semibold text-slateBlue">{formatDate(tpt.lastAttempted)} 🗓️</p> 
                    </div>
                </div>
            </div>
        );
    };

    const getCalendarData = data => {
        return data.map(review => {
            return {
                "value": review.score,
                "day": review.reviewDate
            };
        });
    };

    const getCalendarLabel = value => {
        return value;
    };

    const getTimeHistoryData = () => {
        const map = reviewHistory.map(review => {
            return {
                date: review.reviewDate,
                value: Math.floor(review.testTimeMillis / 1000)
            };
        });
        return map;
    };

    const getScoreHistoryData = () => {
        const map = reviewHistory.map(review => {
            return {
                date: review.reviewDate,
                value: review.score
            };
        });
        return map;
    };

    return (
        <>
            <div className="relative flex-grow flex flex-col gap-4 w-6xl h-full items-center py-4">
                <h1 className="font-bold text-slateBlue text-4xl my-4">{`Section Test - ${sectionTitle}`}</h1>
                <hr className="my-4 w-full border-1 border-slateBlue/20" />

                {
                    submitted ?
					    <div className="flex flex-col gap-20 w-8/10 h-fit bg-slateBlue border-1 border-slateBlue shadow-md rounded-lg py-16 px-16 my-4">
                            <h1 className="text-white font-bold text-3xl mx-auto">🏁 Results - {getPercentage(getScore())} %</h1>
							<div className="flex flex-col items-center gap-2 text-white font-bold text-xl mx-auto">
                            	<h1>Answered {getScore()} of {test.numberOfQuestions} correctly</h1>
                            	<h1>Attempted {getAttempted()} of {test.numberOfQuestions}</h1>
                            	<h1>Time taken - {formatTimeTaken()}</h1>
							</div>
                        </div>
                    : null
                }

                {
                    testProgressTracker ?
					    <div className="flex flex-col gap-16 w-8/10 h-fit bg-white border-1 border-slateBlue/15 shadow-md rounded-lg py-16 px-16 my-4">
                            <h1 className="text-slateBlue font-bold text-3xl mx-auto">Analytics</h1>
                            {getProgressTrackerStats(testProgressTracker)}
                            {
                                reviewHistory ?
                                <>
                                    <ReviewCalendar data={reviewHistory} getCalendarData={getCalendarData} getCalendarLabel={getCalendarLabel} colours={["#2ABAA7"]} showTooltip={false}/>
                                    {
                                        reviewHistory.length > 1 ? // otherwise there is not sufficient review history data
                                            <>
                                                <h1 className="font-bold text-slateBlue text-3xl pl-3 mt-10">History</h1>
                                                <div className="flex flex-col w-full gap-4 items-center mb-6">
                                                    <Graph data={getScoreHistoryData()} fillColour={"#A78BFA"}/>
                                                    <h3 className="text-md text-slateBlue/50">previous scores</h3>
                                                </div>
                                                <div className="flex flex-col w-full gap-4 items-center mb-6">
                                                    <Graph data={getTimeHistoryData()} fillColour={"#1F2D3D"}/>
                                                    <h3 className="text-md text-slateBlue/50">previous test times (seconds)</h3>
                                                </div>
                                            </>
                                        : null
                                    }
                                </>
                                : null
                            }
                        </div>
                    : null
                }

                <div className="relative flex flex-row w-full gap-8">
					<div className="sticky top-8 -ml-28 w-fit h-fit	flex flex-col gap-6">
						<Timer timeLimit={test.timeLimit} handleTimeExpired={handleTimeExpired} submitted={submitted} handleSubmit={handleSubmit} handleStart={handleStart} started={started}/>
						<Link
                            to={`/courses/${courseId}`}
                            className="
                                block my-auto text-center h-fit w-full
                                bg-transparent text-lavender border-lavender border-3 p-4 px-8 text-xl font-bold rounded-xl
                                hover:bg-lavender hover:text-offWhite duration-100 active:scale-98
                            "
                            state={null}
                        >
							<div className="flex flex-col h-fit w-fit absolute left-5">
								<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2.5" stroke="currentColor" class="size-8">
									<path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
								</svg>
							</div>
                            <p>course</p>
                        </Link>
					</div>
					<div className="flex flex-col gap-20 w-8/10 min-h-96 bg-white border-1 border-slateBlue/15 shadow-md rounded-lg py-16 px-16 items-center justify-between my-4">
						{test.questions.map(question => {
							if (question.questionType === "MULTIPLE_CHOICE") {
								return <MultipleChoiceQuestion question={question} submitted={submitted} started={started} flagAnswer={flagAnswer}/>;
							} else {
								return <TrueFalseQuestion question={question} submitted={submitted} started={started} flagAnswer={flagAnswer}/>;
							}
						})}
					</div>
				</div>
                
                <div className="grid grid-cols-3 gap-8 w-full p-4 h-fit items-center justify-between">
                        <div className="
                                block my-auto text-center h-fit w-full
                                bg-transparent text-transparent border-transparent border-3 p-4 px-8 text-xl font-bold rounded-2xl
                            "
                        >placeholder</div>
                        <Link
                            to={`/courses/${courseId}`}
                            className="
                                block my-auto text-center h-fit w-full
                                bg-transparent text-lavender border-lavender border-3 p-4 px-8 text-xl font-bold rounded-xl
                                hover:bg-lavender hover:text-offWhite duration-100 active:scale-98
                            "
                            state={null}
                        >
                            <p>back to course</p>
                        </Link>
                        <div className="
                                block my-auto text-center h-fit w-full
                                bg-transparent text-transparent border-transparent border-3 p-4 px-8 text-xl font-bold rounded-2xl
                            "
                        >placeholder</div>
                </div>
            </div>
        </>
    );
}