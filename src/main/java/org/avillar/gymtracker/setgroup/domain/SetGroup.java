package org.avillar.gymtracker.setgroup.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.domain.BaseEntity;
import org.avillar.gymtracker.exercise.domain.Exercise;
import org.avillar.gymtracker.session.domain.Session;
import org.avillar.gymtracker.set.domain.Set;
import org.avillar.gymtracker.workout.domain.Workout;

import javax.persistence.*;
import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SetGroup extends BaseEntity {

    private String description;
    @OrderBy
    @Column(nullable = false)
    private Integer listOrder;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @OneToMany(mappedBy = "setGroup", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private java.util.Set<Set> sets = new HashSet<>();
}