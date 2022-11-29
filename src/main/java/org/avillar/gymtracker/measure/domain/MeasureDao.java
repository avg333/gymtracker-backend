package org.avillar.gymtracker.measure.domain;

import org.avillar.gymtracker.user.domain.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasureDao extends JpaRepository<Measure, Long> {

    List<Measure> findByUserAppOrderByDateDesc(UserApp userApp);
}
