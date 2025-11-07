import { formatDuration } from "../../../utils/coursesUtils";
import { Link, useLocation } from "react-router-dom";

const getQuestionBankState = (sectionId, lessonId) => {
    const state = {
        filters: {
            lesson: lessonId,
            section: sectionId,
        },
        prevNav: {
            from: useLocation().pathname,
            prevPageName: "unit overview",
        }
    };
    return state;
}

export default function UnitSummaryCard({lesson, iconURL, section, courseTrackerId}) {

    return (
        <div className="col-span-3 row-span-5 flex flex-col w-full gap-4 bg-slateBlue text-offWhite border-2 border-slateBlue rounded-md p-4 items-center hover:shadow-xl">
            <div
                style={{ backgroundImage: `url(${iconURL})`}} 
                className="inset-0 flex flex-col items-center justify-start rounded-md w-full lg:h-40 xl:h-40 mb-4 p-4 bg-black/40 bg-blend-multiply bg-center bg-cover"
            >
                <h1 className="text-lg font-bold line-clamp-3">Section {section.position} - {section.title}</h1>
            </div>
            <div className="flex flex-col gap-2 w-full h-fit mb-2">
                <div className="flex flex-row w-full h-fit justify-between overflow-wrap lg:text-md xl:text-md">
                    <div className="flex flex-row h-fit gap-2 items-center">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M12 6v6h4.5m4.5 0a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
                        </svg>
                        <p className="font-bold">Duration</p>
                    </div>
                    <p className="italic">{formatDuration(lesson.duration)}</p>
                </div>
                <div className="flex flex-row w-full h-fit justify-between overflow-wrap lg:text-md xl:text-md">
                    <div className="flex flex-row h-fit gap-2 items-center">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M5.25 8.25h15m-16.5 7.5h15m-1.8-13.5-3.9 19.5m-2.1-19.5-3.9 19.5" />
                        </svg>
                        <p className="font-bold">Position</p>
                    </div>
                    <p className="italic">{lesson.position} of {lesson.numberOfLessonsInCourse}</p>
                </div>
                <div className="flex flex-row w-full h-fit justify-between overflow-wrap lg:text-md xl:text-md">
                    <div className="flex flex-row h-fit gap-2 items-center">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M9 12.75 11.25 15 15 9.75M21 12c0 1.268-.63 2.39-1.593 3.068a3.745 3.745 0 0 1-1.043 3.296 3.745 3.745 0 0 1-3.296 1.043A3.745 3.745 0 0 1 12 21c-1.268 0-2.39-.63-3.068-1.593a3.746 3.746 0 0 1-3.296-1.043 3.745 3.745 0 0 1-1.043-3.296A3.745 3.745 0 0 1 3 12c0-1.268.63-2.39 1.593-3.068a3.745 3.745 0 0 1 1.043-3.296 3.746 3.746 0 0 1 3.296-1.043A3.746 3.746 0 0 1 12 3c1.268 0 2.39.63 3.068 1.593a3.746 3.746 0 0 1 3.296 1.043 3.746 3.746 0 0 1 1.043 3.296A3.745 3.745 0 0 1 21 12Z" />
                        </svg>
                        <p className="font-bold truncate">Core Questions</p>
                    </div>
                    <p className="italic">{lesson.numberOfCoreQuestionsInLesson}</p>
                </div>
                <div className="flex flex-row w-full h-fit justify-between overflow-wrap lg:text-md xl:text-md">
                    <div className="flex flex-row h-fit gap-2 items-center">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-5.5">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M8.25 6.75h12M8.25 12h12m-12 5.25h12M3.75 6.75h.007v.008H3.75V6.75Zm.375 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0ZM3.75 12h.007v.008H3.75V12Zm.375 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm-.375 5.25h.007v.008H3.75v-.008Zm.375 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Z" />
                        </svg>
                        <p className="font-bold truncate">All Questions</p>
                    </div>
                    <p className="italic">{lesson.numberOfQuestionsInLesson}</p>
                </div>
            </div>
            <div className="flex flex-col w-full h-fit gap-2">
                <Link
                    to={`/course-units/${lesson.id}/${courseTrackerId}`}
                    className="flex flex-row gap-4 w-full items-center justify-center lg:p-4 xl:p-3 bg-transparent border-3 border-lavender text-lavender lg:text-xl xl:text-lg rounded-lg font-bold hover:bg-lavender hover:text-offWhite transition active:scale-98"
                >
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-5.5">
                        <path d="M11.7 2.805a.75.75 0 0 1 .6 0A60.65 60.65 0 0 1 22.83 8.72a.75.75 0 0 1-.231 1.337 49.948 49.948 0 0 0-9.902 3.912l-.003.002c-.114.06-.227.119-.34.18a.75.75 0 0 1-.707 0A50.88 50.88 0 0 0 7.5 12.173v-.224c0-.131.067-.248.172-.311a54.615 54.615 0 0 1 4.653-2.52.75.75 0 0 0-.65-1.352 56.123 56.123 0 0 0-4.78 2.589 1.858 1.858 0 0 0-.859 1.228 49.803 49.803 0 0 0-4.634-1.527.75.75 0 0 1-.231-1.337A60.653 60.653 0 0 1 11.7 2.805Z" />
                        <path d="M13.06 15.473a48.45 48.45 0 0 1 7.666-3.282c.134 1.414.22 2.843.255 4.284a.75.75 0 0 1-.46.711 47.87 47.87 0 0 0-8.105 4.342.75.75 0 0 1-.832 0 47.87 47.87 0 0 0-8.104-4.342.75.75 0 0 1-.461-.71c.035-1.442.121-2.87.255-4.286.921.304 1.83.634 2.726.99v1.27a1.5 1.5 0 0 0-.14 2.508c-.09.38-.222.753-.397 1.11.452.213.901.434 1.346.66a6.727 6.727 0 0 0 .551-1.607 1.5 1.5 0 0 0 .14-2.67v-.645a48.549 48.549 0 0 1 3.44 1.667 2.25 2.25 0 0 0 2.12 0Z" />
                        <path d="M4.462 19.462c.42-.419.753-.89 1-1.395.453.214.902.435 1.347.662a6.742 6.742 0 0 1-1.286 1.794.75.75 0 0 1-1.06-1.06Z" />
                    </svg>
                    <p>go to unit</p>
                </Link>
                <Link
                    to={`/question-bank/${courseTrackerId}`}
                    state={getQuestionBankState(section.id, lesson.id)}
                    className="flex flex-row gap-4 w-full items-center justify-center lg:p-4 xl:p-3 bg-transparent border-3 border-lavender text-lavender lg:text-xl xl:text-lg rounded-lg font-bold hover:bg-lavender hover:text-offWhite transition active:scale-98"
                >
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-5.5">
                        <path d="M5.625 3.75a2.625 2.625 0 1 0 0 5.25h12.75a2.625 2.625 0 0 0 0-5.25H5.625ZM3.75 11.25a.75.75 0 0 0 0 1.5h16.5a.75.75 0 0 0 0-1.5H3.75ZM3 15.75a.75.75 0 0 1 .75-.75h16.5a.75.75 0 0 1 0 1.5H3.75a.75.75 0 0 1-.75-.75ZM3.75 18.75a.75.75 0 0 0 0 1.5h16.5a.75.75 0 0 0 0-1.5H3.75Z" />
                    </svg>
                    <p>question bank</p>
                </Link>
            </div>
        </div>
    );
}