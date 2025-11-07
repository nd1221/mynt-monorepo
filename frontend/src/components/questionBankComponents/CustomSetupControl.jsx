import { getReviewButton, getSectionInput, getLessonInput, getDifficultyInput, getNumberToReviewInput, getPriorityInput, getNewQuestionSlider, buildSearchParams } from "../../utils/renderReviewSetupUtil";

export default function CustomSetupControl({filters, toggleFilters, customFilters, toggleCustomFilters, sections, showDefault, load, toggleLoad, resetFilters, metadata, courseTrackerId, prevNav}) {

    return (
        <div className="flex flex-col items-center gap-4 w-full h-full p-4 rounded-md">

            <div className="flex flex-row w-full h-fit gap-4 items-center justify-between">
                <h1 className="italic text-sm font-semibold">Select reviews:</h1>
                <div className="flex flex-row gap-4 w-fit h-fit">
                    {getSectionInput(sections, filters, showDefault, toggleFilters)}
                    {getLessonInput(sections, filters, showDefault, toggleFilters)}
                </div>
            </div>

            <div className="flex flex-row w-full h-fit gap-4 items-center justify-between">
                <h1 className="italic text-sm font-semibold">Filter:</h1>
                <div className="flex flex-row gap-4 w-fit h-fit">
                    {getDifficultyInput(filters, showDefault, toggleFilters)}
                    {getPriorityInput(customFilters, showDefault, toggleCustomFilters)}
                </div>
            </div>

            <div className="flex flex-row gap-4 w-full h-fit">
                {getNumberToReviewInput(filters, showDefault, toggleFilters)}
                {getNewQuestionSlider(customFilters, showDefault, toggleCustomFilters, [0, 0.2, 0.4, 0.6, 0.8, 1.0], 0.2)}
            </div>

            {getReviewButton(load, toggleLoad, showDefault, resetFilters, metadata, courseTrackerId, prevNav)}
        </div>
    );
}