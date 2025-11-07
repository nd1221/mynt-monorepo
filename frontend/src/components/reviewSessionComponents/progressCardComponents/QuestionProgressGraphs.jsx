import { useState } from "react";
import Graph from "../../trendComponents/Graph";

const getNavBar = (graphs, currentGraph, toggle) => {
    
    const baseClass = "font-semibold italic hover:underline hover:underline-offset-4 hover:decoration-2 hover:cursor-pointer transition duration-100";
    const activeClass = "underline underline-offset-4 decoration-2 decoration-lavender";
    
    return (
        <div className="flex flex-row w-fit h-fit gap-8">
            {graphs.map((graph, i) => (
                <div
                    onClick={() => toggle(i)} 
                    className={`${baseClass} ${i === currentGraph ? activeClass : ""}`}
                >
                    {graph}
                </div>
            ))}
        </div>
    );
}

const getGraph = (current, reviews) => {
    switch (current) {
        case "user rating": return getUserRatingGraph(reviews);
        default: return getAverageTimeGraph(reviews);
    };
}

const getUserRatingGraph = reviews => {
    return (
        <div className="flex flex-col w-full gap-4 items-center mb-6">
            <Graph data={getUserRatingHistoryData(reviews)} fillColour={"#A78BFA"} domain={[0, 5]} ticks={[0, 1, 2, 3, 4, 5]}/>
            <h3 className="text-md text-slateBlue/50">user rating history</h3>
        </div>
    );
}

const getAverageTimeGraph = reviews => {
    return (
        <div className="col-span-9 flex flex-col w-full gap-4 items-center mb-6">
            <Graph data={getAverageTimeHistoryData(reviews)} fillColour={"#1F2D3D"}/>
            <h3 className="text-md text-slateBlue/50">average time (seconds)</h3>
        </div>
    );
}

const getUserRatingHistoryData = reviewHistory => {
    return reviewHistory.map(review => {
        return {
            date: review.reviewDate,
            value: review.userRating
        };
    });
};

const getAverageTimeHistoryData = reviewHistory => {
    const map = reviewHistory.map(review => {
        return {
            date: review.reviewDate,
            value: Math.floor(review.questionTimeMillis / 1000)
        };
    });
    return map;
};

export default function QuestionProgressGraphs({reviewHistory}) {

    const reviews = reviewHistory == null ? [] : reviewHistory;

    const graphs = ["user rating", "average time"];
    const [currentGraph, setCurrentGraph] = useState(0);
    const toggleGraph = graphIndex => {
        setCurrentGraph(graphIndex);
    }

    return (
        <div className="col-span-9 flex flex-col gap-4 justify-between items-center w-full h-full bg-white p-2 py-4 rounded-lg border-1 border-slateBlue/15 shadow-md hover:shadow-xl">
            <h1 className="italic text-slateBlue/25 text-center">review history data</h1>
            {getNavBar(graphs, currentGraph, toggleGraph)}
            {getGraph(graphs[currentGraph], reviews)}
        </div>
    );
}