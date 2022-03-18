package org.avillar.gymtracker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MuscleGroupDto{

    private Long id;
    private String name;
    private String description;
    private List<MuscleSubGroupDto> muscleSubGroups = new ArrayList<>();
    private List<ExerciseDto> exercises = new ArrayList<>();
}
