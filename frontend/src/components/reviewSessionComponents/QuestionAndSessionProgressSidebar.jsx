import CurrentQuestionDataCard from "./CurrentQuestionDataCard";
import CurrentSessionDataCard from "./CurrentSessionDataCard";
import QuestionProgressCard from "./QuestionProgressCard";

export default function QuestionAndSessionProgressSidebar({data, review, sessionTracker, reviewDependency}) {

    return (
        <div className="grid grid-rows-4 gap-8 lg:h-[80vh] xl:h-[80vh]">
            <div className="row-span-1 grid grid-cols-5 gap-8 w-full">
                <CurrentQuestionDataCard data={review} />
                <CurrentSessionDataCard data={sessionTracker} />
            </div>
            <QuestionProgressCard data={data} reviewDependency={reviewDependency} />
        </div>
    );
}