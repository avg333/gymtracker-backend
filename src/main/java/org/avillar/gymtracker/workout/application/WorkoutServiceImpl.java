package org.avillar.gymtracker.workout.application;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.exercise.application.dto.ExerciseDto;
import org.avillar.gymtracker.exercise.domain.Exercise;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleGroupDto;
import org.avillar.gymtracker.musclegroup.domain.MuscleGroup;
import org.avillar.gymtracker.musclegroup.domain.MuscleGroupExercise;
import org.avillar.gymtracker.set.domain.Set;
import org.avillar.gymtracker.user.domain.UserApp;
import org.avillar.gymtracker.user.domain.UserDao;
import org.avillar.gymtracker.workout.domain.Workout;
import org.avillar.gymtracker.workout.domain.WorkoutDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class WorkoutServiceImpl extends BaseService implements WorkoutService {
    private static final String WORKOUT_NOT_FOUND = "The workout does not exist";
    private static final String USER_NOT_FOUND = "The user does not exist";

    private final WorkoutDao workoutDao;
    private final UserDao userDao;

    @Autowired
    public WorkoutServiceImpl(WorkoutDao workoutDao, UserDao userDao) {
        this.workoutDao = workoutDao;
        this.userDao = userDao;
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Date> getAllUserWorkoutsDates(final Long userId) throws IllegalAccessException {
        final UserApp userApp = this.userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        final Workout workout = new Workout();
        workout.setUserApp(userApp);
        this.authService.checkAccess(workout);

        return this.workoutDao.findByUserAppOrderByDateDesc(userApp).stream().map(Workout::getDate).toList();
    }


    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<WorkoutDto> getAllUserWorkouts(final Long userId) throws IllegalAccessException {
        final UserApp userApp = this.userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        final List<Workout> workouts = this.workoutDao.findByUserAppOrderByDateDesc(userApp);

        if (workouts.isEmpty()) {
            return new ArrayList<>();
        }

        this.authService.checkAccess(workouts.get(0));
        return workouts.stream().map(this::getWorkoutMetadata).toList();
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public WorkoutDto getWorkout(final Long workoutId) throws EntityNotFoundException, IllegalAccessException {
        final Workout workout = this.workoutDao.findById(workoutId)
                .orElseThrow(() -> new EntityNotFoundException(WORKOUT_NOT_FOUND));
        this.authService.checkAccess(workout);
        return this.getWorkoutMetadata(workout);
    }

    private WorkoutDto getWorkoutMetadata(final Workout workout) {
        final WorkoutDto workoutDto = this.modelMapper.map(workout, WorkoutDto.class);

        final Map<Long, ExerciseDto> exercises = new HashMap<>();
        final Map<Long, MuscleGroupDto> muscleGroups = new HashMap<>();
        final Map<Long, Set> efectiveSets = workout.getSetGroups().stream()
                .flatMap(setGroup -> setGroup.getSets().stream())
                .filter(set -> set.getRir() < 4)
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
            exercises.putIfAbsent(exercise.getId(), this.modelMapper.map(exercise, ExerciseDto.class));

            for (final MuscleGroup muscleGroupSet : exercise.getMuscleGroupExercises().stream()
                    .map(MuscleGroupExercise::getMuscleGroup)
                    .toList()) {
                muscleGroups.putIfAbsent(muscleGroupSet.getId(), this.modelMapper.map(muscleGroupSet, MuscleGroupDto.class));
                muscleGroups.get(muscleGroupSet.getId()).setVolume(muscleGroups.get(muscleGroupSet.getId()).getVolume() + 1);
            }

            weight += set.getWeight();
        }

        if (startWo != null && endWo != null) {
            final long duration = endWo.getTime() - startWo.getTime();
            workoutDto.setDuration((int) TimeUnit.MILLISECONDS.toMinutes(duration));
        }
        final List<MuscleGroupDto> muscleGroupDtos = new ArrayList<>(muscleGroups.values());
        muscleGroupDtos.sort(Comparator.comparing(MuscleGroupDto::getVolume).reversed());

        workoutDto.setExerciseNumber(exercises.size());
        workoutDto.setMuscleGroupDtos(muscleGroupDtos);
        workoutDto.setSetsNumber(efectiveSets.size());
        workoutDto.setWeightVolume((int) weight);

        return workoutDto;
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public WorkoutDto createWorkout(final WorkoutDto workoutDto, final Long userId) throws IllegalAccessException {
        final UserApp userApp = this.userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        final Workout workout = this.modelMapper.map(workoutDto, Workout.class);
        workout.setUserApp(userApp);
        this.authService.checkAccess(workout);
        return this.modelMapper.map(this.workoutDao.save(workout), WorkoutDto.class);
    }


    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public WorkoutDto updateWorkout(final WorkoutDto workoutDto) throws EntityNotFoundException, IllegalAccessException {
        final Workout workoutDb = this.workoutDao.findById(workoutDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(WORKOUT_NOT_FOUND));
        this.authService.checkAccess(workoutDb);
        final Workout workout = this.modelMapper.map(workoutDto, Workout.class);
        workout.setUserApp(workoutDb.getUserApp());
        return this.modelMapper.map(this.workoutDao.save(workout), WorkoutDto.class);
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
