package org.avillar.gymtracker.model.dao;

import org.avillar.gymtracker.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}