package org.avillar.gymtracker.workout.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.domain.BaseEntity;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.avillar.gymtracker.user.domain.UserApp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Workout extends BaseEntity {
    @Column(nullable = false)
    private Date date;
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_app_id", nullable = false)
    private UserApp userApp;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<SetGroup> setGroups = new HashSet<>();
}
