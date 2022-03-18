package org.avillar.gymtracker.dao;

import org.avillar.gymtracker.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}