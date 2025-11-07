import { useEffect, useState } from "react";
import Graph from "../../trendComponents/Graph";
import api from "../../../api/modules.js";
import { handleError } from "../../../api/apiUtils";

const fetchReviewHistoryData = async (lessonTrackerId, time, difficulty) => {
    try {
        const response = await api.get(`/review/lessons/${lessonTrackerId}/past-${time}-months/${difficulty}`);
        return response.data;
    } catch(err) {
        handleError(err);
    }
}

const getAverageTimeHistoryData = data => {
    const map = data.map(review => {
        return {
            date: review.reviewDate,
            value: Math.floor(review.questionTimeMillis / 1000)
        };
    });
    return map;
};

const getTabs = (params, current, onClick) => {

    const style = "font-semibold italic text-md text-slateBlue hover:text-lavender hover:cursor-pointer";
    const activeStyle = "underline underline-offset-5 decoration-2 decoration-lavender";

    return params.map((param, index) => (
        <div
            onClick={() => onClick(index)}
            className={`${style} ${index === current ? activeStyle : ""}`}
        >
            {param}
        </div>
    ))
}

const getDifficulty = difficulty => {
    switch (difficulty) {
        case "all": return 0;
        case "easy": return 1;
        case "medium": return 2;
        default: return 3;
    };
}

const getTimeSpan = timeSpan => {
    switch (timeSpan) {
        case "past year": return 12;
        case "past 6 months": return 6;
        default: return 1;
    };
}

export default function UnitAverageQuestionTimeCard({trackerId}) {

    const timeParams = ["past year", "past 6 months", "past month"];
    const difficultyParams = ["all", "easy", "medium", "hard"];

    const [reviewHistory, setReviewHistory] = useState(null);
    const [currentTimeParam, setCurrentTimeParam] = useState(0);
    const [currentDifficultyParam, setCurrentDifficultyParam] = useState(0);
    
    // Fetch review history data
    useEffect(() => {
        const getData = async trackerId => {
            const time = getTimeSpan(timeParams[currentTimeParam]);
            const difficulty = getDifficulty(difficultyParams[currentDifficultyParam]);
            const reviewHistory = await fetchReviewHistoryData(trackerId, time, difficulty);
            setReviewHistory(reviewHistory);
        }
        getData(trackerId);
    }, [currentDifficultyParam, currentTimeParam]);

    const toggleDifficulty = index => {
        setCurrentDifficultyParam(index);
    }

    const toggleTime = index => {
        setCurrentTimeParam(index);
    }

    return (
        <div className="col-span-5 row-span-4 flex flex-col gap-2 items-center w-full h-full p-4 bg-white border-1 border-slateBlue/15 rounded-lg shadow-md hover:shadow-xl">
            <h1 className="font-bold text-lg italic text-slateBlue/35">Average Question Time History</h1>
            <div className="flex flex-row justify-between gap-4 w-5/7 h-fit">
                {getTabs(difficultyParams, currentDifficultyParam, toggleDifficulty)}
            </div>
            {
                reviewHistory == null ?
                null :
                <Graph data={getAverageTimeHistoryData(reviewHistory)} fillColour={"1F2D3D"} />
            }
            <div className="flex flex-row justify-between gap-4 w-4/5 h-fit">
                {getTabs(timeParams, currentTimeParam, toggleTime)}
            </div>
        </div>
    );
}