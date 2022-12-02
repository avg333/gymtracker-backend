package org.avillar.gymtracker.musclegroup.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.domain.BaseEntity;
import org.avillar.gymtracker.exercise.domain.Exercise;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MuscleGroupExercise extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "muscle_group_id")
    private MuscleGroup muscleGroup;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    private Double weight;

}
