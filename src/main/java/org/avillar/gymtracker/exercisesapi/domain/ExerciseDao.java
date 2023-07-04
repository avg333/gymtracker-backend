package org.avillar.gymtracker.exercisesapi.domain;

import java.util.Collection;
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
            AND ((:loadTypeIds) IS NULL OR CAST(e.loadType.id as org.hibernate.type.UUIDCharType) IN (:loadTypeIds))
            AND ((:muscleSupGroupIds) IS NULL OR CAST(msupg.id as org.hibernate.type.UUIDCharType) IN (:muscleSupGroupIds))
            AND ((:muscleGroupIds) IS NULL OR CAST(mg.id as org.hibernate.type.UUIDCharType) IN (:muscleGroupIds))
            AND ((:muscleSubGroupIds) IS NULL OR CAST(msubg.id as org.hibernate.type.UUIDCharType) IN (:muscleSubGroupIds))
          """) // TODO Necesario el fetch?
  List<Exercise> getAllFullExercises(
      @Param("userId") final UUID userId,
      @Param("privateAT") final AccessTypeEnum privateAT,
      @Param("publicAT") final AccessTypeEnum publicAT,
      @Param("name") final String name,
      @Param("description") final String description,
      @Param("unilateral") final Boolean unilateral,
      @Param("loadTypeIds") final Collection<UUID> loadTypeIds,
      @Param("muscleSupGroupIds") final Collection<UUID> muscleSupGroupIds,
      @Param("muscleGroupIds") final Collection<UUID> muscleGroupIds,
      @Param("muscleSubGroupIds") final Collection<UUID> muscleSubGroupIds);
}
