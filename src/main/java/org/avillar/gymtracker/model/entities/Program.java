package org.avillar.gymtracker.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.model.enums.ProgramLevelEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    private String url;
    @Column(nullable = false)
    private ProgramLevelEnum level;
    private final Date createdAt = new Date();

    @OneToMany(mappedBy = "program", cascade = CascadeType.REMOVE)
    private List<Session> sessions = new ArrayList<>();

}
