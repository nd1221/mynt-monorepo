export function formatAverageTime(millis) {

    if (millis < 1000) {
        return "0.0s";
    }

    const seconds = Math.floor(millis / 1000);
    const minutes = Math.floor(seconds / 60);
    const secs = seconds % 60;
    const mins = minutes % 60;
    const hrs = Math.floor(minutes / 60);
    return (hrs > 0 ? `${hrs}h` : "") + " " + (mins > 0 ? `${mins}m` : "") + " " + (secs > 0 ? `${secs}s` : "");
};

export function formatAverageTimeSeconds(seconds) {

    if (seconds === 0) {
        return "0.0s";
    }

    const minutes = Math.floor(seconds / 60);
    const secs = seconds % 60;
    const mins = minutes % 60;
    const hrs = Math.floor(minutes / 60);
    return (hrs > 0 ? `${hrs}h` : "") + " " + (mins > 0 ? `${mins}m` : "") + " " + (secs > 0 ? `${secs}s` : "");
};


export function formatDate(date) {
    return new Date(date).toLocaleDateString("en-GB", {
        year: "numeric",
        month: "short",
        day: "2-digit"
    });
};

export function formatDecimal(float, places) {
    return Number.parseFloat(float).toFixed(places);
}

export function calculateCorrectQuestions(totalQuestions, accuracy) {
    return Math.floor(totalQuestions * accuracy);
}

export function formatPercentage(decimal) {
    return Math.floor(decimal * 100) + "%";
}

export function decimalToPercent(decimal) {
    return Math.floor(decimal * 100);
}

export function getAggregateSectionStats(data, key) {
    let total = 0;
    data.lessonDTOs.forEach(dto => {
        total += dto[key];
    });
    return (total / data.lessonDTOs.length);
}

export function reviewsNotPresent(data) {
    return data == null || Object.keys(data).length === 0;
}