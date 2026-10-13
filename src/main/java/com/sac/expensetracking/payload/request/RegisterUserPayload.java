package com.sac.expensetracking.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserPayload
{
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
