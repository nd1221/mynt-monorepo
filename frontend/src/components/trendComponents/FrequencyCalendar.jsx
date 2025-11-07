import { ResponsiveCalendar } from "@nivo/calendar";

const reviewsNotPresent = data => {
    return data == null || Object.keys(data).length === 0;
}

const getCalenderData = data => {
    
    // No reviews present
    if (reviewsNotPresent(data)) {
        return [];
    }
    
    return data.map(review => {
        const histogram = {};
        const day = review.reviewDate;
        histogram[day] = (histogram[day] || 0) + 1;
        return {
            "value": histogram[day],
            "day": day
        };
    });
}

const getYearStartDate = () => {
    const today = new Date();
    return new Date(today.getFullYear(), 0, 1);
}

const getFromDate = data => {
    // Data is ordered by ascending review date by the backend
    if (reviewsNotPresent(data)) {
        return getYearStartDate();
    }
    return data[0].day;
};

const getToDate = () => {
    return new Date().toISOString().split("T")[0];
};

const yearsShown = (data) => {
    const from = new Date(getFromDate(data));
    const today = new Date();
    const yearDifference = today.getFullYear() - from.getFullYear();
    return yearDifference + 1;
};

const getContainerClass = data => {
    const height = yearsShown(data) * 50;
    return `flex flex-col items-center justify-center w-full h-${height}`;
}

export default function FrequencyCalendar({data}) {

    const calendarData = getCalenderData(data);

    return ( 
        <div className={getContainerClass(calendarData)}>
            <div className="w-full h-full">
                <ResponsiveCalendar
                    data={calendarData}
                    from={reviewsNotPresent() ? getYearStartDate() : getFromDate(calendarData)}
                    to={getToDate()}
                    emptyColor="#eeeeee"
                    margin={{ top: 5, right: 5, bottom: 5, left: 5 }}
                    yearSpacing={40}
                    monthBorderColor="#ffffff"
                    dayBorderWidth={2}
                    dayBorderColor="#ffffff"
                    colors={["#1F2D3D", "#A78BFA"]} // Nivo automatically maps values to this colors array, this is why the boolean values are transformed to 0 - 1
                    legends={[
                        {
                            anchor: 'bottom-right',
                            direction: 'row',
                            translateY: 36,
                            itemCount: 4,
                            itemWidth: 42,
                            itemHeight: 36,
                            itemsSpacing: 14,
                            itemDirection: 'right-to-left'
                        }
                    ]}
                    tooltip={({ day, value, color }) => (
                        <div
                            style={{
                                background: "white",
                                padding: "10px",
                                border: "1px solid rgba(31, 45, 61, 0.25)",
                                borderRadius: "4px",
                                fontSize: "16px",
                                color: "#1F2D3D"
                            }}
                        >
                            <strong>
                                <span style={{ color }}>{day}</span>
                            </strong>
                            <br/>
                            {value} attempts
                        </div>
                    )}
                />
            </div>
        </div>
    );
}