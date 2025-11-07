import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { useAuthContext } from "../components/AuthContext.jsx";
import { authorise } from "../api/authorise.js";
import api from "../api/modules.js";
import { handleError } from "../api/apiUtils.js";
import RightSidebar from "../components/UserProfileComponents/RightSidebar.jsx";
import CourseCard from "../components/UserProfileComponents/CourseCard.jsx";
import CourseSearchBar from "../components/UserProfileComponents/CourseSearchBar.jsx";
import { useParams } from "react-router-dom";

export async function loader() {
    // Authorise
    await authorise();
};

const fetchUserProgressTracker = async userProgressTrackerId => {
    try {
        return await api.get(`/progress/user-tracker/${userProgressTrackerId}`);
    } catch(err) {
        handleError(err);
    }
};

const fetchCourseEntityTrackerPair = async courseProgressTrackerId => {
    try {
        return await api.get(`/progress/course-entity-tracker/${courseProgressTrackerId}`)
    } catch(err) {
        handleError(err);
    }
}

const fetchCourseSearchData = async userProgressTrackerId => {
    try {
        return await api.get(`/progress/course-search-summary/${userProgressTrackerId}`);
    } catch(err) {
        handleError(err);
    }
}

const fetchRecentActivity = async userProgressTrackerId => {
    try {
        return await api.get(`/review/aggregate/recent-activity/${userProgressTrackerId}`);
    } catch(err) {
        handleError(err);
    }
}

const fetchCourseReviewQuickstartData = async userProgressTrackerId => {
    try {
        return await api.get(`/practice/course-review-quickstart/${userProgressTrackerId}`);
    } catch(err) {
        handleError(err);
    }
}

const getUserStreakProps = (userProgressTracker, recentActivityData) => {
    if (userProgressTracker) {
        return ({
            streak: userProgressTracker.streak,
            longestStreak: userProgressTracker.longestStreak,
            recentActivityData: recentActivityData
        });
    }
    return ({});
}

const getEmptyCourseCard = () => {
    return (
        <div className="flex-1 flex flex-col gap-4 overflow-auto justify-center items-center rounded-lg bg-slateBlue/5 p-6 shadow-md border-1 border-slateBlue/6">
            <div className="flex flex-col gap-6 w-2/7 h-fit p-4 items-center">
                <p className="text-xl text-center">you are not currently enrolled on any of our courses</p>
                <Link to="/paths" className="bg-offWhite text-lg border-lavender border-3 rounded-md text-lavender p-4 px-6 font-bold hover:bg-lavender hover:text-offWhite transition active:scale-98 active:opacity-90">
                    browse courses here
                </Link>
            </div>
        </div>
    );
}

export default function UserProfile() {
    
    const authContext = useAuthContext();
    const currentUser = authContext?.currentUser;

    const [userProgressTracker, setUserProgressTracker] = useState(null);
    const [recentActivityData, setRecentActivityData] = useState(null);
    const [reviewQuickstartData, setReviewQuickstartData] = useState(null);

    const [course, setCourse] = useState(null);
    const [tracker, setTracker] = useState(null);
    const [courseSearchData, setCourseSearchData] = useState(null);

    const {courseProgressTrackerId} = useParams();

    // Fetch user profile data
    useEffect(() => {

        const getData = async trackerId => {

            // Get user progress tracker
            const userProgressTracker = await fetchUserProgressTracker(currentUser?.userProgressTrackerId);
            setUserProgressTracker(userProgressTracker.data);

            // Get current course based on url params
            let courseTrackerPair;
            if (currentUser?.courseIds?.length === 1) {
                courseTrackerPair = await fetchCourseEntityTrackerPair(currentUser.courseProgressTrackerIds[0]);
                persistMostRecentCourse(userProgressTracker.data.id, courseTrackerPair.data.tracker.id);
            } else if (courseProgressTrackerId) {
                courseTrackerPair = await fetchCourseEntityTrackerPair(trackerId);
                persistMostRecentCourse(userProgressTracker.data.id, courseTrackerPair.data.tracker.id);
            } else if (userProgressTracker.data.mostRecentCourseTrackerId) {
                courseTrackerPair = await fetchCourseEntityTrackerPair(userProgressTracker.data.mostRecentCourseTrackerId);
            } else {}

            setCourse(courseTrackerPair.data.entity);
            setTracker(courseTrackerPair.data.tracker);
            
            // Get user streak card data
            const recentActivity = await fetchRecentActivity(userProgressTracker.data.id);
            setRecentActivityData(recentActivity.data);
            
            // Get review quickstart data
            if (currentUser?.courseIds.length > 0) {
                const reviewQuickstartData = await fetchCourseReviewQuickstartData(userProgressTracker.data.id);
                setReviewQuickstartData(reviewQuickstartData.data);
            }

            // Get course searchbar data
            const courseSearchData = await fetchCourseSearchData(userProgressTracker.data.id);
            setCourseSearchData(courseSearchData.data);

        }
        
        if (!currentUser?.userId || !currentUser?.courseProgressTrackerIds?.length) {
            return;
        }
        getData(courseProgressTrackerId);

    }, [currentUser?.userId, currentUser?.courseProgressTrackerIds?.length, courseProgressTrackerId]);

    const persistMostRecentCourse = async (userProgressTrackerId, trackerId) => {
        // Store user's most recent course in backend to be loaded immediately upon return for UI purposes
        if (userProgressTrackerId != null) {
            await api.patch(`/progress/remember-course/${userProgressTrackerId}/${trackerId}`);
            await authContext.updateCurrentUser();
        }
    }
    
    const isUserEnrolledOnCourses = () => {
        return currentUser?.courseIds.length === 0;
    }

    return (
        <div className="flex flex-row flex-1 gap-8 h-fit w-full">

            <CourseSearchBar courseSummaries={courseSearchData} />

            <div className="flex flex-row gap-8 lg:flex-5 md:flex-4">

                <div className="flex flex-col lg:flex-4 gap-8">
                    <div className=" flex flex-row justify-between h-fit text-3xl text-slateBlue">
                        <h1 className="font-bold">
                            {
                                isUserEnrolledOnCourses() ?
                                "Welcome to your dashboard"
                                : "Welcome back"
                            }
                        </h1>
                        <h1 className="italic">COURSE OVERVIEW</h1>
                    </div>
                    {
                        course == null ? 
                        getEmptyCourseCard()
                        : 
                        <CourseCard course={course} tracker={tracker} />
                    }
                </div>

                <RightSidebar userStreakProps={getUserStreakProps(userProgressTracker, recentActivityData)} reviewQuickstartProps={reviewQuickstartData} />

            </div>
        </div>
    );
};