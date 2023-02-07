package org.avillar.gymtracker.exercise.application;

import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.errors.application.exceptions.BadFormException;
import org.avillar.gymtracker.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercise.application.dto.ExerciseDto;
import org.avillar.gymtracker.exercise.application.dto.ExerciseDtoValidator;
import org.avillar.gymtracker.exercise.application.dto.ExerciseFilterDto;
import org.avillar.gymtracker.exercise.application.dto.ExerciseMapper;
import org.avillar.gymtracker.exercise.domain.Exercise;
import org.avillar.gymtracker.exercise.domain.ExerciseDao;
import org.avillar.gymtracker.musclegroup.domain.MuscleGroupExercise;
import org.avillar.gymtracker.musclegroup.domain.MuscleGroupExerciseDao;
import org.avillar.gymtracker.musclegroup.domain.MuscleSubGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.DataBinder;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class ExerciseServiceImpl extends BaseService implements ExerciseService {

    private final ExerciseDao exerciseDao;
    private final MuscleGroupExerciseDao muscleGroupExerciseDao;
    private final ExerciseMapper exerciseMapper;

    @Autowired
    public ExerciseServiceImpl(ExerciseDao exerciseDao, MuscleGroupExerciseDao muscleGroupExerciseDao,
                               ExerciseMapper exerciseMapper) {
        this.exerciseDao = exerciseDao;
        this.muscleGroupExerciseDao = muscleGroupExerciseDao;
        this.exerciseMapper = exerciseMapper;
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public List<ExerciseDto> getAllExercises(ExerciseFilterDto exerciseFilterDto) {
        final ExerciseFilterDto finalExerciseFilterDto = exerciseFilterDto != null
                ? exerciseFilterDto
                : new ExerciseFilterDto();

        // TODO Llevar filtros a la BD
        return this.exerciseDao.findAll()
                .stream()
                .filter(exercise -> this.applyFilter(finalExerciseFilterDto, exercise))
                .map(exercise -> this.exerciseMapper.toDto(exercise, 0))
                .toList();
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public ExerciseDto getExercise(final Long exerciseId)
            throws EntityNotFoundException, IllegalAccessException {
        final Exercise exercise = this.exerciseDao.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(Exercise.class, exerciseId));
        this.authService.checkAccess(exercise);
        return this.exerciseMapper.toDto(exercise, -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public ExerciseDto createExercise(final ExerciseDto exerciseDto, final Map<String, String> errorMap)
            throws EntityNotFoundException, BadFormException {
        final DataBinder dataBinder = new DataBinder(exerciseDto);
        dataBinder.addValidators(new ExerciseDtoValidator());
        dataBinder.validate();
        if (dataBinder.getBindingResult().hasErrors()) {
            throw new BadFormException(ExerciseDto.class, dataBinder.getBindingResult());
        }

        final Exercise exercise = this.exerciseMapper.toEntity(exerciseDto);
        final Exercise exerciseDb = this.exerciseDao.save(exercise);
        exercise.getMuscleGroupExercises().forEach(mge -> mge.setExercise(exercise));
        exerciseDb.setMuscleGroupExercises(new HashSet<>(this.muscleGroupExerciseDao.saveAll(exercise.getMuscleGroupExercises())));
        return this.exerciseMapper.toDto(exerciseDb, -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public ExerciseDto updateExercise(final ExerciseDto exerciseDto, final Map<String, String> errorMap)
            throws EntityNotFoundException, IllegalAccessException, BadFormException {
        final DataBinder dataBinder = new DataBinder(exerciseDto);
        dataBinder.addValidators(new ExerciseDtoValidator());
        dataBinder.validate();
        if (dataBinder.getBindingResult().hasErrors()) {
            throw new BadFormException(ExerciseDto.class, dataBinder.getBindingResult());
        }

        this.authService.checkAccess(
                this.exerciseDao.findById(exerciseDto.getId())
                        .orElseThrow(() -> new EntityNotFoundException(Exercise.class, exerciseDto.getId())));

        final Exercise exercise = this.exerciseMapper.toEntity(exerciseDto);
        final Exercise exerciseDb = this.exerciseDao.save(exercise);

        this.deleteOldMuscleGroupExerciseRelations(exerciseDb);
        exercise.getMuscleGroupExercises().forEach(mge -> mge.setExercise(exerciseDb));
        exerciseDb.setMuscleGroupExercises(new HashSet<>(this.muscleGroupExerciseDao.saveAll(exercise.getMuscleGroupExercises())));

        return this.exerciseMapper.toDto(exerciseDb, -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteExercise(final Long exerciseId)
            throws EntityNotFoundException, IllegalAccessException {
        final Exercise exercise = this.exerciseDao.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(Exercise.class, exerciseId));
        this.authService.checkAccess(exercise);
        this.exerciseDao.deleteById(exerciseId);
    }

    private boolean applyFilter(final ExerciseFilterDto exerciseFilterDto, final Exercise exercise) {
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
            cumple = exerciseFilterDto.getMuscleGroupIds().stream().anyMatch(muscleGroupIds::contains);
        }
        if (cumple && exerciseFilterDto.getMuscleSubGroupIds() != null && !exerciseFilterDto.getMuscleSubGroupIds().isEmpty()) {
            final List<Long> muscleSubGroupIds = exercise.getMuscleSubGroups().stream().map(MuscleSubGroup::getId).toList();
            cumple = exerciseFilterDto.getMuscleSubGroupIds().stream().anyMatch(muscleSubGroupIds::contains);
        }
        if (cumple && exerciseFilterDto.getMuscleSupGroupIds() != null && !exerciseFilterDto.getMuscleSupGroupIds().isEmpty()) {
            final List<Long> muscleSupGroupIds = exercise
                    .getMuscleGroupExercises()
                    .stream()
                    .map(mge -> mge.getMuscleGroup().getMuscleSupGroups().iterator().next().getId()).toList();
            cumple = exerciseFilterDto.getMuscleSupGroupIds().stream().anyMatch(muscleSupGroupIds::contains);
        }
        return cumple;
    }

    private void deleteOldMuscleGroupExerciseRelations(final Exercise exercise) {
        //TODO Mejorar y hacer mas eficiente esta logica
        final List<MuscleGroupExercise> muscleGroupExercises = this.muscleGroupExerciseDao.findAll();
        final List<Long> muscleGroupExercisesIds = muscleGroupExercises.stream()
                .filter(mge -> mge.getExercise().getId().equals(exercise.getId()))
                .map(MuscleGroupExercise::getId)
                .toList();
        this.muscleGroupExerciseDao.deleteAllById(muscleGroupExercisesIds);
    }

}
