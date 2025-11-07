import { RouterProvider, Route, createBrowserRouter, createRoutesFromElements, Navigate } from 'react-router-dom';
import Layout from './components/Layout';
import Home from './pages/Home';
import About from './pages/About';
import Courses from './pages/Courses';
import UserProfile, {loader as userProfileLoader} from './pages/UserProfile';
import UnitOverview, {loader as unitOverviewLoader} from './pages/UnitOverview';
import Register from './pages/Register';
import Login from './pages/Login';
import CourseLanding, {loader as courseLandingLoader} from './pages/CourseLanding';
import LearningPathLanding, {loader as learningPathLandingLoader} from './pages/LearningPathLanding';
import CourseUnit, {loader as unitContentLoader} from './pages/CourseUnit';
import QuestionPage, {loader as questionLoader} from './pages/QuestionPage';
import TestPage, {loader as testLoader} from './pages/TestPage';
import NotFound from './pages/NotFound';
import {loader as authLoader} from './components/AuthContext';
import QuestionBank, {loader as questionBankLoader} from './pages/QuestionBank';
import ReviewSession, {loader as reviewSessionLoader} from './pages/ReviewSession';

import './App.css'

function App() {

	const router = createBrowserRouter(createRoutesFromElements(
		<>
			<Route path="/" element={<Layout />} loader={authLoader}>
				<Route index element={<Home />}/>
				<Route path="about" element={<About />}/>
				<Route path="paths" element={<Courses />}/>
					<Route path="learning-paths/:id" element={<LearningPathLanding />} loader={learningPathLandingLoader}/>
					<Route path="courses/:id" element={<CourseLanding />} loader={courseLandingLoader}/>
						<Route path="course-units/:unitId/:courseProgressTrackerId" element={<CourseUnit />} loader={unitContentLoader}/>
							<Route path="unit-questions/:questionId/:lessonProgressTrackerId" element={<QuestionPage />} loader={questionLoader}/>
							<Route path="courses/:courseId/sections/:sectionId/section-test" element={<TestPage />} loader={testLoader}/>
				<Route path="user-profile/courses/:courseProgressTrackerId?" element={<UserProfile />} loader={userProfileLoader}/>
					<Route path="/user-profile/:courseProgressTrackerId/unit-overview/:lessonProgressTrackerId" element={<UnitOverview />} loader={unitOverviewLoader}/>
				<Route path="/question-bank/:courseProgressTrackerId" element={<QuestionBank />} loader={questionBankLoader}/>
				<Route path="/review-session/:courseProgressTrackerId" element={<ReviewSession />} loader={reviewSessionLoader}/>
				<Route path="register" element={<Register />}/>
				<Route path="login" element={<Login />} />
				<Route path="not-found" element={<NotFound />}/>
				<Route path="*" element={<Navigate to="/not-found" replace/>}/>
			</Route>
		</>
	));

	return (
		<RouterProvider router={router} />
	)
}

export default App
