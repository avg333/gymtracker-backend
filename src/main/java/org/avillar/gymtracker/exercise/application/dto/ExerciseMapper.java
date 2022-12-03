package org.avillar.gymtracker.exercise.application.dto;

import org.avillar.gymtracker.exercise.domain.Exercise;

import java.util.Collection;
import java.util.List;

public interface ExerciseMapper {

    List<ExerciseDto> toDtos(Collection<Exercise> objects, boolean nested);

    ExerciseDto toDto(Exercise exercise, boolean nested);

    Exercise toEntity(ExerciseDto exerciseDto);

}
