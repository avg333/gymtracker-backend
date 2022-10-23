package org.avillar.gymtracker.model.dao;

import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.model.entities.Session;
import org.avillar.gymtracker.model.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface SessionDao extends JpaRepository<Session, Long> {

    List<Session> findByProgramOrderByListOrder(Program program);

    List<Session> findByUserAppAndProgramIsNullOrderByDateDesc(UserApp userApp);

    List<Session> findByDateAndUserAppAndProgramIsNullOrderByDateDesc(Date date, UserApp userApp);
}