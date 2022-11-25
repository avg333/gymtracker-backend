package org.avillar.gymtracker.model.dao;

import org.avillar.gymtracker.model.entities.MuscleSupGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuscleSupGroupDao extends JpaRepository<MuscleSupGroup, Long> {
}