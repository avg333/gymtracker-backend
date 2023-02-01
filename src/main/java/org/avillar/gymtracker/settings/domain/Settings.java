package org.avillar.gymtracker.settings.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.domain.BaseEntity;
import org.avillar.gymtracker.enums.domain.WeightUnitEnum;
import org.avillar.gymtracker.user.domain.UserApp;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Settings extends BaseEntity {

    @Column
    private WeightUnitEnum weightUnit;

    @Column
    private Double selectedIncrement;
    @Column
    private Double selectedBar;
    @Column
    private String selectedPlates;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "user_app_id", nullable = false, unique = true)
    private UserApp userApp;

}
