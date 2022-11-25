package org.avillar.gymtracker.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MuscleSupGroup extends BaseEntity {
    private String name;
    private String description;

    @OneToMany(mappedBy = "muscleSupGroup", orphanRemoval = true)
    private Set<MuscleGroup> muscleGroups = new LinkedHashSet<>();

}