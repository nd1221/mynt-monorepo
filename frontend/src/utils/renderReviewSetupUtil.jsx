// Defines common components for rendering UI of review setup feature
// Components are too small to be extracted into their own React components
// and cannot be extracted into a single component due to differing layouts between ReviewSetupControl and CustomSetupControl

import { Link, createSearchParams } from "react-router-dom";
import { formatPercentage } from "./progressTrackingUtils.js";

const getSectionOptions = sections => {
    return sections.map(section => (
        <option value={section.parent.id} className="max-w-5">{section.parent.position} - {section.parent.title}</option>
    ));
}

const getLessonOptions = (sections, filters) => {

    if (filters.section !== 0) {
        for (let i = 0; i < sections.length; i++) {
            if (sections[i].parent.id == filters.section) {
                return sections[i].children.map(lesson => (
                    <option value={lesson.id}>{lesson.position} - {lesson.title}</option>
                ));
            }
        }
    }

    return sections.flatMap(section => (
        section.children.map(lesson => (
            <option value={lesson.id}>{lesson.position} - {lesson.title}</option>
        ))
    ));
}

export function getReviewButton(loaded, toggleLoad, showDefault, resetFilters, metadata, courseTrackerId, prevNav) {

    const buttonClass = showDefault ? 
    "border-lavender text-lavender hover:bg-lavender active:bg-lavenderDark active:border-lavenderDark" :
    "border-teal text-teal hover:bg-teal active:bg-tealDark active:border-tealDark";
    
    const clearClass = showDefault ? 
    "active:text-lavender" :
    "active:text-teal";
    
    if (!loaded) {
        return (
            <div className="flex flex-col w-full h-full items-center justify-center">
                <button
                    onClick={() => toggleLoad(true)}
                    className={`${buttonClass} border-2 text-lg font-semibold rounded-md p-2 px-4 hover:text-offWhite hover:cursor-pointer transition duration-100`}
                    >load session</button>
            </div>
        );
    }

    if (!metadata) {
        return null;
    }

    const unseen = metadata?.unseenQuestionIds?.length;
    const numberLoaded = metadata?.questionProgressTrackerIds?.length + unseen;
    const corePercentage = numberLoaded === 0 ? "--" : formatPercentage(metadata?.numberOfCoreQuestions / numberLoaded);

    return (
        <div className="flex flex-row w-full h-full">
            {
                loaded ?
                getLoadedSessionOverview(numberLoaded, unseen, corePercentage, showDefault)
                : null
            }
            <div className="flex flex-col gap-2 w-full h-full items-center justify-center">
                {
                    numberLoaded > 0 ?
                    <Link
                        to={`/review-session/${courseTrackerId}`}
                        state={{sessionState: metadata, prevNav: prevNav}}
                        className={`${buttonClass} border-2 text-lg font-semibold rounded-md p-2 px-4 hover:text-offWhite transition duration-100`}
                        >
                        start review
                    </Link>
                    :
                    <div className="opacity-50 border-offWhite border-2 text-offWhite text-lg font-semibold rounded-md p-2 px-4 hover:cursor-pointer">
                        start review
                    </div>
                }
                <button
                    onClick={() => resetFilters()}
                    className={`${clearClass} text-offWhite text-sm underline hover:cursor-pointer transition duration-100`}
                    >clear session</button>
            </div>
        </div>
    );
}

const getLoadedSessionOverview = (numberLoaded, unseen, corePercentage, showDefault) => {

    const colour = showDefault ? "lavender" : "teal";

    return (
        <div className="flex flex-col w-full h-full p-2">
            <h1 className={`font-semibold text-${colour} mb-2`}>Loaded Review Session:</h1>
            <div className="flex flex-row w-9/10 justify-between items-center text-sm italic">
                <p>Number loaded: </p>
                <p className={`font-semibold text-${colour}`}>{numberLoaded}</p>
            </div>
            <div className="flex flex-row w-9/10 justify-between items-center text-sm italic">
                <p>New/Unseen: </p>
                <p className={`font-semibold text-${colour}`}>{unseen}</p>
            </div>
            <div className="flex flex-row w-9/10 justify-between items-center text-sm italic">
                <p>Core: </p>
                <p className={`font-semibold text-${colour}`}>{corePercentage}</p>
            </div>
        </div>
    );
}

export function getSectionInput(sections, filters, showDefault, toggle) {

    const textClass = showDefault ? "text-lavender" : "text-teal";
    const labelClass = showDefault ? 
        "bg-lavender/15 hover:bg-lavender/20" :
        "bg-teal/15 hover:bg-teal/20" ;

    return (
        <div className="flex flex-col gap-1">
            <p className={`${textClass} text-sm italic`}>section</p>
            <label className={`${labelClass} p-1 px-2 rounded-sm text-sm`}>
                <select
                    name="sections"
                    value={filters.section}
                    onChange={e => toggle("section", Number(e.target.value))}
                    className="truncate lg:w-30 xl:w-30 hover:cursor-pointer"
                >
                    <option value={0}>-- all --</option>
                    {getSectionOptions(sections)}
                </select>
            </label>
        </div>
    );
}

