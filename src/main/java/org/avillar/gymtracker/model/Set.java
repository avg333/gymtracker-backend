package org.avillar.gymtracker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Set {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date date;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    private Double weight;
    private int reps;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
