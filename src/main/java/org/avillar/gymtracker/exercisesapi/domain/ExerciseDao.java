package org.avillar.gymtracker.exercisesapi.domain;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AccessTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExerciseDao extends JpaRepository<Exercise, UUID> {

  @Query(
      """
        SELECT e
        FROM Exercise e
        LEFT JOIN FETCH e.loadType lt
        LEFT JOIN FETCH e.muscleSubGroups msubg
        JOIN FETCH e.muscleGroupExercises mge
        JOIN FETCH mge.muscleGroup mg
        JOIN FETCH mg.muscleSupGroups msupg
        WHERE e.id IN (:exerciseIds)
      """)
  List<Exercise> getFullExerciseByIds(@Param("exerciseIds") Set<UUID> exerciseIds);

  @Query(
      """
            SELECT e
            FROM Exercise e
            LEFT JOIN FETCH e.muscleSubGroups msubg
            JOIN FETCH e.muscleGroupExercises mge
            JOIN FETCH mge.muscleGroup mg
            JOIN FETCH mg.muscleSupGroups msupg
            WHERE ((e.owner = :userId AND e.accessType = :privateAT) OR e.accessType = :publicAT)
            AND (:name IS NULL OR e.name LIKE CONCAT('%',:name,'%'))
            AND (:description IS NULL OR e.description LIKE CONCAT('%',:description,'%'))
            AND (:unilateral IS NULL OR e.unilateral = :unilateral)
          """) // FIXME Necesario el fetch?
  List<Exercise> getAllFullExercises(
      @Param("userId") final UUID userId,
      @Param("privateAT") final AccessTypeEnum privateAT,
      @Param("publicAT") final AccessTypeEnum publicAT,
      @Param("name") final String name,
      @Param("description") final String description,
      @Param("unilateral") final Boolean unilateral);
}
