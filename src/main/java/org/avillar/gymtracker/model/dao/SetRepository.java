package org.avillar.gymtracker.model.dao;

import org.avillar.gymtracker.model.entities.Set;
import org.avillar.gymtracker.model.entities.SetGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetRepository extends JpaRepository<Set, Long> {

    List<Set> findBySetGroupOrderByListOrderAsc(SetGroup setGroup);
}