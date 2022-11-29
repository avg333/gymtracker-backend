package org.avillar.gymtracker.exercise.application;

import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.exercise.domain.Exercise;
import org.avillar.gymtracker.exercise.domain.ExerciseDao;
import org.avillar.gymtracker.musclegroup.domain.MuscleGroup;
import org.avillar.gymtracker.musclegroup.domain.MuscleSubGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExerciseServiceImpl extends BaseService implements ExerciseService {

    private static final String NOT_FOUND_ERROR_MSG = "El programa no existe";

    private final ExerciseDao exerciseDao;

    @Autowired
    public ExerciseServiceImpl(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
    }

    @Override
    public List<ExerciseDto> getAllExercises(ExerciseFilterDto exerciseFilterDto) {
        final List<Exercise> exercises = this.exerciseDao.findAll();
        final List<ExerciseDto> exerciseDtos = new ArrayList<>();

        if (exerciseFilterDto == null) {
            exerciseFilterDto = new ExerciseFilterDto();
        }

        for (final Exercise exercise : exercises) {
            boolean cumple = true;
            if (exerciseFilterDto.getName() != null && exerciseFilterDto.getName().length() > 0) {
                cumple = exercise.getName().contains(exerciseFilterDto.getName());
            }
            if (cumple && exerciseFilterDto.getUnilateral() != null) {
                cumple = exercise.getUnilateral() == exerciseFilterDto.getUnilateral();
            }
            if (cumple && exerciseFilterDto.getLoadType() != null) {
                cumple = exercise.getLoadType() == exerciseFilterDto.getLoadType();
            }
            if (cumple && exerciseFilterDto.getMuscleGroupIds() != null && exerciseFilterDto.getMuscleGroupIds().size() > 0) {
                final List<Long> muscleGroupIds = exercise.getMuscleGroups().stream().map(MuscleGroup::getId).toList();
                cumple = this.estaEnLista(exerciseFilterDto.getMuscleGroupIds(), muscleGroupIds);
            }
            if (cumple && exerciseFilterDto.getMuscleSubGroupIds() != null && exerciseFilterDto.getMuscleSubGroupIds().size() > 0) {
                final List<Long> muscleSubGroupIds = exercise.getMuscleSubGroups().stream().map(MuscleSubGroup::getId).toList();
                cumple = this.estaEnLista(exerciseFilterDto.getMuscleSubGroupIds(), muscleSubGroupIds);
            }
            if (cumple && exerciseFilterDto.getMuscleSupGroupIds() != null && exerciseFilterDto.getMuscleSupGroupIds().size() > 0) {
                final List<Long> muscleSupGroupIds = exercise.getMuscleGroups().stream().map(mg -> mg.getMuscleSupGroup().getId()).toList();
                cumple = this.estaEnLista(exerciseFilterDto.getMuscleSupGroupIds(), muscleSupGroupIds);
            }

            if (cumple)
                exerciseDtos.add(this.getExerciseMetadata(exercise));
        }

        return exerciseDtos;
    }

    private boolean estaEnLista(final List<Long> elementosBuscados, final List<Long> elementosDelElemento) {
        for (final long elementoBuscado : elementosBuscados) {
            if (elementosDelElemento.contains(elementoBuscado))
                return true;
        }
        return false;
    }

    private ExerciseDto getExerciseMetadata(final Exercise exercise) {
        final ExerciseDto exerciseDto = this.modelMapper.map(exercise, ExerciseDto.class);
        final Set<String> muscleSupGroups = new HashSet<>();
        final Set<String> muscleGroups = new HashSet<>();
        final Set<String> muscleSubGroups = new HashSet<>();
        for (final MuscleGroup muscleGroup : exercise.getMuscleGroups()) {
            muscleSupGroups.add(muscleGroup.getMuscleSupGroup().getName());
            muscleGroups.add(muscleGroup.getName());
        }
        for (final MuscleSubGroup musclesubGroup : exercise.getMuscleSubGroups()) {
            muscleSubGroups.add(musclesubGroup.getName());
        }
        exerciseDto.setMuscleSupGroups(new ArrayList<>(muscleSupGroups));
        exerciseDto.setMuscleGroups(new ArrayList<>(muscleGroups));
        exerciseDto.setMuscleSubGroups(new ArrayList<>(muscleSubGroups));
        return exerciseDto;
    }

    @Override
    public ExerciseDto getExercise(final Long exerciseId) {
        final Exercise exercise = this.exerciseDao.findById(exerciseId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        return this.getExerciseMetadata(exercise);
    }

    @Override
    public ExerciseDto createExercise(final ExerciseDto exerciseDto) {
        final Exercise exercise = this.modelMapper.map(exerciseDto, Exercise.class);
        return this.modelMapper.map(this.exerciseDao.save(exercise), ExerciseDto.class);
    }

    @Override
    public ExerciseDto updateExercise(final ExerciseDto exerciseDto) {
        final Exercise exercise = this.modelMapper.map(exerciseDto, Exercise.class);
        return this.modelMapper.map(this.exerciseDao.save(exercise), ExerciseDto.class);
    }

    @Override
    public void deleteExercise(final Long exerciseId) {
        this.exerciseDao.deleteById(exerciseId);
    }
}