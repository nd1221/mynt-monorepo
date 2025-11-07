import { getReviewButton, getSectionInput, getLessonInput, getDifficultyInput, getNumberToReviewInput, getNewQuestionSlider } from "../../utils/renderReviewSetupUtil.jsx";

export default function ReviewSetupControl({filters, toggleFilters, reviewFilters, toggleReviewFilters, sections, showDefault, load, toggleLoad, resetFilters, metadata, courseTrackerId, prevNav}) {
    
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
                    {getNumberToReviewInput(filters, showDefault, toggleFilters)}
                    {getDifficultyInput(filters, showDefault, toggleFilters)}
                </div>
            </div>

            <div className="flex flex-row gap-4 w-full h-fit">
                <div className="flex flex-row gap-4 items-center">
                    <input
                        id="include-unseen" 
                        type="checkbox" 
                        value={reviewFilters.includeUnseen}
                        className="accent-lavender rounded border-slateBlue/15 cursor-pointer"
                        onChange={() => toggleReviewFilters("includeUnseen", !reviewFilters.includeUnseen)}
                    />
                    <label for="include-unseen" className="text-sm italic">include unseen questions</label>
                </div>
                {getNewQuestionSlider(reviewFilters, showDefault, toggleReviewFilters, [0.1, 0.2, 0.3, 0.4, 0.5], 0.1)}
            </div>

            {getReviewButton(load, toggleLoad, showDefault, resetFilters, metadata, courseTrackerId, prevNav)}
        
        </div>
    );
}