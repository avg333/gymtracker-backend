package org.avillar.gymtracker.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SessionDto {
    private Long id;
    private String name;
    private String description;
    private int sessionOrder;
    private Date createdAt;
    private int exercisesNumber;
    private int setsNumber;
}
