package org.avillar.gymtracker.dao;

import org.avillar.gymtracker.model.MuscleSubGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MuscleSubGroupRepository extends JpaRepository<MuscleSubGroup, Long> {

    @Query("SELECT m FROM MuscleSubGroup m WHERE m.muscleGroup.id = :muscleGroupId ORDER BY m.name ASC")
    List<MuscleSubGroup> findByMuscleGroup(Long muscleGroupId);
}