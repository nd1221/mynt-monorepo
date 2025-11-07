import { Link, useLocation } from "react-router-dom";

const getReviewSetupState = (sectionId, lessonId) => {
    const state = {
        reviewSetupFilters: {
            section: sectionId,
            lesson: lessonId,
            numberRequested: 0,
            difficulty: 0,
        },
        prevNav: {
            from: useLocation().pathname,
            prevPageName: "unit overview",
        }
    };
    return state;
}

const getReviewQuickLinks = (data, sectionId, courseTrackerId) => {
    return data.map(review => (
        <div className="flex flex-col w-19/20 h-fit gap-2 p-4 text-sm rounded-md bg-slateBlue text-offWhite transition hover:shadow-2xl">
            <h1 className="w-full h-fit overflow-hidden font-bold">{review.title}</h1>
            <div className="flex flex-row justify-between items-center">
                <p className="w-fit h-fit">{review.reviewsDueToday} due today</p>
                <Link
                    to={`/question-bank/${courseTrackerId}`}
                    state={getReviewSetupState(sectionId, review.id)}
                    className="w-fit h-fit bg-transparent p-2 px-3 text-xs rounded-sm font-bold text-lavender border-2 border-lavender hover:bg-lavender hover:text-offWhite transition active:scale-98"
                >{review.reviewsDueToday > 0 ? "quick review" : "setup review"}</Link>
            </div>
        </div>
    ));
}

const getNoReviewsMessage = () => {
    return (
        <div className="h-fit w-4/5 text-lg my-4">
            <p className="italic text-center">you have not reviewed these units yet</p>
        </div>
    );
}

const getLessonReviewLinks = (data, sectionId, courseTrackerId) => {
    if (data == null || data.length === 0) {
        return getNoReviewsMessage();
    }
    return getReviewQuickLinks(data, sectionId, courseTrackerId);
}

const getSectionQuickStartCards = (data, toggle, flags, courseTrackerId) => {
    return data.map(section => (
        <>
            <div
                onClick={() => toggle(section.sectionPosition)}
                className="flex flex-row w-full gap-4 justify-between items-start bg-white p-2 rounded-lg border-1 border-slateBlue/6 hover:cursor-pointer"
            >
                <div className="flex-6 flex flex-row gap-4 px-2">
                    <h1 className="text-lg font-bold">{section.sectionPosition}</h1>
                    <h1 className="text-md line-clamp-3">{section.sectionTitle}</h1>
                </div>
                <div className="flex-1 flex-col w-full h-full">
                    {
                        flags[section.sectionPosition - 1] ?
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" class="size-6">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M5 12h14" />
                        </svg>
                        :
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" class="size-6">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M12 4.5v15m7.5-7.5h-15" />
                        </svg>
                    }
                </div>
            </div>
            {
                flags[section.sectionPosition - 1] ?
                getLessonReviewLinks(section.lessonQuickstartData, section.sectionId, courseTrackerId)
                : null
            }
        </>
    ))
}

export default function ReviewQuickstartCard({reviewData, toggleSection, sectionFlags, courseTrackerId}) {
    return (
        <div className="flex-1 flex flex-col w-full h-full gap-4 items-center rounded-lg bg-slateBlue/5 p-4 py-6 shadow-md border-1 border-slateBlue/6">
            <div className="flex flex-row gap-4 w-full h-fit justify-center items-center">
                <h1 className="font-bold text-xl">review quickstart</h1>
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                    <path d="M5.055 7.06C3.805 6.347 2.25 7.25 2.25 8.69v8.122c0 1.44 1.555 2.343 2.805 1.628L12 14.471v2.34c0 1.44 1.555 2.343 2.805 1.628l7.108-4.061c1.26-.72 1.26-2.536 0-3.256l-7.108-4.061C13.555 6.346 12 7.249 12 8.689v2.34L5.055 7.061Z" />
                </svg>
            </div>
            <hr className="w-9/10 border-1 border-slateBlue/10 mb-1" />
            <div className="flex flex-col items-center gap-4 overflow-auto w-full h-fit">
                {getSectionQuickStartCards(reviewData, toggleSection, sectionFlags, courseTrackerId)}
            </div>
        </div>
    );
}