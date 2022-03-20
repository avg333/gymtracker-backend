package org.avillar.gymtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LoadType {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "loadType", orphanRemoval = true)
    private Set<Exercise> exercises = new LinkedHashSet<>();
}
