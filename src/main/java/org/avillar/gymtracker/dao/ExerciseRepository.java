package org.avillar.gymtracker.dao;

import org.avillar.gymtracker.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

}