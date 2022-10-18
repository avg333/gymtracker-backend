package org.avillar.gymtracker.model.dao;

import org.avillar.gymtracker.model.entities.Session;
import org.avillar.gymtracker.model.entities.SetGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetGroupRepository  extends JpaRepository<SetGroup, Long> {

    List<SetGroup> findBySessionOrderBySetGroupOrderAsc(Session session);
}
