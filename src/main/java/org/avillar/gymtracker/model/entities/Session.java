package org.avillar.gymtracker.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Session extends BaseEntity {
    @Column(nullable = false)
    private String name;
    private String description;
    private Integer listOrder;
    private Date date;

    @ManyToOne()
    @JoinColumn(name = "user_app_id")
    private UserApp userApp;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @OneToMany(mappedBy = "session", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<SetGroup> setGroups = new HashSet<>();
}