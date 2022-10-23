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
    private ProgramLevelEnum level;
    //userAppId
    //sessions
    private MultipartFile image;

    private int sessionNumber;
}