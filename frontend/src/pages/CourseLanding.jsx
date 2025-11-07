import enrolledIcon from "../assets/graduation-cap.png";

import { useLoaderData } from "react-router-dom";
import { useState, useEffect } from "react";
import api from "../api/modules.js";
import { handleError } from "../api/apiUtils.js";
import CourseSectionsMenu from "../components/uiComponents/CourseSectionsMenu.jsx";
import { parseDate } from "../utils/coursesUtils.js";
import ExpertCards from "../components/uiComponents/ExpertCards.jsx";
import { Link, useLocation } from "react-router-dom";
import { authorise } from "../api/authorise.js";
import { useAuthContext } from "../components/AuthContext.jsx";
import { fetchCourseData } from "../utils/fetchCourseDataUtils.js";

export async function loader({params}) {
  const courseId = params.id;
  // Authorise
  await authorise();
  return fetchCourseData(courseId);
}

const enroll = async (userId, courseId, updateUser) => {
  try {
    await api.patch(`/users/${userId}/add-course/${courseId}`);
    updateUser();
  } catch(err) {
    handleError(err);
  }
}

const unEnroll = async (userId, courseId, updateUser) => {
  try {
    await api.patch(`/users/${userId}/remove-course/${courseId}`);
    updateUser();
  } catch(err) {
    handleError(err);
  }
}

