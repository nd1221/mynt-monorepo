import { useState, useEffect } from "react";
import { Link, useLoaderData } from "react-router-dom";
import api from "../api/modules.js";
import DOMPurify from 'dompurify';
import { authorise } from "../api/authorise.js";
import { handleError } from "../api/apiUtils.js";

const fetchUnitContent = async unitId => {
	try {
		// Fetch content
		return await api.get(`/lessons/${unitId}/content`);
	} catch (err) {
		handleError(err);
	}
}

const fetchLessonTracker = async (courseTrackerId, lessonId) => {
	try {
		const response = await api.get(`/progress/${courseTrackerId}/lesson-${lessonId}/lesson-tracker`);
		return response.data;
	} catch(err) {
		handleError(err);
	}
}
	
export async function loader({params}) {
	const unitId = params.unitId;
	const courseTrackerId = params.courseProgressTrackerId;
	// Authorise
	await authorise();
	const data = await fetchUnitContent(unitId);
	return [data.data, courseTrackerId];
}

const getPrevLink = (prevId, courseTrackerId) => {

	if (!prevId) {
		return (
			<div className="
					block my-auto text-center h-fit w-full
					bg-transparent text-transparent border-transparent border-3 p-4 px-8 text-xl font-bold rounded-2xl
				"
			>
				placeholder
			</div>
		);
	}

	return (
		<Link
			to={`/course-units/${prevId}/${courseTrackerId}`}
			className="
				block my-auto text-center h-fit w-full
				bg-transparent text-lavenderDark/90 border-lavender border-3 p-4 px-8 text-xl font-bold rounded-lg
				hover:bg-lavender hover:text-offWhite duration-100 active:scale-98
			"
		>
			<p>prev unit</p>
		</Link>
	);
}

const getNextLink = (nextId, courseTrackerId, isLastLesson, sectionId, courseId) => {

	let path;

	if (isLastLesson) {
		path = `/courses/${courseId}/sections/${sectionId}/section-test`;
	} else if (nextId) {
		path = `/course-units/${nextId}/${courseTrackerId}`;
	} else {
		path = "/not-found";
	}

	return (
		<Link
			to={path}
			className="
				block my-auto text-center h-fit w-full
				bg-transparent text-lavenderDark/90 border-lavender border-3 p-4 px-8 text-xl font-bold rounded-lg
				hover:bg-lavender hover:text-offWhite duration-100 active:scale-98
			"
		>
			<p>{isLastLesson ? "section test" : "next unit"}</p>
		</Link>
	);
}

const getRandomQuestionLink = (questionId, lessonTrackerId, nextId, prevId, courseTrackerId, courseId, isTestNext, sectionId) => {

	let path;
	let state = {
		nextId: nextId,
		prevId: prevId,
		lessonTrackerId: lessonTrackerId,
		courseTrackerId: courseTrackerId,
		courseId: courseId,
		isTestNext: isTestNext,
		sectionId: sectionId
	};

	if (!questionId) {
		path = "/not-found";
		state = null;
	} else {
		path = `/unit-questions/${questionId}/${lessonTrackerId}`;
	}

	return (
		<Link
			to={path}
			className="
				block my-auto text-center h-fit w-full
				bg-transparent text-slateBlue border-slateBlue border-3 p-4 px-8 text-xl font-bold rounded-lg
				hover:bg-slateBlue hover:text-offWhite duration-100 active:scale-98
			"
			state={state}
		>
			<p>question</p>
		</Link>
	);
}

