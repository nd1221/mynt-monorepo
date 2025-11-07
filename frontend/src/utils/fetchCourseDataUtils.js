import api from "../api/modules.js";
import { handleError } from "../api/apiUtils.js";

const sortCourse = course => {
  
  let sortedCourse = course;
  
  const comparePosition = (a, b) => {
    return a.position < b.position ? -1 : 1;
  }
  
  // Sort Sections
  if (course.sections.length) {
    const sortedSections = course.sections.sort(comparePosition);
    sortedCourse = {sections: sortedSections, ...sortedCourse}
  }

  // Sort lessons
  sortedCourse.sections.forEach(section => {
    if (section.lessons.length) {
      const sortedLessons = section.lessons.sort(comparePosition);
      return {lessons: sortedLessons, ...section};
    }
  })

  return sortedCourse;
}

const fetchSections = async fetchedCourse => {
  const {sectionIds, ...otherKeys} = fetchedCourse.data;
  const course = {
      ...otherKeys,
      sections: await Promise.all(
          sectionIds.map(async sectionId => {
              const fetchedSection = await api.get(`/sections/${sectionId}`);
              return fetchedSection.data;
          })
      )
  }
  return course;
}

const fetchLessons = async fetchedSection => {
  const {lessonIds, ...otherKeys} = fetchedSection;
  const section = {
      ...otherKeys,
      lessons: await Promise.all(
          lessonIds.map(async lessonId => {
              const fetchedLesson = await api.get(`/lessons/${lessonId}`);
              return fetchedLesson.data;
          })
      )
  }
  return section;
}

// To display necessary data for course landing page, will need to fetch the course, all of its sections, and each section's lesson data
export const fetchCourseData = async courseId => {
  try {
      // Fetch course
      let course = await api.get(`/courses/${courseId}`);
      
      // Fetch sections
      course = await fetchSections(course);

      // Fetch lessons and populate them into sections
      course.sections = await Promise.all(
          course.sections.map(section => fetchLessons(section))
      );

      return sortCourse(course);
  } catch (err) {
      handleError(err);
  }
};