import { authorise } from "../api/authorise";
import { useState } from "react";
import { useLoaderData } from "react-router-dom";
import api from "../api/modules.js";
import { handleError } from "../api/apiUtils.js";
import UnitSearchSidebar from "../components/UserProfileComponents/unitOverviewComponents/UnitSearchSidebar.jsx";
import UnitReviewQuickstartCard from "../components/UserProfileComponents/unitOverviewComponents/UnitReviewQuickstartCard.jsx";
import { Link } from "react-router-dom";
import UnitOverviewCard from "../components/UserProfileComponents/unitOverviewComponents/UnitOverviewCard.jsx";

const fetchLessonProgressData = async lessonProgressTrackerId => {
    try {
        const response = await api.get(`/progress/lesson-overview-data/${lessonProgressTrackerId}`);
        return response.data;
    } catch(err) {
        handleError(err);
    }
}

const fetchReviewQuickstartData = async courseProgressTrackerId => {
    try {
        const response = await api.get(`/practice/section-review-quickstart/${courseProgressTrackerId}`);
        return response.data;
    } catch(err) {
        handleError(err);
    }
}

const fetchUnitSearchbarData = async courseProgressTrackerId => {
    try {
        const response = await api.get(`/progress/lesson-search-summary/${courseProgressTrackerId}`);
        return response.data;
    } catch(err) {
        handleError(err);
    }
}

const fetchSectionList = async courseProgressTrackerId => {
    try {
        const response = await api.get(`/progress/course-section-list/${courseProgressTrackerId}`);
        return response.data;
    } catch(err) {
        handleError(err);
    }
}

const fetchSection = async sectionId => {
    try {
        const response = await api.get(`/sections/${sectionId}`);
        return response.data;
    } catch(err) {
        handleError(err);
    }
}

const fetchReviewHistoryData = async lessonTrackerId => {
    try {
        const response = await api.get(`/review/lessons/past-year/${lessonTrackerId}`);
        return response.data;
    } catch(err) {
        handleError(err);
    }
}

export async function loader({params}) {
    
    const lessonTrackerId = params.lessonProgressTrackerId;
    const courseTrackerId = params.courseProgressTrackerId;
    
    // Authorise
    await authorise();
    
    // Get lesson progress tracker data
    const lessonProgressData = await fetchLessonProgressData(lessonTrackerId);
    const reviewQuickstartData = await fetchReviewQuickstartData(courseTrackerId);
    const lessonSearchbarData = await fetchUnitSearchbarData(courseTrackerId);
    const sectionList = await fetchSectionList(courseTrackerId);
    const section = await fetchSection(lessonProgressData.data.entity.sectionId);
    const reviewHistoryData = await fetchReviewHistoryData(lessonTrackerId);
    
    return [lessonProgressData, reviewQuickstartData, lessonSearchbarData, sectionList, courseTrackerId, section, reviewHistoryData];
};

const initialiseQuickstartFlags = data => {
    const flags = new Array(data.length).fill(false);
    flags[0] = true;
    return flags;
}

const getPrevNextCourseLinkContent = (direction) => {
    return direction === "next" ?
    <>
        <p>next lesson</p>
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
            <path d="M5.055 7.06C3.805 6.347 2.25 7.25 2.25 8.69v8.122c0 1.44 1.555 2.343 2.805 1.628L12 14.471v2.34c0 1.44 1.555 2.343 2.805 1.628l7.108-4.061c1.26-.72 1.26-2.536 0-3.256l-7.108-4.061C13.555 6.346 12 7.249 12 8.689v2.34L5.055 7.061Z" />
        </svg>
    </>
    :
    <>
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
            <path d="M9.195 18.44c1.25.714 2.805-.189 2.805-1.629v-2.34l6.945 3.968c1.25.715 2.805-.188 2.805-1.628V8.69c0-1.44-1.555-2.343-2.805-1.628L12 11.029v-2.34c0-1.44-1.555-2.343-2.805-1.628l-7.108 4.061c-1.26.72-1.26 2.536 0 3.256l7.108 4.061Z" />
        </svg>
        <p>prev lesson</p>
    </>
}

