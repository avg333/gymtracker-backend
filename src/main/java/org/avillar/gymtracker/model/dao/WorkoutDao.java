package org.avillar.gymtracker.model.dao;

import org.avillar.gymtracker.model.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutDao extends JpaRepository<Workout, Long> {
}
