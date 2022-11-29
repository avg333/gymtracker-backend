package org.avillar.gymtracker.musclegroup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.domain.BaseEntity;
import org.avillar.gymtracker.exercise.domain.Exercise;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MuscleGroup extends BaseEntity {
    private String name;
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "muscle_sup_group_id", nullable = false)
    private MuscleSupGroup muscleSupGroup;

    @JsonIgnore
    @OneToMany(mappedBy = "muscleGroup", orphanRemoval = true)
    private Set<MuscleSubGroup> muscleSubGroups = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "muscleGroups")
    private Set<Exercise> exercises = new HashSet<>();

}