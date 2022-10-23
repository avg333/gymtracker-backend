package org.avillar.gymtracker.model.dao;

import org.avillar.gymtracker.model.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserApp, Long> {

    UserApp findByUsername(String username);
}