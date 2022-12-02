package org.avillar.gymtracker.exercise.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.domain.BaseEntity;
import org.avillar.gymtracker.enums.domain.LoadTypeEnum;
import org.avillar.gymtracker.musclegroup.domain.MuscleGroupExercise;
import org.avillar.gymtracker.musclegroup.domain.MuscleSubGroup;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Exercise extends BaseEntity {
    private String name;
    private String description;
    private Boolean unilateral = false;
    private LoadTypeEnum loadType;

    @OneToMany(mappedBy = "exercise", orphanRemoval = true)
    private Set<MuscleGroupExercise> muscleGroupExercises = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "exercise_muscle_sub_groups",
            joinColumns = @JoinColumn(name = "exercise_null"),
            inverseJoinColumns = @JoinColumn(name = "muscle_sub_groups_id"))
    private java.util.Set<MuscleSubGroup> muscleSubGroups = new HashSet<>();

    public Exercise(String name, String description, Boolean unilateral, LoadTypeEnum loadType, java.util.Set<MuscleSubGroup> muscleSubGroups) {
        this.name = name;
        this.description = description;
        this.unilateral = unilateral;
        this.loadType = loadType;
        this.muscleSubGroups = muscleSubGroups;
    }

}
