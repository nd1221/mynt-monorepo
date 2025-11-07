import { formatAverageTime, formatDate } from "../../../utils/progressTrackingUtils";

export default function QuestionProgressStatsCard({qpt}) {

    const notAttempted = qpt == null;

    return (
        <div className="col-span-6 grid grid-cols-2 grid-rows-3 gap-4 bg-white rounded-lg shadow-md p-4 border border-slateBlue/6 hover:shadow-xl">
            <div className="flex flex-col w-fit h-fit gap-1">
                <h3 class="text-sm font-medium text-slateBlue/50">Streak</h3>
                <p class="text-lg font-bold text-slateBlue">{notAttempted ? "--" : `${qpt.correctStreak} 🔥`}</p>
            </div>
            <div className="flex flex-col w-fit h-fit gap-1">
                <h3 class="text-sm font-medium text-slateBlue/50">Correct Answers</h3>
                <p class="text-lg font-semibold text-slateBlue">{notAttempted ? "--" : `${qpt.correctCount} ✅`}</p>
            </div>
            <div className="flex flex-col w-fit h-fit gap-1">
                <h3 class="text-sm font-medium text-slateBlue/50">Total Attempts</h3>
                <p class="text-lg font-semibold text-slateBlue">{notAttempted ? "--" : `${qpt.totalCount} 🎯`}</p>
            </div>
            <div className="flex flex-col w-fit h-fit gap-1">
                <h3 class="text-sm font-medium text-slateBlue/50">Avg. Time per Question</h3>
                <p class="text-lg font-semibold text-slateBlue">{notAttempted ? "--" : `${formatAverageTime(qpt.averageQuestionTimeMillis)} ⏱️`}</p>
            </div>
            <div className="flex flex-col w-fit h-fit gap-1">
                <h3 class="text-sm font-medium text-slateBlue/50">First Reviewed</h3>
                <p class="text-lg font-semibold text-slateBlue">{notAttempted || !qpt.firstReviewedDate ? "--" : `${formatDate(qpt.firstReviewedDate)} 📅`}</p>
            </div>
            <div className="flex flex-col w-fit h-fit gap-1">
                <h3 class="text-sm font-medium text-slateBlue/50">Last Reviewed</h3>
                <p class="text-lg font-semibold text-slateBlue">{notAttempted || !qpt.lastReviewedDate ? "--" : `${formatDate(qpt.lastReviewedDate)} 🕓`}</p> 
            </div>
        </div>
    );
}