package org.avillar.gymtracker.authapi.auth.application.model;

import lombok.Data;

@Data
public class AuthControllerRequest {

    private String username;

    private String password;
}
