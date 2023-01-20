package org.avillar.gymtracker.workout.application;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.base.application.VolumeConstants;
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
import org.avillar.gymtracker.workout.application.dto.WorkoutMapper;
import org.avillar.gymtracker.workout.application.dto.WorkoutValidator;
import org.avillar.gymtracker.workout.domain.Workout;
import org.avillar.gymtracker.workout.domain.WorkoutDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class WorkoutServiceImpl extends BaseService implements WorkoutService {
    private static final String WORKOUT_NOT_FOUND = "The workout does not exist";
    private static final String SESSION_NOT_FOUND = "The session does not exist";
    private static final String USER_NOT_FOUND = "The user does not exist";

    private final WorkoutDao workoutDao;
    private final UserDao userDao;
    private final WorkoutValidator workoutValidator;
    private final WorkoutMapper workoutMapper;
    private final ExerciseMapper exerciseMapper;
    private final MuscleGroupMapper muscleGroupMapper;
    private final SetGroupDao setGroupDao;
    private final SetDao setDao;
    private final SessionDao sessionDao;
    private final ExerciseDao exerciseDao;
    private final SetGroupMapper setGroupMapper;

    @Autowired
    public WorkoutServiceImpl(WorkoutDao workoutDao, UserDao userDao, WorkoutValidator workoutValidator,
                              WorkoutMapper workoutMapper, ExerciseMapper exerciseMapper, SessionDao sessionDao,
                              MuscleGroupMapper muscleGroupMapper, SetGroupDao setGroupDao, SetDao setDao,
                              ExerciseDao exerciseDao, SetGroupMapper setGroupMapper) {
        this.workoutDao = workoutDao;
        this.userDao = userDao;
        this.workoutValidator = workoutValidator;
        this.workoutMapper = workoutMapper;
        this.exerciseMapper = exerciseMapper;
        this.muscleGroupMapper = muscleGroupMapper;
        this.setGroupDao = setGroupDao;
        this.setDao = setDao;
        this.sessionDao = sessionDao;
        this.exerciseDao = exerciseDao;
        this.setGroupMapper = setGroupMapper;
    }

    @Override
    public Map<Date, Long> getAllUserWorkoutDates(final Long userId) throws EntityNotFoundException, IllegalAccessException {
        final UserApp userApp = this.userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        final Workout workoutAux = new Workout();
        workoutAux.setUserApp(userApp);
        this.authService.checkAccess(workoutAux);

        return this.workoutDao.getWorkoutDatesByUser(userApp)
                .stream()
                .collect(Collectors.toMap(Workout::getDate, Workout::getId));
    }

    @Override
    public List<Date> getAllUserWorkoutsWithExercise(final Long userId, final Long exerciseId) throws EntityNotFoundException, IllegalAccessException {
        final UserApp userApp = this.userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        final Exercise exercise = this.exerciseDao.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        final Workout workout = new Workout();
        workout.setUserApp(userApp);
        this.authService.checkAccess(workout);

        return this.workoutDao.findWorkoutsWithUserAndExercise(userApp, exercise);
    }

    @Override
    public List<WorkoutDto> getAllUserWorkoutsByDate(final Long userId, final Date date) throws EntityNotFoundException, IllegalAccessException {
        final UserApp userApp = this.userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

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
                .orElseThrow(() -> new EntityNotFoundException(WORKOUT_NOT_FOUND));
        this.authService.checkAccess(workout);
        return this.getWorkoutMetadata(workout);
    }

    private WorkoutDto getWorkoutMetadata(final Workout workout) {
        final WorkoutDto workoutDto = this.workoutMapper.toDto(workout, true);

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
            exercises.putIfAbsent(exercise.getId(), this.exerciseMapper.toDto(exercise, true));

            for (final MuscleGroup muscleGroupSet : exercise.getMuscleGroupExercises().stream()
                    .map(MuscleGroupExercise::getMuscleGroup)
                    .toList()) {
                muscleGroups.putIfAbsent(muscleGroupSet.getId(), this.muscleGroupMapper.toDto(muscleGroupSet, true));
                muscleGroups.get(muscleGroupSet.getId()).setVolume(muscleGroups.get(muscleGroupSet.getId()).getVolume() + 1);
            }

            if (set.getWeight() != null){
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

        workoutDto.setSetGroups(this.setGroupMapper.toDtos(workout.getSetGroups(),true));

        return workoutDto;
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public WorkoutDto createWorkout(final WorkoutDto workoutDto) throws IllegalAccessException {
        if (!this.workoutValidator.validate(workoutDto, new HashMap<>()).isEmpty()) {
            throw new RuntimeException("El workout esta mal formado");
        }

        final Workout workout = this.workoutMapper.toEntity(workoutDto);
        workout.setUserApp(this.userDao.getReferenceById(workoutDto.getUserApp().getId()));
        this.authService.checkAccess(workout);

        final int workoutsInDate = this.workoutDao.countByUserAppAndDate(workout.getUserApp(), workout.getDate());
        if (workoutsInDate >0) {
            throw new RuntimeException("Ya existe un workout ese dia!!!"); //TODO Mejorar
        }


        return this.workoutMapper.toDto(this.workoutDao.save(workout), true);
    }

    @Override
    @Transactional
    public WorkoutDto addSetGroupsToWorkoutFromWorkout(final Long workoutDestinationId, final Long workoutSourceId) throws IllegalAccessException {
        final Workout workoutSource = this.workoutDao.findById(workoutSourceId)
                .orElseThrow(() -> new EntityNotFoundException(WORKOUT_NOT_FOUND));
        this.authService.checkAccess(workoutSource);

        final Workout workoutDestination = this.workoutDao.findById(workoutDestinationId)
                .orElseThrow(() -> new EntityNotFoundException(WORKOUT_NOT_FOUND));
        this.authService.checkAccess(workoutDestination);

        this.copySetGroupsToWorkout(workoutDestination, workoutSource.getSetGroups());

        return this.getWorkoutMetadata(this.workoutDao.getReferenceById(workoutDestinationId));
    }

    @Override
    @Transactional
    public WorkoutDto addSetGroupsToWorkoutFromSession(final Long workoutDestinationId, final Long sessionSourceId) throws IllegalAccessException {
        final Session sessionSource = this.sessionDao.findById(sessionSourceId)
                .orElseThrow(() -> new EntityNotFoundException(SESSION_NOT_FOUND));
        this.authService.checkAccess(sessionSource.getProgram());

        final Workout workoutDestination = this.workoutDao.findById(workoutDestinationId)
                .orElseThrow(() -> new EntityNotFoundException(WORKOUT_NOT_FOUND));
        this.authService.checkAccess(workoutDestination);

        this.copySetGroupsToWorkout(workoutDestination, sessionSource.getSetGroups());

        return this.getWorkoutMetadata(this.workoutDao.getReferenceById(workoutDestinationId));
    }

    private void copySetGroupsToWorkout(final Workout workout, final java.util.Set<SetGroup> setGroupsSource){
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
    public WorkoutDto updateWorkout(final WorkoutDto workoutDto) throws EntityNotFoundException, IllegalAccessException {
        if (!this.workoutValidator.validate(workoutDto, new HashMap<>()).isEmpty()) {
            throw new RuntimeException("El workout esta mal formado");
        }

        final Workout workoutDb = this.workoutDao.findById(workoutDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(WORKOUT_NOT_FOUND));
        this.authService.checkAccess(workoutDb);

        final Workout workout = this.workoutMapper.toEntity(workoutDto);
        workout.setUserApp(workoutDb.getUserApp());
        return this.workoutMapper.toDto(this.workoutDao.save(workout), true);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteWorkout(final Long workoutId) throws EntityNotFoundException, IllegalAccessException {
        final Workout workout = this.workoutDao.findById(workoutId)
                .orElseThrow(() -> new EntityNotFoundException(WORKOUT_NOT_FOUND));
        this.authService.checkAccess(workout);
        this.workoutDao.deleteById(workoutId);
    }

}
