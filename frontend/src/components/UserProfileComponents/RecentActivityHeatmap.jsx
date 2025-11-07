import dayjs from "dayjs";

const getEmptyArray = () => {
    
    const endDate = new Date();
    const startDate = new Date();
    startDate.setDate(endDate.getDate() - 29);
    const emptyData = [];

    let day = startDate;
    while (day <= endDate) {
        emptyData.push({
            reviewDate: day.toISOString().split("T")[0],
            questionsReviewed: 0,
        });
        day.setDate(day.getDate() + 1);
    }

    return emptyData;
}

export default function RecentActivityHeatmap({data}) {

    const dayjsEndOfWeek = 6;

    const reversedArray = !data ? getEmptyArray() : data.toReversed();
    const startDate = dayjs(reversedArray[0]?.reviewDate, "YYYY-MM-DD");
    const endDate = dayjs(reversedArray[reversedArray.length - 1]?.reviewDate, "YYYY-MM-DD");

    const startPadding = new Array(startDate.day());
    const endPadding = new Array(dayjsEndOfWeek - endDate.day());

    const paddedArray = [
        ...startPadding.fill(null),
        ...reversedArray,
        ...endPadding.fill(null)
    ];

    const getCalendarLabels = () => {
        return ['S', 'M', 'T', 'W', 'T', 'F', 'S'].map(day => (
            <div className="flex flex-col items-center justify-center h-5 w-5 bg-transparent text-xs text-slateBlue/30">
                {day}
            </div>
        ));
    }

    const getCalendarSquares = array => {
        
        return array.map(date => {
            
            if (date !== null) {
                
                const dateContainsReviews = date.questionsReviewed > 0;
                const colour = dateContainsReviews ? "bg-lavender" : "bg-lavender/30";
                const hoverStyle = dateContainsReviews ? "hover:bg-lavenderDark" : "hover:bg-lavender/50";
                
                return (
                    <div className="relative group">
                        <div className={`h-5 w-5 ${colour} border-none rounded-xs transition ${hoverStyle}`}></div>
                        {
                            <div className="absolute justify-center w-40 h-fit p-2 bg-slateBlue rounded-sm shadow-lg border-none top-8 right-0 transition hidden group-hover:block z-50">
                                <p className="text-sm text-offWhite font-bold">{dayjs(date.reviewDate, "YYYY-MM-DD").format("D MMM, YYYY")}</p>
                                {
                                    dateContainsReviews ? 
                                    <p className="text-sm text-offWhite">{date.questionsReviewed} {date.questionsReviewed > 1 ? "questions" : "question"} reviewed</p>
                                    : null
                                }
                            </div>
                        }
                    </div>
                );
            } else {
                return (
                    <div className="h-5 w-5 bg-transparent"></div>
                );
            }
        });
    }

    return (
        <div className="flex flex-col gap-3 w-fit h-fit">
            <div className="grid grid-cols-7 gap-1.5">
                {getCalendarLabels()}
            </div>
            <div className="grid grid-cols-7 gap-1.5 overflow-visible">
                {getCalendarSquares(paddedArray)}
            </div>
        </div>
    );
}