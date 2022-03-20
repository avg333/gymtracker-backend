package org.avillar.gymtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MuscleGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "muscleGroup", orphanRemoval = true)
    private Set<MuscleSubGroup> muscleSubGroups = new LinkedHashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "muscleGroups")
    private Set<Exercise> exercises = new LinkedHashSet<>();

}
