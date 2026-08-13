package controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payload.request.LoginRequestPayload;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {


    @Autowired
    AuthenticationManager authenticationManager;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(LoginRequestPayload loginRequestPayload){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestPayload.getUsername(), loginRequestPayload.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt;
        return null;
    }
}
