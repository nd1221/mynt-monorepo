package com.example.mynt_finance_backend.authentication.services;

import com.example.mynt_finance_backend.authentication.domain.DTOs.AuthenticationDetailsDTO;
import com.example.mynt_finance_backend.authentication.domain.DTOs.SessionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {

    void register(AuthenticationDetailsDTO AuthenticationDetails, HttpServletRequest request);

    void login(AuthenticationDetailsDTO AuthenticationDetails, HttpServletRequest request);

    void logout(HttpServletRequest request);

    boolean checkSession(Authentication authentication);

    SessionDTO getSessionData(UserDetails userDetails);
}
