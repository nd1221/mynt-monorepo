import { Link, useLoaderData, useLocation } from "react-router-dom";
import { useAuthContext } from "./AuthContext.jsx";
import { useEffect, useState } from "react";
import api from "../api/modules.js";
import { handleError } from "../api/apiUtils.js";
import { authorise } from "../api/authorise.js";
import { fetchCourseData } from "../utils/fetchCourseDataUtils.js";
import FrequencyCalendar from "./trendComponents/FrequencyCalendar.jsx";
import ProgressPieChart from "./trendComponents/ProgressPieChart.jsx";


const fetchCourseProgressTracker = async (courseId, userProgressTrackerId) => {
    try {
        // Fetch course tracker
        const trackerData = await api.get(`/progress/course-tracker/${userProgressTrackerId}/${courseId}`);
        
        // Fetch lesson trackers and map to object with key-value lessonId-lessonTracker for convenience
        const {lessonProgressTrackerIds, ...otherKeys} = trackerData.data;
        const tracker = {
            ...otherKeys,
            lessonProgressTrackers: {}
        }
    
        await Promise.all(
            lessonProgressTrackerIds.map(async id => {
                const fetchedLessonTracker = await api.get(`/progress/lesson-tracker/${id}`);
                const lessonId = fetchedLessonTracker.data.lessonId;
                tracker.lessonProgressTrackers[lessonId] = fetchedLessonTracker.data;
            })
        )
    
        return tracker;
    } catch(err) {
        handleError(err);
    }
};

const fetchCourseQuestionReviewHistory = async courseTrackerId => {
    try {
        const reviewHistory = await api.get(`/review/courses/past-year/${courseTrackerId}`);
        return reviewHistory.data;
    } catch(err) {
        handleError(err);
    }
};

export async function loader({params}) {
    // Authorise
    await authorise();

    // Fetch all data relating to course, sections and lessons -> May be too heavy depending on complexity of practice feature
    const course = await fetchCourseData(params.courseId);
    const tracker = await fetchCourseProgressTracker(params.courseId, params.trackerId);
    const reviewHistory = await fetchCourseQuestionReviewHistory(tracker.id);

    return {course, tracker, reviewHistory};
};

const initialiseSectionMenu = sections => {
    const menu = {};
    sections.forEach(section => {
        menu[section.title] = false;
    });
    return menu;
}

