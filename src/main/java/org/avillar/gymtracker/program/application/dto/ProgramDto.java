package org.avillar.gymtracker.program.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.application.BaseDto;
import org.avillar.gymtracker.enums.domain.ProgramLevelEnum;

@Getter
@Setter
@NoArgsConstructor
public class ProgramDto extends BaseDto {

    private String name;
    private String description;
    private String url;
    private ProgramLevelEnum level = ProgramLevelEnum.ANY;
    private Boolean favourite = false;
    private Long userAppId;
    private int sessionNumber;
    //sessions
    private int averageVolumePerMuscleGroup;
    private int averageVolumePerSession;
    private int averageExercisesNumberPerSession;
    private String ownerName;

    public ProgramDto(Long id) {
        super(id);
    }


}