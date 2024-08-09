package com.example.TravelJournal.TravelJournal.Configuration;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
    public class JwtAuthenticationFilter extends OncePerRequestFilter {

        @Autowired
        private JwtUtil jwtUtil;

        @Autowired
        private CustomUserDetailsService customUserDetailsService;

    // Define the endpoints to be skipped
    private static final List<String> EXCLUDE_URLS = Arrays.asList(
            "/users/register",
            "/users/login"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    final String requestTokenHeader = request.getHeader("Authorization");
    String username=null;
    String jwtToken= null;
        // Skip JWT authentication for specified URLs
        if (EXCLUDE_URLS.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

    if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
        jwtToken = requestTokenHeader.substring(7);
        try {
            username = jwtUtil.extractUsername(jwtToken);
            logger.info("Extracted username from token: {}");

        } catch (IllegalArgumentException e) {
            System.out.println("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired");
        } catch (SignatureException e) {
            System.out.println("JWT Token signature is invalid");
        } catch (MalformedJwtException e) {
            System.out.println("JWT Token is malformed");
        }
    }else {
        logger.warn("JWT Token does not begin with Bearer String");
    }

    //validate the token,once recieved
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

            // if token is valid, configure Spring Security to manually set authentication
            if (jwtUtil.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify that the current user is authenticated.
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("User {} authenticated successfully");

            }
        }
        filterChain.doFilter(request, response); //cause for user controller functions not working while (eg:GET user/username) request is sent

    }
}

