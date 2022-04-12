package org.avillar.gymtracker.api.dto;

import lombok.Data;
import org.avillar.gymtracker.model.enums.ProgramLevelEnum;

@Data
public class ProgramDto {
    private Long id;
    private String name;
    private String description;
    private String url;
    private ProgramLevelEnum level;
}
