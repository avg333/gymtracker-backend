package org.avillar.gymtracker.dao;

import org.avillar.gymtracker.model.Exercise;
import org.avillar.gymtracker.model.LoadType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @Query("SELECT ex FROM Exercise ex WHERE (:loadType IS NULL OR :loadType = ex.loadType) AND " +
            "(:unilateral IS NULL OR :unilateral = ex.unilateral)")
    List<Exercise> findByFilters(@Param("loadType") LoadType loadType, @Param("unilateral") Boolean unilateral);
}