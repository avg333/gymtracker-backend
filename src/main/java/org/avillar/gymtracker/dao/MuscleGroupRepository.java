package org.avillar.gymtracker.dao;

import org.avillar.gymtracker.model.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, Long> {
}