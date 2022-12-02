package org.avillar.gymtracker.measure.application;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class MeasureDto {
    private Long id;
    private Date date;
    private String comment;
    private double height;
    private double weight;
    private double fatPercent;
    private MultipartFile image;
}