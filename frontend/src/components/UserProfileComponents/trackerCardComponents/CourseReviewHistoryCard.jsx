import FrequencyCalendar from "../../trendComponents/FrequencyCalendar";
import { reviewsNotPresent } from "../../../utils/progressTrackingUtils";

export default function CourseReviewHistoryCard({reviewHistory}) {

    return (
        <div className="col-span-7 row-span-3 flex flex-col justify-between items-center w-full h-full bg-white p-2 py-4 rounded-lg border-1 border-slateBlue/15 shadow-md hover:shadow-xl">
            <h1 className="font-bold text-lg italic">review summary - past year</h1>
            {reviewHistory == null ? null : <FrequencyCalendar data={reviewHistory} />}
            <h3 className="text-md text-slateBlue/50">{reviewsNotPresent(reviewHistory) ? "no reviews yet" : "days practised"}</h3>
        </div>
    );
}