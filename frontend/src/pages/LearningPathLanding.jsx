import enrolledIcon from "../assets/graduation-cap.png";

import { useLoaderData } from "react-router-dom";
import api from "../api/modules.js";
import { handleError } from "../api/apiUtils.js";
import { parseDate } from "../utils/coursesUtils.js";
import ExpertCards from "../components/uiComponents/ExpertCards.jsx";
import LearningPathCoursesMenu from "../components/uiComponents/LearningPathCoursesMenu.jsx";
import backIconLavender from "../assets/back-arrow-lavender.png";
import backIconWhite from "../assets/back-arrow-white.png";
import { Link, useLocation } from "react-router-dom";
import { authorise } from "../api/authorise.js";
import { useAuthContext } from "../components/AuthContext.jsx";

const fetchCourses = async fetchedLearningPath => {
  const {courseIds, ...otherKeys} = fetchedLearningPath;
  const learningPath = {
      ...otherKeys, 
      courses: await Promise.all(
          courseIds.map(async courseId => {
              const fetchedCourse = await api.get(`/courses/${courseId}`);
              return fetchedCourse.data;
          })
      )
  }
  return learningPath;
}

const fetchLearningPathData = async learningPathId => {
  try {
      // Fetch learning path
      let learningPath = await api.get(`/learning-paths/${learningPathId}`);

      // Fetch courses
      learningPath = await fetchCourses(learningPath.data);

      return learningPath;
  } catch (err) {
      handleError(err);
  }
}

export async function loader({params}) {
  const learningPathId = params.id;

  // Authorise
  await authorise();

  return fetchLearningPathData(learningPathId);
}

const enroll = async (userId, learningPathId, updateUser) => {
  try {
    await api.patch(`/users/${userId}/add-learning-path/${learningPathId}`);
    updateUser();
  } catch(err) {
    handleError(err);
  }
}

const unEnroll = async (userId, learningPathId, updateUser) => {
  try {
    await api.patch(`/users/${userId}/remove-learning-path/${learningPathId}`);
    updateUser();
  } catch(err) {
    handleError(err);
  }
}

export default function LearningPathLanding() {

    const learningPath = useLoaderData();

    // Search param state for presrving search filters in '/paths'
    const location = useLocation();
    const filterState = location.state;

    const authContext = useAuthContext();
    const currentUser = authContext.currentUser;
    const enrolled = currentUser ? currentUser.learningPathIds.includes(learningPath.id) : false;

    return (
        <>
          <div className="flex flex-row gap-12 p-10 w-7xl justify-center">
            <div className="flex flex-col gap-4 w-1/3 items-center">
              <div className="flex flex-row gap-6 justify-center items-center">
                <img className="h-14" src={enrolledIcon} alt="Icon indicating user is enrolled on learning path" />
                <h1 className="font-bold text-3xl text-slateBlue my-3">
                  {enrolled ? "enrolled" : "join this path"}
                </h1>
              </div>
              <button
                className="
                bg-transparent w-9/10 text-tealDark/90 border-tealDark/90 border-3 p-4 text-3xl font-bold rounded-xl mb-4
                hover:bg-tealDark/90 hover:text-offWhite duration-100 active:scale-98 
                "
                onClick={() => {
                  const func = enrolled ? unEnroll : enroll; 
                  func(currentUser.userId, learningPath.id, authContext.updateCurrentUser);
                }}
              >
                {enrolled ? "unenroll" : "enroll"}
              </button>
              <Link
                to="/paths"
                state={{filterState}}
                className="
                backButton
                relative flex flex-row items-center justify-center gap-12
                bg-transparent w-9/10 text-lavenderDark/90 border-lavenderDark/90 border-3 p-4 text-3xl font-bold rounded-xl mb-10
                hover:bg-lavenderDark/90 hover:text-offWhite duration-100 active:scale-98 
                "
              >
                <img className="defaultMode absolute h-8 left-5" src={backIconLavender} alt="Back to courses button icon" />
                <img className="hoverMode absolute h-8 left-5" src={backIconWhite} alt="Back to courses button icon" />
                <p>courses</p>
              </Link>
              <img className="h-200 w-9/10 object-cover rounded-2xl rounded-bl-[150px]" src={learningPath.iconURL} alt="Course landing image" />
            </div>
            <div className="w-2/3">
              <div className="flex flex-col gap-8 w-full p-4">
                <h1 className="text-6xl text-tealDark/90 font-bold w-full pb-6 border-b-2 border-slateBlue/20">{learningPath.title}</h1>
                <p className="w-full text-2xl pb-6 border-b-2 border-slateBlue/20">{learningPath.description}</p>
                <div className="flex flex-col gap-4 pb-6 border-slateBlue/20">
                  <h1 className="font-bold text-2xl mb-4">EXPERTS</h1>
                  <div className="flex flex-row flex-wrap gap-4 mb-8">
                    <ExpertCards experts={learningPath.courses.flatMap(course => (course.creators))} />
                  </div>
                  <div className="flex flex-col gap-4 pb-6">
                    <p className="text-xl"><span className="font-bold pr-4">LEVEL</span>{learningPath.difficulty}</p>
                    <p className="text-xl"><span className="font-bold pr-4">USERS ENROLLED</span>{learningPath.numberOfEnrolledUsers}</p>
                    <p className="text-xl"><span className="font-bold pr-4">COURSES</span>{learningPath.courses.length}</p>
                    <p className="text-xl"><span className="font-bold pr-4">CREATED</span>{parseDate(learningPath.createdAt)}</p>
                    <p className="text-xl"><span className="font-bold pr-4">LAST UPDATED</span>{parseDate(learningPath.lastUpdatedAt)}</p>
                  </div>
                </div>
                <div className="flex flex-col gap-3 p-4 px-6 bg-slateBlue rounded-xl">
                  <LearningPathCoursesMenu learningPath={learningPath} />
                  <hr className="my-4 border-2 border-teal" />
                </div>
              </div>
            </div>
          </div>
        </>
    );
}

