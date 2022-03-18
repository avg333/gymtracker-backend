package org.avillar.gymtracker.dto;

import java.util.List;

public record ExerciseDto(Long id, String name, String description, Boolean unilateral, List<Long> idMuscleGroup,
                          Long idLoadType) {
}
