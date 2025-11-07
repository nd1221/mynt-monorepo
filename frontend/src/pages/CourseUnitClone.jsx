import { useEffect } from "react";
import { useLocation, Link, useLoaderData } from "react-router-dom";
import api from "../api/modules.js";
import DOMPurify from 'dompurify';
import { authorise } from "../api/authorise.js";
import { handleError } from "../api/apiUtils.js";

const fetchUnitContent = async unitId => {
    try {
          // Fetch content
          return await api.get(`/lessons/${unitId}/content`);
    } catch (err) {
        handleError(err);
    }
}

export async function loader({params}) {
    const unitId = params.id;
    // Authorise
    await authorise();
    return fetchUnitContent(unitId);
}

export default function CourseUnit() {

    const content = DOMPurify.sanitize(useLoaderData().data);

    const location = useLocation();
    const lessonMetadata = location?.state;

    const courseId = lessonMetadata?.courseId; // Will always have a courseId, logically enforced by backend
    const sectionId = lessonMetadata?.sectionId;
    const sectionTitle = lessonMetadata?.sectionTitle;
    const currentLessonPosition = lessonMetadata?.currentLessonPosition;
    const lessons = lessonMetadata?.lessons; // In order to reach this page, there must be at least one lesson in the list
    const currentLesson = lessons[currentLessonPosition - 1];

    // Ensure lesson progress tracker exists
    useEffect(() => {
        try {
            api.post(`/progress/${lessonMetadata?.courseProgressTrackerId}/lesson-${currentLesson.id}/lesson-tracker`);
        } catch(err) {
            handleError(err);
        }
    }, [currentLesson]);

    const getNewLessonMetadata = nextLessonPosition => {
        return {
            ...lessonMetadata,
            currentLessonPosition: nextLessonPosition
        };
    }

    const getPrevLink = () => {

        let path;
        const currentLessonIndex = currentLessonPosition - 1;
        const nextLessonPosition = currentLessonIndex;
        
        // Get previous lesson path
        if (currentLessonPosition > 1 && currentLessonPosition <= lessons.length) {
            path = `/course-units/${lessons[currentLessonIndex - 1].id}`;
        } else {
            path = "/not-found";
        }

        // Build prev Link element
        return (
            <Link
                to={path}
                className="
                    block my-auto text-center h-fit w-full
                    bg-transparent text-lavenderDark/90 border-lavender border-3 p-4 px-8 text-xl font-bold rounded-2xl
                    hover:bg-lavender hover:text-offWhite duration-100 active:scale-98
                "
                state={getNewLessonMetadata(nextLessonPosition)}
            >
                <p>prev unit</p>
            </Link>
        );
    }

    const getNextLink = () => {

        let path;
        let state;

        // Get next lesson path
        if (currentLessonPosition >= 1 && currentLessonPosition < lessons.length) {
            const nextIndex = currentLessonPosition;
            path = `/course-units/${lessons[nextIndex].id}`;
            state = getNewLessonMetadata(currentLessonPosition + 1);
        } else if (currentLessonPosition === lessons.length) {
            path = `/courses/${courseId}/sections/${sectionId}/section-test`;
            state = {courseId: courseId, sectionTitle: sectionTitle};
        } else {
            path = "/not-found";
            state = null;
        }

        // Build next link element
        return (
            <Link
                to={path}
                className="
                    block my-auto text-center h-fit w-full
                    bg-transparent text-lavenderDark/90 border-lavender border-3 p-4 px-8 text-xl font-bold rounded-2xl
                    hover:bg-lavender hover:text-offWhite duration-100 active:scale-98
                "
                state={state}
            >
                <p>{currentLessonPosition === lessons.length ? "section test" : "next unit"}</p>
            </Link>
        );
    }

    const getRandomQuestionLink = () => {

        let path;
        let state;
        const numberOfQuestions = currentLesson.questionIds.length;

        // Get next path
        if (numberOfQuestions === 0) {
            path = "/not-found";
            state = null;
        } else {
            const randomQuestionIndex = Math.floor(Math.random() * numberOfQuestions);
            path = `/unit-questions/${currentLesson.questionIds[randomQuestionIndex]}`;
            state = getNewLessonMetadata(currentLessonPosition);
        }

        // Build Question Link
        return (
            <Link
                to={path}
                className="
                    block my-auto text-center h-fit w-full
                    bg-transparent text-slateBlue border-slateBlue border-3 p-4 px-8 text-xl font-bold rounded-2xl
                    hover:bg-slateBlue hover:text-offWhite duration-100 active:scale-98
                "
                state={state}
            >
                <p>question</p>
            </Link>
        );
    }

    return (
        <>
            <div className="relative flex flex-col gap-4 w-7xl p-10 pb-0">
                <div className="relative right-20 flex flex-row gap-6">
                    <div className="sticky top-0 flex flex-col gap-6 w-1/4 h-fit items-center">
                        <div className="w-full h-fit bg-slateBlue rounded-lg p-2 px-4 mt-17">
                            <hr className="my-4 border-2 border-lavender" />
                            <h1 className="text-2xl font-bold mb-2 text-offWhite">{`Unit ${currentLessonPosition} of ${lessons.length}`}</h1>
                            <p className="text-lg text-offWhite">{currentLesson.description}</p>
                            <hr className="my-4 border-2 border-lavender" />
                        </div>
                        <Link
                            to={`/courses/${courseId}`}
                            className="
                                block mx-auto text-center h-fit w-full
                                bg-transparent text-slateBlue border-slateBlue border-3 p-4 px-8 text-xl font-bold rounded-lg
                                hover:bg-slateBlue hover:text-offWhite duration-100 active:scale-98 
                            "
                        >
                            <p>back to course</p>
                        </Link>
                    </div>
                    <div className="relative flex flex-col h-fit w-3/4 gap-4 p-4 items-center">
                        <h1 className="text-4xl font-bold text-slateBlue">{currentLesson.title}</h1>
                        <hr className="w-full border-1 border-slateBlue/20" />

                        <div className="flex flex-col gap-4 h-fit w-full my-8 p-4 unitContent" dangerouslySetInnerHTML={{__html: content}}></div>

                        <div className="sticky bottom-0 flex flex-col gap-2 w-full h-fit bg-offWhite py-8">
                            <hr className="w-full border-1 border-slateBlue/20" />
                            <div className="grid grid-cols-3 gap-8 w-full p-4 h-fit items-center justify-between">
                                {
                                    currentLessonPosition > 1 ?
                                    getPrevLink()
                                    :
                                    // Invisible placeholder for styling purposes
                                    <div className="
                                            block my-auto text-center h-fit w-full
                                            bg-transparent text-transparent border-transparent border-3 p-4 px-8 text-xl font-bold rounded-2xl
                                        "
                                    >
                                        placeholder
                                    </div>
                                }
                                {getRandomQuestionLink()}
                                {getNextLink()}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}