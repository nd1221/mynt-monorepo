import { formatAverageTime, formatDate, calculateCorrectQuestions } from "../../utils/progressTrackingUtils";

const getAverageQuestionTime = time => {
    
    if (time === 0) {
        return (
            <div className="text-lg font-semibold text-slateBlue">
                <p className="">no reviews yet</p>
            </div>
        );
    }

    return (
        <p class="text-2xl font-semibold text-slateBlue">
            {formatAverageTime(time)} ⏱️
        </p>
    );
}

export default function ProgressStatsContent({tracker}) {

    return (
        <>
            <div className="flex flex-col h-fit p-2">
                <h3 class="text-sm font-medium text-slateBlue/50">Streak</h3>
                <p class="text-2xl font-bold text-slateBlue">{tracker.streak} 🔥</p>
            </div>
            <div className="flex flex-col h-fit p-2">
                <h3 class="text-sm font-medium text-slateBlue/50">Correct Answers</h3>
                <p class="text-2xl font-semibold text-slateBlue">{calculateCorrectQuestions(tracker.totalQuestionsReviewed, (tracker.accuracy / 100))} ✅</p>
            </div>
            <div className="flex flex-col h-fit p-2">
                <h3 class="text-sm font-medium text-slateBlue/50">Total Attempts</h3>
                <p class="text-2xl font-semibold text-slateBlue">{tracker.totalQuestionsReviewed} 🎯</p>
            </div>
            <div className="flex flex-col h-fit p-2">
                <h3 class="text-xs font-medium text-slateBlue/50">Avg. Time per Question</h3>
                {getAverageQuestionTime(tracker.averageQuestionTimeMillis)}
            </div>
            <div className="flex flex-col h-fit p-2">
                <h3 class="text-sm font-medium text-slateBlue/50">First Reviewed</h3>
                <p class="text-lg font-semibold text-slateBlue">{tracker.firstPracticeDate == null ? "n/a" : formatDate(tracker.firstPracticeDate)} 📅</p>
            </div>
            <div className="flex flex-col h-fit p-2">
                <h3 class="text-sm font-medium text-slateBlue/50">Last Reviewed</h3>
                <p class="text-lg font-semibold text-slateBlue">{tracker.lastPracticeDate == null ? "n/a" : formatDate(tracker.lastPracticeDate)} 🕓</p> 
            </div>
        </>
    );
}