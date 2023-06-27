package org.avillar.gymtracker.workoutapi.domain;

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
  java.util.Set<Set> getSetsBySetGroupId(@Param("setGroupId") UUID setGroupId);

  @Query(
      """
            SELECT s
            FROM Set s
            JOIN FETCH s.setGroup sg
            WHERE sg.id = :setGroupId
            ORDER BY s.listOrder DESC
            """)
  List<Set> findLastSetForExerciseAndUserAux(@Param("setGroupId") UUID setGroupId);
}
