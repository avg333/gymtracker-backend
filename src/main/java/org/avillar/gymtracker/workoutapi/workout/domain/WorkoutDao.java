package org.avillar.gymtracker.workoutapi.workout.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkoutDao extends JpaRepository<Workout, UUID> {

  @Query(
      """
            SELECT w
            FROM Workout w
            WHERE w.userId = :userId
            ORDER BY w.date ASC
            """)
  List<WorkoutDateAndId> getWorkoutsIdAndDatesByUser(@Param("userId") UUID userId);

  @Query(
      """
            SELECT w
            FROM Workout w
            JOIN w.setGroups sg
            WHERE w.userId = :userId AND sg.exerciseId = :exerciseId
            ORDER BY w.date DESC
            """)
  List<WorkoutDateAndId> getWorkoutsIdAndDatesByUserAndExercise(
      @Param("userId") UUID userId, @Param("exerciseId") UUID exerciseId);

  //
  @Query(
      """
            SELECT COUNT(w) > 0
            FROM Workout w
            WHERE w.userId = :userId AND w.date = :date
            """)
  boolean existsWorkoutByUserAndDate(@Param("userId") UUID userId, @Param("date") Date date);

  // Se usa en el POST y en el UPDATE date y en el UpdateSetGroups
  @Query(
      """
            SELECT w
            FROM Workout w
            LEFT JOIN FETCH w.setGroups sg
            LEFT JOIN FETCH sg.sets s
            WHERE w.id IN :ids
            """)
  Set<Workout> getFullWorkoutByIds(@Param("ids") List<UUID> ids);

  // Se usa en el POST SetGroup
  @Query(
      """
            SELECT w
            FROM Workout w
            LEFT JOIN FETCH w.setGroups sg
            WHERE w.id = :id
            """)
  List<Workout> getWorkoutWithSetGroupsById(@Param("id") UUID id);
}
