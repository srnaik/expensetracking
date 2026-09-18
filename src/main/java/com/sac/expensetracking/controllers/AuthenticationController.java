package com.sac.expensetracking.controllers;


import com.sac.expensetracking.models.User;
import com.sac.expensetracking.payload.request.RegisterUserPayload;
import com.sac.expensetracking.payload.response.MessageResponse;
import com.sac.expensetracking.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.sac.expensetracking.payload.request.LoginRequestPayload;
import com.sac.expensetracking.payload.response.JwtResponse;
import com.sac.expensetracking.security.jwt.JwtUtils;
import com.sac.expensetracking.security.services.UserDetailsImpl;

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

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestPayload loginRequestPayload){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestPayload.getUsername(), loginRequestPayload.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getId(), userDetails.getUsername(),userDetails
                .getFirstName()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserPayload
                                           registerUserPayload){
        if (userRepository.existsByEmail(registerUserPayload.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email ID is already taken!"));
        }

        User user = new User(registerUserPayload.getEmail()
        , passwordEncoder.encode(registerUserPayload.getPassword()),registerUserPayload.getFirstName()
        ,registerUserPayload.getLastName());

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User Registered successfully!"));
    }
}
