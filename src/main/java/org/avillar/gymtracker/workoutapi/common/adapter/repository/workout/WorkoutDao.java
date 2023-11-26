package org.avillar.gymtracker.workoutapi.common.adapter.repository.workout;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.model.WorkoutDateAndId;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.model.WorkoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkoutDao extends JpaRepository<WorkoutEntity, UUID> {

  @Query(
      """
          SELECT COUNT(w) > 0
          FROM WorkoutEntity w
          WHERE w.userId = :userId AND w.date = :date
          """)
  boolean existsWorkoutByUserAndDate(@Param("userId") UUID userId, @Param("date") Date date);

  @Query(
      """
          SELECT w
          FROM WorkoutEntity w
          WHERE w.userId = :userId
          ORDER BY w.date ASC
          """)
  List<WorkoutDateAndId> getWorkoutsIdAndDatesByUser(@Param("userId") UUID userId);

  @Query(
      """
          SELECT w
          FROM WorkoutEntity w
          JOIN w.setGroups sg
          WHERE w.userId = :userId AND sg.exerciseId = :exerciseId
          ORDER BY w.date DESC
          """)
  List<WorkoutDateAndId> getWorkoutsIdAndDatesByUserAndExercise(
      @Param("userId") UUID userId, @Param("exerciseId") UUID exerciseId);

  @Query(
      """
          SELECT w
          FROM WorkoutEntity w
          LEFT JOIN FETCH w.setGroups sg
          LEFT JOIN FETCH sg.sets s
          WHERE w.id IN :ids
          ORDER BY sg.listOrder ASC, s.listOrder ASC
          """)
  List<WorkoutEntity> getFullWorkoutsByIds(@Param("ids") Set<UUID> ids);

  @Query(
      """
          SELECT w
          FROM WorkoutEntity w
          LEFT JOIN FETCH w.setGroups sg
          WHERE w.id = :id
          ORDER BY sg.listOrder ASC
          """)
  Optional<WorkoutEntity> getWorkoutWithSetGroupsById(@Param("id") UUID id);
}
