package org.avillar.gymtracker.api.dto;

import lombok.Data;

@Data
public class SessionDto {
    private Long id;
    private String name;
    private String description;
    private int sessionOrder;
}
