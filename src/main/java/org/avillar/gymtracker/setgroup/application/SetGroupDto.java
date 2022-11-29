package org.avillar.gymtracker.setgroup.application;

import lombok.Data;
import org.avillar.gymtracker.exercise.application.ExerciseDto;
import org.avillar.gymtracker.set.application.SetDto;

import java.util.List;

@Data
public class SetGroupDto {
    private Long id;
    private String description;
    private int listOrder;
    private Long exerciseId; //TODO Susituir esto por exerciseDto
    private Long sessionId;
    private Long workoutId;

    private ExerciseDto exerciseDto;
    private List<SetDto> setDtoList;
}
