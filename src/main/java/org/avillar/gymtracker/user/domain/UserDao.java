package org.avillar.gymtracker.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserApp, Long> {

    UserApp findByUsername(String username);
}