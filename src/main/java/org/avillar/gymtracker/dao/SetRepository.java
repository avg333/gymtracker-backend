package org.avillar.gymtracker.dao;

import org.avillar.gymtracker.model.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetRepository extends JpaRepository<Set, Long> {
}