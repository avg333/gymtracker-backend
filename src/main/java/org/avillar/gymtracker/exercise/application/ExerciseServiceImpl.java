package org.avillar.gymtracker.exercise.application;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.exercise.application.dto.ExerciseDto;
import org.avillar.gymtracker.exercise.application.dto.ExerciseFilterDto;
import org.avillar.gymtracker.exercise.application.dto.ExerciseMapper;
import org.avillar.gymtracker.exercise.application.dto.ExerciseValidator;
import org.avillar.gymtracker.exercise.domain.Exercise;
import org.avillar.gymtracker.exercise.domain.ExerciseDao;
import org.avillar.gymtracker.musclegroup.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ExerciseServiceImpl extends BaseService implements ExerciseService {

    private static final String EX_FOUND_ERROR_MSG = "The exercise does not exist";

    private final ExerciseDao exerciseDao;
    private final MuscleGroupExerciseDao muscleGroupExerciseDao;
    private final ExerciseMapper exerciseMapper;
    private final ExerciseValidator exerciseValidator;

    @Autowired
    public ExerciseServiceImpl(ExerciseDao exerciseDao, MuscleGroupExerciseDao muscleGroupExerciseDao,
                               ExerciseMapper exerciseMapper, ExerciseValidator exerciseValidator) {
        this.exerciseDao = exerciseDao;
        this.muscleGroupExerciseDao = muscleGroupExerciseDao;
        this.exerciseValidator = exerciseValidator;
        this.exerciseMapper = exerciseMapper;
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
                .map(exercise -> this.exerciseMapper.toDto(exercise, false))
                .toList();
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public ExerciseDto getExercise(final Long exerciseId) throws EntityNotFoundException, IllegalAccessException {
        final Exercise exercise = this.exerciseDao.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(EX_FOUND_ERROR_MSG));
        this.authService.checkAccess(exercise);
        return this.exerciseMapper.toDto(exercise, true);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public ExerciseDto createExercise(final ExerciseDto exerciseDto) throws EntityNotFoundException {
        if(!this.exerciseValidator.validate(exerciseDto, new HashMap<>()).isEmpty()){
            throw new RuntimeException("El ejercicio esta mal formado");
        }

        final Exercise exercise = this.exerciseMapper.toEntity(exerciseDto);
        final Exercise exerciseDb = this.exerciseDao.save(exercise);
        exerciseDb.setMuscleGroupExercises(this.saveMuscleGroupExercises(exercise.getMuscleGroupExercises(), exerciseDb));
        return this.exerciseMapper.toDto(exerciseDb, true);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public ExerciseDto updateExercise(final ExerciseDto exerciseDto) throws EntityNotFoundException, IllegalAccessException {
        if(!this.exerciseValidator.validate(exerciseDto, new HashMap<>()).isEmpty()){
            throw new RuntimeException("El ejercicio esta mal formado");
        }

        Exercise exerciseDb = this.exerciseDao.findById(exerciseDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(EX_FOUND_ERROR_MSG));
        this.authService.checkAccess(exerciseDb);

        final Exercise exercise = this.exerciseMapper.toEntity(exerciseDto);
        exerciseDb = this.exerciseDao.save(exercise);
        exerciseDb.setMuscleGroupExercises(this.saveMuscleGroupExercises(exercise.getMuscleGroupExercises(), exerciseDb));
        return this.exerciseMapper.toDto(exerciseDb, true);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteExercise(final Long exerciseId) throws EntityNotFoundException, IllegalAccessException {
        final Exercise exercise = this.exerciseDao.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(EX_FOUND_ERROR_MSG));
        this.authService.checkAccess(exercise);
        this.exerciseDao.deleteById(exerciseId);
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
        return elementosBuscados.stream().anyMatch(elementosDelElemento::contains);
    }

    private Set<MuscleGroupExercise> saveMuscleGroupExercises(final Set<MuscleGroupExercise> muscleGroupExercises,
                                                              final Exercise exercise) {
        muscleGroupExercises.forEach(mge -> mge.setExercise(exercise));
        return new HashSet<>(this.muscleGroupExerciseDao.saveAll(muscleGroupExercises));
    }
}