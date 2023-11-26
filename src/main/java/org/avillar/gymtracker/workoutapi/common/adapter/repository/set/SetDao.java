package org.avillar.gymtracker.workoutapi.common.adapter.repository.set;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.model.SetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SetDao extends JpaRepository<SetEntity, UUID> {

  @Query(
      """
          SELECT s
          FROM SetEntity s
          JOIN FETCH s.setGroup sg
          JOIN FETCH sg.workout w
          WHERE s.id = :id
          """)
  Optional<SetEntity> getSetFullById(@Param("id") UUID id);

  @Query(
      """
          SELECT s
          FROM SetEntity s
          WHERE s.setGroup.id = :setGroupId
          ORDER BY s.listOrder ASC
          """)
  List<SetEntity> getSetsBySetGroupId(@Param("setGroupId") UUID setGroupId);

  @Query(
      """
          SELECT s
          FROM SetEntity s
          JOIN FETCH s.setGroup sg
          JOIN sg.workout w
          WHERE sg.exerciseId = :exerciseId
          AND w.userId = :userId
          AND w.date < :workoutDate
          ORDER BY w.date DESC, sg.listOrder DESC, s.listOrder DESC
          """)
  List<SetEntity> findLastSetForExerciseAndUserAux(
      @Param("userId") UUID userId,
      @Param("exerciseId") UUID exerciseId,
      @Param("workoutDate") Date workoutDate);
}
