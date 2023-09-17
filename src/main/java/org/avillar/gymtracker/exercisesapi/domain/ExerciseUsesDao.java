package org.avillar.gymtracker.exercisesapi.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExerciseUsesDao extends JpaRepository<ExerciseUses, UUID> {

  @Query(
      """
            SELECT eu
            FROM ExerciseUses eu
            WHERE eu.exercise.id = :exerciseId
            AND eu.userId= :userId
          """)
  List<ExerciseUses> getExerciseUsesByExerciseIdAndUserId(
      @Param("exerciseId") UUID exerciseId, @Param("userId") UUID userId);
}
