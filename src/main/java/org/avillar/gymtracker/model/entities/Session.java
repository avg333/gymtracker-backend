package org.avillar.gymtracker.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Session extends BaseEntity {

    @NotBlank
    @Column(nullable = false)
    private String name;
    private String description;
    @NotNull
    @Column(nullable = false)
    private Integer sessionOrder;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @OneToMany(mappedBy = "session", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<SetGroup> setGroups = new HashSet<>();

    @Override
    public String toString() {
        return "Session{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sessionOrder=" + sessionOrder +
                ", program=" + program +
                ", setGroups=" + setGroups +
                "} " + super.toString();
    }
}