export function getLessonInput(sections, filters, showDefault, toggle) {

    const textClass = showDefault ? "text-lavender" : "text-teal";
    const labelClass = showDefault ? 
        "bg-lavender/15 hover:bg-lavender/20" :
        "bg-teal/15 hover:bg-teal/20" ;

    return (
        <div className="flex flex-col gap-1">
            <p className={`${textClass} text-sm italic`}>lesson</p>
            <label className={`${labelClass} p-1 px-2 rounded-sm text-sm`}>
                <select
                    name="lessons"
                    value={filters.lesson}
                    onChange={e => toggle("lesson", Number(e.target.value))}
                    className="truncate lg:w-40 xl:w-40 hover:cursor-pointer"
                >
                    <option value={0}>-- all --</option>
                    {getLessonOptions(sections, filters)}
                </select>
            </label>
        </div>
    );
}

export function getDifficultyInput(filters, showDefault, toggle) {

    const textClass = showDefault ? "text-lavender" : "text-teal";

    const labelClass = showDefault ? 
        "bg-lavender/15 hover:bg-lavender/20" : 
        "bg-teal/15 hover:bg-teal/20" ;

    const selectClass = showDefault ? 
        "lg:w-40 xl:w-40" : 
        "lg:w-25 xl:w-25" ;

    return (
        <div className="flex flex-col gap-1">
            <p className={`${textClass} text-sm italic`}>difficulty</p>
            <label className={`${labelClass} p-1 px-2 rounded-sm text-sm`}>
                <select
                    name="difficulty"
                    value={filters.difficulty}
                    onChange={e => toggle("difficulty", Number(e.target.value))}
                    className={`${selectClass} truncate hover:cursor-pointer`}
                >
                    <option value={0}>-- all --</option>
                    <option value={1}>easy</option>
                    <option value={2}>medium</option>
                    <option value={3}>hard</option>
                </select>
            </label>
        </div>
    );
}

export function getNumberToReviewInput(filters, showDefault, toggle) {

    const textClass = showDefault ? "text-lavender" : "text-teal";

    const labelClass = showDefault ? 
        "bg-lavender/15 hover:bg-lavender/20" : 
        "bg-teal/15 hover:bg-teal/20" ;
    
    const selectClass = showDefault ? 
        "lg:w-30 xl:w-30" : 
        "lg:w-25 xl:w-25" ;

    return (
        <div className="flex flex-col gap-1">
            <p className={`${textClass} text-sm italic`}>number to review</p>
            <label className={`${labelClass} p-1 px-2 rounded-sm text-sm`}>
                <select
                    name="number"
                    value={filters.numberRequested}
                    onChange={e => toggle("numberRequested", Number(e.target.value))}
                    className={`${selectClass} truncate hover:cursor-pointer`}
                >
                    <option value={0}>-- all --</option>
                    <option value={5}>5</option>
                    <option value={10}>10</option>
                    <option value={15}>15</option>
                    <option value={20}>20</option>
                </select>
            </label>
        </div>
    );
}

export function getPriorityInput(filters, showDefault, toggleFilters) {

    const textClass = showDefault ? "text-lavender" : "text-teal";
    
    const labelClass = showDefault ? 
        "bg-lavender/15 hover:bg-lavender/20" : 
        "bg-teal/15 hover:bg-teal/20" ;

    // Cuurently, only custom setup uses priority input
    const value = filters.priority;

    return (
        <div className="flex flex-col gap-1">
            <p className={`${textClass} text-sm italic`}>prioritise by</p>
            <label className={`${labelClass} p-1 px-2 rounded-sm text-sm`}>
                <select
                    name="priority"
                    value={value}
                    onChange={e => toggleFilters("priority", Number(e.target.value))}
                    className="truncate lg:w-45 xl:w-45 hover:cursor-pointer"
                >
                    <option value={0}>next review date</option>
                    <option value={1}>avg accuracy</option>
                    <option value={2}>my accuracy</option>
                    <option value={3}>most popular</option>
                    <option value={4}>most attempted</option>
                    <option value={5}>least attempted</option>
                </select>
            </label>
        </div>
    );
}

export function getNewQuestionSlider(filters, showDefault, toggle, rangeArray, step) {

    if (!rangeArray || rangeArray.length === 0) {
        return null;
    }

    const min = rangeArray[0];
    const max = rangeArray[rangeArray.length - 1];
    const inputClass = showDefault ? "bg-lavender accent-lavender" : "bg-teal accent-teal ml-2";
    
    return (
        <div className="flex flex-col w-full h-fit items-center p-1 px-4">
            <label className="italic text-sm mb-1">proportion of unseen questions</label>
            <input 
                type="range"
                min={min}
                max={max}
                step={step}
                value={filters.slider}
                className={`${inputClass} w-full cursor-pointer`}
                onChange={e => toggle("slider", Number(e.target.value))}
            />
            <div className="flex justify-between w-full mt-1 text-xs text-offWhite italic">
                {showDefault ? mapReviewSliderTicks(rangeArray) : mapCustomSliderTicks(rangeArray)}
            </div>
        </div>
    );
}

const mapReviewSliderTicks = range => {
    return range.map(step => (
        step === 0.5 ? <p>half</p> : <p>{step}</p>
    ));
}

const mapCustomSliderTicks = range => {
    
    return range.map(step => {
        if (step === 0) {
            return <p>none</p>;
        } else if (step === 1) {
            return <p>all</p>;
        } else {
            return <p>{step.toPrecision(1)}</p>;
        }
    });
}

export function buildSearchParams(baseFilters, extraFilters) {
    return createSearchParams(
        {
            ...baseFilters,
            ...extraFilters
        }
    );
}