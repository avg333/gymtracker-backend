package org.avillar.gymtracker.user.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.avillar.gymtracker.enums.domain.ActivityLevelEnum;
import org.avillar.gymtracker.enums.domain.GenderEnum;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAppDto {
    private static final String type = "Bearer";
    private String token;
    private Long id;

    private String username;
    private String password;
    private String email;
    private String name;
    private String lastNameFirst;
    private String lastNameSecond;
    private GenderEnum gender;
    private ActivityLevelEnum activityLevel;
    private MultipartFile image;

    public UserAppDto(Long id) {
        this.id = id;
    }
}