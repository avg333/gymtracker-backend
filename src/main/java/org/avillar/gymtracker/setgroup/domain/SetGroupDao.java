package org.avillar.gymtracker.setgroup.domain;

import org.avillar.gymtracker.session.domain.Session;
import org.avillar.gymtracker.workout.domain.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetGroupDao extends JpaRepository<SetGroup, Long> {

    List<SetGroup> findBySessionOrderByListOrderAsc(Session session);

    List<SetGroup> findByWorkoutOrderByListOrderAsc(Workout workout);
}
