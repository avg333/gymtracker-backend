package org.avillar.gymtracker.workout.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.domain.BaseEntity;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.avillar.gymtracker.user.domain.UserApp;
import org.hibernate.annotations.BatchSize;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.TemporalType.DATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Workout extends BaseEntity {
    @Column(nullable = false)
    @Temporal(DATE)
    private Date date;
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_app_id", nullable = false)
    private UserApp userApp;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("listOrder ASC")
    @BatchSize(size = 20)
    private Set<SetGroup> setGroups = new HashSet<>();

    public Workout(Long id) {
        super(id);
    }

}
