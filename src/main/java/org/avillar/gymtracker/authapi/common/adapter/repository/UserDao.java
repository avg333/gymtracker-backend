package org.avillar.gymtracker.authapi.common.adapter.repository;

import java.util.UUID;
import org.avillar.gymtracker.authapi.common.adapter.repository.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity, UUID> {

  UserEntity findByUsername(String username);
}
