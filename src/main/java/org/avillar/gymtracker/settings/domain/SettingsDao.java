package org.avillar.gymtracker.settings.domain;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SettingsDao extends JpaRepository<Settings, Long> {

}