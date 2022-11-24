package org.avillar.gymtracker.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class WorkoutDto {
    private Long id;
    private Date date;
    private String description;
    private Long userId;
}
