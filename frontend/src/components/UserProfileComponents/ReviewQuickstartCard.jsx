import { Link, useLocation } from "react-router-dom";

const getQuickStartReviewState = () => {
    const filters = {
        section: 0,
        lesson: 0,
        numberRequested: 0,
        difficulty: 0,
    };
    return filters;
}

const getReviewQuickLinks = data => {
    return data.map(review => (
        <div className="flex flex-col gap-2 p-4 text-sm rounded-md bg-slateBlue text-offWhite transition hover:shadow-2xl">
            <h1 className="w-full h-fit overflow-hidden font-bold">{review.title}</h1>
            <div className="flex flex-row justify-between items-center">
                <p className="w-fit h-fit">{review.reviewsDueToday} due today</p>
                <Link
                    to={`/question-bank/${review.trackerId}`}
                    state={{
                        reviewSetupFilters: getQuickStartReviewState(),
                        prevNav: {
                            from: useLocation().pathname,
                            prevPageName: "course overview",
                        }
                    }}
                    className="w-fit h-fit bg-transparent p-2 px-4 text-xs rounded-sm font-bold text-lavender border-2 border-lavender hover:bg-lavender hover:text-offWhite transition active:scale-98"
                >{review.reviewsDueToday > 0 ? "quick review" : "setup review"}</Link>
            </div>
        </div>
    ));
}

const getNoReviewsMessage = () => {
    return (
        <div className="h-fit w-4/5 text-lg my-4">
            <p className="italic text-center">you have no courses to review yet</p>
        </div>
    );
}

export default function ReviewQuickstartCard({props}) {

    const reviewData = props;

    return (
        <div className="flex flex-col w-full max-h-screen h-fit gap-4 items-center rounded-lg bg-slateBlue/5 p-4 py-6 shadow-md border-1 border-slateBlue/6">
            <div className="flex flex-row gap-4 w-full h-fit justify-center items-center">
                <h1 className="font-bold text-xl">review quickstart</h1>
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                    <path d="M5.055 7.06C3.805 6.347 2.25 7.25 2.25 8.69v8.122c0 1.44 1.555 2.343 2.805 1.628L12 14.471v2.34c0 1.44 1.555 2.343 2.805 1.628l7.108-4.061c1.26-.72 1.26-2.536 0-3.256l-7.108-4.061C13.555 6.346 12 7.249 12 8.689v2.34L5.055 7.061Z" />
                </svg>
            </div>
            <hr className="w-9/10 border-1 border-slateBlue/10 mb-1" />
            <div className="flex flex-col items-center gap-4 overflow-auto w-full h-fit">
                {reviewData === null ? getNoReviewsMessage() : getReviewQuickLinks(reviewData)}
            </div>
        </div>
    );
}