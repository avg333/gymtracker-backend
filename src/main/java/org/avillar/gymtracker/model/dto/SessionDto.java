package org.avillar.gymtracker.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SessionDto {
    private Long id;
    private String name;
    private String description;
    private int listOrder;
    private Date date;
    private Long userAppId;
    private Long programId;
    //setGroups

    private int exercisesNumber;
    private int setsNumber;
}