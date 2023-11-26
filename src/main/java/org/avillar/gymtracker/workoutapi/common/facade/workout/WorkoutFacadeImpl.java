package org.avillar.gymtracker.workoutapi.common.facade.workout;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.WorkoutDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.model.WorkoutDateAndId;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.common.facade.mapper.WorkoutEntityFacadeMapper;
import org.avillar.gymtracker.workoutapi.common.facade.mapper.WorkoutFacadeMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WorkoutFacadeImpl implements WorkoutFacade {

  private final WorkoutDao workoutDao;
  private final WorkoutFacadeMapper workoutFacadeMapper;
  private final WorkoutEntityFacadeMapper workoutEntityFacadeMapper;

  @Override
  public boolean existsWorkoutByUserAndDate(final UUID userId, final LocalDate date) {
    return workoutDao.existsWorkoutByUserAndDate(userId, Date.valueOf(date));
  }

  @Override
  public Map<LocalDate, UUID> getWorkoutsIdAndDatesByUser(final UUID userId) {
    return workoutEntityFacadeMapper.mapMap(
        workoutDao.getWorkoutsIdAndDatesByUser(userId).stream()
            .collect(Collectors.toMap(WorkoutDateAndId::getDate, WorkoutDateAndId::getId)));
  }

  @Override
  public Map<LocalDate, UUID> getWorkoutsIdAndDatesByUserAndExercise(
      final UUID userId, final UUID exerciseId) {
    return workoutEntityFacadeMapper.mapMap(
        workoutDao.getWorkoutsIdAndDatesByUserAndExercise(userId, exerciseId).stream()
            .collect(Collectors.toMap(WorkoutDateAndId::getDate, WorkoutDateAndId::getId)));
  }

  @Override
  public Workout getWorkout(final UUID workoutId) throws WorkoutNotFoundException {
    return workoutEntityFacadeMapper.map(
        workoutDao.findById(workoutId).orElseThrow(() -> new WorkoutNotFoundException(workoutId)));
  }

  @Override
  public Workout getWorkoutWithSetGroups(final UUID workoutId) throws WorkoutNotFoundException {
    return workoutEntityFacadeMapper.map(
        workoutDao
            .getWorkoutWithSetGroupsById(workoutId)
            .orElseThrow(() -> new WorkoutNotFoundException(workoutId)));
  }

  @Override
  public Workout getFullWorkout(final UUID workoutId) throws WorkoutNotFoundException {
    return workoutEntityFacadeMapper.map(
        workoutDao.getFullWorkoutsByIds(Set.of(workoutId)).stream()
            .findAny()
            .orElseThrow(() -> new WorkoutNotFoundException(workoutId)));
  }

  @Override
  public List<Workout> getFullWorkoutsByIds(final List<UUID> workoutIds) {
    return workoutEntityFacadeMapper.mapWorkouts(
        workoutDao.getFullWorkoutsByIds(new HashSet<>(workoutIds)));
  }

  @Override
  public Workout saveWorkout(final Workout workout) {
    return workoutEntityFacadeMapper.map(workoutDao.save(workoutFacadeMapper.map(workout)));
  }

  @Override
  public void deleteWorkout(final UUID workoutId) {
    workoutDao.deleteById(workoutId);
  }
}
