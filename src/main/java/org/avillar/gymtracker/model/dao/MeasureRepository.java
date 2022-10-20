package org.avillar.gymtracker.model.dao;

import org.avillar.gymtracker.model.entities.Measure;
import org.avillar.gymtracker.model.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasureRepository extends JpaRepository<Measure, Long> {

    List<Measure> findByUserAppOrderByDateDesc(UserApp userApp);
}
