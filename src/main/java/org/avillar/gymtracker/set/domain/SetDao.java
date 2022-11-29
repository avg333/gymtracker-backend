package org.avillar.gymtracker.set.domain;

import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetDao extends JpaRepository<Set, Long> {

    List<Set> findBySetGroupOrderByListOrderAsc(SetGroup setGroup);
}