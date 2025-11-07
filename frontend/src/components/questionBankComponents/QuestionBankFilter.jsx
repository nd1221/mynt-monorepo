import api from "../../api/modules.js";
import { handleError } from "../../api/apiUtils.js";
import { useState, useEffect } from "react";
import QuestionBankRow from "./QuestionBankRow.jsx";

const fetchQuestions = async (courseProgressTrackerId, searchParams) => {
    try {
        const response = await api.get(`/progress/question-bank/${courseProgressTrackerId}?${searchParams}`);
        return response.data;
    } catch(err) {
        handleError(err);
    }
}

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

const getQuestionBankRows = (questions, courseTrackerId, prevNav, filters) => {
    return questions.map((question, index) => (
        <QuestionBankRow data={question} index={index} courseTrackerId={courseTrackerId} prevNav={prevNav} questionBankFilters={filters} />
    ));
}

const getPaginationParams = (page, size) => {
    return `page=${page}&size=${size}`;
}

const getCoreParam = param => {
    switch (parseInt(param)) {
        case 0:
            return "all";
        case 1:
            return "core";
        default:
            return "nonCore";
    };
}

const getAnsweredParam = param => {
    switch (parseInt(param)) {
        case 0:
            return "all";
        case 1:
            return "answered";
        default:
            return "unanswered";
    };
}

const getDifficultyParam = param => {
    switch (parseInt(param)) {
        case 0:
            return "all";
        case 1:
            return "easy";
        case 2:
            return "medium";
        default:
            return "hard";
    };
}

const getFilterParams = (section, lesson, core, answered, difficulty) => {

    const coreParam = getCoreParam(core);
    const answeredParam = getAnsweredParam(answered);
    const difficultyParam = getDifficultyParam(difficulty);

    return `section=${section}&lesson=${lesson}&core=${coreParam}&answered=${answeredParam}&difficulty=${difficultyParam}`;
}

const getSortParams = (category, direction) => {

    let param;
    switch (parseInt(category)) {
        case 0:
            param = "position";
            break;
        case 1:
            param = "accuracy";
            break;
        case 2:
            param = "myAccuracy";
            break;
        case 3:
            param = "attempts"
            break;
        case 4:
            param = "myAttempts";
            break;
        case 5:
            param = "time";
            break;
        default:
            param = "myTime";
            break;
    };

    return `sort=${param},${direction}`;
}

const getEmptyQuestionList = (clearFunction, initialFilterState) => {
    return (
        <div className="flex flex-col gap-8 items-center justify-center w-full h-full">
            <div className="flex flex-row gap-4 items-center justify-center">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-8">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M12 9v3.75m9-.75a9 9 0 1 1-18 0 9 9 0 0 1 18 0Zm-9 3.75h.008v.008H12v-.008Z" />
                </svg>
                <p className="text-xl italic">No matches found.</p>
            </div>
            <button 
                className="border-lavender border-2 rounded-md p-2 px-4 text-lavender font-semibold hover:cursor-pointer hover:bg-lavender hover:text-white transition duration-100 active:bg-lavenderDark active:border-lavenderDark"
                onClick={() => clearFunction(initialiseFilters(initialFilterState))}
            >clear filters</button>
        </div>
    );
};

const extractFilter = (filter, defaultFilter) => {
    return Number.isFinite(filter) ? filter : defaultFilter;
};

const initialiseFilters = initialFilters => {

    const filters = {
        section: extractFilter(initialFilters?.section, 0),
        lesson: extractFilter(initialFilters?.lesson, 0),
        core: extractFilter(initialFilters?.core, 0),
        attempted: extractFilter(initialFilters?.attempted, 0),
        difficulty: extractFilter(initialFilters?.difficulty, 0),
    }
    return filters;
};

const initialiseSort = () => {
    const sort = {
        orderBy: 0,
        direction: "asc",

    };
    return sort;
};

const noQuestionsPresentMessage = message => {
    return (
        <div className="flex flex-col gap-4 items-center justify-center w-full h-full text-2xl italic">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-12">
                <path stroke-linecap="round" stroke-linejoin="round" d="M12 9v3.75m9-.75a9 9 0 1 1-18 0 9 9 0 0 1 18 0Zm-9 3.75h.008v.008H12v-.008Z" />
            </svg>
            <p className="w-2/5 text-center">{message}</p>
        </div>
    );
}

