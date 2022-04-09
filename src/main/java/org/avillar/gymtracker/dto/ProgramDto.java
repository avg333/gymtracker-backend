package org.avillar.gymtracker.dto;

import lombok.Data;

@Data
public class ProgramDto {
    private Long id;
    private String name;
    private String description;
    private String level;
}
