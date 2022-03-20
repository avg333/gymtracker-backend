package org.avillar.gymtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MuscleSubGroup {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "muscle_group_id")
    private MuscleGroup muscleGroup;

    @JsonIgnore
    @ManyToMany(mappedBy = "muscleSubGroups")
    private Set<Exercise> exercises = new LinkedHashSet<>();

}
