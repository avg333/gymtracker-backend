package org.avillar.gymtracker.session.application;

import lombok.Data;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupDto;

import java.util.Date;
import java.util.List;

@Data
public class SessionDto {
    private Long id;
    private String name;
    private String description;
    private int listOrder;
    private Date date;
    private Long userAppId;
    private Long programId;

    private List<SetGroupDto> setGroupDtoList;

    private int exercisesNumber;
    private int setsNumber;
}