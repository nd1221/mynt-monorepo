import { ResponsiveCalendar } from "@nivo/calendar";

export default function ReviewCalendar({data, getCalendarData, getCalendarLabel, colours, showTooltip = true}) {

    const calendarData = getCalendarData(data);

    const getFromDate = data => {
        // Data is ordered by ascending review date by the backend
        return data[0].day;
    };

    const getToDate = () => {
        return new Date().toISOString().split("T")[0];
    };

    const yearsShown = () => {
        const from = new Date(getFromDate(calendarData));
        const today = new Date();
        const yearDifference = today.getFullYear() - from.getFullYear();
        return yearDifference + 1;
    };

	const getContainerClass = () => {
		const height = yearsShown() * 50;
		return `flex flex-col items-center justify-center w-full h-${height} mt-4`;
	}

    return ( 
		<div className={getContainerClass()}>
			<div className="w-full h-full">
				<ResponsiveCalendar
					data={calendarData}
					from={getFromDate(calendarData)}
					to={getToDate()}
					emptyColor="#eeeeee"
					margin={{ top: 5, right: 5, bottom: 5, left: 5 }}
					yearSpacing={40}
					monthBorderColor="#ffffff"
					dayBorderWidth={2}
					dayBorderColor="#ffffff"
					colors={colours} // Nivo automatically maps values to this colors array based on data.value field, this is why data.value is mapped to integer in the getCalendarData function passed in
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
					tooltip={({ day, value, color }) => {
						if (showTooltip) {
							return (
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
										<span style={{ color }}>{getCalendarLabel(value)}</span>
									</strong>
									<br/>
									{day}
								</div>
							);
						} else {
							return null;
						}
					}}
				/>
			</div>
			<h3 className="text-md text-slateBlue/50">previous attempts</h3>
		</div>
	);
}