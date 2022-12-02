package org.avillar.gymtracker.exercise.application;

import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.exercise.domain.Exercise;
import org.avillar.gymtracker.exercise.domain.ExerciseDao;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleGroupDto;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleSubGroupDto;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleSupGroupDto;
import org.avillar.gymtracker.musclegroup.domain.MuscleGroup;
import org.avillar.gymtracker.musclegroup.domain.MuscleGroupExercise;
import org.avillar.gymtracker.musclegroup.domain.MuscleSubGroup;
import org.avillar.gymtracker.musclegroup.domain.MuscleSupGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExerciseServiceImpl extends BaseService implements ExerciseService {

    private static final String EX_FOUND_ERROR_MSG = "The exercise does not exist";

    private final ExerciseDao exerciseDao;

    @Autowired
    public ExerciseServiceImpl(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<ExerciseDto> getAllExercises(ExerciseFilterDto exerciseFilterDto) {
        final ExerciseFilterDto finalExerciseFilterDto = exerciseFilterDto != null
                ? exerciseFilterDto
                : new ExerciseFilterDto();

        return this.exerciseDao.findAll()
                .stream()
                .filter(exercise -> this.applyFilter(finalExerciseFilterDto, exercise))
                .map(this::getExerciseMetadata)
                .toList();
    }

    private boolean applyFilter(ExerciseFilterDto exerciseFilterDto, Exercise exercise) {
        boolean cumple = true;
        if (exerciseFilterDto.getName() != null && exerciseFilterDto.getName().length() > 0) {
            cumple = exercise.getName().contains(exerciseFilterDto.getName());
        }
        if (cumple && exerciseFilterDto.getUnilateral() != null) {
            cumple = Objects.equals(exercise.getUnilateral(), exerciseFilterDto.getUnilateral());
        }
        if (cumple && exerciseFilterDto.getLoadType() != null) {
            cumple = exercise.getLoadType() == exerciseFilterDto.getLoadType();
        }
        if (cumple && exerciseFilterDto.getMuscleGroupIds() != null && !exerciseFilterDto.getMuscleGroupIds().isEmpty()) {
            final List<Long> muscleGroupIds = exercise
                    .getMuscleGroupExercises()
                    .stream()
                    .map(mge -> mge.getMuscleGroup().getId())
                    .toList();
            cumple = this.estaEnLista(exerciseFilterDto.getMuscleGroupIds(), muscleGroupIds);
        }
        if (cumple && exerciseFilterDto.getMuscleSubGroupIds() != null && !exerciseFilterDto.getMuscleSubGroupIds().isEmpty()) {
            final List<Long> muscleSubGroupIds = exercise.getMuscleSubGroups().stream().map(MuscleSubGroup::getId).toList();
            cumple = this.estaEnLista(exerciseFilterDto.getMuscleSubGroupIds(), muscleSubGroupIds);
        }
        if (cumple && exerciseFilterDto.getMuscleSupGroupIds() != null && !exerciseFilterDto.getMuscleSupGroupIds().isEmpty()) {
            final List<Long> muscleSupGroupIds = exercise
                    .getMuscleGroupExercises()
                    .stream()
                    .map(mge -> mge.getMuscleGroup().getMuscleSupGroups().iterator().next().getId()).toList();
            cumple = this.estaEnLista(exerciseFilterDto.getMuscleSupGroupIds(), muscleSupGroupIds);
        }
        return cumple;
    }

    private boolean estaEnLista(final List<Long> elementosBuscados, final List<Long> elementosDelElemento) {
        for (final long elementoBuscado : elementosBuscados) {
            if (elementosDelElemento.contains(elementoBuscado))
                return true;
        }
        return false;
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public ExerciseDto getExercise(final Long exerciseId) {
        final Exercise exercise = this.exerciseDao.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(EX_FOUND_ERROR_MSG));
        return this.getExerciseMetadata(exercise);
    }

    private ExerciseDto getExerciseMetadata(final Exercise exercise) {
        final ExerciseDto exerciseDto = this.modelMapper.map(exercise, ExerciseDto.class);
        final Set<MuscleGroup> muscleGroups = new HashSet<>();
        final Set<MuscleSupGroup> muscleSupGroups = new HashSet<>();
        for (final MuscleGroupExercise muscleGroupExercise : exercise.getMuscleGroupExercises()) {
            if(muscleGroupExercise.getWeight() > VolumeConstants.MIN_VOL_FOR_EX){
                muscleGroups.add(muscleGroupExercise.getMuscleGroup());
                muscleSupGroups.addAll(muscleGroupExercise.getMuscleGroup().getMuscleSupGroups());
            }
        }
        exerciseDto.setMuscleSupGroups(
                muscleSupGroups
                        .stream()
                        .map(mgs -> this.modelMapper.map(mgs, MuscleSupGroupDto.class))
                        .collect(Collectors.toSet())
        );
        exerciseDto.setMuscleGroups(
                muscleGroups
                        .stream()
                        .map(mg -> this.modelMapper.map(mg, MuscleGroupDto.class))
                        .collect(Collectors.toSet())
        );
        exerciseDto.setMuscleSubGroups(
                exercise.getMuscleSubGroups()
                        .stream()
                        .map(msg -> this.modelMapper.map(msg, MuscleSubGroupDto.class))
                        .collect(Collectors.toSet())
        );
        return exerciseDto;
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public ExerciseDto createExercise(final ExerciseDto exerciseDto) {
        final Exercise exercise = this.modelMapper.map(exerciseDto, Exercise.class);
        return this.modelMapper.map(this.exerciseDao.save(exercise), ExerciseDto.class);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public ExerciseDto updateExercise(final ExerciseDto exerciseDto) {
        final Exercise exercise = this.modelMapper.map(exerciseDto, Exercise.class);
        return this.modelMapper.map(this.exerciseDao.save(exercise), ExerciseDto.class);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteExercise(final Long exerciseId) {
        this.exerciseDao.deleteById(exerciseId);
    }
}