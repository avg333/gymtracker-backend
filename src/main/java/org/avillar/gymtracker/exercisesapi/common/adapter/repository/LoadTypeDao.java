package org.avillar.gymtracker.exercisesapi.common.adapter.repository;

import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.LoadTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoadTypeDao extends JpaRepository<LoadTypeEntity, UUID> {}
