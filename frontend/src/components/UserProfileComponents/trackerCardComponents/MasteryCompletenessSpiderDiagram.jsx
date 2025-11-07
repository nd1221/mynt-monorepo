import { Radar } from "react-chartjs-2";
import "chart.js/auto";
import { useState } from "react";
import { formatPercentage, getAggregateSectionStats } from "../../../utils/progressTrackingUtils.js";

const getLabels = data => {
    return data.map(entry => (
        "Section " + entry.sectionPosition
    ));
}

const getDataset = (data, tab) => {
    return ({
        label: tab,
        data: data.map(entry => getAggregateSectionStats(entry, tab)),
        backgroundColor: tab === "mastery" ? "rgba(42, 186, 167, 0.5)" : "rgba(167, 139, 250, 0.5)",
        borderColor: tab === "mastery" ? "rgb(42, 186, 167)" : "rgb(167, 139, 250)",
        pointBackgroundColor: "rgb(0, 0, 0)",
        pointBorderColor: "#fff",
    });
}

const getRadarChartData = (data, tab) => {
    return ({
        labels: getLabels(data),
        datasets: data.length === 0 ? [] : [getDataset(data, tab)],
    });
}

const getOptions = tab => {
    return ({
        scales: {
            r: {
                angleLines: {display: true},
                suggestedMin: 0,
                suggestedMax: 1,
                ticks: {
                    stepSize: 0.2,
                    callback: function (stepDecimal) { return formatPercentage(stepDecimal); },
                },
            },
        },
        plugins: {
            legend: {
                display: false,
            },
            tooltip: {
                enabled: true,
                padding: 4,
                titleFont: {
                    size: 16,
                    weight: "bold",
                },
                bodyFont: {
                    size: 14,
                },
                callbacks: {
                    label: tooltip => {
                        const value = tooltip.formattedValue;
                        return `${tab} - ${formatPercentage(value)}`;
                    },
                    labelColor: () => {
                        return {
                            backgroundColor: tab === "mastery" ? "rgb(42, 186, 167)" : "rgb(167, 139, 250)",
                        };
                    },
                },
            },
        },
    });
}

const getTab = (tab, toggle, currentTab) => {
    
    const base = `text-xl font-bold hover:cursor-pointer hover:underline hover:underline-offset-8 hover:decoration-3 ${tab === "mastery" ? "decoration-teal" : "decoration-lavender"}`;
    const active = "underline underline-offset-8 decoration-3";

    return (
        <div
            className={`${base} ${tab === currentTab ? active : ""}`}
            onClick={() => toggle(tab)}
        >
            {tab}
        </div>
    );
}

export default function MasteryCompletenessSpiderDiagram({courseKey, completeness, data}) {

    const [tab, setTab] = useState("completeness");
    const toggleTab = tab => {
        setTab(tab);
    };

    // const masteryLocked = !completeness || completeness !== 1.0;

    return (
        <div className="col-span-5 row-span-8 flex flex-col items-center w-full h-full gap-4 p-4 bg-white border-1 border-slateBlue/15 rounded-lg shadow-md hover:shadow-xl">
            <div className="flex flex-row w-3/5 h-fit my-2 mb-8 justify-between">
                {getTab("completeness", toggleTab, tab)}
                {getTab("mastery", toggleTab, tab)}
            </div>
            {data == null? null : <Radar key={courseKey} data={getRadarChartData(data, tab)} options={getOptions(tab)} />}
        </div>
    );
}