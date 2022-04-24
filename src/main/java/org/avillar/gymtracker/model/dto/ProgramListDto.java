package org.avillar.gymtracker.model.dto;

import lombok.Data;
import org.avillar.gymtracker.model.enums.ProgramLevelEnum;

@Data
public class ProgramListDto {
    private Long id;
    private String name;
    private ProgramLevelEnum level;
    private int sessionNumber;

}
