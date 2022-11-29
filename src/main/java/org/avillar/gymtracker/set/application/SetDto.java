package org.avillar.gymtracker.set.application;

import lombok.Data;

import java.util.Date;


@Data
public class SetDto {
    private Long id;
    private String description;
    private int listOrder;
    private int reps;
    private double rir;
    private double weight;
    private Long setGroupId;

    private Date lastModifiedAt;
}