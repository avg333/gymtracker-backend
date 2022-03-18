package org.avillar.gymtracker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Exercise> exercises = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Program> programs = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Session> sessions = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Set> sets = new ArrayList<>();

}
