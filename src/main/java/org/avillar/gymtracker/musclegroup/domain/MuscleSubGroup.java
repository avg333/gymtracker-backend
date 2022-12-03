package org.avillar.gymtracker.musclegroup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.domain.BaseEntity;
import org.avillar.gymtracker.exercise.domain.Exercise;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MuscleSubGroup extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "muscle_group_id")
    private MuscleGroup muscleGroup;

    @JsonIgnore
    @ManyToMany(mappedBy = "muscleSubGroups")
    private Set<Exercise> exercises = new HashSet<>();

}