package org.avillar.gymtracker.exercisesapi.musclegroup.domain;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuscleGroupExerciseDao extends JpaRepository<MuscleGroupExercise, UUID> {}
