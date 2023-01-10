package org.avillar.gymtracker.session.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.domain.SortableEntity;
import org.avillar.gymtracker.program.domain.Program;
import org.avillar.gymtracker.setgroup.domain.SetGroup;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Session extends SortableEntity {
    @Column(nullable = false)
    private String name;
    private String description;
    private DayOfWeek dayOfWeek;

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @OneToMany(mappedBy = "session", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<SetGroup> setGroups = new HashSet<>();
}