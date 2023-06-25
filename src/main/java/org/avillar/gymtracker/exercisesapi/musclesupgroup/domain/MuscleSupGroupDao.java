package org.avillar.gymtracker.exercisesapi.musclesupgroup.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MuscleSupGroupDao extends JpaRepository<MuscleSupGroup, UUID> {

  @Query(
      """
  SELECT msg
  FROM MuscleSupGroup msg
  JOIN FETCH msg.muscleGroups mg
  LEFT JOIN FETCH mg.muscleSubGroups msubg
""")
  List<MuscleSupGroup> getAll();
}
