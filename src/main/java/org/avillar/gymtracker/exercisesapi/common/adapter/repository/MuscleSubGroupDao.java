package org.avillar.gymtracker.exercisesapi.common.adapter.repository;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleSubGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MuscleSubGroupDao extends JpaRepository<MuscleSubGroupEntity, UUID> {

  @Query(
      """
            SELECT msubg
            FROM MuscleSubGroup msubg
            WHERE msubg.muscleGroup.id = :muscleGroupId
          """)
  List<MuscleSubGroupEntity> getAllByMuscleGroupId(UUID muscleGroupId);
}
