package org.avillar.gymtracker.exercisesapi.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MuscleGroupDao extends JpaRepository<MuscleGroup, UUID> {

  @Query(
      """
  SELECT mg
  FROM MuscleGroup mg
  JOIN mg.muscleSupGroups msg
  WHERE msg.id = :muscleSupGroupId
""")
  List<MuscleGroup> getALlMuscleGroupsByMuscleSupGroupId(
      @Param("muscleSupGroupId") UUID muscleSupGroupId);
}
