package com.myquickfixj.springsecuritypractice.security;

import com.myquickfixj.springsecuritypractice.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Constant for the "Bearer " prefix in the Authorization header
    private static final String BEARER_PREFIX = "Bearer ";

    // Dependencies for JWT processing and user details retrieval
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Constructor for dependency injection
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Step 1: Extract the Authorization header from the request
            String authorizationHeader = request.getHeader("Authorization");
            System.out.println("Authorization Header: " + authorizationHeader); // Debugging line to check the header

            // Step 2: Check if the header starts with "Bearer " and extract the JWT token
            if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
                String jwtToken = authorizationHeader.substring(BEARER_PREFIX.length()); // Remove "Bearer " prefix
                String username = jwtService.extractUsername(jwtToken); // Extract username from the token

                // Step 3: If the username is valid and no authentication exists in the SecurityContextHolder
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Validate the token and authenticate the user
                    validateAndAuthenticateUser(jwtToken, username, request);
                }
            }
            else {
                throw new AuthenticationException("Token is not present or does not start with 'Bearer '") {
                    // Custom exception for token validation failure
                };
            }
        } catch (Exception e) {
            // Log the exception (optional: handle specific exceptions if needed)
            e.printStackTrace();
        }

        // Step 5: Call filterChain.doFilter to pass the request to the next filter
        filterChain.doFilter(request, response);
    }

    /**
     * Validates the JWT token and sets the authentication in the SecurityContextHolder.
     *
     * @param jwtToken The JWT token extracted from the request
     * @param username The username extracted from the token
     * @param request  The current HTTP request
     */
    private void validateAndAuthenticateUser(String jwtToken, String username, HttpServletRequest request) {
        // Load user details using UserDetailsService
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Step 4: Validate the token using JwtService
        if (jwtService.validateToken(jwtToken, username)) {
            // Create an Authentication object with the user's details and authorities
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // Set additional details (e.g., request metadata) and set the authentication in the SecurityContextHolder
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }
}