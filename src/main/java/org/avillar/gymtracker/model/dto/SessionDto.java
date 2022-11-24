package org.avillar.gymtracker.model.dto;

import lombok.Data;

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