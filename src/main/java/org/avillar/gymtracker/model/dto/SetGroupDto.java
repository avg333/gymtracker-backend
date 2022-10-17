package org.avillar.gymtracker.model.dto;

import lombok.Data;

@Data
public class SetGroupDto {
    private Long id;
    private String description;
    private int setGroupOrder;
    private Long idExercise;
    private Long idSession;
}
