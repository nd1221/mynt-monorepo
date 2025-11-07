import { useEffect, useRef } from "react";
import { formatDecimal } from "../../utils/progressTrackingUtils";

const getDifficulty = accuracy => {

    let difficulty;
    let colour;

    if (accuracy <= 33.33) {
        difficulty = "Hard";
        colour = "bg-slateBlue";
    } else if (accuracy <= 66.66) {
        difficulty = "Med";
        colour = "bg-lavender"
    } else {
        difficulty = "Easy";
        colour = "bg-teal";
    }

    return (
        <div className="col-span-1 flex flex-col justify-center w-full h-full">
            <div className={`w-fit h-fit p-1 px-2 rounded-xl ${colour} text-xs text-white font-bold`}>{difficulty}</div>
        </div>
    );
}

const getAggregateStats = aqt => {
    
    if (!aqt) {
        return null;
    }

    return (
        <div className="flex flex-row w-full justify-between items-center gap-4">
            <div className="flex-4 flex flex-col h-fit">
                <div className="flex flex-row gap-4">
                    <p className="text-sm italic">Avg accuracy:</p>
                    <p className="italic font-semibold">{formatDecimal(aqt.accuracy, 1)}%</p>
                </div>
                <div className="flex flex-row gap-4">
                    <p className="text-sm italic">Total Attempts:</p>
                    <p className="italic font-semibold">{aqt.numberOfAttempts}</p>
                </div>
            </div>
            <div className="flex-1 flex flex-row">
                {getDifficulty(aqt.accuracy)}
            </div>
        </div>
    );
}

const getTick = (answered, isCorrect, isCurrent) => {

    if (answered) {
        if (isCorrect) {
            return (
                <div className="w-7 h-7 text-teal bg-[#f4f5f5] rounded-full">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-7">
                        <path fill-rule="evenodd" d="M2.25 12c0-5.385 4.365-9.75 9.75-9.75s9.75 4.365 9.75 9.75-4.365 9.75-9.75 9.75S2.25 17.385 2.25 12Zm13.36-1.814a.75.75 0 1 0-1.22-.872l-3.236 4.53L9.53 12.22a.75.75 0 0 0-1.06 1.06l2.25 2.25a.75.75 0 0 0 1.14-.094l3.75-5.25Z" clip-rule="evenodd" />
                    </svg>
                </div>
            );
        } else if (isCorrect === null) {
            return (
                <div className="w-7 h-7 text-lavender bg-[#f4f5f5] rounded-full">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-7">
                        <path fill-rule="evenodd" d="M12 2.25c-5.385 0-9.75 4.365-9.75 9.75s4.365 9.75 9.75 9.75 9.75-4.365 9.75-9.75S17.385 2.25 12 2.25Zm3 10.5a.75.75 0 0 0 0-1.5H9a.75.75 0 0 0 0 1.5h6Z" clip-rule="evenodd" />
                    </svg>
                </div>
            );
        } else {
            return (
                <div className="w-7 h-7 text-slateBlue bg-[#f4f5f5] rounded-full">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-7">
                        <path fill-rule="evenodd" d="M12 2.25c-5.385 0-9.75 4.365-9.75 9.75s4.365 9.75 9.75 9.75 9.75-4.365 9.75-9.75S17.385 2.25 12 2.25Zm-1.72 6.97a.75.75 0 1 0-1.06 1.06L10.94 12l-1.72 1.72a.75.75 0 1 0 1.06 1.06L12 13.06l1.72 1.72a.75.75 0 1 0 1.06-1.06L13.06 12l1.72-1.72a.75.75 0 1 0-1.06-1.06L12 10.94l-1.72-1.72Z" clip-rule="evenodd" />
                    </svg>
                </div>
            );
        }
    } else {
        return (
            <div className={`w-7 h-7 bg-[#f4f5f5] rounded-full ${ isCurrent ? "text-lavender" : "text-slateBlue/30"}`}>
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-7">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M15 12H9m12 0a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
                </svg>
            </div>
        );
    }

}

const getProgressBlocks = (questions, correct, toggleCurrent, answered) => {

    const sessionFinished = questions.length === answered;

    const handleClick = (index, sessionOver) => {
        if (sessionOver) {
            toggleCurrent(index);
        }
    }

    return questions.map((question, index) => {
    
        const isAnswered = index <= answered - 1;
        const isCurrent = index === answered;
        const upperLineSegment = isAnswered || isCurrent ? "bg-teal" : "bg-teal/15";
        const lowerLineSegment = index === questions.length - 1 ? "bg-transparent" : ( isAnswered ? "bg-teal" : "bg-teal/15" );

        return (
            <div className="relative flex flex-row items-center gap-4 w-full h-fit p-1">
                <div className="flex flex-col items-center h-full">
                    <div className={`w-[3px] flex-1 -mb-4 ${upperLineSegment}`}></div>
                    {getTick(isAnswered, correct[index], isCurrent)}
                        <div className={`w-[3px] flex-1 -mb-4 ${lowerLineSegment}`}></div>
                </div>
                <div
                    onClick={() => handleClick(index, sessionFinished)} 
                    className={`flex flex-col gap-2 w-full text-sm border-t-1 p-1 border-slateBlue/20 ${isCurrent ? "currentQuestion" : ""} ${sessionFinished ? "hover:bg-slateBlue/5 hover:rounded-md hover:cursor-pointer" : ""}`}
                >
                    <h1 className="italic font-semibold text-lavender">
                        S{question.question.sectionPosition} - L{question.question.lessonPosition}, Question {question.question.lessonNumber}
                    </h1>
                    {getAggregateStats(question.aqt)}
                </div>
            </div>
        );
    });
}

export default function SessionProgressCard({data, correct, toggleCurrent, answered}) {

    // Ensure component scrolls as user progersses through session to avoid having to manually scroll
    const scrollRef = useRef(null);
    useEffect(() => {
        if (scrollRef.current) {
            const currentQuestion = scrollRef.current.querySelector(".currentQuestion");
            if (currentQuestion) {
                currentQuestion.scrollIntoView({behavior: "smooth", block: "center"});
            }
        }
    }, [answered]);

    return (
        <div 
            ref={scrollRef}
            className="row-span-6 flex flex-col overflow-auto gap-4 items-center rounded-lg bg-slateBlue/5 p-6 py-8 shadow-md border-1 border-slateBlue/6 hover:shadow-xl"
        >
            <div className="relative flex flex-col gap-2 w-full h-fit">
                {getProgressBlocks(data, correct, toggleCurrent, answered)}
            </div>
        </div>
    );
}