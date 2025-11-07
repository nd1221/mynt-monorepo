import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useAuthContext } from "./AuthContext.jsx";
import { authorise } from "../api/authorise.js";
import api from "../api/modules.js";
import { handleError } from "../api/apiUtils.js";
import { parseDate } from "../utils/coursesUtils.js";

export async function loader() {
    // Authorise
    await authorise();
}

export default function UserCourses() {
    
    const [tabs, setTabs] = useState({
        "courses": true,
        "learning paths": false,
    });

    const [learningPaths, setLearningPaths] = useState([]);
    const [courses, setCourses] = useState([]);

    const currentUser = useAuthContext().currentUser;

    // Fetch users enrolled courses and learning paths
    useEffect(() => {

        const fetchCourses = async () => {
            return await Promise.all(
                currentUser.courseIds.map(async courseId => {
                    const fetchedCourse = await api.get(`/courses/${courseId}`);
                    return fetchedCourse.data;
                })
            );
        }

        const fetchLearningPaths = async () => {
            return await Promise.all(
                currentUser.learningPathIds.map(async learningPathId => {
                    const fetchedLearningPath =  await api.get(`/learning-paths/${learningPathId}`);
                    return fetchedLearningPath.data;
                })
            );
        }

        const fetchData = async () => {
            try {
                if (currentUser) {
                    setCourses(await fetchCourses());
                    setLearningPaths(await fetchLearningPaths());
                }
            } catch(err) {
                handleError(err);
            }
        }

        fetchData();

    }, [currentUser]);

    const switchTab = newTab => {
        setTabs(prevTabs => (
            Object.fromEntries(
                Object.keys(prevTabs).map(tab => [tab, tab === newTab])
            )
        ));
    }

    const getTabBar = () => {
        return Object.entries(tabs).map(([tab, isActive]) => {
            
            const baseClass = "flex flex-col items-center justify-center text-xl font-bold hover:cursor-pointer";
            const activeClass = "text-lavender";
            const inactiveClass = "text-offWhite hover:text-teal";

            return (
                <div
                    className={`${baseClass} ${isActive ? activeClass : inactiveClass}`}
                    onClick={() => {switchTab(tab)}}
                >
                    {tab}
                </div>
            );
        });
    }

    const getCourses = () => {
        return courses.map(course => {
            return (
                <>
                    <Link
                        to={`/courses/${course.id}`}
                        className="w-4/5"
                    >
                        <div className="flex flex-col gap-4 my-3 hover:opacity-75 hover:cursor-pointer">
                            <h1 className="text-2xl font-bold text-offWhite mb-4">{course.title}</h1>
                            <div className="flex flex-row gap-4 mb-4">
                                <img className="h-36 rounded-lg" src={course.iconURL} alt="Course hero image" />
                                <p className="text-md text-offWhite">{course.description}</p>
                            </div>
                            <div className="flex flex-row justify-between">
                                <p className="text-lg text-offWhite font-bold">{course.creators.join(", ")}</p>
                                <p className="text-lg text-teal font-bold">{parseDate(course.createdAt)}</p>
                            </div>
                        </div>
                    </Link>
                    <div className="flex flex-row items-center w-full h-fit mb-4">
                        <Link
                            to={`/user-profile/practice/${course.id}/${currentUser.userProgressTrackerId}`}
                            className="mx-auto my-auto text-offWhite font-bold text-lg bg-slateBlue border-2 border-lavender py-4 px-8 rounded-lg hover:bg-lavender hover:text-white active:opacity-75 active:scale-98 active:text-white"
                        >
                            practice questions
                        </Link>
                    </div>
                    <hr className="border-1 border-offWhite/15 w-3/4" />
                </>
            );
        });
    }

    const getLearningPaths = () => {
        return learningPaths.map(learningPath => {
            return (
                <Link
                    to={`/learning-paths/${learningPath.id}`}
                    className="w-4/5"
                >
                    <div className="flex flex-col gap-4 mb-3 hover:opacity-75 hover:cursor-pointer">
                        <h1 className="text-2xl font-bold text-offWhite mb-4">{learningPath.title}</h1>
                        <div className="flex flex-row gap-4 mb-4">
                            <img className="h-36 rounded-lg" src={learningPath.iconURL} alt="Course hero image" />
                            <p className="text-md text-offWhite">{learningPath.description}</p>
                        </div>
                        <div className="flex flex-row justify-between">
                            <p className="text-lg text-offWhite font-bold">{learningPath.creators.join(", ")}</p>
                            <p className="text-lg text-teal font-bold">{parseDate(learningPath.createdAt)}</p>
                        </div>
                        <hr className="border-1 border-offWhite/15 w-ful" />
                    </div>
                </Link>
            );
        });
    }
    
    return (
        <div className="flex flex-col items-center gap-4 h-fit w-5xl bg-slateBlue shadow-2xl border-1 border-slateBlue/10 rounded-xl p-10 pb-20 mb-10">
            <div className="flex flex-row gap-4 justify-between w-1/3 px-4 my-4 mb-8">
                {getTabBar()}
            </div>
            <hr className="border-1 border-offWhite/15 w-4/5" />
            {
                tabs.courses ? getCourses() : getLearningPaths()
            }
        </div>
    );
}