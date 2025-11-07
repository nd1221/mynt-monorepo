import ProgressBar from "../../trendComponents/ProgressBar";
import { formatPercentage, getAggregateSectionStats } from "../../../utils/progressTrackingUtils.js";

const getMasteryCompletenessStats = (data, key) => {

    if (data == null) {
        return null;
    }

    // data is in form [{lessonProgressTrackerId, lessonTitle, completeness, mastery}, {}, {} ...]
    return data.map(entry => (
        <div className="flex flex-row text-lg justify-between">
            <h1 className="italic flex-7 pr-4 truncate"><span className="font-bold">{entry.sectionPosition}</span> - {entry.sectionTitle}</h1>
            <h1 className={`flex-1 font-bold text-right ${key === "mastery" ? "text-teal" : "text-lavender"}`}>{formatPercentage(getAggregateSectionStats(entry, key))}</h1>
        </div>
    ));
}

const getMasteryLockedModal = data => {

    if (data == null) {
        return null;
    }

    return (
        <div className="flex flex-col gap-4">
            {
                data.map(entry => (
                    <div className="flex flex-row text-lg justify-between">
                        <h1 className="italic flex-7 pr-4 truncate"><span className="font-bold">{entry.sectionPosition}</span> - {entry.sectionTitle}</h1>
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                            <path fill-rule="evenodd" d="M12 1.5a5.25 5.25 0 0 0-5.25 5.25v3a3 3 0 0 0-3 3v6.75a3 3 0 0 0 3 3h10.5a3 3 0 0 0 3-3v-6.75a3 3 0 0 0-3-3v-3c0-2.9-2.35-5.25-5.25-5.25Zm3.75 8.25v-3a3.75 3.75 0 1 0-7.5 0v3h7.5Z" clip-rule="evenodd" />
                        </svg>
                    </div>
                ))
            }
        </div>
    );
}

export default function MasteryCompletenessCard({data}) {

    const {completeness, mastery, sectionData} = data;
    const masteryLocked = completeness !== 1;

    return (
        <div className="col-span-9 flex flex-col w-full h-30 gap-4 p-4">
            <div className="relative group flex flex-row gap-8 w-full h-fit items-center">
                <h1 className="lg:w-38 font-bold text-xl">completeness</h1>
                <ProgressBar 
                    progressDecimal={completeness}
                    containerStyle={"flex-1 w-full h-5 bg-slateBlue/20 border-1 border-slateBlue rounded-md overflow-hidden group-hover:shadow-xl hover:cursor-pointer"}
                    barStyle={"h-full bg-lavender rounded-l-md group-hover:opacity-75"}
                />
                <div className="
                    absolute group-hover:opacity-100 group-hover:scale-100 opacity-0 scale-90
                    transition-all duration-200 z-50
                    flex flex-col gap-2
                    bg-white border-1 border-slateBlue/15 h-fit lg:w-120 xl:w-133 shadow-lg rounded-md p-4
                    lg:left-45 lg:top-10
                    pointer-events-none
                    "
                >
                    <h1 className="text-lg font-bold italic">Completeness Per Section</h1>
                    <hr className="w-full border-1 border-slateBlue/15 mb-2" />
                    {getMasteryCompletenessStats(sectionData, "completeness")}
                </div>
                <div className="flex flex-col items-center xl:w-13 h-fit">
                    <h1 className="w-fit italic text-xl">{formatPercentage(completeness)}</h1>
                </div>
            </div>
            <div className="relative group flex flex-row gap-8 w-full h-fit items-center">
                <h1 className={`lg:w-38 font-bold text-xl ${masteryLocked ? "opacity-30" : ""}`}>mastery</h1>
                <ProgressBar 
                    progressDecimal={masteryLocked ? 0 : mastery}
                    containerStyle={`flex-1 w-full h-5 bg-slateBlue/20 border-1 border-slateBlue rounded-md overflow-hidden group-hover:shadow-xl hover:cursor-pointer ${masteryLocked ? "opacity-30" : ""}`}
                    barStyle={"h-full bg-teal rounded-l-md group-hover:opacity-75"}
                />
                <div className="
                    absolute group-hover:opacity-100 group-hover:scale-100 opacity-0 scale-90
                    transition-all duration-200 z-50
                    flex flex-col gap-2
                    bg-white border-1 border-slateBlue/15 h-fit lg:w-133 xl:w-133 shadow-lg rounded-md p-4
                    lg:left-45 xl:top-10
                    pointer-events-none
                    "
                >
                    <h1 className="text-lg font-bold italic">{`Mastery Per Section ${masteryLocked ? " - locked until 100% Completeness" : ""}`}</h1>
                    <hr className="w-full border-1 border-slateBlue/15 mb-2" />
                    {
                        masteryLocked ?
                        getMasteryLockedModal(sectionData)
                        :
                        getMasteryCompletenessStats(sectionData, "mastery")
                    }
                </div>
                <div className={`flex flex-col items-center xl:w-13 h-fit ${masteryLocked ? "opacity-30" : ""}`}>
                    {
                        masteryLocked ?
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                            <path fill-rule="evenodd" d="M12 1.5a5.25 5.25 0 0 0-5.25 5.25v3a3 3 0 0 0-3 3v6.75a3 3 0 0 0 3 3h10.5a3 3 0 0 0 3-3v-6.75a3 3 0 0 0-3-3v-3c0-2.9-2.35-5.25-5.25-5.25Zm3.75 8.25v-3a3.75 3.75 0 1 0-7.5 0v3h7.5Z" clip-rule="evenodd" />
                        </svg>
                        :
                        <h1 className="w-fit italic text-xl">{formatPercentage(mastery)}</h1>
                    }
                </div>
            </div>
        </div>
    );
}