export default function CourseLanding() {
  
	const course = useLoaderData();
	const [sectionToggleState, setSectionToggleState] = useState(new Array(course.sections.length).fill(false));
	const [courseProgressTracker, setCourseProgressTracker] = useState(null);

	const authContext = useAuthContext();
	const currentUser = authContext.currentUser;
	const enrolled = currentUser ? currentUser.courseIds.includes(course.id) : false;

	// Search param state for preserving search filters in '/paths'
	const location = useLocation();
	const filterState = location.state;

  // Get or create a course progress tracker for the user if they are enrolled
	useEffect(() => {
		const getCourseProgressTracker = async courseId => {
			if (enrolled && courseProgressTracker == null) {
        try {
          const response = await api.get(`/progress/course-tracker-${currentUser.userProgressTrackerId}/${courseId}`);
          setCourseProgressTracker(response.data);
        } catch(err) {
          handleError(err);
        }
      }
		};
		getCourseProgressTracker(course.id);
	}, [enrolled]);

	const calculateNumberOfModules = sections => {
		let count = 0;
		sections.forEach(section => {
			count += section.lessons.length;
		});
		return count;
	};

	const toggleSection = index => {
		setSectionToggleState(prevState => 
			prevState.map((bool, i) => (
				index == i ? !bool : bool
			))
		);
	};

  const toggleAllSections = bool => {
    setSectionToggleState(new Array(course.sections.length).fill(bool));
  };

  const getLessonMetadata = (section, lessonPosition) => {
    return {
      courseId: section.courseId,
      sectionId: section.id,
      sectionTitle: section.title,
      currentLessonPosition: lessonPosition,
      lessons: section.lessons,
      courseProgressTrackerId: courseProgressTracker?.id,
      testId: section.testId
    };
  };

  const getRequirements = () => {
    if (course.requirements.length === 0) {
      return (<li>none</li>);
    } else {
      return course.requirements.map(requirement => (
        <li>{requirement}</li>
      ));
    }
  };

  const getObjectives = () => {
    return course.objectives.map(objective => (
      <div className="flex flex-row gap-4 items-start">
        <div className="w-fit h-fit">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
            <path stroke-linecap="round" stroke-linejoin="round" d="M9 12.75 11.25 15 15 9.75M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
          </svg>
        </div>
        <p className="text-lg">
          {objective}
        </p>
      </div>
    ));
  };

    return (
        <>
          <div className="flex flex-row gap-12 p-10 w-7xl justify-center">

            <div className="flex flex-col gap-2 w-1/3 items-center">
              <div className="flex flex-row gap-6 justify-center items-center mt-6">
                <img className="h-14 mb-2" src={enrolledIcon} alt="Icon indicating user is enrolled in course" />
                <h1 className="font-bold text-2xl text-slateBlue my-3">
                  {enrolled ? "enrolled" : "join this course"}
                </h1>
              </div>

              <button
                className="
                relative flex flex-row items-center justify-center bg-transparent w-9/10 text-lavender border-lavender border-3 p-3 text-xl font-bold rounded-md mb-2
                hover:bg-lavender hover:text-offWhite duration-100 active:scale-98 
                "
                onClick={() => {
                  const func = enrolled ? unEnroll : enroll;
                  func(currentUser.userId, course.id, authContext.updateCurrentUser)
                }}
              >
                <div className="flex flex-col h-fit w-fit absolute left-5">
                  {
                    enrolled ?
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-7">
                      <path d="M10.375 2.25a4.125 4.125 0 1 0 0 8.25 4.125 4.125 0 0 0 0-8.25ZM10.375 12a7.125 7.125 0 0 0-7.124 7.247.75.75 0 0 0 .363.63 13.067 13.067 0 0 0 6.761 1.873c2.472 0 4.786-.684 6.76-1.873a.75.75 0 0 0 .364-.63l.001-.12v-.002A7.125 7.125 0 0 0 10.375 12ZM16 9.75a.75.75 0 0 0 0 1.5h6a.75.75 0 0 0 0-1.5h-6Z" />
                    </svg>
                    :
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-7">
                      <path d="M5.25 6.375a4.125 4.125 0 1 1 8.25 0 4.125 4.125 0 0 1-8.25 0ZM2.25 19.125a7.125 7.125 0 0 1 14.25 0v.003l-.001.119a.75.75 0 0 1-.363.63 13.067 13.067 0 0 1-6.761 1.873c-2.472 0-4.786-.684-6.76-1.873a.75.75 0 0 1-.364-.63l-.001-.122ZM18.75 7.5a.75.75 0 0 0-1.5 0v2.25H15a.75.75 0 0 0 0 1.5h2.25v2.25a.75.75 0 0 0 1.5 0v-2.25H21a.75.75 0 0 0 0-1.5h-2.25V7.5Z" />
                    </svg>
                  }
                </div>
                <p>{enrolled ? "unenroll" : "enroll"}</p>
              </button>

              <Link
                to="/user-profile/courses"
                state={{filterState}}
                className="
                backButton
                relative flex flex-row items-center justify-center
                bg-transparent w-9/10 text-slateBlue border-slateBlue border-3 p-3 text-xl font-bold rounded-md mb-2
                hover:bg-slateBlue hover:text-offWhite duration-100 active:scale-98 
                "
              >
                <div className="flex flex-col h-fit w-fit absolute left-5">
                  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                    <path fill-rule="evenodd" d="M1.5 7.125c0-1.036.84-1.875 1.875-1.875h6c1.036 0 1.875.84 1.875 1.875v3.75c0 1.036-.84 1.875-1.875 1.875h-6A1.875 1.875 0 0 1 1.5 10.875v-3.75Zm12 1.5c0-1.036.84-1.875 1.875-1.875h5.25c1.035 0 1.875.84 1.875 1.875v8.25c0 1.035-.84 1.875-1.875 1.875h-5.25a1.875 1.875 0 0 1-1.875-1.875v-8.25ZM3 16.125c0-1.036.84-1.875 1.875-1.875h5.25c1.036 0 1.875.84 1.875 1.875v2.25c0 1.035-.84 1.875-1.875 1.875h-5.25A1.875 1.875 0 0 1 3 18.375v-2.25Z" clip-rule="evenodd" />
                  </svg>
                </div>
                <p>dashboard</p>
              </Link>

              <Link
                to="/paths"
                state={{filterState}}
                className="
                backButton
                relative flex flex-row items-center justify-center
                bg-transparent w-9/10 text-tealDark/90 border-tealDark/90 border-3 p-3 text-xl font-bold rounded-md mb-10
                hover:bg-tealDark/90 hover:text-offWhite duration-100 active:scale-98 
                "
              >
                <div className="flex flex-col h-fit w-fit absolute left-5">
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2.5" stroke="currentColor" class="size-8">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
                  </svg>
                </div>
                <p>all courses</p>
              </Link>

              <img className="h-200 w-9/10 object-cover rounded-2xl rounded-bl-[150px]" src={course.iconURL} alt="Course landing image" />
            </div>

            <div className="w-2/3">
              <div className="flex flex-col gap-8 w-full p-4">

                <h1 className="text-5xl text-lavender font-bold w-full pb-6 border-b-2 border-slateBlue/20">{course.title}</h1>

                <p className="w-full text-xl pb-6 border-b-2 border-slateBlue/20">{course.description}</p>

                <div className="flex flex-col gap-4 pb-6 border-slateBlue/20">
                  
                  <h1 className="font-bold text-2xl mb-4">EXPERTS</h1>
                  <div className="flex flex-row flex-wrap gap-4 mb-8">
                    <ExpertCards experts={course.creators} />
                  </div>

                  <div className="flex flex-col gap-4 pb-6 mb-4">
                    <div className="flex flex-row gap-6 item-center">
                      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-6">
                        <path d="M18.375 2.25c-1.035 0-1.875.84-1.875 1.875v15.75c0 1.035.84 1.875 1.875 1.875h.75c1.035 0 1.875-.84 1.875-1.875V4.125c0-1.036-.84-1.875-1.875-1.875h-.75ZM9.75 8.625c0-1.036.84-1.875 1.875-1.875h.75c1.036 0 1.875.84 1.875 1.875v11.25c0 1.035-.84 1.875-1.875 1.875h-.75a1.875 1.875 0 0 1-1.875-1.875V8.625ZM3 13.125c0-1.036.84-1.875 1.875-1.875h.75c1.036 0 1.875.84 1.875 1.875v6.75c0 1.035-.84 1.875-1.875 1.875h-.75A1.875 1.875 0 0 1 3 19.875v-6.75Z" />
                      </svg>
                      <p className="text-lg"><span className="font-bold pr-4">LEVEL</span>{course.difficulty}</p>
                    </div>	
                      <div className="flex flex-row gap-6 items-center">
                          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                        <path d="M4.5 6.375a4.125 4.125 0 1 1 8.25 0 4.125 4.125 0 0 1-8.25 0ZM14.25 8.625a3.375 3.375 0 1 1 6.75 0 3.375 3.375 0 0 1-6.75 0ZM1.5 19.125a7.125 7.125 0 0 1 14.25 0v.003l-.001.119a.75.75 0 0 1-.363.63 13.067 13.067 0 0 1-6.761 1.873c-2.472 0-4.786-.684-6.76-1.873a.75.75 0 0 1-.364-.63l-.001-.122ZM17.25 19.128l-.001.144a2.25 2.25 0 0 1-.233.96 10.088 10.088 0 0 0 5.06-1.01.75.75 0 0 0 .42-.643 4.875 4.875 0 0 0-6.957-4.611 8.586 8.586 0 0 1 1.71 5.157v.003Z" />
                      </svg>
                      <p className="text-lg"><span className="font-bold pr-4">USERS ENROLLED</span>{course.numberOfEnrolledUsers}</p>
                    </div>
                    <div className="flex flex-row gap-6 item-center">
                      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 14.25v-2.625a3.375 3.375 0 0 0-3.375-3.375h-1.5A1.125 1.125 0 0 1 13.5 7.125v-1.5a3.375 3.375 0 0 0-3.375-3.375H8.25m0 12.75h7.5m-7.5 3H12M10.5 2.25H5.625c-.621 0-1.125.504-1.125 1.125v17.25c0 .621.504 1.125 1.125 1.125h12.75c.621 0 1.125-.504 1.125-1.125V11.25a9 9 0 0 0-9-9Z" />
                      </svg>
                      <p className="text-lg"><span className="font-bold pr-4">UNITS</span>{calculateNumberOfModules(course.sections)}</p>
                    </div>
                    <div className="flex flex-row gap-6 item-center">
                      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                        <path d="M12.75 12.75a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0ZM7.5 15.75a.75.75 0 1 0 0-1.5.75.75 0 0 0 0 1.5ZM8.25 17.25a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0ZM9.75 15.75a.75.75 0 1 0 0-1.5.75.75 0 0 0 0 1.5ZM10.5 17.25a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0ZM12 15.75a.75.75 0 1 0 0-1.5.75.75 0 0 0 0 1.5ZM12.75 17.25a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0ZM14.25 15.75a.75.75 0 1 0 0-1.5.75.75 0 0 0 0 1.5ZM15 17.25a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0ZM16.5 15.75a.75.75 0 1 0 0-1.5.75.75 0 0 0 0 1.5ZM15 12.75a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0ZM16.5 13.5a.75.75 0 1 0 0-1.5.75.75 0 0 0 0 1.5Z" />
                        <path fill-rule="evenodd" d="M6.75 2.25A.75.75 0 0 1 7.5 3v1.5h9V3A.75.75 0 0 1 18 3v1.5h.75a3 3 0 0 1 3 3v11.25a3 3 0 0 1-3 3H5.25a3 3 0 0 1-3-3V7.5a3 3 0 0 1 3-3H6V3a.75.75 0 0 1 .75-.75Zm13.5 9a1.5 1.5 0 0 0-1.5-1.5H5.25a1.5 1.5 0 0 0-1.5 1.5v7.5a1.5 1.5 0 0 0 1.5 1.5h13.5a1.5 1.5 0 0 0 1.5-1.5v-7.5Z" clip-rule="evenodd" />
                      </svg>
                      <p className="text-lg"><span className="font-bold pr-4">CREATED</span>{parseDate(course.createdAt)}</p>
                    </div>	
                    <div className="flex flex-row gap-6 item-center">
                      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                        <path d="M21.731 2.269a2.625 2.625 0 0 0-3.712 0l-1.157 1.157 3.712 3.712 1.157-1.157a2.625 2.625 0 0 0 0-3.712ZM19.513 8.199l-3.712-3.712-8.4 8.4a5.25 5.25 0 0 0-1.32 2.214l-.8 2.685a.75.75 0 0 0 .933.933l2.685-.8a5.25 5.25 0 0 0 2.214-1.32l8.4-8.4Z" />
                        <path d="M5.25 5.25a3 3 0 0 0-3 3v10.5a3 3 0 0 0 3 3h10.5a3 3 0 0 0 3-3V13.5a.75.75 0 0 0-1.5 0v5.25a1.5 1.5 0 0 1-1.5 1.5H5.25a1.5 1.5 0 0 1-1.5-1.5V8.25a1.5 1.5 0 0 1 1.5-1.5h5.25a.75.75 0 0 0 0-1.5H5.25Z" />
                      </svg>
                      <p className="text-lg"><span className="font-bold pr-4">LAST UPDATED</span>{parseDate(course.lastUpdatedAt)}</p>
                    </div>
                  </div>

                  <div className="flex flex-col gap-2 w-full h-fit">
                    <h1 className="font-bold text-2xl">REQUIREMENTS</h1>
                    <ul class="list-disc flex flex-col gap-2 text-lg p-4 px-5">
                      {getRequirements()}
                    </ul>
                  </div>

                  {
                    course.objectives.length > 0 ?
                      <div className="flex flex-col gap-8 w-full h-fit bg-white border-1 border-slateBlue/10 shadow-md rounded-lg my-4 p-8">
                        <h1 className="font-bold text-2xl">What you'll learn</h1>
                        <div className="grid grid-cols-2 gap-8">
                          {getObjectives()}
                        </div>
                      </div>
                    : null
                  }
                </div>
                {
                  courseProgressTracker ?
                    <CourseSectionsMenu 
                      courseId={course.id} 
                      courseTrackerId={courseProgressTracker.id} 
                      sections={course.sections} 
                      sectionFlags={sectionToggleState} 
                      toggleSection={toggleSection} 
                      toggleAllSections={toggleAllSections} 
                      getLessonMetadata={getLessonMetadata} 
                      enrolled={enrolled} 
                      courseProgressTracker={courseProgressTracker} 
                    />
                  : null
                }
              </div>
            </div>
          </div>
        </>
    )
}