package org.avillar.gymtracker.musclegroup.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MuscleGroupDao extends JpaRepository<MuscleGroup, Long> {

    @Query("SELECT mg FROM MuscleGroup mg JOIN mg.muscleSupGroups msg WHERE msg = :muscleSupGroup")
    List<MuscleGroup> findByMuscleSupGroupsOrderByNameAsc(MuscleSupGroup muscleSupGroup);

}