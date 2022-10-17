package org.avillar.gymtracker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class UserAppDto {

    private final String type = "Bearer";
    private String token;
    private String username;
    private String password;
    private String email;

}
