package org.avillar.gymtracker.session.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.application.BaseDto;
import org.avillar.gymtracker.program.application.dto.ProgramDto;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupDto;

import java.time.DayOfWeek;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SessionDto extends BaseDto {
    private String name;
    private String description;
    private DayOfWeek dayOfWeek;
    private int listOrder;

    private ProgramDto programDto;

    private List<SetGroupDto> setGroups;

    private int exercisesNumber;
    private int setsNumber;

    public SessionDto(Long id) {
        super(id);
    }
}