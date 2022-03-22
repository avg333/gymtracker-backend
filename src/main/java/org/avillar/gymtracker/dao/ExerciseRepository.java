package org.avillar.gymtracker.dao;

import org.avillar.gymtracker.model.Exercise;
import org.avillar.gymtracker.model.LoadType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @Query("SELECT ex FROM Exercise ex INNER JOIN ex.muscleGroups INNER JOIN ex.loadType WHERE ex.muscleGroups = :idMuscleGroup")
    List<Exercise> findByFilters(@Param("idMuscleGroup") Long idMuscleGroup, @Param("idLoadType") Long idLoadType, @Param("unilateral") Boolean unilateral);
}