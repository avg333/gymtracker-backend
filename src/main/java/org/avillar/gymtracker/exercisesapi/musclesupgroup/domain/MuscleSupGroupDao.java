package org.avillar.gymtracker.exercisesapi.musclesupgroup.domain;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuscleSupGroupDao extends JpaRepository<MuscleSupGroup, UUID> {}
