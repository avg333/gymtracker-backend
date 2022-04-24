package org.avillar.gymtracker.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.model.enums.ProgramLevelEnum;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@BatchSize(size = 20)
public class Program {

    private final Date createdAt = new Date();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    private String url;
    @Column(nullable = false)
    private ProgramLevelEnum level;
    @OneToMany(mappedBy = "program", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Session> sessions = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
