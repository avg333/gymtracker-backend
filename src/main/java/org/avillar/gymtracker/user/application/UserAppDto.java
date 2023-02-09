package org.avillar.gymtracker.user.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.application.BaseDto;
import org.avillar.gymtracker.enums.domain.ActivityLevelEnum;
import org.avillar.gymtracker.enums.domain.GenderEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAppDto extends BaseDto {
    private static final String type = "Bearer";
    private String token;

    private String username;
    private String password;
    private String email;
    private String name;
    private String lastNameFirst;
    private String lastNameSecond;
    private GenderEnum gender;
    private ActivityLevelEnum activityLevel;

    public UserAppDto(Long id) {
        super(id);
    }
}