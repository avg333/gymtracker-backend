package org.avillar.gymtracker.usersapi.common.adapter.repository;

import java.util.Optional;
import java.util.UUID;
import org.avillar.gymtracker.usersapi.common.adapter.repository.model.SettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SettingsDao extends JpaRepository<SettingsEntity, UUID> {

  @Query("""
      SELECT s FROM SettingsEntity s
      WHERE s.userId = :userId
      """)
  Optional<SettingsEntity> findByUserId(@Param("userId") UUID userId);
}
