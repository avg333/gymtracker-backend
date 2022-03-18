package org.avillar.gymtracker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "muscle_group_id")
    private MuscleGroup muscleGroup;

    @ManyToMany(mappedBy = "muscleSubGroups")
    private List<Exercise> exercises = new ArrayList<>();

}
