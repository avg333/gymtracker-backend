package org.avillar.gymtracker.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class SetGroupDto {
    private Long id;
    private String description;
    private int listOrder;
    private Long exerciseId; //TODO Susituir esto por exerciseDto
    private Long sessionId;

    private ExerciseDto exerciseDto;
    private List<SetDto> setDtoList;
}
