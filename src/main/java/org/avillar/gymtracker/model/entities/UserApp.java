package org.avillar.gymtracker.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.model.enums.ActivityLevelEnum;
import org.avillar.gymtracker.model.enums.GenderEnum;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserApp extends BaseEntity {

    private String username;
    private String password;
    private String email;

    private String name;
    private String lastNameFirst;
    private String lastNameSecond;
    private Date birth;
    private GenderEnum gender;

    private ActivityLevelEnum activityLevel;


    @OneToMany(mappedBy = "userApp", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Program> programs = new HashSet<>();

    @OneToMany(mappedBy = "userApp", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Measure> measures = new HashSet<>();

}
