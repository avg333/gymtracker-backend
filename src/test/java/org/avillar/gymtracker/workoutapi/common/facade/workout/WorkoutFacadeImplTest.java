package org.avillar.gymtracker.workoutapi.common.facade.workout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Data;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.WorkoutDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.model.WorkoutDateAndId;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.model.WorkoutEntity;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.common.facade.mapper.WorkoutEntityFacadeMapper;
import org.avillar.gymtracker.workoutapi.common.facade.mapper.WorkoutFacadeMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class WorkoutFacadeImplTest {

  private static final int LIST_SIZE = 5;

  @InjectMocks private WorkoutFacadeImpl facade;

  @Mock private WorkoutDao dao;
  @Mock private WorkoutFacadeMapper workoutFacadeMapper;
  @Mock private WorkoutEntityFacadeMapper workoutEntityFacadeMapper;

  private static List<WorkoutDateAndId> getWorkoutDateAndIds() {
    final List<WorkoutDateAndId> daoResponse = new ArrayList<>();
    for (int i = 0; i < LIST_SIZE; i++) {
      daoResponse.add(Instancio.create(WorkoutDateAndIdDummy.class));
    }
    return daoResponse;
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldReturnTrueOrFalseWhenExistsWorkoutByUserOnThatDateOrNotSuccessfully(
      final boolean existsWorkout) {
    final UUID workoutId = UUID.randomUUID();
    final Date date = Instancio.create(Date.class);
    final LocalDate localDate = date.toLocalDate();

    when(dao.existsWorkoutByUserAndDate(workoutId, date)).thenReturn(existsWorkout);

    assertThat(facade.existsWorkoutByUserAndDate(workoutId, localDate)).isEqualTo(existsWorkout);
  }

  @Test
  void shouldReturnMapUserWorkoutsIdDateSuccessfully() {
    final UUID userId = UUID.randomUUID();
    final List<WorkoutDateAndId> daoResponse = getWorkoutDateAndIds();
    final Map<LocalDate, UUID> expected = Instancio.createMap(LocalDate.class, UUID.class);

    when(dao.getWorkoutsIdAndDatesByUser(userId)).thenReturn(daoResponse);
    when(workoutEntityFacadeMapper.mapMap(
            daoResponse.stream()
                .collect(Collectors.toMap(WorkoutDateAndId::getDate, WorkoutDateAndId::getId))))
        .thenReturn(expected);

    assertThat(facade.getWorkoutsIdAndDatesByUser(userId)).isEqualTo(expected);
  }

  @Test
  void shouldReturnMapUserWorkoutsIdDateFilteredByExerciseIdSuccessfully() {
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final List<WorkoutDateAndId> daoResponse = getWorkoutDateAndIds();
    final Map<LocalDate, UUID> expected = Instancio.createMap(LocalDate.class, UUID.class);

    when(dao.getWorkoutsIdAndDatesByUserAndExercise(userId, exerciseId)).thenReturn(daoResponse);
    when(workoutEntityFacadeMapper.mapMap(
            daoResponse.stream()
                .collect(Collectors.toMap(WorkoutDateAndId::getDate, WorkoutDateAndId::getId))))
        .thenReturn(expected);

    assertThat(facade.getWorkoutsIdAndDatesByUserAndExercise(userId, exerciseId))
        .isEqualTo(expected);
  }

  @Test
  void shouldReturnWorkoutSuccessfully() throws WorkoutNotFoundException {
    final UUID workoutId = UUID.randomUUID();
    final WorkoutEntity workoutEntity = Instancio.create(WorkoutEntity.class);
    final Workout workout = Instancio.create(Workout.class);

    when(dao.findById(workoutId)).thenReturn(Optional.of(workoutEntity));
    when(workoutEntityFacadeMapper.map(workoutEntity)).thenReturn(workout);

    assertThat(facade.getWorkout(workoutId)).isEqualTo(workout);
  }

  @Test
  void shouldThrowEntityNotFoundExceptionWhenWorkoutIsNotFound() {
    final UUID workoutId = UUID.randomUUID();
    final WorkoutNotFoundException exception = new WorkoutNotFoundException(workoutId);

    when(dao.findById(workoutId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> facade.getWorkout(workoutId)).isEqualTo(exception);
  }

  @Test
  void shouldReturnWorkoutWithSetGroupsSuccessfully() throws WorkoutNotFoundException {
    final UUID workoutId = UUID.randomUUID();
    final WorkoutEntity workoutEntity = Instancio.create(WorkoutEntity.class);
    final Workout workout = Instancio.create(Workout.class);

    when(dao.getWorkoutWithSetGroupsById(workoutId)).thenReturn(Optional.of(workoutEntity));
    when(workoutEntityFacadeMapper.map(workoutEntity)).thenReturn(workout);

    assertThat(facade.getWorkoutWithSetGroups(workoutId)).isEqualTo(workout);
  }

  @Test
  void shouldThrowEntityNotFoundExceptionWhenWorkoutWithSetGroupsIsNotFound() {
    final UUID workoutId = UUID.randomUUID();
    final WorkoutNotFoundException exception = new WorkoutNotFoundException(workoutId);

    when(dao.getWorkoutWithSetGroupsById(workoutId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> facade.getWorkoutWithSetGroups(workoutId)).isEqualTo(exception);
  }

  @Test
  void shouldReturnWorkoutFullSuccessfully() throws WorkoutNotFoundException {
    final UUID workoutId = UUID.randomUUID();
    final WorkoutEntity workoutEntity = Instancio.create(WorkoutEntity.class);
    final Workout workout = Instancio.create(Workout.class);

    when(dao.getFullWorkoutsByIds(Set.of(workoutId))).thenReturn(List.of(workoutEntity));
    when(workoutEntityFacadeMapper.map(workoutEntity)).thenReturn(workout);

    assertThat(facade.getFullWorkout(workoutId)).isEqualTo(workout);
  }

  @Test
  void shouldThrowEntityNotFoundExceptionWhenWorkoutFullIsNotFound() {
    final UUID workoutId = UUID.randomUUID();
    final WorkoutNotFoundException exception = new WorkoutNotFoundException(workoutId);

    when(dao.getFullWorkoutsByIds(Set.of(workoutId))).thenReturn(Collections.emptyList());

    assertThatThrownBy(() -> facade.getFullWorkout(workoutId)).isEqualTo(exception);
  }

  @Test
  void shouldReturnWorkoutsFullSuccessfully() {
    final List<UUID> workoutIds = Instancio.createList(UUID.class);
    final List<WorkoutEntity> workoutEntities = Instancio.createList(WorkoutEntity.class);
    final List<Workout> workouts = Instancio.createList(Workout.class);

    final ArgumentCaptor<Set<UUID>> captor = ArgumentCaptor.forClass(Set.class);

    when(dao.getFullWorkoutsByIds(captor.capture())).thenReturn(workoutEntities);
    when(workoutEntityFacadeMapper.mapWorkouts(workoutEntities)).thenReturn(workouts);

    assertThat(facade.getFullWorkoutsByIds(workoutIds)).isEqualTo(workouts);

    final Set<UUID> capturedSet = captor.getValue();
    assertThat(capturedSet).containsExactlyInAnyOrderElementsOf(workoutIds);
  }

  @Test
  void shouldReturnWorkoutsFullSuccessfullyWithRepeatedUUIDs() {
    final List<UUID> workoutIds = Instancio.createList(UUID.class);
    workoutIds.add(workoutIds.get(0));
    final List<WorkoutEntity> workoutEntities = Instancio.createList(WorkoutEntity.class);
    final List<Workout> workouts = Instancio.createList(Workout.class);

    final ArgumentCaptor<Set<UUID>> captor = ArgumentCaptor.forClass(Set.class);

    when(dao.getFullWorkoutsByIds(captor.capture())).thenReturn(workoutEntities);
    when(workoutEntityFacadeMapper.mapWorkouts(workoutEntities)).thenReturn(workouts);

    assertThat(facade.getFullWorkoutsByIds(workoutIds)).isEqualTo(workouts);

    final Set<UUID> capturedSet = captor.getValue();
    assertThat(capturedSet)
        .containsExactlyInAnyOrderElementsOf(workoutIds.stream().distinct().toList());
  }

  @Test
  void shouldSaveAndReturnWorkoutSuccessfully() {
    final Workout workoutBeforeSave = Instancio.create(Workout.class);
    final WorkoutEntity workoutEntityBeforeSave = Instancio.create(WorkoutEntity.class);
    final WorkoutEntity workoutEntityAfterSave = Instancio.create(WorkoutEntity.class);
    final Workout workoutAfterSave = Instancio.create(Workout.class);

    when(workoutFacadeMapper.map(workoutBeforeSave)).thenReturn(workoutEntityBeforeSave);
    when(dao.save(workoutEntityBeforeSave)).thenReturn(workoutEntityAfterSave);
    when(workoutEntityFacadeMapper.map(workoutEntityAfterSave)).thenReturn(workoutAfterSave);

    assertThat(facade.saveWorkout(workoutBeforeSave)).isEqualTo(workoutAfterSave);
  }

  @Test
  void shouldDeleteWorkoutSuccessfully() {
    final UUID workoutId = UUID.randomUUID();

    doNothing().when(dao).deleteById(workoutId);

    assertDoesNotThrow(() -> facade.deleteWorkout(workoutId));
  }

  @Data
  public static class WorkoutDateAndIdDummy implements WorkoutDateAndId {
    private UUID id;
    private Date date;
  }
}
