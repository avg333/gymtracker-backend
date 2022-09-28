package org.avillar.gymtracker.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SetGroup extends BaseEntity {

    @Column(nullable = false)
    private int setGroupOrder;

    @ManyToOne
    @JoinColumn(name = "session_id", unique = true)
    private Session session;

    @OneToMany(mappedBy = "setGroup", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private java.util.Set<Set> sets = new HashSet<>();

}
