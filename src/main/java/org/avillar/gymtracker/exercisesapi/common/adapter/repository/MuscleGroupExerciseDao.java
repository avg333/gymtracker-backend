package org.avillar.gymtracker.exercisesapi.common.adapter.repository;

import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleGroupExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuscleGroupExerciseDao extends JpaRepository<MuscleGroupExerciseEntity, UUID> {}
