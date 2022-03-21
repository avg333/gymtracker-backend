package org.avillar.gymtracker.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Exercise {

    public Exercise(String name, String description, Boolean unilateral, LoadType loadType, java.util.Set<MuscleGroup> muscleGroups, java.util.Set<MuscleSubGroup> muscleSubGroups) {
        this.name = name;
        this.description = description;
        this.unilateral = unilateral;
        this.loadType = loadType;
        this.muscleGroups = muscleGroups;
        this.muscleSubGroups = muscleSubGroups;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @NotNull
    private Boolean unilateral = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "load_type_id", nullable = false)
    private LoadType loadType;

    @ManyToMany
    @JoinTable(name = "exercise_muscle_groups",
            joinColumns = @JoinColumn(name = "exercise_null"),
            inverseJoinColumns = @JoinColumn(name = "muscle_groups_id"))
    private java.util.Set<MuscleGroup> muscleGroups = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "exercise_muscle_sub_groups",
            joinColumns = @JoinColumn(name = "exercise_null"),
            inverseJoinColumns = @JoinColumn(name = "muscle_sub_groups_id"))
    private java.util.Set<MuscleSubGroup> muscleSubGroups = new LinkedHashSet<>();

    @OneToMany(mappedBy = "exercise", orphanRemoval = true)
    private List<Set> sets = new ArrayList<>();

}
