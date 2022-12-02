package org.avillar.gymtracker.musclegroup.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
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
public class MuscleSupGroup extends BaseEntity {
    private String name;
    private String description;

    @ManyToMany(mappedBy = "muscleSupGroups")
    private Set<MuscleGroup> muscleGroups = new HashSet<>();

}