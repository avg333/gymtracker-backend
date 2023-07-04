package org.avillar.gymtracker.authapi.domain;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserApp, UUID> {

  UserApp findByUsername(String username);
}