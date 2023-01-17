package org.avillar.gymtracker.setgroup.domain;

import org.avillar.gymtracker.exercise.domain.Exercise;
import org.avillar.gymtracker.session.domain.Session;
import org.avillar.gymtracker.user.domain.UserApp;
import org.avillar.gymtracker.workout.domain.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SetGroupDao extends JpaRepository<SetGroup, Long> {

    List<SetGroup> findBySessionOrderByListOrderAsc(Session session);

    List<SetGroup> findByWorkoutOrderByListOrderAsc(Workout workout);

    @Query("SELECT sg " +
            "FROM SetGroup sg " +
            "JOIN sg.workout w " +
            "JOIN w.userApp u " +
            "JOIN sg.exercise e " +
            "WHERE u = :user " +
            "AND e = :exercise " +
            "ORDER BY w.date DESC, sg.listOrder DESC " +
            "OFFSET 1 ROW")
    List<SetGroup> findLastUserExerciseSetGroup(@Param("user") UserApp user, @Param("exercise") Exercise exercise);
}
