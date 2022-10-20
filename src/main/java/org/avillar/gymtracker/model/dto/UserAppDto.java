package org.avillar.gymtracker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAppDto {
    private static final String type = "Bearer";
    private Long id;
    private String token;
    private String username;
    private String password;
    private String email;

}
