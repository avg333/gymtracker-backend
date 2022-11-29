package org.avillar.gymtracker.musclegroup.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MuscleGroupDao extends JpaRepository<MuscleGroup, Long> {

    List<MuscleGroup> findByMuscleSupGroupOrderByNameAsc(MuscleSupGroup muscleSupGroup);
}