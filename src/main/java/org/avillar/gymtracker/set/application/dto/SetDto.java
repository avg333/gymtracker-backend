package org.avillar.gymtracker.set.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.application.BaseDto;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupDto;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SetDto extends BaseDto {
    private int listOrder;
    private String description;
    private int reps;
    private double rir;
    private double weight;

    private SetGroupDto setGroup;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date lastModifiedAt;

    public SetDto(Long id) {
        super((id));
    }

}