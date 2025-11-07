import CourseSummaryCard from "./trackerCardComponents/CourseSummaryCard";
import { useState, useEffect } from "react";
import { handleError } from "../../api/apiUtils";
import api from "../../api/modules.js";
import MasteryCompletenessCard from "./trackerCardComponents/MasteryCompletenessCard";
import MasteryCompletenessSpiderDiagram from "./trackerCardComponents/MasteryCompletenessSpiderDiagram";
import ProgressStatsContent from "./ProgressStatsContent";
import CourseReviewHistoryCard from "./trackerCardComponents/CourseReviewHistoryCard.jsx";
import CourseUnitCardLinks from "./trackerCardComponents/CourseUnitCardLinks.jsx";

const fetchMasteryCompletenessPerLesson = async (lessonProgressTrackerIds, courseProgressTrackerId) => {
    try {
        const body = {lessonProgressTrackerIds: Object.values(lessonProgressTrackerIds)};
        return await api.post(
            `/progress/course-lesson-mastery-completeness/${courseProgressTrackerId}`, 
            body
        );
    } catch(err) {
        handleError(err);
    }
};

const sortByLessonPosition = data => {
    return data.sort(
        (a, b) => {
            const x = a.lessonPosition;
            const y = b.lessonPosition;
            if (x < y) {
                return -1;
            } else if (y < x) {
                return 1;
            } else {
                return 0;
            }
        }
    );
};

const getMasteryCompletenessCard = (tracker, sectionData) => {
    
    if (tracker == null || sectionData == null) {
        return {
            completeness: 0,
            mastery: 0,
            sectionData: []
        };
    }
    
    return {
        completeness: tracker.completeness,
        mastery: tracker.mastery,
        sectionData: sectionData
    };
};

const fetchCourseQuestionReviewHistory = async courseTrackerId => {
    try {
        const reviewHistory = await api.get(`/review/courses/past-year/${courseTrackerId}`);
        return reviewHistory.data;
    } catch(err) {
        handleError(err);
    }
};

const initialiseSectionLinkFlags = sections => {
    const flags = Array(sections.length).fill(false);
    flags[0] = true;
    return flags;
}

export default function CourseCard({course, tracker}) {

    const [sectionMasteryCompleteness, setSectionMasteryCompleteness] = useState(null);
    const [reviewHistory, setReviewHistory] = useState();
    const [sectionLinkFlags, setSectionLinkFlags] = useState(null);

    useEffect(() => {

        if (!tracker?.id) {
            return;
        }

        const getData = async () => {

            const masteryCompletenessData = await fetchMasteryCompletenessPerLesson(tracker.lessonProgressTrackerIds, tracker.id);
            const sortedData = sortByLessonPosition(masteryCompletenessData.data);
            setSectionMasteryCompleteness(sortedData);
            setSectionLinkFlags(initialiseSectionLinkFlags(sortedData));

            const reviewHistory = await fetchCourseQuestionReviewHistory(tracker.id);
            setReviewHistory(reviewHistory);
        };
        getData();
    }, [tracker?.id]);

    const toggleSections = sectionPosition => {
        setSectionLinkFlags(prevFlags => prevFlags.map(
            (flag, index) => index === sectionPosition - 1 ? !flag : flag
        ));
    };

    return (
        <div className="grid grid-cols-12 gap-4 overflow-auto items-center rounded-lg bg-slateBlue/5 p-6 shadow-md border-1 border-slateBlue/6">
            <div className="col-span-12 row-span-2 flex flex-row h-fit w-full text-3xl font-bold gap-8 items-center overflow-hidden">
                <h1 className="flex-8 overflow-wrap">{course.title}</h1>
                <h1 className="flex-2 font-medium text-lg text-right px-2">Reviews due today - {tracker.questionsDueToday}</h1>
            </div>
            <MasteryCompletenessCard data={getMasteryCompletenessCard(tracker, sectionMasteryCompleteness)} />
            <CourseSummaryCard
                course={course}
                courseTrackerId={tracker.id}
            />
            <MasteryCompletenessSpiderDiagram
                courseKey={course.id}
                completeness={tracker?.completeness}
                data={sectionMasteryCompleteness}
            />
            <div className="col-span-4 row-span-5 grid grid-cols-2 grid-rows-3 gap-4 p-2 w-full h-full bg-white border-1 border-slateBlue/15 rounded-lg shadow-md hover:shadow-xl">
                <ProgressStatsContent tracker={tracker} />
            </div>
            <CourseReviewHistoryCard reviewHistory={reviewHistory} />
            {
                sectionLinkFlags == null ? 
                null : 
                <CourseUnitCardLinks
                    toggleSections={toggleSections}
                    sectionFlags={sectionLinkFlags}
                    sectionData={sectionMasteryCompleteness}
                    courseTrackerId={tracker.id}
                />
            }
        </div>
    );
}