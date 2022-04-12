package org.avillar.gymtracker.model.dao;

import org.avillar.gymtracker.model.entities.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetRepository extends JpaRepository<Set, Long> {
}