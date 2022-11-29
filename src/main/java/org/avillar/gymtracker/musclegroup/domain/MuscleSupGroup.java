package org.avillar.gymtracker.musclegroup.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.domain.BaseEntity;

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