package org.avillar.gymtracker.exercise.application.dto;

import org.avillar.gymtracker.exercise.domain.Exercise;

import java.util.Collection;
import java.util.List;

public interface ExerciseMapper {

    List<ExerciseDto> toDtos(Collection<Exercise> objects, int depth);

    ExerciseDto toDto(Exercise exercise, int depth);

    Exercise toEntity(ExerciseDto exerciseDto);

}
