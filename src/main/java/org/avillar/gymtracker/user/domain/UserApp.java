package org.avillar.gymtracker.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.domain.BaseEntity;
import org.avillar.gymtracker.enums.domain.ActivityLevelEnum;
import org.avillar.gymtracker.enums.domain.GenderEnum;
import org.avillar.gymtracker.image.domain.Image;
import org.avillar.gymtracker.measure.domain.Measure;
import org.avillar.gymtracker.program.domain.Program;
import org.avillar.gymtracker.workout.domain.Workout;

import javax.persistence.*;
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

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToMany(mappedBy = "userApp", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Program> programs = new HashSet<>();

    @OneToMany(mappedBy = "userApp", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Workout> workouts = new HashSet<>();

    @OneToMany(mappedBy = "userApp", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Measure> measures = new HashSet<>();

}