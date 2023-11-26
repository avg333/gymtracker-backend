package org.avillar.gymtracker.exercisesapi.common.adapter.repository;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleSupGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MuscleSupGroupDao extends JpaRepository<MuscleSupGroupEntity, UUID> {

  @Query(
      """
            SELECT msg
            FROM MuscleSupGroup msg
            JOIN FETCH msg.muscleGroups mg
            LEFT JOIN FETCH mg.muscleSubGroups msubg
          """)
  List<MuscleSupGroupEntity> getAll();
}
