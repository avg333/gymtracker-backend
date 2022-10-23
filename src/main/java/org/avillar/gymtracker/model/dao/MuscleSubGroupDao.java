package org.avillar.gymtracker.model.dao;

import org.avillar.gymtracker.model.entities.MuscleGroup;
import org.avillar.gymtracker.model.entities.MuscleSubGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MuscleSubGroupDao extends JpaRepository<MuscleSubGroup, Long> {

    List<MuscleSubGroup> findByMuscleGroupOrderByNameAsc(MuscleGroup muscleGroup);
}