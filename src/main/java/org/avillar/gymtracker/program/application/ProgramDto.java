package org.avillar.gymtracker.program.application;

import lombok.Data;
import org.avillar.gymtracker.enums.domain.ProgramLevelEnum;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProgramDto {
    private Long id;
    private String name;
    private String description;
    private String url;
    private ProgramLevelEnum level = ProgramLevelEnum.ANY;
    private Boolean favourite = false;
    private Long userAppId;
    private MultipartFile image;
    //sessions

    private int sessionNumber;
    private int averageVolumePerMuscleGroup;
    private int averageVolumePerSession;
    private int averageExercisesNumberPerSession;
    private String ownerName;

}