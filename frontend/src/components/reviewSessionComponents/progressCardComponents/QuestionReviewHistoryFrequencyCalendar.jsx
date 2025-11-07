import { reviewsNotPresent } from "../../../utils/progressTrackingUtils";
import FrequencyCalendar from "../../trendComponents/FrequencyCalendar";

export default function QuestionReviewHistoryFrequencyCalendar({reviewHistory}) {

    const reviews = reviewHistory === null ? [] : reviewHistory;

    return (
        <div className="col-span-9 flex flex-col justify-between items-center w-full h-full bg-white p-2 py-4 rounded-lg border-1 border-slateBlue/15 shadow-md hover:shadow-xl">
            <h1 className="font-bold text-lg italic text-slateBlue/25">question reviews - past year</h1>
            <FrequencyCalendar data={reviews} />
            <h3 className="text-md text-slateBlue/50">{reviewsNotPresent(reviewHistory) ? "no reviews yet" : "days practised"}</h3>
        </div>
    );
}