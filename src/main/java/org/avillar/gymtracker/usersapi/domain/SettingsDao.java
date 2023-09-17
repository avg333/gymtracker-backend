package org.avillar.gymtracker.usersapi.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SettingsDao extends JpaRepository<Settings, UUID> {

  @Query("""
      select s from Settings s
      where s.userId = :userId
      """)
  List<Settings> findByUserId(@Param("userId") UUID userId);
}
