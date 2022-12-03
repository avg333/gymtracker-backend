package org.avillar.gymtracker.exercise.application;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.exercise.application.dto.ExerciseDto;
import org.avillar.gymtracker.exercise.application.dto.ExerciseFilterDto;
import org.avillar.gymtracker.exercise.application.dto.ExerciseMapper;
import org.avillar.gymtracker.exercise.domain.Exercise;
import org.avillar.gymtracker.exercise.domain.ExerciseDao;
import org.avillar.gymtracker.musclegroup.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExerciseServiceImpl extends BaseService implements ExerciseService {

    private static final String EX_FOUND_ERROR_MSG = "The exercise does not exist";
    private static final String MG_FOUND_ERROR_MSG = "The muscle group does not exist";
    private static final String MSG_FOUND_ERROR_MSG = "The muscle sub group does not exist";

    private final ExerciseDao exerciseDao;
    private final MuscleGroupExerciseDao muscleGroupExerciseDao;
    private final MuscleGroupDao muscleGroupDao;
    private final MuscleSubGroupDao muscleSubGroupDao;
    private final ExerciseMapper exerciseMapper;

    @Autowired
    public ExerciseServiceImpl(ExerciseDao exerciseDao, MuscleGroupExerciseDao muscleGroupExerciseDao,
                               MuscleGroupDao muscleGroupDao, MuscleSubGroupDao muscleSubGroupDao,
                               ExerciseMapper exerciseMapper) {
        this.exerciseDao = exerciseDao;
        this.muscleGroupExerciseDao = muscleGroupExerciseDao;
        this.muscleGroupDao = muscleGroupDao;
        this.muscleSubGroupDao = muscleSubGroupDao;
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

    // ------------ TODO por implementar todavia -------------------

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public ExerciseDto createExercise(final ExerciseDto exerciseDto) throws EntityNotFoundException {
        final Exercise exercise = this.exerciseMapper.toEntity(exerciseDto);
        this.muscleSubGroupsExists(exercise.getMuscleSubGroups());
        final Exercise exerciseDb = this.exerciseDao.save(exercise);
        exerciseDb.setMuscleGroupExercises(this.saveMuscleGroupExercises(exercise.getMuscleGroupExercises(), exercise));
        return exerciseMapper.toDto(exerciseDb, true);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public ExerciseDto updateExercise(final ExerciseDto exerciseDto) throws EntityNotFoundException, IllegalAccessException {
        Exercise exerciseDb = this.exerciseDao.findById(exerciseDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(EX_FOUND_ERROR_MSG));
        this.authService.checkAccess(exerciseDb);

        final Exercise exercise = this.exerciseMapper.toEntity(exerciseDto);
        this.muscleSubGroupsExists(exercise.getMuscleSubGroups());
        exerciseDb = this.exerciseDao.save(exercise);
        exerciseDb.setMuscleGroupExercises(this.saveMuscleGroupExercises(exercise.getMuscleGroupExercises(), exercise));
        return exerciseMapper.toDto(exerciseDb, true);
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

    private void muscleSubGroupsExists(final Set<MuscleSubGroup> muscleSubGroups) {
        if (CollectionUtils.isEmpty(muscleSubGroups)) {
            return;
        }

        final Set<Long> muscleSubGroupsIds = muscleSubGroups.stream().map(MuscleSubGroup::getId).collect(Collectors.toSet());
        if (muscleSubGroupsIds.size() < muscleSubGroups.size() ||
                this.muscleSubGroupDao.findAllById(muscleSubGroupsIds).size() != muscleSubGroups.size()) {
            throw new EntityNotFoundException(MSG_FOUND_ERROR_MSG);
        }
    }

    private void muscleGroupsExists(final Set<MuscleGroup> muscleGroups) {
        if (CollectionUtils.isEmpty(muscleGroups)) {
            return;
        }

        final Set<Long> muscleGroupsIds = muscleGroups.stream().map(MuscleGroup::getId).collect(Collectors.toSet());
        if (muscleGroupsIds.size() < muscleGroups.size() ||
                this.muscleGroupDao.findAllById(muscleGroupsIds).size() != muscleGroups.size()) {
            throw new EntityNotFoundException(MG_FOUND_ERROR_MSG);
        }
    }

    private Set<MuscleGroupExercise> saveMuscleGroupExercises(final Set<MuscleGroupExercise> muscleGroupExercises,
                                                              final Exercise exercise) {
        final Set<MuscleGroup> muscleGroups = new HashSet<>(muscleGroupExercises.size());
        for (final MuscleGroupExercise muscleGroupExercise : muscleGroupExercises) {
            muscleGroupExercise.setExercise(exercise);
            muscleGroups.add(muscleGroupExercise.getMuscleGroup());
        }
        this.muscleGroupsExists(muscleGroups);
        return new HashSet<>(this.muscleGroupExerciseDao.saveAll(muscleGroupExercises));
    }
}