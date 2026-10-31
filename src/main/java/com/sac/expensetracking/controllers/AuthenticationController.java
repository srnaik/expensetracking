package com.sac.expensetracking.controllers;


import com.sac.expensetracking.models.User;
import com.sac.expensetracking.payload.request.LoginRequestPayload;
import com.sac.expensetracking.payload.request.RegisterUserPayload;
import com.sac.expensetracking.payload.response.JwtResponse;
import com.sac.expensetracking.payload.response.MessageResponse;
import com.sac.expensetracking.repository.UserRepository;
import com.sac.expensetracking.security.jwt.JwtUtils;
import com.sac.expensetracking.security.services.UserDetailsImpl;
import com.sac.expensetracking.service.TokenBlacklistService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenBlacklistService tokenBlacklistService;

    public static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestPayload loginRequestPayload) {

        logger.info("Login request for user{}", loginRequestPayload.getUsername());
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestPayload.getUsername(), loginRequestPayload.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails
                .getFirstName()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserPayload
                                                  registerUserPayload) {
        if (userRepository.existsByEmail(registerUserPayload.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email ID is already taken!"));
        }

        User user = new User(registerUserPayload.getEmail()
                , passwordEncoder.encode(registerUserPayload.getPassword()), registerUserPayload.getFirstName()
                , registerUserPayload.getLastName());

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User Registered successfully!"));
    }

    @PostMapping("/signout")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> signOut(@AuthenticationPrincipal UserDetailsImpl currentUser, HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Invalid authorization header!"));
        }

        String jwt = authHeader.substring(7);

        if (!jwtUtils.validateJwtToken(jwt)) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Invalid JWT token"));
        }

        tokenBlacklistService.blacklistToken(jwt);

        SecurityContextHolder.clearContext();

        logger.info("User signed out: {}", currentUser.getUsername());

        return ResponseEntity.ok(
                new MessageResponse("User signed out successfully!")
        );
    }
}
