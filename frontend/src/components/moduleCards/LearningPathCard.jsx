import play from "../../assets/play-purple.png";
import userIcon from "../../assets/user-icon.png";

import {Link} from "react-router-dom";

export default function LearningPathCard(props) {

    const {data, userEnrolled, getFilterState} = props;

    let tagLabels = [];
    for (let i = 0; i < data.tags.length; i++) {
        if (i < 3) {
            tagLabels.push(<div key={i} className="p-2 items-center bg-teal text-offWhite rounded-md">{data.tags[i]}</div>);
        } else if (i === 3) {
            tagLabels.push(<div key={i} className="p-2 items-center bg-teal text-offWhite rounded-md">{`+ ${data.tags.length - 3} more`}</div>);
        } else {
            break;
        }
    }

    return (
        <div className="relative flex flex-col gap-2 bg-offWhite rounded-3xl border-teal border-4 p-4 hover:border-6 hover:p-3.5 hover:bg-white hover:shadow-md transition-transform duration-100 group cursor-pointer active:scale-99">
            <Link to={`/learning-paths/${data.id}`} state={getFilterState()}>
                <div className="relative h-50 w-full">
                    {userEnrolled ? <img className="absolute w-10 top-2 left-2 z-2" src={play} alt="Module in progress icon" /> : null}
                    <div className="absolute z-1 top-0 left-0 w-full h-full rounded-xl bg-gradient-to-br from-black/80 to-transparent group-hover:opacity-15 duration-100" />
                    <img className="z-0 h-full w-full object-cover rounded-xl" src={data.iconURL} alt="Learning path card image" />
                </div>
                <h1 className="mt-2 text-slateBlue font-bold text-xl truncate">{data.title}</h1>
                <div className="flex flex-row gap-4 justify-center mb-2">
                    <div className="flex flex-col gap-1.5 items-end">
                        <p className="text-sm">Learning Path</p>
                        {/* <p className="text-sm">{`Courses: ${data.courseIds.length}`}</p> */}
                        <div className="flex flex-row gap-2">
                            <p className="text-sm">{data.numberOfEnrolledUsers}</p>
                            <img className="w-4" src={userIcon} alt="Number of enrolled users icon" />
                        </div>
                    </div>
                    <div className="flex flex-col gap-1.5 items-center">
                        <p className="text-sm">&bull;</p>
                        <p className="text-sm">&bull;</p>
                    </div>
                    <div className="flex flex-col gap-1.5 items-start">
                        <p className="text-sm truncate">{data.difficulty}</p>
                        <p className="text-sm truncate">{data.createdAt.split("T")[0]}</p>
                    </div>
                </div>
                <div className="flex flex-row flex-wrap text-xs font-bold gap-2">
                    {tagLabels}
                </div>
            </Link>
        </div>
    );
}