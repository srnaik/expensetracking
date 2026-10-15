package com.sac.expensetracking.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private UUID id;
    private String email;
    private String firstName;

    public JwtResponse(String accessToken, UUID id, String email, String firstName) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
        this.firstName = firstName;
    }

}
