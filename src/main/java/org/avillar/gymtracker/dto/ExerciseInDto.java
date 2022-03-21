package org.avillar.gymtracker.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class ExerciseInDto {

    private Long id;
    private String name;
    private String description;
    private Boolean unilateral;
    private Long loadType;
    private Long muscleGroups;
    private Long muscleSubGroups;
}
