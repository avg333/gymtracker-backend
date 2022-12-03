package org.avillar.gymtracker.exercise.application.dto;

import org.avillar.gymtracker.base.domain.BaseEntity;
import org.avillar.gymtracker.exercise.domain.Exercise;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleGroupDto;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleSubGroupDto;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleSupGroupDto;
import org.avillar.gymtracker.musclegroup.domain.MuscleGroup;
import org.avillar.gymtracker.musclegroup.domain.MuscleSubGroup;
import org.avillar.gymtracker.musclegroup.domain.MuscleSupGroup;

import java.util.Collection;
import java.util.List;

public interface ExerciseMapper {

    List<ExerciseDto> toDtos(Collection<Exercise> objects, boolean nested);

    ExerciseDto toDto(Exercise exercise, boolean nested);

    Exercise toEntity(ExerciseDto exerciseDto);

}
