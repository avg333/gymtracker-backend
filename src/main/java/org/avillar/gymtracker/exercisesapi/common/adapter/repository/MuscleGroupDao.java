package org.avillar.gymtracker.exercisesapi.common.adapter.repository;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MuscleGroupDao extends JpaRepository<MuscleGroupEntity, UUID> {

  @Query(
      """
            SELECT mg
            FROM MuscleGroup mg
            JOIN mg.muscleSupGroups msg
            LEFT JOIN FETCH mg.muscleSubGroups mSubg
            WHERE msg.id = :muscleSupGroupId
          """)
  List<MuscleGroupEntity> getAllMuscleGroupsByMuscleSupGroupId(
      @Param("muscleSupGroupId") UUID muscleSupGroupId);
}
