package org.avillar.gymtracker.session.application.dto;

import lombok.Data;
import org.avillar.gymtracker.program.application.ProgramDto;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupDto;

import java.time.DayOfWeek;
import java.util.List;

@Data
public class SessionDto {
    private Long id;
    private String name;
    private String description;
    private DayOfWeek dayOfWeek;
    private int listOrder;

    private ProgramDto programDto;

    private List<SetGroupDto> setGroups;

    private int exercisesNumber;
    private int setsNumber;
}