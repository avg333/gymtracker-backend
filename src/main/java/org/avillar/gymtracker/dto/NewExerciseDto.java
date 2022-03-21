package org.avillar.gymtracker.dto;

public record NewExerciseDto (Long id, String name, String description, Long idMuscleGroup, Long idSubMuscleGroup, Long idLoadType, Boolean unilateral){
}
