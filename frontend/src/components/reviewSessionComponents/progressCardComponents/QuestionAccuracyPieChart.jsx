import ProgressPieChart from "../../trendComponents/ProgressPieChart";

export default function QuestionAccuracyPieChart({qpt}) {

    return (
        <div className="col-span-3 flex flex-col gap-2 bg-white rounded-lg shadow-md p-4 border-1 border-slateBlue/6 hover:shadow:xl">
            <h1 className="text-lg text-slateBlue/25 italic text-center">accuracy</h1>
            <ProgressPieChart
                correctCount={qpt ? qpt.correctCount : 0}
                totalCount={qpt ? qpt.totalCount : 0}
                dimensions={"lg:h-50 lg:w-40 xl:h-50 xl:w-40"}
                textDimensions={"text-md"}
            />
        </div>
    );
}