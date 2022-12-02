package org.avillar.gymtracker.program.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.domain.BaseEntity;
import org.avillar.gymtracker.enums.domain.ProgramLevelEnum;
import org.avillar.gymtracker.image.domain.Image;
import org.avillar.gymtracker.session.domain.Session;
import org.avillar.gymtracker.user.domain.UserApp;

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
    private ProgramLevelEnum level = ProgramLevelEnum.ANY;

    @ManyToOne
    @JoinColumn(name = "user_app_id", nullable = false)
    private UserApp userApp;

    @OneToMany(mappedBy = "program", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Session> sessions = new HashSet<>();

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "image_id")
    private Image image;
}