export default function CourseUnit() {

	const [data, courseTrackerId] = useLoaderData();
	const [lessonTracker, setLessonTracker] = useState(null);
	const {content, lesson, isLastLesson, nextId, prevId, randomQuestionId} = data;

	const safeContent = DOMPurify.sanitize(content);

	// Ensure lesson progress tracker exists
	useEffect(() => {
		const getData = async () => {
			const lessonTracker = await fetchLessonTracker(courseTrackerId, lesson.id);
			setLessonTracker(lessonTracker);
		};
		getData();
	}, [courseTrackerId, lesson, lessonTracker]);

    return (
		<div className="relative flex flex-col gap-4 w-7xl p-10 pb-0">
			<div className="relative right-20 flex flex-row gap-6">
				<div className="sticky top-0 flex flex-col gap-6 w-1/4 h-fit items-center">
					<div className="w-full h-fit bg-slateBlue rounded-lg p-2 px-4 mt-17">
						<hr className="my-4 border-2 border-lavender" />
						<h1 className="text-2xl font-bold mb-2 text-offWhite">{`Unit ${lesson.position} of ${lesson.numberOfLessonsInCourse}`}</h1>
						<p className="text-lg text-offWhite">{lesson.description}</p>
						<hr className="my-4 border-2 border-lavender" />
					</div>
					<Link
						to={`/courses/${lesson.courseId}`}
						className="
							text-center h-fit w-full
							bg-transparent text-slateBlue border-slateBlue border-3 p-4 px-8 text-xl font-bold rounded-lg
							hover:bg-slateBlue hover:text-offWhite duration-100 active:scale-98 
							flex flex-row gap-4 justify-center items-center
						"
					>
						<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-7">
							<path fill-rule="evenodd" d="M9.53 2.47a.75.75 0 0 1 0 1.06L4.81 8.25H15a6.75 6.75 0 0 1 0 13.5h-3a.75.75 0 0 1 0-1.5h3a5.25 5.25 0 1 0 0-10.5H4.81l4.72 4.72a.75.75 0 1 1-1.06 1.06l-6-6a.75.75 0 0 1 0-1.06l6-6a.75.75 0 0 1 1.06 0Z" clip-rule="evenodd" />
						</svg>
						<p>back to course</p>
					</Link>
					{
						lessonTracker ?
							<Link
								to={`/user-profile/${courseTrackerId}/unit-overview/${lessonTracker.id}`}
								className="
									text-center h-fit w-full
									bg-transparent text-lavender border-lavender border-3 p-4 px-8 text-xl font-bold rounded-lg
									hover:bg-lavender hover:text-offWhite duration-100 active:scale-98 
									flex flex-row gap-4 justify-center items-center
								"
							>
								<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6">
									<path fill-rule="evenodd" d="M1.5 7.125c0-1.036.84-1.875 1.875-1.875h6c1.036 0 1.875.84 1.875 1.875v3.75c0 1.036-.84 1.875-1.875 1.875h-6A1.875 1.875 0 0 1 1.5 10.875v-3.75Zm12 1.5c0-1.036.84-1.875 1.875-1.875h5.25c1.035 0 1.875.84 1.875 1.875v8.25c0 1.035-.84 1.875-1.875 1.875h-5.25a1.875 1.875 0 0 1-1.875-1.875v-8.25ZM3 16.125c0-1.036.84-1.875 1.875-1.875h5.25c1.036 0 1.875.84 1.875 1.875v2.25c0 1.035-.84 1.875-1.875 1.875h-5.25A1.875 1.875 0 0 1 3 18.375v-2.25Z" clip-rule="evenodd" />
								</svg>
								<p>dashboard</p>
							</Link>
						: null
					}
				</div>
				<div className="relative flex flex-col h-fit w-3/4 gap-4 p-4 items-center">
					<h1 className="text-4xl font-bold text-slateBlue">{lesson.title}</h1>
					<hr className="w-full border-1 border-slateBlue/20" />

					<div className="flex flex-col gap-4 h-fit w-full my-8 p-4 unitContent" dangerouslySetInnerHTML={{__html: safeContent}}></div>

					<div className="sticky bottom-0 flex flex-col gap-2 w-full h-fit bg-offWhite py-8">
						<hr className="w-full border-1 border-slateBlue/20" />
						{
							lessonTracker ?
								<div className="grid grid-cols-3 gap-8 w-full p-4 h-fit items-center justify-between">
									{getPrevLink(prevId, courseTrackerId)}
									{getRandomQuestionLink(randomQuestionId, lessonTracker.id, nextId, lesson.id, courseTrackerId, lesson.courseId, isLastLesson, lesson.sectionId)}
									{getNextLink(nextId, courseTrackerId, isLastLesson, lesson.sectionId, lesson.courseId)}
								</div>
							: null
						}
					</div>
				</div>
			</div>
		</div>
	);
}