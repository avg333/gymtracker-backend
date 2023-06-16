package org.avillar.gymtracker.exercisesapi.musclesubgroup.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MuscleSubGroupDao extends JpaRepository<MuscleSubGroup, UUID> {

  @Query(
      """
        SELECT msubg
        FROM MuscleSubGroup msubg
        WHERE msubg.muscleGroup.id = :muscleGroupId
      """)
  List<MuscleSubGroup> getAllByMuscleGroupId(UUID muscleGroupId);
}
