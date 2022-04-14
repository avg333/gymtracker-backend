package org.avillar.gymtracker.api.dto;

import lombok.Data;
import org.avillar.gymtracker.model.enums.ProgramLevelEnum;

import java.util.Date;

@Data
public class ProgramDto {
    private Long id;
    private String name;
    private String description;
    private String url;
    private ProgramLevelEnum level;
    private Date createdAt;
}
