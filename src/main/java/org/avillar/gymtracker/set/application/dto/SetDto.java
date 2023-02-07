package org.avillar.gymtracker.set.application.dto;

import lombok.Data;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupDto;

import java.util.Date;


@Data
public class SetDto {
    private Long id;
    private int listOrder;
    private String description;
    private int reps;
    private double rir;
    private double weight;

    private SetGroupDto setGroup;

    private Date lastModifiedAt;
}