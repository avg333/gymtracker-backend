package org.avillar.gymtracker.exercisesapi.common.adapter.repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AccessTypeEnum;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExerciseDao extends JpaRepository<ExerciseEntity, UUID> {

  @Query(
      """
            SELECT e
            FROM Exercise e
            LEFT JOIN FETCH e.muscleGroupExercises mge
            WHERE e.id = :exerciseId
          """)
  List<ExerciseEntity> getExerciseByIdWithMuscleGroupEx(@Param("exerciseId") UUID exerciseId);

  @Query(
      """
            SELECT e
            FROM Exercise e
            WHERE e.name = :name
            AND ((e.owner = :owner AND e.accessType = :privateAT)
            OR e.accessType = :publicAT)
          """)
  List<ExerciseEntity> getByNameAndOwner(
      @Param("name") String name,
      @Param("owner") UUID owner,
      @Param("privateAT") final AccessTypeEnum privateAT,
      @Param("publicAT") final AccessTypeEnum publicAT);

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
  List<ExerciseEntity> getFullExerciseByIds(@Param("exerciseIds") Set<UUID> exerciseIds);
}
