package org.avillar.gymtracker.exercisesapi.musclegroup.domain;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuscleGroupDao extends JpaRepository<MuscleGroup, UUID> {}
