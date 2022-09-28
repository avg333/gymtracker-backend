package org.avillar.gymtracker.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.model.enums.ProgramLevelEnum;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Program extends BaseEntity {
    @NotBlank
    @Column(nullable = false)
    private String name;
    private String description;
    private String url;
    @NotNull
    @Column(nullable = false)
    private ProgramLevelEnum level;

    @ManyToOne
    @JoinColumn(name = "user_app_id")
    private UserApp userApp;

    @OneToMany(mappedBy = "program", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Session> sessions = new HashSet<>();

    @Override
    public String toString() {
        return "Program{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", level=" + level +
                ", userApp=" + userApp +
                ", sessions=" + sessions +
                "} " + super.toString();
    }
}
