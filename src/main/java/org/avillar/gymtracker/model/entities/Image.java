package org.avillar.gymtracker.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Image  extends BaseEntity{

    @Column
    private Byte[] image;
}
