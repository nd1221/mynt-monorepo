import UnitMasteryCompletenessCard from "./UnitMasteryCompletenessCard";
import UnitSummaryCard from "./UnitSummaryCard";
import ProgressStatsContent from "../ProgressStatsContent";
import ProgressPieChart from "../../trendComponents/ProgressPieChart";
import FrequencyCalendar from "../../trendComponents/FrequencyCalendar";
import UnitAverageQuestionTimeCard from "./UnitAverageQuestionTimeCard";
import { reviewsNotPresent } from "../../../utils/progressTrackingUtils";

export default function UnitOverviewCard({lesson, tracker, iconURL, section, reviewHistory}) {

    return (
        <div className="row-span-7 grid grid-cols-12 gap-4 overflow-auto items-center rounded-lg bg-slateBlue/5 p-6 shadow-md border-1 border-slateBlue/6">
            <div className="col-span-12 row-span-2 flex flex-row h-fit w-full text-3xl font-bold gap-8 items-center overflow-hidden">
                <h1 className="flex-8 overflow-wrap">{lesson.position} - {lesson.title}</h1>
                <h1 className="flex-2 font-medium text-lg text-right px-2">Reviews due today - {tracker.questionsDueToday}</h1>
            </div>
            <UnitMasteryCompletenessCard
                completeness={tracker.completeness}
                mastery={tracker.mastery}
                courseTrackerId={tracker.courseProgressTrackerId}
                sectionId={section.id}
                lessonId={lesson.id}
            />
            <UnitSummaryCard
                lesson={lesson}
                iconURL={iconURL}
                section={section}
                courseTrackerId={tracker.courseProgressTrackerId}
            />
            <UnitAverageQuestionTimeCard trackerId={tracker.id} />
            <div className="col-span-4 row-span-4 grid grid-cols-2 grid-rows-3 gap-4 p-2 w-full h-full bg-white border-1 border-slateBlue/15 rounded-lg shadow-md hover:shadow-xl">
                <ProgressStatsContent tracker={tracker} />
            </div>
            <div className="col-span-3 row-span-2 flex flex-col items-center p-4 w-full h-fit bg-white border-1 border-slateBlue/15 rounded-lg shadow-md hover:shadow-xl">
                <p className="text-md text-slateBlue/50 italic">average review accuracy</p>
                <ProgressPieChart
                    correctCount={Math.floor(tracker.totalQuestionsReviewed * (tracker.accuracy / 100))}
                    totalCount={tracker.totalQuestionsReviewed} isPercentage={true}
                />
            </div>
            <div className="col-span-9 row-span-2 flex flex-col items-center justify-between w-full h-full bg-white p-2 py-4 rounded-lg border-1 border-slateBlue/15 shadow-md hover:shadow-xl">
                <h1 className="font-bold text-lg italic">review summary - past year</h1>
                <FrequencyCalendar data={reviewHistory} />
                <h3 className="text-md text-slateBlue/50">{reviewsNotPresent(reviewHistory) ? "no reviews yet" : "days practised"}</h3>
            </div>
        </div>
    );
}