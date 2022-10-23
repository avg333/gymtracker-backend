package org.avillar.gymtracker.model.dao;

import org.avillar.gymtracker.model.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseDao extends JpaRepository<Exercise, Long> {

}