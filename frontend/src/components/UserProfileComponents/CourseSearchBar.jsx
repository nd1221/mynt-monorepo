import SidebarCourseLink from "./SidebarCourseLink";
import { useState } from "react";
import { Link } from "react-router-dom";

export default function CourseSearchBar({courseSummaries}) {

    const [sortParam, setSortParam] = useState("az");
    const [currentSearch, setCurrentSearch] = useState("");
    
    const handleSearch = searchString => {
        setCurrentSearch(searchString);
    }

    const filterSummaryBySearchParam = (visibleCourseTrackerSummaries, searchString) => {

        if (currentSearch === "") {
            return visibleCourseTrackerSummaries;
        }

        const keyword = parseSearchString(searchString);
        const filteredCourses = visibleCourseTrackerSummaries.filter(
            summary => summary.courseTitle.toLowerCase().includes(keyword)
        );

        return filteredCourses;
    }

    const parseSearchString = str => {
        return str.toLowerCase().trim();
    }

    const applySortParam = visibleSummaries => {

        const sortProperty = getSortProperty(sortParam);
        const reversed = sortParam == "az" ? -1 : 1;

        return visibleSummaries.sort((a, b) => compare(a, b, sortProperty, reversed));
    }

    const getSortProperty = param => {
        switch (param) {
            case "recent":
                return "lastReviewDate";
            case "completeness":
                return "completeness";
            case "mastery":
                return "mastery";
            default:
                return "courseTitle";
        }
    }

    const compare = (a, b, property, reversed) => {
        
        // Given the current sort parameters, all properties which can be sorted by
        // are in a form where this general function can be used to compare them.
        // Will require separating if the date format changes.

        // index refers to which entry of the tuple is being operated on
        // property refers to the property of the object in that index being operated on
        // reversed is -1 or 1, indicating whether order is ascending/descending
        
        let result;

        if (a[property] < b[property]) {
            result = 1;
        } else if (a[property] > b[property]) {
            result = -1;
        } else {
            result = 0;
        }

        return result * reversed;
    }

    const sortSummaries = (summaries, currentSearch) => {
        const visibleSummaries = filterSummaryBySearchParam(summaries, currentSearch);
        return applySortParam(visibleSummaries);
    }

    const formatCourseLinks = summaries => {

        // Check if there are no courses matching the given filters and direct user to '/paths'
        if (summaries.length === 0) {
            return noCoursesSidebar(
                "No courses found. You can try a different search term or try searching our available courses below."
            );
        }

        return summaries.map(summary => (
            <SidebarCourseLink courseSummary={summary} />
        ));
    }

    const noCoursesSidebar = message => {
        return (
            <div className="flex flex-col gap-6 w-full h-fit p-4 items-center">
                <p className="text-xl text-center mt-6">{message}</p>
                <Link to="/paths" className="bg-offWhite border-lavender border-3 rounded-md text-lavender p-4 px-6 font-bold hover:bg-lavender hover:text-offWhite transition active:scale-98 active:opacity-90">
                    browse courses
                </Link>
            </div>
        );
    }
    
    return (
        <div className="flex flex-col max-h-screen h-fit overflow-auto lg:flex-1 gap-4 items-center rounded-lg bg-slateBlue/5 p-6 py-8 shadow-md border-1 border-slateBlue/6">
            
            <div className="flex flex-row gap-4 justify-center w-full h-fit">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                    <path d="M11.7 2.805a.75.75 0 0 1 .6 0A60.65 60.65 0 0 1 22.83 8.72a.75.75 0 0 1-.231 1.337 49.948 49.948 0 0 0-9.902 3.912l-.003.002c-.114.06-.227.119-.34.18a.75.75 0 0 1-.707 0A50.88 50.88 0 0 0 7.5 12.173v-.224c0-.131.067-.248.172-.311a54.615 54.615 0 0 1 4.653-2.52.75.75 0 0 0-.65-1.352 56.123 56.123 0 0 0-4.78 2.589 1.858 1.858 0 0 0-.859 1.228 49.803 49.803 0 0 0-4.634-1.527.75.75 0 0 1-.231-1.337A60.653 60.653 0 0 1 11.7 2.805Z" />
                    <path d="M13.06 15.473a48.45 48.45 0 0 1 7.666-3.282c.134 1.414.22 2.843.255 4.284a.75.75 0 0 1-.46.711 47.87 47.87 0 0 0-8.105 4.342.75.75 0 0 1-.832 0 47.87 47.87 0 0 0-8.104-4.342.75.75 0 0 1-.461-.71c.035-1.442.121-2.87.255-4.286.921.304 1.83.634 2.726.99v1.27a1.5 1.5 0 0 0-.14 2.508c-.09.38-.222.753-.397 1.11.452.213.901.434 1.346.66a6.727 6.727 0 0 0 .551-1.607 1.5 1.5 0 0 0 .14-2.67v-.645a48.549 48.549 0 0 1 3.44 1.667 2.25 2.25 0 0 0 2.12 0Z" />
                    <path d="M4.462 19.462c.42-.419.753-.89 1-1.395.453.214.902.435 1.347.662a6.742 6.742 0 0 1-1.286 1.794.75.75 0 0 1-1.06-1.06Z" />
                </svg>
                <h1 className="text-xl font-bold">your courses</h1>
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                    <path d="M11.7 2.805a.75.75 0 0 1 .6 0A60.65 60.65 0 0 1 22.83 8.72a.75.75 0 0 1-.231 1.337 49.948 49.948 0 0 0-9.902 3.912l-.003.002c-.114.06-.227.119-.34.18a.75.75 0 0 1-.707 0A50.88 50.88 0 0 0 7.5 12.173v-.224c0-.131.067-.248.172-.311a54.615 54.615 0 0 1 4.653-2.52.75.75 0 0 0-.65-1.352 56.123 56.123 0 0 0-4.78 2.589 1.858 1.858 0 0 0-.859 1.228 49.803 49.803 0 0 0-4.634-1.527.75.75 0 0 1-.231-1.337A60.653 60.653 0 0 1 11.7 2.805Z" />
                    <path d="M13.06 15.473a48.45 48.45 0 0 1 7.666-3.282c.134 1.414.22 2.843.255 4.284a.75.75 0 0 1-.46.711 47.87 47.87 0 0 0-8.105 4.342.75.75 0 0 1-.832 0 47.87 47.87 0 0 0-8.104-4.342.75.75 0 0 1-.461-.71c.035-1.442.121-2.87.255-4.286.921.304 1.83.634 2.726.99v1.27a1.5 1.5 0 0 0-.14 2.508c-.09.38-.222.753-.397 1.11.452.213.901.434 1.346.66a6.727 6.727 0 0 0 .551-1.607 1.5 1.5 0 0 0 .14-2.67v-.645a48.549 48.549 0 0 1 3.44 1.667 2.25 2.25 0 0 0 2.12 0Z" />
                    <path d="M4.462 19.462c.42-.419.753-.89 1-1.395.453.214.902.435 1.347.662a6.742 6.742 0 0 1-1.286 1.794.75.75 0 0 1-1.06-1.06Z" />
                </svg>
            </div>

            <hr className="w-9/10 border-b-1 border-slateBlue/10"/>

            <div className="flex flex-col w-full h-fit mb-4">
                <label className="flex flex-col items-center h-fit py-2">
                    <input 
                        name="courseSearch"
                        className="
                            bg-slateBlue p-3 px-6 rounded-full w-full border-2 border-slateBlue text-offWhite
                            focus:border-lavender focus:outline-none
                        "
                        placeholder="enter a course"
                        onChange={e => handleSearch(e.target.value)}
                        value={currentSearch}
                    />
                </label>
                <div className="flex flex-row justify-between gap-2 px-4 mt-2">
                    <label className="bg-slateBlue/15 p-1 px-2 rounded-lg text-sm">
                        <select
                            name="sortOptions"
                            value={sortParam}
                            onChange={e => setSortParam(e.target.value)}
                        >
                            <option value="az">A - Z</option>
                            <option value="za">Z - A</option>
                            <option value="recent">Recent Activity</option>
                            <option value="completeness">Completeness</option>
                            <option value="mastery">Mastery</option>
                        </select>
                    </label>
                    <button 
                        className="text-sm underline hover:text-lavender active:text-lavenderDark cursor-pointer"
                        onClick={() => handleSearch("")}
                    >
                        clear search
                    </button>
                </div>
            </div>

            <hr className="w-9/10 border-b-1 border-slateBlue/10"/>

            <div className="flex flex-col h-fit overflow-auto w-full gap-6 items-center">
                {courseSummaries == null ? noCoursesSidebar("You are not enrolled on any courses. You can try search our available courses below.") : formatCourseLinks(sortSummaries(courseSummaries, currentSearch))}
            </div>

        </div>
    ); 
}