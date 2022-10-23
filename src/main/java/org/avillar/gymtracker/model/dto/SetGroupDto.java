package org.avillar.gymtracker.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class SetGroupDto {
    private Long id;
    private String description;
    private int listOrder;
    private Long idExercise; //TODO Susituir esto por exerciseDto
    private Long idSession;
    private ExerciseDto exerciseDto;
    private List<SetDto> setDtoList;
}
