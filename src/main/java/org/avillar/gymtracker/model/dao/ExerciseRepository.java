package org.avillar.gymtracker.model.dao;

import org.avillar.gymtracker.model.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

}