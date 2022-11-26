package org.avillar.gymtracker.model.dao;

import org.avillar.gymtracker.model.entities.MuscleGroup;
import org.avillar.gymtracker.model.entities.MuscleSubGroup;
import org.avillar.gymtracker.model.entities.MuscleSupGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MuscleGroupDao extends JpaRepository<MuscleGroup, Long> {

    List<MuscleGroup> findByMuscleSupGroupOrderByNameAsc(MuscleSupGroup muscleSupGroup);
}