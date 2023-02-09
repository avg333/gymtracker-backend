package org.avillar.gymtracker.set.domain;

import org.avillar.gymtracker.exercise.domain.Exercise;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.avillar.gymtracker.user.domain.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SetDao extends JpaRepository<Set, Long> {

    List<Set> findBySetGroupOrderByListOrderAsc(SetGroup setGroup);

    @Query("""
            SELECT s FROM Set s
            JOIN s.setGroup sg JOIN sg.workout w JOIN sg.exercise e JOIN w.userApp u
            WHERE u = :user AND e = :exercise AND w.date < :dateWorkoutSetGroup
            ORDER BY w.date DESC, sg.listOrder DESC, s.listOrder DESC
            """)
    List<Set> findLastSetForExerciseAndUser(@Param("user") UserApp user, @Param("exercise") Exercise exercise, @Param("dateWorkoutSetGroup") Date dateWorkoutSetGroup);

    @Query("""
            SELECT s FROM Set s JOIN s.setGroup sg
            WHERE sg = :setGroup
            ORDER BY s.listOrder DESC
            """)
    List<Set> findLastSetForExerciseAndUserAux(@Param("setGroup") SetGroup setGroup);
}