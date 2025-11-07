package com.example.mynt_finance_backend.authentication.controllers;

import com.example.mynt_finance_backend.authentication.domain.DTOs.AuthenticationDetailsDTO;
import com.example.mynt_finance_backend.authentication.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthenticationDetailsDTO authenticationDetails, HttpServletRequest request) {
        try {
            authenticationService.register(authenticationDetails, request);
            return new ResponseEntity<>("Registered & logged in", HttpStatus.OK);
        } catch (AuthenticationException ae) {
            // Catch any auto-login errors
            return ae.getMessage() == null ?
                    new ResponseEntity<>("Invalid authentication details", HttpStatus.UNAUTHORIZED) :
                    new ResponseEntity<>(ae.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDetailsDTO authenticationDetails, HttpServletRequest request) {
        try {
            authenticationService.login(authenticationDetails, request);
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        } catch (AuthenticationException ae) {
            return ae.getMessage() == null ?
                    new ResponseEntity<>("Invalid authentication details", HttpStatus.UNAUTHORIZED) :
                    new ResponseEntity<>(ae.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        authenticationService.logout(request);
        return new ResponseEntity<>("Logged out", HttpStatus.OK);
    }

    @GetMapping("/check-session")
    public ResponseEntity<?> checkSession(Authentication authentication) {
        if (authenticationService.checkSession(authentication)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid authentication details", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/session-data")
    public ResponseEntity<?> getSessionData(Authentication authentication) {
        if (authenticationService.checkSession(authentication)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return new ResponseEntity<>(authenticationService.getSessionData(userDetails), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid authentication details", HttpStatus.UNAUTHORIZED);
        }
    }
}
