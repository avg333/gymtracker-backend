package org.avillar.gymtracker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.model.MuscleGroup;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ExerciseDto {

    private Long id;
    private String name;
    private String description;
    private Boolean unilateral;
    private LoadTypeDto loadTypeDto;
    private java.util.Set<MuscleGroupDto> muscleGroups = new LinkedHashSet<>();
    private java.util.Set<MuscleSubGroupDto> muscleSubGroups = new LinkedHashSet<>();
}
