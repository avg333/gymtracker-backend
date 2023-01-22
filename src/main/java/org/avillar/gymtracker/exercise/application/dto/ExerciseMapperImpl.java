package org.avillar.gymtracker.exercise.application.dto;

import org.avillar.gymtracker.exercise.domain.Exercise;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleGroupMapper;
import org.avillar.gymtracker.musclegroup.domain.MuscleGroupExercise;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
public class ExerciseMapperImpl implements ExerciseMapper {

    private final MuscleGroupMapper muscleGroupMapper;

    public ExerciseMapperImpl(MuscleGroupMapper muscleGroupMapper) {
        this.muscleGroupMapper = muscleGroupMapper;
    }

    @Override
    public List<ExerciseDto> toDtos(final Collection<Exercise> exercises, final int depth) {
        if (CollectionUtils.isEmpty(exercises)) {
            return Collections.emptyList();
        }

        return exercises.stream().map(exercise -> this.toDto(exercise, depth)).toList();
    }

    @Override
    public ExerciseDto toDto(final Exercise exercise, final int depth) {
        if (exercise == null) {
            return null;
        }

        ExerciseDto exerciseDto = new ExerciseDto();
        exerciseDto.setId(exercise.getId());
        exerciseDto.setName(exercise.getName());
        exerciseDto.setDescription(exercise.getDescription());
        exerciseDto.setUnilateral(exercise.getUnilateral());
        exerciseDto.setLoadType(exercise.getLoadType());
        exerciseDto.setMuscleSubGroups(depth != 0
                ? this.muscleGroupMapper.toDtos(exercise.getMuscleSubGroups(), false)
                : Collections.emptyList());
        exerciseDto.setMuscleGroupExercises(depth != 0
                ? this.muscleGroupExercisesToDtos(exercise.getMuscleGroupExercises())
                : Collections.emptyList());

        return exerciseDto;
    }

    private List<MuscleGroupExerciseDto> muscleGroupExercisesToDtos(final Collection<MuscleGroupExercise> muscleGroupExercises) {
        if (CollectionUtils.isEmpty(muscleGroupExercises)) {
            return Collections.emptyList();
        }

        final List<MuscleGroupExerciseDto> muscleGroupExercisesDtos = new ArrayList<>(muscleGroupExercises.size());

        for (final MuscleGroupExercise muscleGroupExercise : muscleGroupExercises) {

            final MuscleGroupExerciseDto muscleGroupExerciseDto = new MuscleGroupExerciseDto();
            muscleGroupExerciseDto.setExercise(null);
            muscleGroupExerciseDto.setMuscleGroup(this.muscleGroupMapper.toDto(muscleGroupExercise.getMuscleGroup(), true));
            muscleGroupExerciseDto.setWeight(muscleGroupExercise.getWeight());

            muscleGroupExercisesDtos.add(muscleGroupExerciseDto);
        }

        return muscleGroupExercisesDtos;
    }

    @Override
    public Exercise toEntity(ExerciseDto exerciseDto) {
        if (exerciseDto == null) {
            return null;
        }

        Exercise exercise = new Exercise();
        exercise.setId(exerciseDto.getId());
        exercise.setName(exerciseDto.getName());
        exercise.setDescription(exerciseDto.getDescription());
        exercise.setUnilateral(exerciseDto.getUnilateral());
        exercise.setLoadType(exerciseDto.getLoadType());
        exercise.setMuscleSubGroups(this.muscleGroupMapper.toEntities(exerciseDto.getMuscleSubGroups()));
        exercise.setMuscleGroupExercises(this.muscleGroupExerciseDtosToEntities(exerciseDto.getMuscleGroupExercises()));

        return exercise;
    }

    private Set<MuscleGroupExercise> muscleGroupExerciseDtosToEntities(final Collection<MuscleGroupExerciseDto> muscleGroupExerciseDtos) {
        if (CollectionUtils.isEmpty(muscleGroupExerciseDtos)) {
            return new HashSet<>();
        }

        final Set<MuscleGroupExercise> muscleGroupExercises = new HashSet<>(muscleGroupExerciseDtos.size());

        for (final MuscleGroupExerciseDto muscleGroupExerciseDto : muscleGroupExerciseDtos) {

            final MuscleGroupExercise muscleGroupExercise = new MuscleGroupExercise();
            muscleGroupExercise.setExercise(this.toEntity(muscleGroupExerciseDto.getExercise()));
            muscleGroupExercise.setMuscleGroup(this.muscleGroupMapper.toEntity(muscleGroupExerciseDto.getMuscleGroup()));
            muscleGroupExercise.setWeight(muscleGroupExerciseDto.getWeight());

            muscleGroupExercises.add(muscleGroupExercise);
        }

        return muscleGroupExercises;
    }

}
