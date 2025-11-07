import ProgressBar from "../trendComponents/ProgressBar.jsx";
import { CircularProgressbar } from 'react-circular-progressbar';

const getCompletedDecimal = (completed, total) => {
    return completed / total;
}

export default function AggregateQuestionStatsCard({data}) {

    if (!data) {
        return null;
    }

    return (
        <div className="row-span-3 grid grid-cols-5 items-center w-full h-full gap-4 p-4 bg-white border-1 border-slateBlue/15 rounded-lg shadow-md hover:shadow-xl">
            <div className="col-span-2 xl:w-[185px] xl:h-[217px] p-4">
                <div className="relative w-full h-full">
                    <CircularProgressbar
                        value={getCompletedDecimal(data.totalCompleted, data.total) * 100}
                        styles={{
                            root: { width: "100%", height: "100%" },
                            path: { stroke: "#1cad9a" },
                            trail: { stroke: "rgba(31, 45, 61, 0.15)" },
                        }}
                    />
                    <div className="absolute inset-0 flex items-center justify-center">
                        <div className="flex flex-col items-center justify-center xl:w-20 xl:h-20 text-xl">
                            <p className="italic text-center">total</p>
                            <p className="text-center"><span className="text-2xl font-semibold">{data.totalCompleted}</span>/{data.total}</p>
                        </div>
                    </div>
                </div>
            </div>
            <div className="col-span-3 flex flex-col gap-2 w-full h-fit">
                <div className="grid grid-rows-3 gap-4">
                    <div className="flex flex-col gap-1 w-full h-fit px-1">
                        <div className="flex flex-row gap-2 justify-between items-end px-1">
                            <p className="italic">Easy</p>
                            <p className="text-slateBlue/50"><span className="text-xl text-slateBlue">{data.easyCompleted}</span>/{data.totalEasy}</p>
                        </div>
                        <div className="w-full h-fit">
                            <ProgressBar
                                containerStyle={"flex-1 w-full h-3 bg-slateBlue/20 border-1 border-slateBlue rounded-md overflow-hidden hover:cursor-pointer"}
                                barStyle={"h-full bg-teal rounded-l-md"} 
                                progressDecimal={getCompletedDecimal(data.easyCompleted, data.totalEasy)} 
                            />
                        </div>
                    </div>
                    <div className="flex flex-col gap-1 w-full h-fit px-1">
                        <div className="flex flex-row gap-2 justify-between items-end px-1">
                            <p className="italic">Medium</p>
                            <p className="text-slateBlue/50"><span className="text-xl text-slateBlue">{data.mediumCompleted}</span>/{data.totalMedium}</p>
                        </div>
                        <div className="w-full h-fit">
                            <ProgressBar
                                containerStyle={"flex-1 w-full h-3 bg-slateBlue/20 border-1 border-slateBlue rounded-md overflow-hidden hover:cursor-pointer"}
                                barStyle={"h-full bg-lavender rounded-l-md"} 
                                progressDecimal={getCompletedDecimal(data.mediumCompleted, data.totalMedium)} 
                            />
                        </div>
                    </div>
                    <div className="flex flex-col gap-1 w-full h-fit px-1">
                        <div className="flex flex-row gap-2 justify-between items-end px-1">
                            <p className="italic">Hard</p>
                            <p className="text-slateBlue/50"><span className="text-xl text-slateBlue">{data.hardCompleted}</span>/{data.totalHard}</p>
                        </div>
                        <div className="w-full h-fit">
                            <ProgressBar
                                containerStyle={"flex-1 w-full h-3 bg-slateBlue/20 border-1 border-slateBlue rounded-md overflow-hidden hover:cursor-pointer"}
                                barStyle={"h-full bg-slateBlue rounded-l-md"} 
                                progressDecimal={getCompletedDecimal(data.hardCompleted, data.totalHard)}
                            />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}