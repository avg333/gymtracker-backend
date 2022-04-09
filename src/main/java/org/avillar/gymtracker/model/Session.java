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
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private int sessionOrder;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;
    @OneToMany(mappedBy = "session", cascade = CascadeType.REMOVE)
    private List<Set> sets = new ArrayList<>();


}