export default function PracticeMenu() {
    
    const currentUser = useAuthContext().currentUser;
    const currentPath = useLocation().pathname;
    const course = useLoaderData().course;
    const tracker = useLoaderData().tracker;
    const reviewHistory = useLoaderData().reviewHistory;

    const [sectionMenu, setSectionMenu] = useState(initialiseSectionMenu(course.sections))

    const getSectionLessonTitleLinks = section => {
        return sectionMenu[section.title] ?
        <div className="flex flex-col w-full mb-6 transition-all duration-500 ease-in-out">
                {
                    section.lessons.map(lesson => {
                        const lessonTracker = tracker.lessonProgressTrackers[lesson.id];
                        return (
                            <>
                                <Link 
                                    className="flex flex-row w-full py-4 justify-between gap-4 my-2 px-8 hover:bg-slateBlue/3 hover:cursor-pointer"
                                    to="/user-profile/question-practice"
                                    state={{returnPath: currentPath, lessonTracker: lessonTracker}}
                                >
                                    <div className="text-xl font-bold">
                                        {lesson.title}
                                    </div>
                                    <div className="text-lg font-bold">
                                        Due today - {lessonTracker ? lessonTracker.questionsDueToday : 0}
                                    </div>
                                </Link>
                                <hr className="w-full border-1 border-slateBlue/15" />
                            </>
                        );
                    })
                }
            </div>
        : null;
    }

    const updateSectionMenu = title => {
        setSectionMenu(prevSectionMenu => {
            return {...prevSectionMenu, [title]: !prevSectionMenu[title]};
        });
    }

    const getCourseMenu = () => {
        return (
            <div className="flex flex-col gap-4 items-center p-4">
                <p className="text-xl mb-6">select a unit to practice</p>
                {
                    course.sections.map(section => {
                        return (
                            <>
                                <div
                                    className="flex flex-col items-center h-fit w-full p-4 rounded-md bg-slateBlue hover:cursor-pointer hover:opacity-85 transition"
                                    onClick={() => updateSectionMenu(section.title)}
                                >
                                    <h1 className="text-2xl font-bold text-offWhite">{section.title}</h1>
                                </div>
                                {getSectionLessonTitleLinks(section)}
                            </>
                        );
                    })
                }
            </div>
        );
    }

    const formatAverageTime = millis => {
        const seconds = Math.floor(millis / 1000);
        const mins = Math.floor(seconds / 60);
        const secs = seconds % 60;
        return (mins > 0 ? `${mins}m` : "") + " " + (secs > 0 ? `${secs}s` : "");
    };

    const formatDate = date => {
        return new Date(date).toLocaleDateString("en-GB", {
            year: "numeric",
            month: "short",
            day: "2-digit"
        });
    };

    return (
        <div className="flex flex-col items-center gap-4 h-fit w-full bg-white shadow-xl border-1 border-slateBlue/10 rounded-xl p-10 mb-10">
            <div className="flex flex-col items-center justify-center h-fit w-full mb-6">
                <h1 className="text-slateBlue font-bold text-4xl">{course.title}</h1>
            </div>
            <hr className="w-3/4 border-1 border-slateBlue/15" />
            <h1 className="text-slateBlue font-bold text-2xl mt-4 mr-auto pl-40">Course Overview</h1>
            <div className="flex flex-col gap-8 items-center w-9/10 h-fit py-4">
                <div className="flex flex-row gap-16 w-8/10 h-fit items-center">
                    <ProgressPieChart correctCount={Math.floor((tracker.accuracy / 100) * tracker.totalQuestionReviewed)} totalCount={tracker.totalQuestionReviewed}/>
                    <div className="grow grid grid-cols-2 grid-rows-3 gap-8 h-full bg-white text-xl rounded-lg shadow-lg p-6 border border-gray-200">
                        <div className="flex flex-col w-fit h-fit gap-1">
                            <h3 class="text-sm font-medium text-slateBlue/50">Correct Answers</h3>
                            <p class="text-3xl font-semibold text-slateBlue">{Math.floor((tracker.accuracy / 100) * tracker.totalQuestionReviewed)} ✅</p>
                        </div>
                        <div className="flex flex-col w-fit h-fit gap-1">
                            <h3 class="text-sm font-medium text-slateBlue/50">Total Attempts</h3>
                            <p class="text-3xl font-semibold text-slateBlue">{tracker.totalQuestionReviewed} 🎯</p>
                        </div>
                        <div className="flex flex-col w-fit h-fit gap-1">
                            <h3 class="text-sm font-medium text-slateBlue/50">Avg. Time per Question</h3>
                            <p class="text-3xl font-semibold text-slateBlue">{formatAverageTime(tracker.averageQuestionTimeMillis)} ⏱️</p>
                        </div>
                        <div className="flex flex-col w-fit h-fit gap-1">
                            <h3 class="text-sm font-medium text-slateBlue/50"></h3>
                            <p class="text-3xl font-semibold text-slateBlue"></p>
                        </div>
                        <div className="flex flex-col w-fit h-fit gap-1">
                            <h3 class="text-sm font-medium text-slateBlue/50">First Reviewed</h3>
                            <p class="text-xl font-semibold text-slateBlue">{formatDate(tracker.firstPracticeDate)} 📅</p>
                        </div>
                        <div className="flex flex-col w-fit h-fit gap-1">
                            <h3 class="text-sm font-medium text-slateBlue/50">Last Reviewed</h3>
                            <p class="text-xl font-semibold text-slateBlue">{formatDate(tracker.lastPracticeDate)} 🕓</p> 
                        </div>
                    </div>
                </div>
                <FrequencyCalendar data={reviewHistory}/>
            </div>
            <hr className="w-3/4 border-1 border-slateBlue/15" />
            <div className="flex flex-col w-full h-fit">
                {getCourseMenu()}
            </div>
            <hr className="w-3/4 border-1 border-slateBlue/15" />
            <div className="flex flex-col items-center justify-center w-full h-fit mt-6">
                <Link
                    to="/user-profile/courses"
                    className="bg-transparent border-4 border-lavender py-4 px-8 text-lavender font-bold text-2xl rounded-lg hover:bg-lavender hover:text-white active-text-white active:scale-98"
                >
                    back to courses
                </Link>
            </div>
        </div>
    );
}