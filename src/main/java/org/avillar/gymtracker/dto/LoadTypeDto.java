package org.avillar.gymtracker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.model.Exercise;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LoadTypeDto {

    private Long id;
    private String name;
    private String description;
    private List<ExerciseDto> exercises = new ArrayList<>();
}
