package com.sac.expensetracking.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.sac.expensetracking.security.services.UserDetailsServiceImpl;

import java.io.IOException;

public class AuthorizationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationTokenFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);

            logger.info(
                    "JWT filter: method={}, uri={}, authHeaderPresent={}, jwtPresent={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getHeader("Authorization") != null,
                    jwt != null
            );

            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        // 1. Try standard header casing
        String headerAuth = request.getHeader("Authorization");

        // 2. Fallback to lowercase in case Azure's proxy normalizes headers
        if (!StringUtils.hasText(headerAuth)) {
            headerAuth = request.getHeader("authorization");
        }

        // 3. Safely extract and trim trailing proxy artifacts
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            // Using substring(7).trim() discards the strict length check
            // and drops any invisible trailing carriage returns (\r\n) or spaces
            return headerAuth.substring(7).trim();
        }

        return null;
    }


   /* private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }*/
}
