package org.avillar.gymtracker.model.dto;

import lombok.Data;
import org.avillar.gymtracker.model.enums.ProgramLevelEnum;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProgramDto {
    private Long id;
    private String name;
    private String description;
    private String url;
    private ProgramLevelEnum level = ProgramLevelEnum.ANY;
    private Boolean favourite = false;
    //userAppId
    //sessions
    private MultipartFile image;

    private int sessionNumber;
}