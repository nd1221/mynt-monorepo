import ReviewSessionMetadataCard from "./ReviewSessionMetadataCard";
import SessionProgressCard from "./SessionProgressCard";

export default function ReviewSessionProgressBar({
    data,
    correct,
    sessionState,
    answered,
    courseTrackerId,
    toggleCurrent,
    prevNav,
    questionBankFilters
}) {

    return (
        <div className="grid grid-cols-1 grid-rows-9 gap-8 lg:h-[80vh] xl:h-[80vh]">
            <ReviewSessionMetadataCard
                sessionState={sessionState}
                answered={answered}
                courseTrackerId={courseTrackerId}
                prevNav={prevNav}
                questionBankFilters={questionBankFilters}
            />
            <SessionProgressCard
                data={data}
                correct={correct}
                toggleCurrent={toggleCurrent}
                answered={answered}
            />
        </div>
    );
}