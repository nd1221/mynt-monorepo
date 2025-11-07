import { useState, useEffect } from "react";
import api from "../../api/modules.js";
import { handleError } from "../../api/apiUtils";
import QuestionAccuracyPieChart from "./progressCardComponents/QuestionAccuracyPieChart.jsx";
import QuestionProgressStatsCard from "./progressCardComponents/QuestionProgressStatsCard.jsx";
import QuestionReviewHistoryFrequencyCalendar from "./progressCardComponents/QuestionReviewHistoryFrequencyCalendar.jsx";
import QuestionProgressGraphs from "./progressCardComponents/QuestionProgressGraphs.jsx";

const getReviews = async qptId => {
    try {
        const reviews = await api.get(`/review/question/past-year/${qptId}`);
        return reviews.data;
    } catch(err) {
        handleError(err);
    }
};

export default function QuestionProgressCard({data, reviewDependency}) {

    const {aqt, qpt, question} = data;

    const [reviewHistory, setReviewHistory] = useState([]);
    useEffect(() => {
        const fetchData = async () => {
            const reviews = await getReviews(qpt.id);
            setReviewHistory(reviews);
        };
        if (qpt) {
            fetchData();
        }
    }, [reviewDependency]);

    return (
        <div className="row-span-3 grid grid-cols-9 overflow-auto gap-4 rounded-lg bg-slateBlue/5 p-4 shadow-md border-1 border-slateBlue/6 hover:shadow-xl">
            <QuestionAccuracyPieChart qpt={qpt} />
            <QuestionProgressStatsCard qpt={qpt} />
            <QuestionReviewHistoryFrequencyCalendar reviewHistory={reviewHistory} />
            <QuestionProgressGraphs reviewHistory={reviewHistory} />
        </div>
    );
}