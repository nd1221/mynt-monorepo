import { useState } from "react";
import { Link } from "react-router-dom";
import UnitSearchLink from "./UnitSearchLink";

const getSectionOptions = sections => {
    return sections.map(section => (
        <option value={`${section.position}`}>{section.position} - {section.title}</option>
    ));
}

const noUnitsSidebar = (message, courseProgressTrackerId) => {
    return (
        <div className="flex flex-col gap-6 w-full h-fit p-4 items-center">
            <p className="text-xl text-center mt-6">{message}</p>
            <Link 
                to={`/user-profile/courses/${courseProgressTrackerId}`}
                className="bg-offWhite border-lavender border-3 rounded-md text-lavender p-4 px-6 font-bold hover:bg-lavender hover:text-offWhite transition active:scale-98 active:opacity-90"
            >
                back to course overview
            </Link>
        </div>
    );
}

const applySectionParam = (units, param) => {
    
    // No section selected
    if (param == 0) {
        return units;
    }

    return units.filter(unit => unit.sectionPosition == param);
}

const applySearchParam = (units, param) => {

    if (param === "") {
        return units;
    }

    const keyword = param.toLowerCase().trim();
    return units.filter(unit => unit.lessonTitle.toLowerCase().includes(keyword));
}

const getSortProperty = param => {
    switch (param) {
        case "recent":
            return "lastReviewDate";
        case "completeness":
            return "completeness";
        case "mastery":
            return "mastery";
        case "pos":
            return "lessonPosition";
        default:
            return "lessonTitle";
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

    if (a?.[property] < b?.[property]) {
        result = 1;
    } else if (a?.[property] > b?.[property]) {
        result = -1;
    } else {
        result = 0;
    }

    return result * reversed;
}

const applySortParam = (units, param) => {

    const sortProperty = getSortProperty(param);
    const reversed = (param === "za" || param === "pos") ? -1 : 1;
    return units.sort((a, b) => compare(a, b, sortProperty, reversed));
}

const getVisibleUnits = (units, sectionParam, sortParam, searchParam) => {
    const filteredBySection = applySectionParam(units, sectionParam);
    const filteredBySearch = applySearchParam(filteredBySection, searchParam);
    const filteredBySort = applySortParam(filteredBySearch, sortParam);
    return filteredBySort;
}

const getUnitLinks = (units, trackerId) => {

    if (units.length === 0) {
        return noUnitsSidebar("No units match your search parameters. Try going back to course overview to find what you're looking for.", trackerId);
    }

    return units.map(unit => (
        <UnitSearchLink unit={unit} trackerId={trackerId} />
    ));
}

export default function UnitSearchSidebar({data, sections, trackerId}) {

    const [currentSearch, setCurrentSearch] = useState("");
    const handleSearch = searchStr => {
        setCurrentSearch(searchStr);
    }

    const [sortParam, setSortParam] = useState("pos");
    const handleSort = newParam => {
        setSortParam(newParam);
    }

    const [selectedSection, setSelectedSection] = useState(0); // 0 = all sections shown
    const handleSelectSection = newSection => {
        setSelectedSection(newSection);
    }

    return (
        <div className="flex flex-col max-h-screen lg:h-[80vh] xl:h-[80vh] overflow-auto lg:flex-1 gap-4 items-center rounded-lg bg-slateBlue/5 p-6 py-8 shadow-md border-1 border-slateBlue/6">
            
            <div className="flex flex-row gap-4 justify-center w-full h-fit">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                    <path d="M11.7 2.805a.75.75 0 0 1 .6 0A60.65 60.65 0 0 1 22.83 8.72a.75.75 0 0 1-.231 1.337 49.948 49.948 0 0 0-9.902 3.912l-.003.002c-.114.06-.227.119-.34.18a.75.75 0 0 1-.707 0A50.88 50.88 0 0 0 7.5 12.173v-.224c0-.131.067-.248.172-.311a54.615 54.615 0 0 1 4.653-2.52.75.75 0 0 0-.65-1.352 56.123 56.123 0 0 0-4.78 2.589 1.858 1.858 0 0 0-.859 1.228 49.803 49.803 0 0 0-4.634-1.527.75.75 0 0 1-.231-1.337A60.653 60.653 0 0 1 11.7 2.805Z" />
                    <path d="M13.06 15.473a48.45 48.45 0 0 1 7.666-3.282c.134 1.414.22 2.843.255 4.284a.75.75 0 0 1-.46.711 47.87 47.87 0 0 0-8.105 4.342.75.75 0 0 1-.832 0 47.87 47.87 0 0 0-8.104-4.342.75.75 0 0 1-.461-.71c.035-1.442.121-2.87.255-4.286.921.304 1.83.634 2.726.99v1.27a1.5 1.5 0 0 0-.14 2.508c-.09.38-.222.753-.397 1.11.452.213.901.434 1.346.66a6.727 6.727 0 0 0 .551-1.607 1.5 1.5 0 0 0 .14-2.67v-.645a48.549 48.549 0 0 1 3.44 1.667 2.25 2.25 0 0 0 2.12 0Z" />
                    <path d="M4.462 19.462c.42-.419.753-.89 1-1.395.453.214.902.435 1.347.662a6.742 6.742 0 0 1-1.286 1.794.75.75 0 0 1-1.06-1.06Z" />
                </svg>
                <h1 className="text-xl font-bold">course units</h1>
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
                    <path d="M11.7 2.805a.75.75 0 0 1 .6 0A60.65 60.65 0 0 1 22.83 8.72a.75.75 0 0 1-.231 1.337 49.948 49.948 0 0 0-9.902 3.912l-.003.002c-.114.06-.227.119-.34.18a.75.75 0 0 1-.707 0A50.88 50.88 0 0 0 7.5 12.173v-.224c0-.131.067-.248.172-.311a54.615 54.615 0 0 1 4.653-2.52.75.75 0 0 0-.65-1.352 56.123 56.123 0 0 0-4.78 2.589 1.858 1.858 0 0 0-.859 1.228 49.803 49.803 0 0 0-4.634-1.527.75.75 0 0 1-.231-1.337A60.653 60.653 0 0 1 11.7 2.805Z" />
                    <path d="M13.06 15.473a48.45 48.45 0 0 1 7.666-3.282c.134 1.414.22 2.843.255 4.284a.75.75 0 0 1-.46.711 47.87 47.87 0 0 0-8.105 4.342.75.75 0 0 1-.832 0 47.87 47.87 0 0 0-8.104-4.342.75.75 0 0 1-.461-.71c.035-1.442.121-2.87.255-4.286.921.304 1.83.634 2.726.99v1.27a1.5 1.5 0 0 0-.14 2.508c-.09.38-.222.753-.397 1.11.452.213.901.434 1.346.66a6.727 6.727 0 0 0 .551-1.607 1.5 1.5 0 0 0 .14-2.67v-.645a48.549 48.549 0 0 1 3.44 1.667 2.25 2.25 0 0 0 2.12 0Z" />
                    <path d="M4.462 19.462c.42-.419.753-.89 1-1.395.453.214.902.435 1.347.662a6.742 6.742 0 0 1-1.286 1.794.75.75 0 0 1-1.06-1.06Z" />
                </svg>
            </div>

            <hr className="w-9/10 border-b-1 border-slateBlue/10"/>

            <div className="flex flex-col w-full h-fit mb-2">
                <label className="flex flex-col items-center h-fit py-2">
                    <input 
                        name="courseSearch"
                        className="
                            bg-slateBlue p-3 px-6 rounded-full w-full border-2 border-slateBlue text-offWhite
                            focus:border-lavender focus:outline-none
                        "
                        placeholder="enter a unit"
                        onChange={e => handleSearch(e.target.value)}
                        value={currentSearch}
                    />
                </label>
                <div className="flex flex-col justify-between gap-4 px-4 mt-2">
                    <div className="flex flex-row gap-2 w-full justify-between">
                        <label className="bg-slateBlue/15 p-1 px-2 rounded-lg text-sm">
                            <select
                                className="w-full"
                                name="selectSection"
                                value={selectedSection}
                                onChange={e => handleSelectSection(e.target.value)}
                            >
                                <option value={"0"}>Show all sections</option>
                                {getSectionOptions(sections)}
                            </select>
                        </label>
                        <label className="bg-slateBlue/15 p-1 px-2 rounded-lg text-sm">
                            <select
                                name="sortOptions"
                                value={sortParam}
                                onChange={e => handleSort(e.target.value)}
                            >
                                <option value="pos">Position</option>
                                <option value="az">A - Z</option>
                                <option value="za">Z - A</option>
                                <option value="recent">Recent Activity</option>
                                <option value="completeness">Completeness</option>
                                <option value="mastery">Mastery</option>
                            </select>
                        </label>
                    </div>
                    <div className="flex flex-col items-end px-2">
                        <button 
                            className="text-sm underline hover:text-lavender active:text-lavenderDark cursor-pointer"
                            onClick={() => handleSearch("")}
                        >
                            clear search
                        </button>
                    </div>
                </div>
            </div>

            <hr className="w-9/10 border-b-1 border-slateBlue/10"/>

            <div className="flex flex-col h-full overflow-auto w-full gap-6 items-center">
                {
                    data.length === 0 ? 
                    noUnitsSidebar("You have not unlocked any Unit's content for this course yet. Either go back to the Course Dashboard or view the Course content to unlock unit summaries.", trackerId)
                    : getUnitLinks(getVisibleUnits(data, selectedSection, sortParam, currentSearch), trackerId)
                }
            </div>

        </div>
    );
}