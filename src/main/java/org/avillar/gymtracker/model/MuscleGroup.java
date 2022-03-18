package org.avillar.gymtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MuscleGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "muscleGroup", orphanRemoval = true)
    private List<MuscleSubGroup> muscleSubGroups = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "muscleGroups")
    private List<Exercise> exercises = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MuscleGroup that = (MuscleGroup) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MuscleGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", muscleSubGroups=" + muscleSubGroups +
                ", exercises=" + exercises +
                '}';
    }
}
