package org.avillar.gymtracker.dao;

import org.avillar.gymtracker.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {

    List<Program> findAllByOrderByNameAsc();
}