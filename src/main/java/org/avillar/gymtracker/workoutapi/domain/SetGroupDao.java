package org.avillar.gymtracker.workoutapi.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SetGroupDao extends JpaRepository<SetGroup, UUID> {

  @Query(
      """
          SELECT sg
          FROM SetGroup sg
          JOIN FETCH sg.workout w
          WHERE sg.id = :id
          """)
  List<SetGroup> getSetGroupWithWorkoutById(@Param("id") UUID id);

  @Query(
      """
          SELECT sg
          FROM SetGroup sg
          WHERE sg.workout.id = :workoutId
          """)
  List<SetGroup> getSetGroupsByWorkoutId(@Param("workoutId") UUID workoutId);

  @Query(
      """
          SELECT sg
          FROM SetGroup sg
          JOIN FETCH sg.workout w
          LEFT JOIN FETCH sg.sets s
          WHERE sg.id IN (:ids)
          """)
  List<SetGroup> getSetGroupFullByIds(@Param("ids") List<UUID> ids);

  @Query(
      """
          SELECT sg
          FROM SetGroup sg
          JOIN FETCH sg.workout w
          LEFT JOIN FETCH sg.sets s
          WHERE w.userId = :userId AND sg.exerciseId = :exerciseId
          ORDER BY w.date DESC, sg.listOrder DESC
          """)
  List<SetGroup> getSetGroupsFullByUserIdAndExerciseId(
      @Param("userId") UUID userId, @Param("exerciseId") UUID exerciseId);
}
