package org.avillar.gymtracker.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.model.enums.ProgramLevelEnum;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Program extends BaseEntity {
    @Column(nullable = false)
    private String name;
    private String description;
    private String url;
    @Column(nullable = false)
    private ProgramLevelEnum level;

    @ManyToOne
    @JoinColumn(name = "user_app_id")
    private UserApp userApp;

    @OneToMany(mappedBy = "program", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Session> sessions = new HashSet<>();

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "image_id")
    private Image image;
}