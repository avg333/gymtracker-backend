package org.avillar.gymtracker.image.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.domain.BaseEntity;


import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Image extends BaseEntity {

    @Column
    private Byte[] image;
}
