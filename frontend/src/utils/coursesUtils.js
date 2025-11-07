export function initialFilterBar() {
    return [
        {
            id: 0,
            on: true,
            category: "Tags",
            options: []
        },
        {
            id: 1,
            on: false,
            category: "Levels",
            options: [{key: "beginner", checked: false}, {key: "intermediate", checked: false}, {key: "advanced", checked: false}]
        },
        {
            id: 2,
            on: false,
            category: "From",
            options: [{key: "past 6 months", checked: false}, {key: "past year", checked: false}, {key: "past 2 years", checked: false}, {key: "anytime", checked: true}]
        },
    ];
}

export function getDateFrom(dateOption) {
    
    if (dateOption === undefined) {
        return "";
    }
    
    const currentTime = new Date();
    
    if (dateOption.key === "past 6 months") {
        currentTime.setMonth(currentTime.getMonth() - 6);
    } else if (dateOption.key === "past year") {
        currentTime.setFullYear(currentTime.getFullYear() - 1);
    } else if (dateOption.key === "anytime") {
        currentTime.setFullYear(currentTime.getFullYear() - 2);
    } else {
        return "";
    }
    
    return currentTime.toISOString();
}

export function getTypeDescription(type) {
        
    switch (type) {
        case "learningPath": return `
        A Learning Path is a guided journey designed to help you build skills or knowledge in a structured and progressive way. 
        Each path is focused on a specific topic or career goal and is made up of several Courses that you complete in sequence.
        `;
        case "course": return `
        A Course is a focused set of content that dives into a specific subject or skill area. 
        Each course is designed to be self-contained and provides everything you need to understand that topic.
        Courses are made up of Units, which break down the content into manageable pieces, making it easier to learn step by step.
        `;
    }
}

export function parseDate(iso) {
    const date = new Date(iso);
    const formattedDate = date.toLocaleDateString("en-GB", {day: "numeric", month: "long", year:"numeric"});
    return formattedDate;
}

export const formatDuration = duration => {
    const hours = Math.floor(duration / 60);
    const mins = duration % 60;
    return `${hours === 0 ? "" : `${hours}h`} ${mins}m`;
};

export const getTotalUnits = sections => {
    let totalUnits = 0;
    sections.forEach(section => {
        totalUnits += section.lessons.length;
    });
    return totalUnits;
};

export const getTotalDuration = sections => {
    let totalDuration = 0;
    sections.forEach(section => {
        totalDuration += section.duration;
    });
    return formatDuration(totalDuration);
};