package org.avillar.gymtracker.model.dao;

import org.avillar.gymtracker.model.entities.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuscleGroupDao extends JpaRepository<MuscleGroup, Long> {
}