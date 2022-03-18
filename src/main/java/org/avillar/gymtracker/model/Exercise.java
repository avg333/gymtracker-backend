package org.avillar.gymtracker.model;

import com.sun.istack.NotNull;
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

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @NotNull
    private Boolean unilateral = false;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "load_type_id", nullable = false)
    private LoadType loadType;

    @OneToOne(mappedBy = "exercise", orphanRemoval = true)
    private Set set;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
