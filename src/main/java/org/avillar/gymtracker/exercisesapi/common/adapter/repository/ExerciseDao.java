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

  @Query(
      """
            SELECT e
            FROM Exercise e
            LEFT JOIN e.muscleSubGroups msubg
            JOIN e.muscleGroupExercises mge
            JOIN mge.muscleGroup mg
            JOIN mg.muscleSupGroups msupg
            WHERE ((e.owner = :userId AND e.accessType = :privateAT) OR e.accessType = :publicAT)
            AND (:name IS NULL OR e.name LIKE CONCAT('%',:name,'%'))
            AND (:description IS NULL OR e.description LIKE CONCAT('%',:description,'%'))
            AND (:unilateral IS NULL OR e.unilateral = :unilateral)
            AND (:unilateral IS NULL OR e.unilateral = :unilateral)
            AND (:emptyLoadTypeIds = True OR e.loadType.id IN (:loadTypeIds))
            AND (:emptyMSGIds = True OR msubg.id IN (:msgIds))
            AND (:emptyMSupGIds = True OR msupg.id IN (:msupgIds))
            AND (:emptyMGIds = True OR mg.id IN (:mgIds))
          """)
  List<ExerciseEntity> getAllFullExercises(
      @Param("userId") final UUID userId,
      @Param("privateAT") final AccessTypeEnum privateAT,
      @Param("publicAT") final AccessTypeEnum publicAT,
      @Param("name") final String name,
      @Param("description") final String description,
      @Param("unilateral") final Boolean unilateral,
      @Param("emptyLoadTypeIds") final boolean emptyLoadTypeIds,
      @Param("loadTypeIds") final List<UUID> loadTypeIds,
      @Param("emptyMSGIds") final boolean emptyMSGIds,
      @Param("msgIds") final List<UUID> msgIds,
      @Param("emptyMSupGIds") final boolean emptyMSupGIds,
      @Param("msupgIds") final List<UUID> msupgIds,
      @Param("emptyMGIds") final boolean emptyMGIds,
      @Param("mgIds") final List<UUID> mgIds);
}
