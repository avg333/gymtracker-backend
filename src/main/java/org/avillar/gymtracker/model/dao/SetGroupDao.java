package org.avillar.gymtracker.model.dao;

import org.avillar.gymtracker.model.entities.Session;
import org.avillar.gymtracker.model.entities.SetGroup;
import org.avillar.gymtracker.model.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetGroupDao extends JpaRepository<SetGroup, Long> {

    List<SetGroup> findBySessionOrderByListOrderAsc(Session session);

    List<SetGroup> findByWorkoutOrderByListOrderAsc(Workout workout);
}
