package org.avillar.gymtracker.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private int sessionOrder;
    private final Date createdAt = new Date();

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;
    @OneToMany(mappedBy = "session", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @BatchSize(size = 20)
    private List<Set> sets = new ArrayList<>();


}
