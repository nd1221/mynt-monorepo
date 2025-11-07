import CourseCard from "../components/moduleCards/CourseCard.jsx";
import LearningPathCard from "../components/moduleCards/LearningPathCard.jsx";
import FilterBarComponent from "../components/uiComponents/FilterBarComponent.jsx";
import api from "../api/modules.js";
import {initialFilterBar, getDateFrom, getTypeDescription} from "../utils/coursesUtils.js";
import {handleError} from "../api/apiUtils.js";

import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";

export default function Courses() {

    // Check for search filter state
    const location = useLocation();
    const filterState = location.state?.filterState;

    // Type of module fetched
    const [type, setType] = useState(filterState ? filterState.type : "learningPath");
    
    // Fetched components
    const [currentPage, setCurrentPage] = useState(filterState ? filterState.pageNumber : null);
    const [moduleComponents, setModuleComponents] = useState([]);
    const [filterBar, setFilterBar] = useState(filterState ? filterState.bar : initialFilterBar());
    
    // API search URL parameters
    const [filterParams, setFilterParams] = useState(
        filterState ?
        filterState.filter :
        {
            type: type,
            from: "",
            difficulty: [],
            tags: []
        }
    );
    const [paginationParams, setPaginationParams] = useState( // Reset pagination state to default
        {
            size: 24,
            pageNumber: 0
        }
    );
    const [sortParams, setSortParams] = useState(filterState ? filterState.sort : "title,asc");
    
    const fetchTags = async () => {
        api.get("/tags/all")
            .then(response => {

                // Initialise tags in filter bar
                setFilterBar(prevFilterBar => (
                    prevFilterBar.map(filter => {
                        if (filter.id === 0) { // i.e. tag filter
                            return {
                                ...filter,
                                options: response.data.map(tag => (
                                    {key: tag.tag, checked: false}
                                ))
                            }
                        } else {
                            return {...filter};
                        }
                    })
                ));
            })
        .catch(err => handleError(err));
    }
        
    // Fetch tags
    useEffect(() => {
        // Only fetch when the location state is null or undefined, i.e. empty
        if (location?.state?.filterState == null) {
            fetchTags();
        }
    }, [location.state]);
    
    const getModuleComponents = (modules, existingModules) => {
        const newModules = modules.map(module => {
            if (type === "learningPath") {
                return <LearningPathCard key={module.id} data={module} userEnrolled={false} getFilterState={getFilterState} />;
            } else {
                return <CourseCard key={module.id} data={module} userEnrolled={false} getFilterState={getFilterState} />;
            }
        });
        return [...existingModules, ...newModules];
    }
    
    const fetchModules = async (queryURL, alreadyFetchedModules) => {
        api.get(queryURL)
        .then(response => {
            setCurrentPage(response.data);
            const updatedModules = getModuleComponents(response.data.content, alreadyFetchedModules);
            setModuleComponents(updatedModules);
        })
        .catch(err => handleError(err));
    }
    
    const buildQueryParams = (filter, pagination, sort) => {
        return `
            /search?
            type=${filter.type}
            ${filter.from !== "" ? `&from=${filter.from}` : ""}
            ${filter.difficulty.length > 0 ? `&difficulty=${filter.difficulty.join(",")}` : ""}
            ${filter.tags.length > 0 ? `&tags=${filter.tags.join(",")}` : ""}
            &size=${pagination.size}
            &page=${pagination.pageNumber}
            ${sort !== "" && sort !== undefined ? `&sort=${sort}` : ""}
            `
            .replace(/\s+/g, "");
    }
    
    // Fetch default returned modules following type update
    useEffect(() => {
        fetchModules(buildQueryParams(
            filterParams,
            paginationParams,
            sortParams
        ), []);
    }, [type]);
    
    // Refetch existing fetched modules with updated sort parameters
    useEffect(() => {
        fetchModules(buildQueryParams(
            filterParams,
            {size: moduleComponents.length, pageNumber: 0},
            sortParams
        ), []);
    }, [sortParams]);
    
    // Fetch modules triggered by filter or pagination updates
    useEffect(() => {
        fetchModules(buildQueryParams(
            filterParams,
            paginationParams,
            sortParams
        ),
        moduleComponents);
    }, [paginationParams, filterParams]);

    const updateType = (newType) => {
        // Toggling type should reset all search parameters and modules returned
        setType(newType);
        setFilterParams({
            type: newType,
            from: "",
            difficulty: [],
            tags: []
        })
        setPaginationParams({
            size: 24,
            pageNumber: 0
        });
        setSortParams("title,asc");
        setModuleComponents([]);
        resetFilterBar();
    }

    const toggleFilterBar = (id) => {
        setFilterBar(prevFilterBar => {
            return prevFilterBar.map(filter => {
                if (filter.id === id) {
                    return {...filter, on: !filter.on};
                }
                return {...filter};
            });
        });
    }

    const toggleCheckbox = ({target}) => {
        setFilterBar(prevFilterBar => {
            return prevFilterBar.map((filter, index) => {
                if (target.dataset.parentId == index) {
                    const updatedOptions = filter.options.map(option => {
                        if (target.name === option.key) {
                            return {...option, checked: !option.checked};
                        }
                        return {...option};
                    });
                    return {...filter, options: updatedOptions}
                }
                return {...filter};
            });
        });
    }

    const toggleRadio = ({target}) => {
        setFilterBar(prevFilterBar => {
            return prevFilterBar.map((filter, index) => {
                if (target.dataset.parentId == index) {
                    const updatedOptions = filter.options.map(option => {
                        if (target.name === option.key) {
                            return {...option, checked: true}
                        }
                        return {...option, checked: false};
                    });
                    return {...filter, options: updatedOptions};
                }
                return {...filter};
            });
        })
    }

    const applyFilters = () => {

        const appliedFilters = {
            tags: filterBar[0].options.filter(option => option.checked),
            difficulty: filterBar[1].options.filter(option => option.checked),
            from: getDateFrom(filterBar[2].options.filter(option => option.checked)[0]) // Always array of length 1 since only one checked at a time
        };
        
        setModuleComponents([]);
        setPaginationParams(prevPaginationParams => ({
            ...prevPaginationParams,
            pageNumber: 0
        }));
        setFilterParams({
            type: type,
            tags: appliedFilters.tags.map(tag => encodeURIComponent(tag.key)),
            difficulty: appliedFilters.difficulty.map(difficulty => difficulty.key.toUpperCase()),
            from: appliedFilters.from
        });
    }
    
    const resetFilterBar = () => {
        setFilterBar(prevFilterBar => {
            return prevFilterBar.map(filter => {
                return {
                    ...filter,
                    options: filter.options.map(option => {
                        if (option.key === "anytime") {
                            return {...option, checked: true};
                        }
                        return {...option, checked: false}
                    })
                };
            });
        });
    }

    const clearFilters = () => {
        resetFilterBar();
        setModuleComponents([]);
        setFilterParams({
            type: type,
            from: "",
            difficulty: [],
            tags: []
        });
        setPaginationParams(prevPaginationParams => ({
            ...prevPaginationParams,
            pageNumber: 0
        }));
    }
    

    const toggleViewMore = () => {
        setPaginationParams(prevPaginationParams => (
            {
                ...prevPaginationParams,
                pageNumber: prevPaginationParams.pageNumber + 1
            }
        ))
    }

    const filterBarComponents = filterBar.map(filter => {
        if (filter.category === "From") {
            return <FilterBarComponent key={filter.category} filter={filter} handleChange={toggleRadio} toggleFilterBar={toggleFilterBar} />
        } else {
            return <FilterBarComponent key={filter.category} filter={filter} handleChange={toggleCheckbox} toggleFilterBar={toggleFilterBar} />
        }
    });

    const getFilterState = () => {
        return {
            type: type,
            pageNumber: currentPage,
            filter: filterParams,
            pagination: paginationParams,
            sort: sortParams,
            bar: filterBar
        };
    }

    return(
        <>
            <div className="flex flex-col w-6xl h-60 my-8 gap-12 p-4 px-12 border-b-2 border-slateBlue/20">
                <div className="flex flex-row justify-center items-center gap-10">
                    <div
                        className={`text-charcoal font-bold text-3xl ${type === "learningPath" ? "underline" : ""} hover:underline underline-offset-10 decoration-4 transition-transform duration-75 decoration-teal cursor-pointer`}
                        onClick={() => updateType("learningPath")}
                    >
                        Learning Paths
                    </div>
                    <div
                        className={`text-charcoal font-bold text-3xl ${type === "course" ? "underline" : ""} hover:underline underline-offset-10 decoration-4 transition-transform duration-75 decoration-lavender cursor-pointer`}
                        onClick={() => updateType("course")}
                    >
                        Courses
                    </div>
                </div>
                <p className="text-charcoal text-lg ">
                    {getTypeDescription(type)}
                </p>
            </div>

            <div className="flex flex-row w-7xl justify-center gap-12 my-8">
                
                <div className="flex flex-col h-fit gap-4 p-4 bg-slateBlue basis-1/4 rounded-lg">
                    {filterBarComponents}
                    <div className="flex flex-row gap-4 mt-8 mb-4 justify-center">
                        <button className="bg-transparent text-offWhite border-offWhite border-2 p-3 px-6 text-xl font-bold rounded-full hover:bg-offWhite hover:text-slateBlue duration-100" onClick={applyFilters}>apply</button>
                        <button className="bg-transparent text-offWhite border-offWhite border-2 p-3 px-6 text-xl font-bold rounded-full hover:bg-offWhite hover:text-slateBlue duration-100" onClick={clearFilters}>clear</button>
                    </div>
                </div>
                
                <div className="flex flex-col gap-12 basis-3/4">
                    <div className="flex flex-row justify-between h-16 p-4 text-slateBlue/80 text-lg font-bold border-b-2 border-slateBlue/20">
                        {currentPage ? `Showing ${moduleComponents.length} out of ${currentPage.totalElements} results` : ""}
                        <div className="flex flex-row gap-4 items-center">
                            <select
                                id="sort"
                                name="sort"
                                value={sortParams}
                                className="text-center px-3 py-2 rounded-lg font-bold bg-slateBlue/10"
                                onChange={event => setSortParams(event.target.value)}
                            >
                                <option value="title,asc">A - Z</option>
                                <option value="title,desc">Z - A</option>
                                <option value="date,desc">newest</option>
                                <option value="date,asc">oldest</option>
                                <option value="popularity,desc">popular</option>
                            </select>
                        </div>
                    </div>
                    <div className="grid grid-cols-3 gap-8">
                        {moduleComponents}
                    </div>
                    {currentPage && paginationParams.pageNumber < currentPage.totalPages - 1 ? 
                        <button
                            className="flex flex-row h-16 bg-slateBlue/80 hover:bg-slateBlue rounded-lg text-offWhite font-bold text-xl justify-center items-center shadow-xl active:shadow-sm"
                            onClick={toggleViewMore}
                        >
                            View More
                        </button>
                    : null}
                </div>

            </div>
        </>
    );
}