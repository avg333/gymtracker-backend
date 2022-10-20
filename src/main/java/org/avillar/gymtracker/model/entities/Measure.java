package org.avillar.gymtracker.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Measure extends BaseEntity {

    @Column(nullable = false)
    private Date date;
    private double height;
    private double weight;
    private double fatPercent;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_app_id", nullable = false)
    private UserApp userApp;

}
