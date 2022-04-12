package org.avillar.gymtracker.model.dao;

import org.avillar.gymtracker.model.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("SELECT s FROM Session s WHERE s.program.id = :programId ORDER BY s.sessionOrder ASC")
    List<Session> getAllProgramSessionsOrderByName(@Param("programId") Long programId);
}