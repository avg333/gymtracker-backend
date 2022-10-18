package org.avillar.gymtracker.model.dto;

import lombok.Data;
import org.avillar.gymtracker.model.enums.ProgramLevelEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class ProgramDto {
    @Positive
    private Long id;
    @NotBlank
    @Size(min = 5, max = 255)
    private String name;
    @Size(max = 255)
    private String description;
    @Size(min = 5, max = 255)
    private String url;
    @NotNull
    private ProgramLevelEnum level;

    private int sessionNumber;
    private Date createdAt;
}
