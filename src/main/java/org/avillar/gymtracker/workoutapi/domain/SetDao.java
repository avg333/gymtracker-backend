package org.avillar.gymtracker.workoutapi.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SetDao extends JpaRepository<Set, UUID> {

  @Query(
      """
          SELECT s
          FROM Set s
          JOIN FETCH s.setGroup sg
          JOIN FETCH sg.workout w
          WHERE s.id = :id
          """)
  List<Set> getSetFullById(@Param("id") UUID id);

  @Query(
      """
          SELECT s
          FROM Set s
          WHERE s.setGroup.id = :setGroupId
          """)
  List<Set> getSetsBySetGroupId(@Param("setGroupId") UUID setGroupId);

  @Query(
      """
          SELECT s
          FROM Set s
          JOIN FETCH s.setGroup sg
          JOIN sg.workout w
          WHERE sg.exerciseId = :exerciseId
          AND w.userId = :userId
          AND w.date < :workoutDate
          ORDER BY w.date DESC, sg.listOrder DESC, s.listOrder DESC
          """)
  List<Set> findLastSetForExerciseAndUserAux(
      @Param("userId") UUID userId,
      @Param("exerciseId") UUID exerciseId,
      @Param("workoutDate") Date workoutDate);
}
