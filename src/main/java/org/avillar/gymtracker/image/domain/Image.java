package org.avillar.gymtracker.image.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.domain.BaseEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Image extends BaseEntity {

    @Column
    private Byte[] image;
}
