package org.avillar.gymtracker.workout.application;

import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.base.application.VolumeConstants;
import org.avillar.gymtracker.errors.application.BadFormException;
import org.avillar.gymtracker.errors.application.EntityNotFoundException;
import org.avillar.gymtracker.errors.application.IllegalAccessException;
import org.avillar.gymtracker.exercise.application.dto.ExerciseDto;
import org.avillar.gymtracker.exercise.application.dto.ExerciseMapper;
import org.avillar.gymtracker.exercise.domain.Exercise;
import org.avillar.gymtracker.exercise.domain.ExerciseDao;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleGroupDto;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleGroupMapper;
import org.avillar.gymtracker.musclegroup.domain.MuscleGroup;
import org.avillar.gymtracker.musclegroup.domain.MuscleGroupExercise;
import org.avillar.gymtracker.session.domain.Session;
import org.avillar.gymtracker.session.domain.SessionDao;
import org.avillar.gymtracker.set.domain.Set;
import org.avillar.gymtracker.set.domain.SetDao;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupMapper;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.avillar.gymtracker.setgroup.domain.SetGroupDao;
import org.avillar.gymtracker.user.domain.UserApp;
import org.avillar.gymtracker.user.domain.UserDao;
import org.avillar.gymtracker.workout.application.dto.WorkoutDto;
import org.avillar.gymtracker.workout.application.dto.WorkoutDtoValidator;
import org.avillar.gymtracker.workout.application.dto.WorkoutMapper;
import org.avillar.gymtracker.workout.domain.Workout;
import org.avillar.gymtracker.workout.domain.WorkoutDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.DataBinder;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class WorkoutServiceImpl extends BaseService implements WorkoutService {

    private final WorkoutDao workoutDao;
    private final UserDao userDao;
    private final SetGroupDao setGroupDao;
    private final SetDao setDao;
    private final SessionDao sessionDao;
    private final ExerciseDao exerciseDao;
    private final WorkoutMapper workoutMapper;
    private final ExerciseMapper exerciseMapper;
    private final MuscleGroupMapper muscleGroupMapper;
    private final SetGroupMapper setGroupMapper;
    private final WorkoutDtoValidator workoutDtoValidator;

    @Autowired
    public WorkoutServiceImpl(WorkoutDao workoutDao, UserDao userDao,
                              WorkoutMapper workoutMapper, ExerciseMapper exerciseMapper, SessionDao sessionDao,
                              MuscleGroupMapper muscleGroupMapper, SetGroupDao setGroupDao, SetDao setDao,
                              ExerciseDao exerciseDao, SetGroupMapper setGroupMapper,
                              WorkoutDtoValidator workoutDtoValidator) {
        this.workoutDao = workoutDao;
        this.userDao = userDao;
        this.workoutMapper = workoutMapper;
        this.exerciseMapper = exerciseMapper;
        this.muscleGroupMapper = muscleGroupMapper;
        this.setGroupDao = setGroupDao;
        this.setDao = setDao;
        this.sessionDao = sessionDao;
        this.exerciseDao = exerciseDao;
        this.setGroupMapper = setGroupMapper;
        this.workoutDtoValidator = workoutDtoValidator;
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public Map<Date, Long> getAllUserWorkoutDates(final Long userId) throws EntityNotFoundException, IllegalAccessException {
        final UserApp userApp = this.userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(UserApp.class, userId));

        final Workout workout = new Workout();
        workout.setUserApp(userApp);
        this.authService.checkAccess(workout);

        //TODO Obtener solo fecha e ID
        return this.workoutDao.getWorkoutDatesByUser(userApp)
                .stream()
                .collect(Collectors.toMap(Workout::getDate, Workout::getId));
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public Map<Date, Long> getAllUserWorkoutsWithExercise(final Long userId, final Long exerciseId) throws EntityNotFoundException, IllegalAccessException {
        final UserApp userApp = this.userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(UserApp.class, userId));
        final Exercise exercise = this.exerciseDao.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(Exercise.class, exerciseId));

        final Workout workout = new Workout();
        workout.setUserApp(userApp);
        this.authService.checkAccess(workout);

        //TODO Obtener solo fecha e ID
        return this.workoutDao.findWorkoutsWithUserAndExercise(userApp, exercise)
                .stream()
                .collect(Collectors.toMap(Workout::getDate, Workout::getId));
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public List<WorkoutDto> getAllUserWorkoutsByDate(final Long userId, final Date date) throws EntityNotFoundException, IllegalAccessException {
        final UserApp userApp = this.userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(UserApp.class, userId));

        final Workout workout = new Workout();
        workout.setUserApp(userApp);
        this.authService.checkAccess(workout);

        return this.workoutDao.findByUserAppAndDate(userApp, date)
                .stream()
                .map(this::getWorkoutMetadata)
                .toList();
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public WorkoutDto getWorkout(final Long workoutId) throws EntityNotFoundException, IllegalAccessException {
        final Workout workout = this.workoutDao.findById(workoutId)
                .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutId));
        this.authService.checkAccess(workout);
        return this.getWorkoutMetadata(workout);
    }

    private WorkoutDto getWorkoutMetadata(final Workout workout) {
        final WorkoutDto workoutDto = this.workoutMapper.toDto(workout, -1);

        final Map<Long, ExerciseDto> exercises = new HashMap<>();
        final Map<Long, MuscleGroupDto> muscleGroups = new HashMap<>();
        final Map<Long, Set> efectiveSets = workout.getSetGroups().stream()
                .flatMap(setGroup -> setGroup.getSets().stream())
                .filter(set -> set.getRir() <= VolumeConstants.MIN_RIR)
                .collect(Collectors.toMap(Set::getId, set -> set));

        double weight = 0;
        Date startWo = null;
        Date endWo = null;
        for (final Set set : efectiveSets.values()) {
            if (startWo == null || set.getLastModifiedAt().getTime() < startWo.getTime()) {
                startWo = set.getLastModifiedAt();
            }
            if (endWo == null || set.getLastModifiedAt().getTime() > endWo.getTime()) {
                endWo = set.getLastModifiedAt();
            }

            final Exercise exercise = set.getSetGroup().getExercise();
            exercises.putIfAbsent(exercise.getId(), this.exerciseMapper.toDto(exercise, -1));

            for (final MuscleGroup muscleGroupSet : exercise.getMuscleGroupExercises().stream()
                    .map(MuscleGroupExercise::getMuscleGroup)
                    .toList()) {
                muscleGroups.putIfAbsent(muscleGroupSet.getId(), this.muscleGroupMapper.toDto(muscleGroupSet, true));
                muscleGroups.get(muscleGroupSet.getId()).setVolume(muscleGroups.get(muscleGroupSet.getId()).getVolume() + 1);
            }

            if (set.getWeight() != null) {
                weight += set.getWeight();
            }
        }

        if (startWo != null) {
            final long duration = endWo.getTime() - startWo.getTime();
            workoutDto.setDuration((int) TimeUnit.MILLISECONDS.toMinutes(duration));
        }
        final List<MuscleGroupDto> muscleGroupDtos = new ArrayList<>(muscleGroups.values());
        muscleGroupDtos.sort(Comparator.comparing(MuscleGroupDto::getVolume).reversed());

        workoutDto.setExerciseNumber(exercises.size());
        workoutDto.setMuscleGroupDtos(muscleGroupDtos);
        workoutDto.setSetsNumber(efectiveSets.size());
        workoutDto.setWeightVolume((int) weight);

        workoutDto.setSetGroups(this.setGroupMapper.toDtos(workout.getSetGroups(), -1));

        return workoutDto;
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public WorkoutDto createWorkout(final WorkoutDto workoutDto)
            throws IllegalAccessException, EntityNotFoundException, BadFormException {
        final DataBinder dataBinder = new DataBinder(workoutDto);
        dataBinder.addValidators(workoutDtoValidator);
        dataBinder.validate();
        if (dataBinder.getBindingResult().hasErrors()) {
            throw new BadFormException(WorkoutDto.class, dataBinder.getBindingResult());
        }

        final Workout workout = this.workoutMapper.toEntity(workoutDto);
        workout.setUserApp(this.userDao.getReferenceById(workoutDto.getUserApp().getId()));

        return this.workoutMapper.toDto(this.workoutDao.save(workout), -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public WorkoutDto addSetGroupsToWorkoutFromWorkout(final Long workoutDestinationId, final Long workoutSourceId)
            throws IllegalAccessException, EntityNotFoundException {
        final Workout workoutSource = this.workoutDao.findById(workoutSourceId)
                .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutSourceId));
        final Workout workoutDestination = this.workoutDao.findById(workoutDestinationId)
                .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutDestinationId));

        this.authService.checkAccess(workoutSource);
        this.authService.checkAccess(workoutDestination);

        this.copySetGroupsToWorkout(workoutDestination, workoutSource.getSetGroups());

        return this.getWorkoutMetadata(this.workoutDao.getReferenceById(workoutDestinationId));
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public WorkoutDto addSetGroupsToWorkoutFromSession(final Long workoutDestinationId, final Long sessionSourceId)
            throws IllegalAccessException, EntityNotFoundException {
        final Session sessionSource = this.sessionDao.findById(sessionSourceId)
                .orElseThrow(() -> new EntityNotFoundException(Session.class, sessionSourceId));
        final Workout workoutDestination = this.workoutDao.findById(workoutDestinationId)
                .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutDestinationId));

        this.authService.checkAccess(sessionSource.getProgram());
        this.authService.checkAccess(workoutDestination);

        this.copySetGroupsToWorkout(workoutDestination, sessionSource.getSetGroups());

        return this.getWorkoutMetadata(this.workoutDao.getReferenceById(workoutDestinationId));
    }

    private void copySetGroupsToWorkout(final Workout workout, final java.util.Set<SetGroup> setGroupsSource) {
        //TODO Mejorar esto
        final int listOrderOffset = workout.getSetGroups().size();
        final var setGroups = new ArrayList<SetGroup>();
        final var sets = new ArrayList<Set>();
        for (final var setGroupDb : setGroupsSource) {
            final SetGroup setGroup = new SetGroup();
            setGroup.setListOrder(setGroupDb.getListOrder() + listOrderOffset);
            setGroup.setDescription(setGroupDb.getDescription());
            setGroup.setExercise(setGroupDb.getExercise());
            setGroup.setWorkout(workout);
            setGroups.add(setGroup);
            for (final var setDb : setGroupDb.getSets()) {
                final Set set = new Set();
                set.setSetGroup(setGroup);
                set.setListOrder(setDb.getListOrder());
                set.setDescription(setDb.getDescription());
                set.setReps(setDb.getReps());
                set.setRir(setDb.getRir());
                set.setWeight(setDb.getWeight());
                sets.add(set);
            }
        }

        this.setGroupDao.saveAll(setGroups);
        this.setDao.saveAll(sets);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public WorkoutDto updateWorkout(final WorkoutDto workoutDto)
            throws EntityNotFoundException, IllegalAccessException, BadFormException {
        final DataBinder dataBinder = new DataBinder(workoutDto);
        dataBinder.addValidators(workoutDtoValidator);
        dataBinder.validate();
        if (dataBinder.getBindingResult().hasErrors()) {
            throw new BadFormException(WorkoutDto.class, dataBinder.getBindingResult());
        }

        final Workout workoutDb = this.workoutDao.getReferenceById(workoutDto.getId());

        final Workout workout = this.workoutMapper.toEntity(workoutDto);
        workout.setUserApp(workoutDb.getUserApp());

        return this.workoutMapper.toDto(this.workoutDao.save(workout), -1);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteWorkout(final Long workoutId)
            throws EntityNotFoundException, IllegalAccessException {
        final Workout workout = this.workoutDao.findById(workoutId)
                .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutId));
        this.authService.checkAccess(workout);
        this.workoutDao.deleteById(workoutId);
    }

}
