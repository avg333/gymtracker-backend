package org.avillar.gymtracker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ExerciseDto {

    private Long id;
    private String name;
    private String description;
    private List<ExerciseDto> exercises = new ArrayList<>();
    private List<MuscleGroupDto> muscleGroups = new ArrayList<>();
    private List<MuscleSubGroupDto> muscleSubGroups = new ArrayList<>();
}
