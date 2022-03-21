package org.avillar.gymtracker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class MuscleGroupDto{

    private Long id;
    private String name;
    private String description;
}
