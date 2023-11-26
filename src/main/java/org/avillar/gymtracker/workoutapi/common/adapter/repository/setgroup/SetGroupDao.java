package org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.model.SetGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SetGroupDao extends JpaRepository<SetGroupEntity, UUID> {

  @Query(
      """
          SELECT sg
          FROM SetGroupEntity sg
          JOIN FETCH sg.workout w
          LEFT JOIN FETCH sg.sets s
          WHERE sg.id IN (:ids)
          ORDER BY sg.listOrder ASC, s.listOrder ASC
          """)
  List<SetGroupEntity> getSetGroupFullByIds(@Param("ids") Set<UUID> ids);

  @Query(
      """
          SELECT sg
          FROM SetGroupEntity sg
          JOIN FETCH sg.workout w
          WHERE sg.id = :id
          """)
  Optional<SetGroupEntity> getSetGroupWithWorkoutById(@Param("id") UUID id);

  @Query(
      """
          SELECT sg
          FROM SetGroupEntity sg
          JOIN FETCH sg.workout w
          WHERE sg.workout.id = :workoutId
          ORDER BY sg.listOrder ASC
          """)
  List<SetGroupEntity> getSetGroupsByWorkoutId(@Param("workoutId") UUID workoutId);

  @Query(
      """
          SELECT sg
          FROM SetGroupEntity sg
          JOIN FETCH sg.workout w
          LEFT JOIN FETCH sg.sets s
          WHERE w.userId = :userId AND sg.exerciseId = :exerciseId
          ORDER BY w.date DESC, sg.listOrder DESC
          """)
  List<SetGroupEntity> getSetGroupsFullByUserIdAndExerciseId(
      @Param("userId") UUID userId, @Param("exerciseId") UUID exerciseId);
}