const displayRows = (resultsNotPresent, resetFilters, initialFilterState, questions, courseTrackerId, prevNav) => {

    if (resultsNotPresent) {

        if (questions.meta.numberOfLessonQuestions !== null && questions.meta.numberOfLessonQuestions === 0) {
            return noQuestionsPresentMessage("No questions found for this lesson. Try a different lesson.");
        } else if (questions.meta.numberOfSectionQuestions !== null && questions.meta.numberOfSectionQuestions === 0) {
            return noQuestionsPresentMessage("No questions found for this section. Try a different section.");
        } else if (questions.meta.numberOfCourseQuestions !== null && questions.meta.numberOfCourseQuestions === 0) {
            return noQuestionsPresentMessage("No questions found for this course.");
        } else {
            return getEmptyQuestionList(resetFilters, initialFilterState);
        }
    }

    return getQuestionBankRows(questions.page.content, courseTrackerId, prevNav, initialFilterState);
}

export default function QuestionBankFilter({
    courseTracker,
    sectionMetadata,
    initialFilters,
    prevNav
}) {
    
    const [pageSize, setPageSize] = useState(12);
    const togglePageSize = size => {
        setPageSize(size);
    }

    const [currentPage, setCurrentPage] = useState(0);
    const toggleCurrentPage = page => {
        setCurrentPage(page);
    }

    const [filters, setFilters] = useState(initialiseFilters(initialFilters));
    const toggleFilters = (key, value) => {
        setFilters(prevFilters => (
            {
                ...prevFilters,
                [key]: value,
            }
        ));
    }

    const [sort, setSort] = useState(initialiseSort());
    const toggleSort = (key, value) => {
        setSort(prevSort => (
            {
                ...prevSort,
                [key]: value,
            }
        ));
    }

    const buildSearchParams = page => {

        const pagination = getPaginationParams(page, pageSize);
        const filter = getFilterParams(filters.section, filters.lesson, filters.core, filters.attempted, filters.difficulty);
        const sorting = getSortParams(sort.orderBy, sort.direction);

        return `${pagination}&${filter}&${sorting}`;
    }

    // Fetch questions
    const [questions, setQuestions] = useState(null);
    useEffect(() => {
        const getData = async courseProgressTrackerId => {
            const searchParams = buildSearchParams(0);
            const questions = await fetchQuestions(courseProgressTrackerId, searchParams);
            setQuestions(questions);
        };
        getData(courseTracker);
    }, [filters, sort, pageSize]);

    // Fetch questions for next page
    useEffect(() => {
        const getData = async courseProgressTrackerId => {
            const searchParams = buildSearchParams(currentPage);
            const questions = await fetchQuestions(courseProgressTrackerId, searchParams);
            setQuestions(questions);
        };
        getData(courseTracker);
    }, [currentPage]);

    const resetFilters = filters => {
        setFilters(filters);
    }

    const resultsNotPresent = questions == null || questions.page.content.length === 0;

    return (
        <div className="row-span-7 flex flex-col items-center w-full h-full gap-2 p-4 bg-white border-1 border-slateBlue/15 rounded-lg shadow-md hover:shadow-xl">
            <div className="grid grid-cols-3 grid-rows-1 gap-8 w-full h-fit mb-2">
                <div className="col-span-2 flex flex-row gap-4 p-2 items-center">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                        <path fill-rule="evenodd" d="M3 6.75A.75.75 0 0 1 3.75 6h16.5a.75.75 0 0 1 0 1.5H3.75A.75.75 0 0 1 3 6.75ZM3 12a.75.75 0 0 1 .75-.75h16.5a.75.75 0 0 1 0 1.5H3.75A.75.75 0 0 1 3 12Zm8.25 5.25a.75.75 0 0 1 .75-.75h8.25a.75.75 0 0 1 0 1.5H12a.75.75 0 0 1-.75-.75Z" clip-rule="evenodd" />
                    </svg>
                    <div className="flex flex-col">
                        <p className="text-sm text-slateBlue/50 italic">section</p>
                        <label className="bg-slateBlue/15 p-1 px-2 rounded-sm text-sm hover:bg-slateBlue/20">
                            <select
                                name="sections"
                                value={filters.section}
                                onChange={e => toggleFilters("section", e.target.value)}
                                className="truncate lg:w-40 xl:w-40 hover:cursor-pointer"
                            >
                                <option value={0}>-- all --</option>
                                {getSectionOptions(sectionMetadata)}
                            </select>
                        </label>
                    </div>
                    <div className="flex flex-col">
                        <p className="text-sm text-slateBlue/50 italic">lesson</p>
                        <label className="bg-slateBlue/15 p-1 px-2 rounded-sm text-sm hover:bg-slateBlue/20">
                            <select
                                name="lessons"
                                value={filters.lesson}
                                onChange={e => toggleFilters("lesson", e.target.value)}
                                className="truncate lg:w-50 xl:w-50 hover:cursor-pointer"
                            >
                                <option value={0}>-- all --</option>
                                {getLessonOptions(sectionMetadata, filters)}
                            </select>
                        </label>
                    </div>
                    <div className="flex flex-col">
                        <p className="text-sm text-slateBlue/50 italic">core</p>
                        <label className="bg-slateBlue/15 p-1 px-2 rounded-sm text-sm hover:bg-slateBlue/20">
                            <select
                                name="core"
                                value={filters.core}
                                onChange={e => toggleFilters("core", e.target.value)}
                                className="hover:cursor-pointer"
                            >
                                <option value={0}>-- all --</option>
                                <option value={1}>core</option>
                                <option value={2}>non-core</option>
                            </select>
                        </label>
                    </div>
                    <div className="flex flex-col">
                        <p className="text-sm text-slateBlue/50 italic">answered</p>
                        <label className="bg-slateBlue/15 p-1 px-2 rounded-sm text-sm hover:bg-slateBlue/20">
                            <select
                                name="attempted"
                                value={filters.attempted}
                                onChange={e => toggleFilters("attempted", e.target.value)}
                                className="hover:cursor-pointer"
                            >
                                <option value={0}>-- all --</option>
                                <option value={1}>attempted</option>
                                <option value={2}>unattempted</option>
                            </select>
                        </label>
                    </div>
                    <div className="flex flex-col">
                        <p className="text-sm text-slateBlue/50 italic">difficulty</p>
                        <label className="bg-slateBlue/15 p-1 px-2 rounded-sm text-sm hover:bg-slateBlue/20">
                            <select
                                name="difficulty"
                                value={filters.difficulty}
                                onChange={e => toggleFilters("difficulty", e.target.value)}
                                className="hover:cursor-pointer"
                            >
                                <option value={0}>-- all --</option>
                                <option value={1}>easy</option>
                                <option value={2}>medium</option>
                                <option value={3}>hard</option>
                            </select>
                        </label>
                    </div>
                </div>
                <div className="col-span-1 flex flex-row gap-4 p-2 justify-end">
                    <div className="flex flex-col">
                        <p className="text-sm text-slateBlue/50 italic">order by</p>
                        <label className="bg-slateBlue/15 p-1 px-2 rounded-sm text-sm hover:bg-slateBlue/20">
                            <select
                                name="orderBy"
                                value={sort.orderBy}
                                onChange={e => toggleSort("orderBy", e.target.value)}
                                className="hover:cursor-pointer"
                            >
                                <option value={0}>position</option>
                                <option value={1}>avg accuracy</option>
                                <option value={2}>my accuracy</option>
                                <option value={3}>most attempted</option>
                                <option value={4}>my most attempted</option>
                                <option value={5}>avg time</option>
                                <option value={6}>my avg time</option>
                            </select>
                        </label>
                    </div>
                    <div className="flex flex-col">
                        <p className="text-sm text-slateBlue/50 italic">per page</p>
                        <label className="bg-slateBlue/15 p-1 px-4 rounded-sm text-sm hover:bg-slateBlue/20">
                            <select
                                name="pageSize"
                                value={pageSize}
                                onChange={e => togglePageSize(e.target.value)}
                                className="hover:cursor-pointer"
                            >
                                <option value={12}>12</option>
                                <option value={24}>24</option>
                                <option value={36}>36</option>
                            </select>
                        </label>
                    </div>
                    <div className="flex flex-col">
                        <p className="text-sm text-slateBlue/50 italic text-transparent">placeholder</p>
                        <label className="bg-slateBlue/15 p-1 px-2 rounded-sm text-sm hover:bg-slateBlue/20">
                            <select
                                name="direction"
                                value={sort.direction}
                                onChange={e => toggleSort("direction", e.target.value)}
                                className="hover:cursor-pointer"
                            >
                                <option value={"asc"}>asc</option>
                                <option value={"desc"}>desc</option>
                            </select>
                        </label>
                    </div>
                </div>
            </div>
            <hr className="w-full border-1 border-slateBlue/15 my-1" />
            <div className="grid w-full grid-cols-14 grid-rows-1 gap-4 px-2 text-md"> 
                <p className="col-span-1 font-bold text-transparent">icon</p>
                <p className="col-span-5 font-bold px-2">Question</p>
                <p className="col-span-1 font-bold text-center">Section</p>
                <p className="col-span-1 font-bold text-center">Difficulty</p>
                <div className="col-span-2 font-bold">
                    <p className="w-full text-center">Accuracy</p>
                    <div className="flex flex-row gap-8 justify-center px-4 w-full text-sm text-slateBlue/30 font-medium italic">
                        <p>my avg</p>
                        <p>global</p>
                    </div>
                </div>
                <div className="col-span-2 font-bold">
                    <p className="w-full text-center">Time</p>
                    <div className="flex flex-row gap-8 justify-center px-4 w-full text-sm text-slateBlue/30 font-medium italic">
                        <p>my avg</p>
                        <p>global</p>
                    </div>
                </div>
                <div className="col-span-2 font-bold">
                    <p className="w-full text-center">Attempts</p>
                    <div className="flex flex-row gap-8 justify-center px-4 w-full text-sm text-slateBlue/30 font-medium italic">
                        <p>my avg</p>
                        <p>global</p>
                    </div>
                </div>
            </div>
            <hr className="w-full border-1 border-slateBlue/15 my-1" />
            <div className="flex flex-col w-full h-full overflow-auto">
                {questions ? displayRows(resultsNotPresent, resetFilters, initialFilters, questions, courseTracker, prevNav) : null}
            </div>
            <hr className="w-full border-1 border-slateBlue/15 my-1" />
            {
                questions ?
                <div className="flex flex-row w-full h-fit justify-between px-2">
                    <p className="italic text-md">found {questions.page.totalElements} questions</p>
                    <div className="flex flex-row gap-8 w-fit h-fit">
                        <button
                            className="underline italic hover:text-lavender hover:cursor-pointer active:text-lavenderDark"
                            onClick={() => {resetFilters(initialiseFilters(initialFilters))}}
                        >initial filters</button>
                        <button
                            className="underline italic hover:text-lavender hover:cursor-pointer active:text-lavenderDark"
                            onClick={() => {resetFilters(initialiseFilters({}))}}
                        >clear filters</button>
                    </div>
                    <div className="flex flex-row gap-4 w-fit h-full">
                        {
                            resultsNotPresent || questions.page.number === 0 ?
                            null
                            :
                            <button
                                className="bg-slateBlue/20 px-3 rounded-xl hover:bg-slateBlue/30 hover:cursor-pointer active:bg-slateBlue/40"
                                onClick={() => {toggleCurrentPage(currentPage - 1)}}
                            >prev</button>
                        }
                        <p className="font-semibold">showing {resultsNotPresent ? 0 : questions.page.number + 1} of {questions.page.totalPages}</p>
                        {
                            resultsNotPresent || questions.page.number === questions.page.totalPages - 1 ?
                            null
                            :
                            <button
                                className="bg-slateBlue/20 px-3 rounded-xl hover:bg-slateBlue/30 hover:cursor-pointer active:bg-slateBlue/40"
                                onClick={() => {toggleCurrentPage(currentPage + 1)}}
                            >next</button>
                        }
                    </div>
                </div>
                : null
            }
        </div>
    );
}