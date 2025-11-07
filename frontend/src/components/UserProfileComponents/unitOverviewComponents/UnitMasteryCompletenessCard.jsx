import ProgressBar from "../../trendComponents/ProgressBar";
import { formatPercentage } from "../../../utils/progressTrackingUtils";
import { Link, useLocation } from "react-router-dom";
import { useState, useRef } from "react";

const getQuestionBankState = (sectionId, lessonId) => {
    const state = {
        filters: {
            lesson: lessonId,
            section: sectionId,
            core: 1,
            attempted: 2,
        },
        prevNav: {
            from: useLocation().pathname,
            prevPageName: "unit overview",
        }
    };
    return state;
}

export default function UnitMasteryCompletenessCard({completeness, mastery, courseTrackerId, sectionId, lessonId}) {

    const masteryLocked = completeness < 1.0;
    const [masteryHovered, setMasteryHovered] = useState(false);
    const hideMasteryRef = useRef(null);

    const handleShowMasteryModal = () => {
        if (hideMasteryRef.current) {
            clearTimeout(hideMasteryRef.current);
            hideMasteryRef.current = null;
        };
        setMasteryHovered(true);
    }

    const handleHideMasteryModal = () => {
        hideMasteryRef.current = setTimeout(() => {
            setMasteryHovered(false);
            hideMasteryRef.current = null;
        }, 3000);
    };

    return (
        <div className="col-span-9 flex flex-col w-full h-30 gap-4 p-4">
            <div className="relative group flex flex-row gap-8 w-full h-fit items-center">
                <h1 className="lg:w-38 font-bold text-xl">completeness</h1>
                <ProgressBar 
                    progressDecimal={completeness}
                    containerStyle={"flex-1 w-full h-5 bg-slateBlue/20 border-1 border-slateBlue rounded-md overflow-hidden group-hover:shadow-xl hover:cursor-pointer"}
                    barStyle={"h-full bg-lavender rounded-l-md group-hover:opacity-75"}
                />
                <div className="flex flex-col items-center xl:w-13 h-fit">
                    <h1 className="w-fit italic text-xl">{formatPercentage(completeness)}</h1>
                </div>
            </div>
            <div className="relative group flex flex-row gap-8 w-full h-fit items-center">

                <h1 className={`lg:w-38 font-bold text-xl ${masteryLocked ? "opacity-30" : ""}`}>mastery</h1>

                <div 
                    className="relative flex-1"
                    onMouseEnter={() => handleShowMasteryModal()}
                    onMouseLeave={() => handleHideMasteryModal()}
                >
                    <ProgressBar 
                        progressDecimal={masteryLocked ? 0 : mastery}
                        containerStyle={`flex-1 w-full h-5 bg-slateBlue/20 border-1 border-slateBlue rounded-md overflow-hidden group-hover:shadow-xl hover:cursor-pointer ${masteryLocked ? "opacity-30" : ""}`}
                        barStyle={"h-full bg-teal rounded-l-md group-hover:opacity-75"}
                    />
                </div>

                {
                    masteryLocked && masteryHovered ?
                        <div 
                            className="
                                absolute opacity-100 scale-90
                                z-50 items-center 
                                flex flex-col gap-6
                                bg-white border-1 border-slateBlue/15 h-fit lg:w-130 xl:w-130 shadow-lg rounded-md p-8
                                lg:left-40 lg:top-10
                            "
                        >
                            <p className="italic text-lg text-center">Mastery is locked until you have reviewed all core questions.</p>
                            <Link 
                                to={`/question-bank/${courseTrackerId}`}
                                state={getQuestionBankState(sectionId, lessonId)}
                                className="flex flex-row items-center gap-4 p-4 px-8 rounded-lg border-3 border-lavender text-lavender text-lg font-bold hover:bg-lavender hover:text-white transition duration-100 active:scale-99"
                                >
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-7">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M9 12.75 11.25 15 15 9.75M21 12c0 1.268-.63 2.39-1.593 3.068a3.745 3.745 0 0 1-1.043 3.296 3.745 3.745 0 0 1-3.296 1.043A3.745 3.745 0 0 1 12 21c-1.268 0-2.39-.63-3.068-1.593a3.746 3.746 0 0 1-3.296-1.043 3.745 3.745 0 0 1-1.043-3.296A3.745 3.745 0 0 1 3 12c0-1.268.63-2.39 1.593-3.068a3.745 3.745 0 0 1 1.043-3.296 3.746 3.746 0 0 1 3.296-1.043A3.746 3.746 0 0 1 12 3c1.268 0 2.39.63 3.068 1.593a3.746 3.746 0 0 1 3.296 1.043 3.746 3.746 0 0 1 1.043 3.296A3.745 3.745 0 0 1 21 12Z" />
                                </svg>
                                <p>review unanswered core questions</p>
                            </Link>
                        </div>
                    : null
                }

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