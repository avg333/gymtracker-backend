package org.avillar.gymtracker.musclegroup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.domain.BaseEntity;

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

    @ManyToMany
    @JoinTable(name = "muscle_group_muscle_sup_groups",
            joinColumns = @JoinColumn(name = "muscle_group_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_sup_groups_id"))
    private Set<MuscleSupGroup> muscleSupGroups = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "muscleGroup", orphanRemoval = true)
    private Set<MuscleSubGroup> muscleSubGroups = new HashSet<>();

    @OneToMany(mappedBy = "muscleGroup", orphanRemoval = true)
    private Set<MuscleGroupExercise> muscleGroupExercises = new HashSet<>();

}