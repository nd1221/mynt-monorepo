import UserStreakCard from "./UserStreakCard";
import ReviewQuickstartCard from "./ReviewQuickstartCard";

export default function RightSidebar({userStreakProps, reviewQuickstartProps}) {

    return (
        <div className="flex flex-col gap-8 lg:flex-1 h-fit max-h-screen">
            <UserStreakCard props={userStreakProps} />
            <ReviewQuickstartCard props={reviewQuickstartProps} />
        </div>
    );
}