package org.avillar.gymtracker.musclegroup.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MuscleSubGroupDao extends JpaRepository<MuscleSubGroup, Long> {

    List<MuscleSubGroup> findByMuscleGroupOrderByNameAsc(MuscleGroup muscleGroup);
}