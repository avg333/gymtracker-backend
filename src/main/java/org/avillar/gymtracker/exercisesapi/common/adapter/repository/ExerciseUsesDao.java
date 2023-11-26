package org.avillar.gymtracker.exercisesapi.common.adapter.repository;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseUsesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExerciseUsesDao extends JpaRepository<ExerciseUsesEntity, UUID> {

  @Query(
      """
            SELECT eu
            FROM ExerciseUses eu
            WHERE eu.exercise.id IN (:exerciseIds)
            AND eu.userId= :userId
          """)
  List<ExerciseUsesEntity> getExerciseUsesByExerciseIdAndUserId(
      @Param("exerciseIds") List<UUID> exerciseIds, @Param("userId") UUID userId);
}
