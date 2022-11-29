package org.avillar.gymtracker.exercise.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseDao extends JpaRepository<Exercise, Long> {

}