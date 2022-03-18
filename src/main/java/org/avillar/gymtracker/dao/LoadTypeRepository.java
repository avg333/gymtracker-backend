package org.avillar.gymtracker.dao;

import org.avillar.gymtracker.model.LoadType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoadTypeRepository extends JpaRepository<LoadType, Long> {
}