const learningPath1 = {
    "id": 1,
    "title": "CFA® Starter Path: From Fundamentals to Level I",
    "description": "This learning path is designed to guide aspiring analysts through the essential concepts needed before and during CFA Level I preparation. It starts with foundational finance and accounting, followed by more advanced CFA-specific material. Ideal for students or professionals building their investment finance expertise from the ground up.",
    "difficulty": "BEGINNER",
    "tags": [
      "portfolio management",
      "career development",
      "banking"
    ],
    "creators": [],
    "numberOfEnrolledUsers": 843,
    "iconURL": "https://images.unsplash.com/photo-1511883040705-6011fad9edfc?q=80&w=1748&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "createdAt": "2024-10-10T00:00:00",
    "lastUpdatedAt": "2025-02-25T00:00:00",
    "courses": [
      {
        "id": 1,
        "title": "CFA® Level I Masterclass: Investment Foundations",
        "description": "This comprehensive course is designed to help aspiring finance professionals prepare for the CFA Level I exam. Covering topics like ethical standards, financial reporting, and portfolio management, it blends theoretical concepts with practical insights. Ideal for those beginning their CFA journey or seeking a structured finance refresher.",
        "difficulty": "ADVANCED",
        "sectionIds": [
          1,
          2,
          3,
          4
        ],
        "tags": [
          "financial analysis",
          "portfolio management",
          "investing",
          "ethics"
        ],
        "creators": [
          "Daniel Rivera",
          "Sarah Kim"
        ],
        "numberOfEnrolledUsers": 1372,
        "iconURL": "https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?q=80&w=1740&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        "createdAt": "2024-11-02T00:00:00",
        "lastUpdatedAt": "2025-03-20T00:00:00"
      },
      {
        "id": 2,
        "title": "CFA® Level I Masterclass: Investment Foundations",
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum euismod, sem ut tincidunt viverra, turpis purus facilisis est, nec eleifend lorem lacus vel sapien. Morbi vitae tellus a turpis iaculis luctus. Curabitur at ligula ac nibh cursus vehicula. Aliquam erat volutpat. Nulla facilisi. Sed at justo ut sapien malesuada rhoncus. Cras suscipit felis a diam euismod, ac cursus nulla semper.",
        "difficulty": "INTERMEDIATE",
        "sectionIds": [],
        "tags": [
          "debt management",
          "portfolio management",
          "budgeting",
          "credit scores"
        ],
        "creators": [
          "John Doe",
          "Ava Thompson"
        ],
        "numberOfEnrolledUsers": 873,
        "iconURL": "https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        "createdAt": "2024-07-14T00:00:00",
        "lastUpdatedAt": "2025-01-09T00:00:00"
      },
      {
        "id": 3,
        "title": "Quantitative Methods for CFA® Level I: Core Concepts & Practice",
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam dignissim enim at nisi posuere, vel fermentum nunc dapibus. Pellentesque sit amet elit vel nulla facilisis consequat. Curabitur vitae metus ac nulla posuere tincidunt. In sed diam et quam scelerisque ullamcorper. Etiam id justo a lorem feugiat euismod. Suspendisse ac malesuada libero.",
        "difficulty": "BEGINNER",
        "sectionIds": [],
        "tags": [
          "banking basics",
          "accounting",
          "career development"
        ],
        "creators": [
          "Jane Patel"
        ],
        "numberOfEnrolledUsers": 1934,
        "iconURL": "https://images.unsplash.com/photo-1620266757065-5814239881fd?q=80&w=1472&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        "createdAt": "2024-06-10T00:00:00",
        "lastUpdatedAt": "2024-12-21T00:00:00"
      },
      {
        "id": 4,
        "title": "Corporate Finance Fundamentals: Decision-Making & Valuation",
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur tristique nulla at diam efficitur porta. Nam nec ligula nec metus tristique laoreet. Donec sodales augue nec sem convallis tristique. Praesent suscipit velit in dolor convallis, id lacinia magna hendrerit. Integer fringilla erat et dolor vehicula, nec luctus felis suscipit.",
        "difficulty": "ADVANCED",
        "sectionIds": [],
        "tags": [
          "cryptocurrency",
          "interest rates",
          "ethics",
          "banking",
          "tax planning"
        ],
        "creators": [
          "Emily Zhang",
          "Marcus Green"
        ],
        "numberOfEnrolledUsers": 156,
        "iconURL": "https://images.unsplash.com/photo-1549483249-f0b359d1e289?q=80&w=1548&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        "createdAt": "2024-08-03T00:00:00",
        "lastUpdatedAt": "2024-11-09T00:00:00"
      },
      {
        "id": 5,
        "title": "Financial Reporting & Analysis: Accounting for CFA® Candidates",
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer sed lacus et metus facilisis scelerisque nec eget velit. Proin lobortis sapien et nisl tincidunt dignissim. Mauris fringilla nisi non leo vestibulum, non laoreet metus porta. Sed malesuada lorem nec orci laoreet, ut sollicitudin justo fermentum.",
        "difficulty": "ADVANCED",
        "sectionIds": [],
        "tags": [
          "financial analysis",
          "insurance",
          "stock market",
          "investing"
        ],
        "creators": [
          "Carlos Rivera"
        ],
        "numberOfEnrolledUsers": 712,
        "iconURL": "https://images.unsplash.com/photo-1640161704729-cbe966a08476?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        "createdAt": "2024-05-02T00:00:00",
        "lastUpdatedAt": "2025-01-11T00:00:00"
      },
      {
        "id": 6,
        "title": "Ethics & Professional Standards: CFA® Code in Action",
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae. Sed vehicula nulla eu ligula dapibus gravida. Vivamus nec orci nulla. Aenean porta est non purus posuere blandit. Cras a dolor a augue finibus commodo.",
        "difficulty": "BEGINNER",
        "sectionIds": [],
        "tags": [
          "portfolio management",
          "savings",
          "inflation"
        ],
        "creators": [
          "John Doe"
        ],
        "numberOfEnrolledUsers": 1340,
        "iconURL": "https://images.unsplash.com/photo-1645226880663-81561dcab0ae?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        "createdAt": "2024-09-18T00:00:00",
        "lastUpdatedAt": "2024-10-25T00:00:00"
      }
    ]
  }