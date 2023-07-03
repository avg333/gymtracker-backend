package org.avillar.gymtracker.exercisesapi.domain;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoadTypeDao extends JpaRepository<LoadType, UUID> {}
