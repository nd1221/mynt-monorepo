import { CircularProgressbar } from 'react-circular-progressbar';
import { Link } from 'react-router-dom';

export default function ReviewSessionMetadataCard({
    sessionState,
    answered,
    courseTrackerId,
    prevNav,
    questionBankFilters
}) {

    const totalUnseen = sessionState.unseenQuestionIds.length;
    const totalQuestions = sessionState.questionProgressTrackerIds.length + totalUnseen;

    return (
        <div className="row-span-3 flex flex-col gap-4 items-center rounded-lg bg-slateBlue p-2 shadow-md">
            <div className="flex flex-col items-center border-1 border-lavender rounded-md w-full h-full p-2 text-offWhite">
                
                <h1 className="text-lg italic font-semibold">Review Session</h1>

                <hr className="border-1 border-lavender/50 w-full mt-1" />
                
                <div className="flex flex-row gap-4 w-full items-center">

                    <div className="xl:w-[180px] xl:h-[130px]">
                        <div className="relative w-full h-full">
                            <CircularProgressbar
                                value={(answered / totalQuestions) * 100}
                                styles={{
                                    root: { width: "100%", height: "100%" },
                                    path: { stroke: "#1cad9a" },
                                    trail: { stroke: "rgba(28, 173, 154, 0.15)" },
                                }}
                            />
                            <div className="absolute inset-0 flex items-center justify-center">
                            <div className="flex flex-col items-center justify-center xl:w-20 xl:h-20 text-xl">
                                <p className="text-center"><span className="text-2xl font-semibold">{answered}</span>/{totalQuestions}</p>
                            </div>
                    </div>
                        </div>
                    </div>

                    <div className="flex flex-col gap-1 w-full px-1">
                        <div className="flex flex-row gap-4 justify-between items-center">
                            <p className="italic text-sm">Total Questions:</p>
                            <p className="font-semibold italic">{totalQuestions}</p>
                        </div>
                        <div className="flex flex-row gap-4 justify-between items-center">
                            <p className="italic text-sm">Unseen Questions:</p>
                            <p className="font-semibold italic">{totalUnseen}</p>
                        </div>
                        <div className="flex flex-row gap-4 justify-between items-center">
                            <p className="italic text-sm">Core Questions:</p>
                            <p className="font-semibold italic">{sessionState.numberOfCoreQuestions}</p>
                        </div>
                    </div>

                </div>

                <div className="flex flex-row gap-4 w-full h-full">
                    <Link
                        to={`/user-profile/courses/${courseTrackerId}`}
                        className="flex flex-row items-center justify-center gap-2 border-2 rounded-sm border-lavender text-lavender font-semibold w-full h-full hover:bg-lavender hover:cursor-pointer hover:text-offWhite transition duration-100 active:scale-99"
                    >
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                            <path fill-rule="evenodd" d="M1.5 7.125c0-1.036.84-1.875 1.875-1.875h6c1.036 0 1.875.84 1.875 1.875v3.75c0 1.036-.84 1.875-1.875 1.875h-6A1.875 1.875 0 0 1 1.5 10.875v-3.75Zm12 1.5c0-1.036.84-1.875 1.875-1.875h5.25c1.035 0 1.875.84 1.875 1.875v8.25c0 1.035-.84 1.875-1.875 1.875h-5.25a1.875 1.875 0 0 1-1.875-1.875v-8.25ZM3 16.125c0-1.036.84-1.875 1.875-1.875h5.25c1.036 0 1.875.84 1.875 1.875v2.25c0 1.035-.84 1.875-1.875 1.875h-5.25A1.875 1.875 0 0 1 3 18.375v-2.25Z" clip-rule="evenodd" />
                        </svg>
                        <p>dashboard</p>
                    </Link>
                    <Link
                        to={`/question-bank/${courseTrackerId}`}
                        state={{prevNav: prevNav, filters: questionBankFilters}}
                        className="flex flex-row items-center justify-center gap-2 border-2 rounded-sm border-lavender text-lavender font-semibold w-full h-full hover:bg-lavender hover:cursor-pointer hover:text-offWhite transition duration-100 active:scale-99"
                    >
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                            <path d="M5.625 3.75a2.625 2.625 0 1 0 0 5.25h12.75a2.625 2.625 0 0 0 0-5.25H5.625ZM3.75 11.25a.75.75 0 0 0 0 1.5h16.5a.75.75 0 0 0 0-1.5H3.75ZM3 15.75a.75.75 0 0 1 .75-.75h16.5a.75.75 0 0 1 0 1.5H3.75a.75.75 0 0 1-.75-.75ZM3.75 18.75a.75.75 0 0 0 0 1.5h16.5a.75.75 0 0 0 0-1.5H3.75Z" />
                        </svg>
                        <p>bank</p>
                    </Link>
                </div>

            </div>
        </div>
    );
}