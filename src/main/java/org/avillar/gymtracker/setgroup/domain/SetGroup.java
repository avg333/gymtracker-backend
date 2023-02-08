package org.avillar.gymtracker.setgroup.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.exercise.domain.Exercise;
import org.avillar.gymtracker.session.domain.Session;
import org.avillar.gymtracker.set.domain.Set;
import org.avillar.gymtracker.sort.domain.SortableEntity;
import org.avillar.gymtracker.workout.domain.Workout;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SetGroup extends SortableEntity {

    private String description;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;
    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @OneToMany(mappedBy = "setGroup", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("listOrder ASC")
    private java.util.Set<Set> sets = new HashSet<>();

    public SetGroup(Long id) {
        super(id);
    }

    public static SetGroup clone(final SetGroup setGroup) {
        final SetGroup newSetGroup = new SetGroup();
        setGroup.setListOrder(setGroup.getListOrder());
        setGroup.setDescription(setGroup.getDescription());
        setGroup.setExercise(setGroup.getExercise());
        return newSetGroup;
    }

}
