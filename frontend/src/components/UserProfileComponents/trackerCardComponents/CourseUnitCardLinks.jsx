import { Link, useLocation } from "react-router-dom";

const getSectionQuestionBankState = sectionId => {
    const state = {
        filters: {
            section: sectionId,
        },
        prevNav: {
            from: useLocation().pathname,
            prevPageName: "course overview",
        }
    };
    return state;
}

const getLessonQuestionBankState = (sectionId, lessonId) => {
    const state = {
        filters: {
            lesson: lessonId,
            section: sectionId,
        },
        prevNav: {
            from: useLocation().pathname,
            prevPageName: "course overview",
        }
    };
    return state;
}

const getLessonLinks = (data, linkPayloadData, courseTrackerId, sectionId) => {
    return data.map(lesson => (
        <div className="flex flex-row w-full justify-between items-center gap-2 p-2 rounded-md text-md">
            <div className="flex flex-row gap-4 h-fit w-fit">
                <h1 className="font-bold">{lesson.lessonPosition}</h1>
                <h1 className="italic">{lesson.lessonTitle}</h1>
            </div>
            <div className="flex flex-row gap-4 h-fit w-fit">
                {
                    lesson.lessonProgressTrackerExists ?
                    <Link
                        to={`/question-bank/${courseTrackerId}`}
                        state={getLessonQuestionBankState(sectionId, lesson.lessonId)}
                        className="flex flex-row gap-2 items-center p-1 px-3 border-2 rounded-md border-lavender text-lavender hover:bg-lavender hover:cursor-pointer hover:text-white active:bg-lavenderDark active:border-lavenderDark transition duration-100"
                        onClick={(e) => e.stopPropagation()}
                    >
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                            <path d="M5.625 3.75a2.625 2.625 0 1 0 0 5.25h12.75a2.625 2.625 0 0 0 0-5.25H5.625ZM3.75 11.25a.75.75 0 0 0 0 1.5h16.5a.75.75 0 0 0 0-1.5H3.75ZM3 15.75a.75.75 0 0 1 .75-.75h16.5a.75.75 0 0 1 0 1.5H3.75a.75.75 0 0 1-.75-.75ZM3.75 18.75a.75.75 0 0 0 0 1.5h16.5a.75.75 0 0 0 0-1.5H3.75Z" />
                        </svg>
                    </Link>
                    : null
                }
                <Link
                    to={`/user-profile/${courseTrackerId}/unit-overview/${lesson.lessonProgressTrackerId}`}
                    state={{linkPayloadData}}
                    onClick={e => !lesson.lessonProgressTrackerExists && e.preventDefault()}
                    className={
                        `border-2 text-md p-1 px-2 rounded-md transition duration-100 flex flex-row items-center justify-center gap-2
                        ${!lesson.lessonProgressTrackerExists ? "text-slateBlue/25 border-slateBlue/25" : "text-lavender border-lavender hover:bg-lavender hover:text-white active:scale-99"}`
                    }
                >
                    <p>unit summary</p>
                    {
                        !lesson.lessonProgressTrackerExists ?
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-5">
                          <path fill-rule="evenodd" d="M12 1.5a5.25 5.25 0 0 0-5.25 5.25v3a3 3 0 0 0-3 3v6.75a3 3 0 0 0 3 3h10.5a3 3 0 0 0 3-3v-6.75a3 3 0 0 0-3-3v-3c0-2.9-2.35-5.25-5.25-5.25Zm3.75 8.25v-3a3.75 3.75 0 1 0-7.5 0v3h7.5Z" clip-rule="evenodd" />
                        </svg>
                        :
                        null
                    }
                </Link>
                <Link
                    to={`/course-units/${lesson.lessonId}/${courseTrackerId}`}
                    className="border-2 border-lavender text-md text-lavender p-1 px-3 rounded-md hover:bg-lavender transition duration-100 hover:text-white active:scale-99"
                >
                    view unit
                </Link>
            </div>
        </div> 
    ));
}

const getSectionLinks = (data, flags, toggleFlag, courseTrackerId) => {
    return data.map(section => (
        <>
            <div
                onClick={() => toggleFlag(section.sectionPosition)}
                className="flex flex-row gap-2 w-full h-fit justify-between p-4 items-center bg-slateBlue/10 rounded-md hover:cursor-pointer"
            >
                <div className="flex flex-row flex-grow justify-between items-center gap-4 h-fit w-fit xl:text-lg pr-4">
                    <div className="flex flex-row gap-4 w-fit h-fit">
                        <h1 className="font-bold">Section {section.sectionPosition}</h1>
                        <h1 className="italic truncate">{section.sectionTitle}</h1>
                    </div>
                    <Link
                        to={`/question-bank/${courseTrackerId}`}
                        state={getSectionQuestionBankState(section.sectionId)}
                        className="flex flex-row gap-2 items-center p-1 px-3 border-3 rounded-md border-teal text-teal hover:bg-teal hover:cursor-pointer hover:text-white active:bg-tealDark active:border-tealDark transition duration-100"
                        onClick={(e) => e.stopPropagation()}
                    >
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                            <path d="M5.625 3.75a2.625 2.625 0 1 0 0 5.25h12.75a2.625 2.625 0 0 0 0-5.25H5.625ZM3.75 11.25a.75.75 0 0 0 0 1.5h16.5a.75.75 0 0 0 0-1.5H3.75ZM3 15.75a.75.75 0 0 1 .75-.75h16.5a.75.75 0 0 1 0 1.5H3.75a.75.75 0 0 1-.75-.75ZM3.75 18.75a.75.75 0 0 0 0 1.5h16.5a.75.75 0 0 0 0-1.5H3.75Z" />
                        </svg>
                        <p className="text-sm font-semibold">question bank</p>
                    </Link>
                </div>
                {
                    flags[section.sectionPosition - 1] ?
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                        <path fill-rule="evenodd" d="M4.25 12a.75.75 0 0 1 .75-.75h14a.75.75 0 0 1 0 1.5H5a.75.75 0 0 1-.75-.75Z" clip-rule="evenodd" />
                    </svg>
                    :
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M12 4.5v15m7.5-7.5h-15" />
                    </svg>
                }
            </div>
            <div className="flex flex-col gap-1 items-center w-9/10 h-fit">
                {flags[section.sectionPosition - 1] ? getLessonLinks(section.lessonDTOs, data, courseTrackerId, section.sectionId) : null}
            </div>
        </>
    ));
}

export default function CourseUnitCardLinks({toggleSections, sectionFlags, sectionData, courseTrackerId}) {

    return (
        <div className="col-span-12 flex flex-col w-full h-fit gap-4 mt-8 bg-white p-6 border-1 border-slateBlue/15 rounded-lg shadow-md text-slateBlue hover:shadow-xl">
            <h1 className="font-bold italic text-2xl p-2">Unit Overviews</h1>
            <hr className="w-full border-1 border-slateBlue/15" />
            <div className="flex flex-col gap-2 w-full h-fit py-2 items-center">
                {sectionData == null ? null : getSectionLinks(sectionData, sectionFlags, toggleSections, courseTrackerId)}
            </div>
        </div>
    );
}