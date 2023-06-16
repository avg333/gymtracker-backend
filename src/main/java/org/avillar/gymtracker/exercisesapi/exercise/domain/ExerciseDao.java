package org.avillar.gymtracker.exercisesapi.exercise.domain;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExerciseDao extends JpaRepository<Exercise, UUID> {

  @Query(
      """
        SELECT e
        FROM Exercise e
        LEFT JOIN FETCH e.muscleSubGroups msubg
        JOIN FETCH e.muscleGroupExercises mge
        JOIN FETCH mge.muscleGroup mg
        JOIN FETCH mg.muscleSupGroups msupg
        WHERE e.id IN (:exerciseIds)
      """)
  List<Exercise> getFullExerciseByIds(@Param("exerciseIds") Set<UUID> exerciseIds);

  @Query(
      """
            SELECT e
            FROM Exercise e
            LEFT JOIN FETCH e.muscleSubGroups msubg
            JOIN FETCH e.muscleGroupExercises mge
            JOIN FETCH mge.muscleGroup mg
            JOIN FETCH mg.muscleSupGroups msupg
          """)
  List<Exercise> getAllFullExercises();
}
