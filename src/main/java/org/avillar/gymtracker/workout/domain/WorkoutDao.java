package org.avillar.gymtracker.workout.domain;

import org.avillar.gymtracker.exercise.domain.Exercise;
import org.avillar.gymtracker.user.domain.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface WorkoutDao extends JpaRepository<Workout, Long> {


    Integer countByUserAppAndDate(UserApp userApp, Date date);

    List<Workout> findByUserAppOrderByDateDesc(UserApp userApp);

    @Query("SELECT w FROM Workout w JOIN w.userApp u WHERE u = :user ORDER BY w.id ASC")
    List<Workout> getWorkoutDatesByUser(@Param("user") UserApp userApp);

    List<Workout> findByUserAppAndDate(UserApp userApp, Date date);

    @Query("""
            SELECT w FROM Workout w
            JOIN w.setGroups sg JOIN sg.exercise e JOIN w.userApp u
            WHERE u = :user AND e = :exercise
            ORDER BY w.date DESC
            """)
    List<Workout> findWorkoutsWithUserAndExercise(@Param("user") UserApp user, @Param("exercise") Exercise exercise);
}