const getPrevNextCourseLink = (trackerId, navigationId, direction) => {

    if (!navigationId) {
        return (<div className="lg:w-50"></div>);
    }

    const colourProperties = direction === "next" ? "text-teal border-teal hover:bg-teal" : "text-lavender border-lavender hover:bg-lavender";

    return (
        <Link
            to={`/user-profile/${trackerId}/unit-overview/${navigationId}`}
            className={
                `flex flex-row gap-4 lg:w-50 items-center justify-center p-2 px-3 font-bold text-lg
                rounded-md border-3 hover:cursor-pointer hover:text-white transition duration-100
                ${colourProperties}`
            }
        >
            {getPrevNextCourseLinkContent(direction)}
        </Link>
    );
}

export default function UnitOverview() {

    const data = useLoaderData();
    const [overviewData, quickstartData, searchbarData, sectionList, courseTrackerId, section, reviewHistoryData] = data;

    const lesson = overviewData.data.entity;
    const tracker = overviewData.data.tracker;
    const nextLessonTrackerId = overviewData.next;
    const prevLessonTrackerId = overviewData.prev;

    const [quickstartFlags, setQuickstartFlags] = useState(initialiseQuickstartFlags(quickstartData));
    const toggleQuickstartFlags = position => {
        setQuickstartFlags(prevFlags => prevFlags.map(
            (flag, index) => index === position - 1 ? !flag : flag
        ));
    };

    // Not great solution but also not worth hitting the database again as icon is same as course icon currently
    const iconURL = searchbarData[0].iconURL;

    return (
        <div className="grid grid-cols-[3fr_12fr_4fr] gap-8 w-full max-h-screen h-full">
            <div className="flex flex-col gap-8 max-h-screen lg:h-[80vh] xl:h-[80vh]">
                <Link
                    to={`/user-profile/courses/${courseTrackerId}`} 
                    className="flex flex-row gap-4 items-center justify-center p-4 h-fit w-full border-3 rounded-md font-bold text-lg border-slateBlue text-slateblue hover:cursor-pointer hover:text-white hover:bg-slateBlue transition duration-100"
                >
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-7">
                        <path fill-rule="evenodd" d="M9.53 2.47a.75.75 0 0 1 0 1.06L4.81 8.25H15a6.75 6.75 0 0 1 0 13.5h-3a.75.75 0 0 1 0-1.5h3a5.25 5.25 0 1 0 0-10.5H4.81l4.72 4.72a.75.75 0 1 1-1.06 1.06l-6-6a.75.75 0 0 1 0-1.06l6-6a.75.75 0 0 1 1.06 0Z" clip-rule="evenodd" />
                    </svg>
                    <p>course overview</p>
                </Link>
                <UnitReviewQuickstartCard
                    reviewData={quickstartData}
                    toggleSection={toggleQuickstartFlags}
                    sectionFlags={quickstartFlags}
                    courseTrackerId={courseTrackerId}
                />
            </div>
            <div className="grid grid-cols-1 grid-rows-8 gap-8 max-h-screen lg:h-[80vh] xl:h-[80vh]">
                <div className="row-span-1 col-span-1 flex flex-row gap-4 justify-between items-center px-6">
                    {getPrevNextCourseLink(courseTrackerId, prevLessonTrackerId, "prev")}
                    <p className="text-2xl italic">UNIT OVERVIEW</p>
                    {getPrevNextCourseLink(courseTrackerId, nextLessonTrackerId, "next")}
                </div>
                <UnitOverviewCard
                    lesson={lesson}
                    tracker={tracker}
                    iconURL={iconURL}
                    section={section} 
                    reviewHistory={reviewHistoryData}
                />
            </div>
            <UnitSearchSidebar
                data={searchbarData}
                sections={sectionList}
                trackerId={courseTrackerId}
            />
        </div>
    );
}