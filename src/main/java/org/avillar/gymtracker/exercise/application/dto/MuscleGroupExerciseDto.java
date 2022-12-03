package org.avillar.gymtracker.exercise.application.dto;

import lombok.Data;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleGroupDto;

import java.io.Serializable;

@Data
public class MuscleGroupExerciseDto implements Serializable {

    private MuscleGroupDto muscleGroup;
    private ExerciseDto exercise;
    private Double weight;
}