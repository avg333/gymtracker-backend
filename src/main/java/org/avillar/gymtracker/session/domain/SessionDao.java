package org.avillar.gymtracker.session.domain;

import org.avillar.gymtracker.program.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionDao extends JpaRepository<Session, Long> {

    List<Session> findByProgramOrderByListOrder(Program program);
}