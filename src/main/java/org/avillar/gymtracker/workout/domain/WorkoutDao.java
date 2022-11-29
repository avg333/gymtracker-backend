package org.avillar.gymtracker.workout.domain;

import org.avillar.gymtracker.user.domain.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutDao extends JpaRepository<Workout, Long> {

    List<Workout> findByUserAppOrderByDateDesc(UserApp userApp);
}
