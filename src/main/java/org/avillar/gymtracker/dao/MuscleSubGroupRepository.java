package org.avillar.gymtracker.dao;

import org.avillar.gymtracker.model.MuscleGroup;
import org.avillar.gymtracker.model.MuscleSubGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MuscleSubGroupRepository extends JpaRepository<MuscleSubGroup, Long> {

    List<MuscleSubGroup> findByMuscleGroup(MuscleGroup muscleGroup);
}