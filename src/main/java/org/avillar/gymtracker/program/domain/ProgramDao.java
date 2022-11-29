package org.avillar.gymtracker.program.domain;

import org.avillar.gymtracker.user.domain.UserApp;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramDao extends JpaRepository<Program, Long> {
    List<Program> findByUserApp(UserApp userApp, Pageable pageable);

    long countByUserApp(UserApp userApp);
}