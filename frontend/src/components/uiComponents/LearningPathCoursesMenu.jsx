import { parseDate } from "../../utils/coursesUtils";
import { Link } from "react-router-dom";

export default function LearningPathCoursesMenu(props) {

    const {learningPath} = props;

    const getFullCourseMenu = () => {
        return (
            <>
                {
                    learningPath.courses.map((course, index) => (
                        <Link to={`/courses/${course.id}`}>
                            <div className="flex flex-col gap-4 mb-3 bg-slateBlue rounded-xl hover:opacity-75 hover:cursor-pointer">
                                <hr className="mt-4 mb-2 border-2 border-teal" />
                                <h1 className="text-2xl font-bold text-offWhite mb-4">{`${index + 1} - ${course.title}`}</h1>
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
                    ))
                }
            </>
        );
    }

    const getEmptyCourseMenu = () => {
        return (
            <>
                <div className="flex flex-col gap-4 justify-center items-center">
                    <hr className="mt-4 mb-2 border-2 border-teal w-full" />
                    <h1 className="text-2xl font-bold text-offWhite mb-4">Courses Coming Soon</h1>
                </div>
            </>
        );
    }

    return (
        <>
        {
            learningPath.courses.length ? getFullCourseMenu() : getEmptyCourseMenu()
        }
        </>
    );
}