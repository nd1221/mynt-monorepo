import ProgressBar from "../trendComponents/ProgressBar";
import { Link } from "react-router-dom";

export default function SidebarCourseLink({courseSummary}) {

    return (
        <Link
            className={`
                flex flex-col flex-shrink-0 
                md:h-fit w-full bg-cover bg-center 
                border-2 border-slateBlue text-offWhite rounded-md shadow-xl
                hover:opacity-75 hover:cursor-pointer
            `}
            style={{ backgroundImage: `url(${courseSummary.iconURL})`}}
            to={`/user-profile/courses/${courseSummary.id}`}
        >   
            {/* This div is purely to achieve a dark overlay */}
            <div className="inset-0 h-full w-full flex flex-col bg-black/60 justify-end p-4">
                <div className="flex flex-col gap-6 text-OffWhite font-bold text-sm">
                    <h1 className="text-OffWhite text-xl">{courseSummary.courseTitle}</h1>
                    <div>
                        <div className="flex flex-col gap-1">
                            <p className="">Completeness</p>
                            <ProgressBar 
                                progressDecimal={courseSummary.completeness}
                                containerStyle={"w-full h-3 bg-offWhite/20 border-1 border-slateBlue rounded-sm overflow-hidden"}
                                barStyle={"h-full bg-lavender rounded-r-sm"}
                            />
                        </div>
                        <div className="flex flex-col gap-1">
                            <p className="">Mastery</p>
                            <ProgressBar 
                                progressDecimal={courseSummary.mastery}
                                containerStyle={"w-full h-3 bg-offWhite/20 border-1 border-slateBlue rounded-sm overflow-hidden"}
                                barStyle={"h-full bg-teal rounded-r-sm"}
                            />
                        </div>
                    </div>
                    <div className="flex flex-row justify-between">
                        <p className="">Last activity</p>
                        <p className="">{courseSummary.lastReviewDate}</p>
                    </div>
                </div> 
            </div>
        </Link>
    );
}