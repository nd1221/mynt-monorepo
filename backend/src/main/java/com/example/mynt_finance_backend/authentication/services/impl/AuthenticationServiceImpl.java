package com.example.mynt_finance_backend.authentication.services.impl;

import com.example.mynt_finance_backend.authentication.domain.DTOs.AuthenticationDetailsDTO;
import com.example.mynt_finance_backend.authentication.domain.DTOs.SessionDTO;
import com.example.mynt_finance_backend.authentication.services.AuthenticationService;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LearningPathEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.UserEntity;
import com.example.mynt_finance_backend.learningPlatform.services.UserService;
import com.example.mynt_finance_backend.progressTracking.domain.entities.CourseProgressTracker;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void register(AuthenticationDetailsDTO authenticationDetails, HttpServletRequest request) {

        // Create user
        if (userService.userExistsByEmail(authenticationDetails.email())) {
            throw new BadCredentialsException("Email already exists.");
        }
        userService.createUser(authenticationDetails);

        // Auto-login
        login(authenticationDetails, request);
    }

    @Override
    public void login(AuthenticationDetailsDTO authenticationDetails, HttpServletRequest request) {

        // Invalidate any existing sessions if user logins in twice
        HttpSession existingSession = request.getSession(false);
        if (existingSession != null) {
            existingSession.invalidate();
        }
        SecurityContextHolder.clearContext();

        // Create authentication object, will throw AuthenticationException if invalid credentials
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationDetails.email(),
                        authenticationDetails.password()
                )
        );

        // Set authentication object and create session
        SecurityContextHolder.getContext().setAuthentication(auth);
        // Must ensure that Spring Security saves the authentication into a session manually
        // SS expects the FilterChain to handle updating and persisting the security context to the session
        // However, we are manually logging the user in after filters have run, so must do this manually as well
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }

    @Override
    public void logout(HttpServletRequest request) {

        // Invalidate and clear session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // Must clear SecurityContextHolder otherwise thread is returned to threadpool containing pre-authenticated details
        SecurityContextHolder.clearContext();
    }

    @Override
    public boolean checkSession(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }

    @Override
    public SessionDTO getSessionData(UserDetails userDetails) {
        String email = userDetails.getUsername();
        UserEntity user = userService.findUserByEmail(email).get(); // know user exists as we have called checkSession which validates authentication
        return new SessionDTO(
                user.getId(),
                true,
                user.getEmail(),
                user.getLearningPaths().stream().map(LearningPathEntity::getId).collect(Collectors.toSet()),
                user.getUserProgressTracker().getCourseProgressTrackers().stream().map(CourseProgressTracker::getId).collect(Collectors.toSet()),
                user.getCourses().stream().map(CourseEntity::getId).collect(Collectors.toSet()),
                user.getUserProgressTracker().getId()
        );
    }
}
