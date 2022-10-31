package org.avillar.gymtracker.model.dao;

import org.avillar.gymtracker.model.entities.UserApp;
import org.avillar.gymtracker.model.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutDao extends JpaRepository<Workout, Long> {

    List<Workout> findByUserAppOrderByDateDesc(UserApp userApp);
}
