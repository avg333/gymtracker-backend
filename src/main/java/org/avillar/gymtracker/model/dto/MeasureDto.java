package org.avillar.gymtracker.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MeasureDto {

    private Long id;
    private Date date;
    private double height;
    private double weight;
    private double fatPercent;
}
