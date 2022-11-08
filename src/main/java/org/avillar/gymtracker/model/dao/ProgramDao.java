package org.avillar.gymtracker.model.dao;

import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.model.entities.UserApp;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramDao extends JpaRepository<Program, Long> {
    List<Program> findByUserApp(UserApp userApp, Pageable pageable);

    long countByUserApp(UserApp userApp);
}