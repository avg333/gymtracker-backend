package org.avillar.gymtracker.musclegroup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.domain.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MuscleGroup extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "muscle_group_muscle_sup_groups",
            joinColumns = @JoinColumn(name = "muscle_group_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_sup_groups_id"))
    private Set<MuscleSupGroup> muscleSupGroups = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "muscleGroup", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<MuscleSubGroup> muscleSubGroups = new HashSet<>();

    @OneToMany(mappedBy = "muscleGroup", orphanRemoval = true)
    private Set<MuscleGroupExercise> muscleGroupExercises = new HashSet<>();

}