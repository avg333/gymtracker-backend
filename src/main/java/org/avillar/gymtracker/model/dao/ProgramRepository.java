package org.avillar.gymtracker.model.dao;

import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    List<Program> findByUserOrderByNameAsc(User user);
}