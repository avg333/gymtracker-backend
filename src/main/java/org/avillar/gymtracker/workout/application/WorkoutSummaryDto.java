package org.avillar.gymtracker.workout.application;

import lombok.Data;

import java.util.List;

@Data
public class WorkoutSummaryDto {
    private Integer exerciseNumber;
    private Integer setsNumber;
    private Integer weightVolume;
    private Integer duration;
    private List<String> muscles;
}
