package org.avillar.gymtracker.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class SessionDto {
    @Positive
    private Long id;
    @NotBlank
    @Size(min = 5, max = 255)
    private String name;
    @Size(max = 255)
    private String description;
    @Positive
    private Long idProgram;
    private int sessionOrder;

    private int exercisesNumber;
    private int setsNumber;

    private Date createdAt;
}
