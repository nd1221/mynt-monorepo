import { Link } from "react-router-dom";
import { useState } from "react";
import { formatDuration, getTotalUnits, getTotalDuration } from "../../utils/coursesUtils";

export default function CourseSectionsMenu(props) {

    const {courseId, courseTrackerId, sections, sectionFlags, toggleSection, toggleAllSections, getLessonMetadata, enrolled, courseProgressTracker = {}} = props;

    const lessonTrackers = courseProgressTracker?.lessonProgressTrackerIds;
    const testTrackers = courseProgressTracker?.testProgressTrackerIds;

    const [expanded, setExpanded] = useState(false);
    const toggleExpand = () => {
        toggleAllSections(!expanded);
        setExpanded(prevExpanded => !prevExpanded);
    };

    // Check if lesson or test has been visited before
    const visitedSectionUnit = (id, trackerIds) => {
        if (trackerIds) {
            return trackerIds.hasOwnProperty(id);
        }
    }

    const getLockIcon = () => {
        return (
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                <path fill-rule="evenodd" d="M12 1.5a5.25 5.25 0 0 0-5.25 5.25v3a3 3 0 0 0-3 3v6.75a3 3 0 0 0 3 3h10.5a3 3 0 0 0 3-3v-6.75a3 3 0 0 0-3-3v-3c0-2.9-2.35-5.25-5.25-5.25Zm3.75 8.25v-3a3.75 3.75 0 1 0-7.5 0v3h7.5Z" clip-rule="evenodd" />
            </svg>
        );
    };

    const getUnitIcon = lessonId => {
        if (enrolled) {
            return (
                visitedSectionUnit(lessonId, lessonTrackers) ?
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                    <path fill-rule="evenodd" d="M2.25 12c0-5.385 4.365-9.75 9.75-9.75s9.75 4.365 9.75 9.75-4.365 9.75-9.75 9.75S2.25 17.385 2.25 12Zm14.024-.983a1.125 1.125 0 0 1 0 1.966l-5.603 3.113A1.125 1.125 0 0 1 9 15.113V8.887c0-.857.921-1.4 1.671-.983l5.603 3.113Z" clip-rule="evenodd" />
                </svg>
                :
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
                    <path stroke-linecap="round" stroke-linejoin="round" d="M15.91 11.672a.375.375 0 0 1 0 .656l-5.603 3.113a.375.375 0 0 1-.557-.328V8.887c0-.286.307-.466.557-.327l5.603 3.112Z" />
                </svg>
            );
        } else {
            return getLockIcon();
        }
    };

    const getTestIcon = testId => {
        if (enrolled) {
            return (
                visitedSectionUnit(testId, testTrackers) ?
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                    <path fill-rule="evenodd" d="M2.25 12c0-5.385 4.365-9.75 9.75-9.75s9.75 4.365 9.75 9.75-4.365 9.75-9.75 9.75S2.25 17.385 2.25 12Zm13.36-1.814a.75.75 0 1 0-1.22-.872l-3.236 4.53L9.53 12.22a.75.75 0 0 0-1.06 1.06l2.25 2.25a.75.75 0 0 0 1.14-.094l3.75-5.25Z" clip-rule="evenodd" />
                </svg>
                :
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M9 12.75 11.25 15 15 9.75M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
                </svg>
            );
        } else {
            return getLockIcon();
        }
    }

    const getTestLink = section => {
        return(
            <Link
                to={`/courses/${courseId}/sections/${section.id}/section-test`}
                state={getLessonMetadata(section, section.lessons.length)}
                onClick={event => !enrolled && event.preventDefault()}
            >
                <div className={`flex flex-row justify-between py-4 gap-4 px-4 text-${enrolled && visitedSectionUnit(section.testId, testTrackers) ? "teal font-bold" : "slateBlue"} text-md rounded-sm border-2 border-transparent hover:border-2 hover:border-slateBlue hover:cursor-pointer active:bg-slateBlue/5`}>
                    <div className="flex flex-row gap-4">
                        {getTestIcon(section.testId)}
                        <h1>section test</h1>
                    </div>
                </div>
            </Link>
        );
    };

    const getLessonLinks = section => {

        return (
            <div className="flex flex-col gap-2 my-4">
                {
                    section.lessons.map(lesson => (
                        <Link
                            to={`/course-units/${lesson.id}/${courseTrackerId}`}
                            state={getLessonMetadata(section, lesson.position)}
                            onClick={event => !enrolled && event.preventDefault()}
                        >
                            <div className={`flex flex-row justify-between py-4 gap-4 px-4 text-${enrolled && visitedSectionUnit(lesson.id, lessonTrackers) ? "teal font-bold" : "slateBlue"} text-md rounded-sm border-2 border-transparent hover:border-2 hover:border-slateBlue hover:cursor-pointer active:bg-slateBlue/5`}>
                                <div className="flex flex-row gap-4">
                                    {getUnitIcon(lesson.id)}
                                    <h1>{`${lesson.position} - ${lesson.title}`}</h1>
                                </div>
                                <p>{formatDuration(lesson.duration)}</p>
                            </div>
                        </Link>
                    ))
                }
                {getTestLink(section)}
            </div>
        );
    };

    const getFullLessonList = section => {
        return (
            <div className="flex flex-col gap-2">
                <p className="text-md my-6 mt-8 w-9/10 mx-auto">
                    {section.description}
                </p>
                <hr className="w-9/10 border-b-1 border-slateBlue/10 mx-auto" />
                {getLessonLinks(section)}
            </div>
        );
    };

    const getEmptyLessonList = () => {
        return (
            <div className="flex flex-row py-2 gap-4 border-t-lavender border-2 w-full justify-center items-center">
                <h1 className="text-white font-bold text-xl mt-4">Lessons Coming Soon</h1>
            </div>
        );
    };

    const getFullSectionMenu = () => {
        return sections.map((section, i) => (
            <>
                <div className="flex flex-row justify-between h-fit w-full bg-slateBlue/5 p-4 text-md text-slateBlue rounded-sm border-1 border-slateBlue/15 hover:bg-slateBlue hover:text-white hover:cursor-pointer" onClick={() => toggleSection(i)}>
                    <h1 className="font-bold">{`${section.position} - ${section.title}`}</h1>
                    <p>{`${section.lessons.length} units • ${formatDuration(section.duration)}`}</p>
                </div>
                {
                    sectionFlags[i] ?
                        section.lessons.length ? getFullLessonList(section) : getEmptyLessonList()
                    : null
                }
            </>
        ));
    };
    
    const getEmptySectionMenu = () => {
        return (
            <div className="flex flex-col gap-4 justify-center items-center">
                <hr className="w-9/10 border-b-1 border-slateBlue/10 mx-auto my-4" />
                <h1 className="text-slateBlue font-bold text-xl">Sections Coming Soon</h1>
            </div>
        );
    };

    return (
        <div className="flex flex-col bg-offWhite">
            <h1 className="font-bold text-2xl mb-8">COURSE CONTENT</h1>
            <div className="flex flex-row justify-between">
                <p className="text-sm mb-2">{`${sections.length} sections • ${getTotalUnits(sections)} units • ${getTotalDuration(sections)} total length`}</p>
                <button
                    className="mx-4 text-slateBlue font-bold hover:cursor-pointer hover:text-lavender"
                    onClick={toggleExpand}
                >{expanded ? "collapse all" : "expand all"}</button>
            </div>
            {
                sections.length ? getFullSectionMenu() : getEmptySectionMenu()
            }
        </div>
    );
}