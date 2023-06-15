package org.avillar.gymtracker.authapi.auth.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserApp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String lastNameFirst;
    private String lastNameSecond;
    private Date birth;
    private GenderEnum gender;
    private ActivityLevelEnum activityLevel;

    public enum ActivityLevelEnum {
        SEDENTARY, LIGHT, MODERATE, HIGH, EXTREME
    }

    public enum GenderEnum {
        MALE, FEMALE
    }
}